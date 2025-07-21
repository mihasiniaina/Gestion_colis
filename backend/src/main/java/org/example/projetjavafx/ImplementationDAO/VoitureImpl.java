package org.example.projetjavafx.ImplementationDAO;

import jakarta.persistence.PersistenceException;
import org.example.projetjavafx.DAO.VoitureDAO;
import org.example.projetjavafx.Model.Envoyer;
import org.example.projetjavafx.Model.Itineraire;
import org.example.projetjavafx.Model.Recevoir;
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
    public Boolean ajouterVoiture(String idvoit, String design, String codeit) {

        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();

            Itineraire i = session.get(Itineraire.class, codeit);

            Voiture v = new Voiture(idvoit, design, i);

            session.persist(v);
            tx.commit();

            return true;

        }catch (Exception e){

            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
            return false;

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

            session.remove(v);
            tx.commit();

            return "Suppression réussie";

        }catch (Exception e){
            return null;
        }
    }
}
