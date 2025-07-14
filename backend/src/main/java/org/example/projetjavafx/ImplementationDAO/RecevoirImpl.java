package org.example.projetjavafx.ImplementationDAO;

import org.example.projetjavafx.DAO.RecevoirDAO;
import org.example.projetjavafx.Model.Envoyer;
import org.example.projetjavafx.Model.Recevoir;
import org.example.projetjavafx.Model.Voiture;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class RecevoirImpl implements RecevoirDAO {
    private SessionFactory sessionFactory;

    public RecevoirImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String ajouterRecu(int idenvoi, LocalDateTime date_recept) {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();

            Envoyer e = session.get(Envoyer.class, idenvoi);

            Recevoir r = new Recevoir(e, date_recept);

            session.persist(r);
            tx.commit();

            return "Ajout réussi";

        }catch (Exception e){

            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
            return null;

        }
    }

    @Override
    public List<Recevoir> listerRecu() {
        try(Session session = sessionFactory.openSession()){

            Query<Recevoir> query = session.createQuery("FROM Recevoir ", Recevoir.class);

            return query.list();

        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String modifierRecu(int idrecept, LocalDateTime date_recept) {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();

            Recevoir r = session.get(Recevoir.class, idrecept);
            r.setDate_recept(date_recept);

            session.merge(r);

            tx.commit();

            return "Modification réussie";

        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String supprimerRecu(int idrecept) {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();
            Recevoir r = session.get(Recevoir.class, idrecept);

            session.remove(r);
            tx.commit();

            return "Suppression réussie";

        }catch (Exception e){
            return null;
        }
    }
}
