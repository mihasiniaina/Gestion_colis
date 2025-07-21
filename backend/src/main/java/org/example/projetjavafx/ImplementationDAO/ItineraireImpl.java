package org.example.projetjavafx.ImplementationDAO;

import jakarta.persistence.PersistenceException;
import org.example.projetjavafx.DAO.ItineraireDAO;
import org.example.projetjavafx.Model.Envoyer;
import org.example.projetjavafx.Model.Itineraire;
import org.example.projetjavafx.Model.Recevoir;
import org.example.projetjavafx.Model.Voiture;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ItineraireImpl implements ItineraireDAO {
    private SessionFactory sessionFactory;

    public  ItineraireImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String newId(){
        try(Session session = sessionFactory.openSession()){

            String sql = "SELECT codeit FROM itineraire ORDER BY CAST(SUBSTRING(codeit from 3) AS INTEGER) " +
                    "DESC LIMIT 1 ";

            try{
                String dernierId = session.createNativeQuery(sql, String.class).getSingleResult();

                int num = Integer.parseInt(dernierId.substring(2));

                return "It" + ( num + 1 );
            }catch (PersistenceException e ){
                return "It1";
            }
        }
    }

    @Override
    public Boolean checkIt(String villedep, String villearr) {
        try(Session session = sessionFactory.openSession()){

            String hql ="SELECT i.codeit from Itineraire i where i.villedep = :villedep and i.villearr = :villearr";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("villedep", villedep);
            query.setParameter("villearr", villearr);

            List<String> res = query.getResultList();

            return res.isEmpty();


        }catch (Exception e){
            e.printStackTrace(); // Ajoutez au moins ça pour debug
            return false;  // Ne jamais retourner null
        }
    }


    @Override
    public Boolean ajouterItineraire(String villedep, String villearr, int frais){

        Boolean check = checkIt(villedep, villearr);

        if (check == true){
            String codeit = newId();

            Itineraire i = new Itineraire(codeit, villedep, villearr, frais);

            try(Session session = sessionFactory.openSession()){

                Transaction tx = session.beginTransaction();

                session.persist(i);
                tx.commit();

                return true;

            }catch (Exception e){

                System.err.println("Erreur lors de l'ajout : " + e.getMessage());
                return false;

            }
        }else{
            return false;
        }
    }

    @Override
    public List<Itineraire> listerItineraire() {
        try(Session session = sessionFactory.openSession()){

            Query<Itineraire> query = session.createQuery("FROM Itineraire", Itineraire.class);

            return query.list();

        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Boolean modifierItineraire(String codeit, String villedep, String villearr, int frais) {
        try(Session session = sessionFactory.openSession()){

            String hql ="SELECT i.codeit from Itineraire i where i.villedep = :villedep and i.villearr = :villearr and i.codeit != :codeit";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("villedep", villedep);
            query.setParameter("villearr", villearr);
            query.setParameter("codeit", codeit);

            List<String> res = query.getResultList();
            Boolean notFindDouble = res.isEmpty();

        if (notFindDouble){
            Transaction tx = session.beginTransaction();

            Itineraire i = session.get(Itineraire.class, codeit);
            i.setVilledep(villedep);
            i.setVillearr(villearr);
            i.setFrais(frais);

            session.merge(i);
            tx.commit();

            return true;
        }else {
            return false;
        }

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String supprimerItineraire(String codeit) {
        Transaction tx =null;
        try(Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();

            Itineraire itineraire = session.get(Itineraire.class, codeit);

            // Charger les voitures liées à cet itinéraire
            List<Voiture> voitures = session.createQuery(
                            "FROM Voiture WHERE itineraire = :it", Voiture.class)
                    .setParameter("it", itineraire)
                    .getResultList();

            for (Voiture v : voitures) {

                // Charger les envois liés à cette voiture
                List<Envoyer> envois = session.createQuery(
                                "FROM Envoyer WHERE voiture = :v and arrived = false", Envoyer.class)
                        .setParameter("v", v)
                        .getResultList();

                for (Envoyer e : envois) {
                    // Supprimer le recevoir lié s’il existe
                    Recevoir r = e.getRecevoir();
                    if (r != null) {
                        session.remove(r);
                    }
                    session.remove(e); // supprimer l'envoi
                }

                session.remove(v); // supprimer la voiture
            }

            session.remove(itineraire); // enfin, supprimer l’itinéraire

            tx.commit();
            return "Supprésion réussie";
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return null;
        }
    }

}
