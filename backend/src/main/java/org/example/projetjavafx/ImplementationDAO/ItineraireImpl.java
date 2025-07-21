package org.example.projetjavafx.ImplementationDAO;

import jakarta.persistence.PersistenceException;
import org.example.projetjavafx.DAO.ItineraireDAO;
import org.example.projetjavafx.Model.Itineraire;
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

            Transaction tx = session.beginTransaction();

            Itineraire i = session.get(Itineraire.class, codeit);
            i.setVilledep(villedep);
            i.setVillearr(villearr);
            i.setFrais(frais);

            session.merge(i);
            tx.commit();

            return true;

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String supprimerItineraire(String codeit) {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();
            Itineraire i = session.get(Itineraire.class, codeit);

            session.remove(i);
            tx.commit();

            return "Suppression réussie";

        }catch (Exception e){
            return null;
        }
    }

}
