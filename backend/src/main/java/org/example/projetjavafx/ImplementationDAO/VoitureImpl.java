package org.example.projetjavafx.ImplementationDAO;

import jakarta.persistence.PersistenceException;
import org.example.projetjavafx.DAO.VoitureDAO;
import org.example.projetjavafx.Model.Itineraire;
import org.example.projetjavafx.Model.Voiture;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class VoitureImpl implements VoitureDAO {
    private SessionFactory sessionFactory;

    public VoitureImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    @Override
    public String newId() {
        try(Session session = sessionFactory.openSession()){

            String sql = "SELECT idvoit FROM voiture ORDER BY CAST(SUBSTRING(idvoit from 2) AS INTEGER) " +
                    "DESC LIMIT 1 ";

            try{
                String dernierId = session.createNativeQuery(sql, String.class).getSingleResult();

                int num = Integer.parseInt(dernierId.substring(1));

                return "V" + ( num + 1 );
            }catch (PersistenceException e ){
                return "V1";
            }
        }
    }

    @Override
    public String ajouterVoiture(String design, String codeit) {

        String idvoit = newId();
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();

            Itineraire i = session.get(Itineraire.class, codeit);

            Voiture v = new Voiture(idvoit, design, i);

            session.persist(v);
            tx.commit();

            return "Ajout réussi";

        }catch (Exception e){

            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
            return null;

        }
    }

    @Override
    public List<Voiture> listerVoiture() {
        try(Session session = sessionFactory.openSession()){

            Query<Voiture> query = session.createQuery("FROM Voiture ", Voiture.class);

            return query.list();

        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String modifierVoiture(String idvoit, String design, String codeit) {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();

            Itineraire i = session.get(Itineraire.class, codeit);

            Voiture v = session.get(Voiture.class, idvoit);
            v.setDesign(design);
            v.setItineraire(i);

            session.merge(v);

            tx.commit();

            return "Modification réussie";

        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String supprimerVoiture(String idvoit) {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();
            Voiture v = session.get(Voiture.class, idvoit);

            session.remove(v);
            tx.commit();

            return "Suppression réussie";

        }catch (Exception e){
            return null;
        }
    }
}
