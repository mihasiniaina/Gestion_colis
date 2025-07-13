package org.example.projetjavafx.ImplementationDAO;

import org.example.projetjavafx.DAO.EnvoyerDAO;
import org.example.projetjavafx.Model.Envoyer;
import org.example.projetjavafx.Model.Voiture;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class EnvoyerImpl implements EnvoyerDAO {
    private SessionFactory sessionFactory;

    public EnvoyerImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public String ajouterEnvoi(String idvoit, String colis, String nomEnvoyeur, String emailEnvoyeur,
                               LocalDateTime date_envoi, String nomRecepteur, String contactRecepteur)
    {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();

            Voiture v = session.get(Voiture.class, idvoit);
            int frais = v.getItineraire().getFrais();

            Envoyer e = new Envoyer(v, colis, nomEnvoyeur, emailEnvoyeur, date_envoi, frais, nomRecepteur,
                    contactRecepteur);

            session.persist(v);
            tx.commit();

            return "Ajout réussi";

        }catch (Exception e){

            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
            return null;

        }
    }

    @Override
    public List<Envoyer> listerEnvoi() {
        try(Session session = sessionFactory.openSession()){

            Query<Envoyer> query = session.createQuery("FROM Envoyer ", Envoyer.class);

            return query.list();

        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String modifierEnvoi(int idenvoi, String idvoit, String colis, String nomEnvoyeur, String emailEnvoyeur,
                                LocalDateTime date_envoi, String nomRecepteur, String contactRecepteur)
    {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();

            Voiture v = session.get(Voiture.class, idvoit);

            Envoyer e = session.get(Envoyer.class, idenvoi);
            e.setColis(colis);
            e.setNomEnvoyeur(nomEnvoyeur);
            e.setEmailEnvoyeur(emailEnvoyeur);
            e.setDate_envoi(date_envoi);
            e.setNomRecepteur(nomRecepteur);
            e.setContactRecepteur(contactRecepteur);

            session.merge(v);

            tx.commit();

            return "Modification réussie";

        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String supprimerEnvoi(int idenvoi) {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();
            Envoyer e = session.get(Envoyer.class, idenvoi);

            session.remove(e);
            tx.commit();

            return "Suppression réussie";

        }catch (Exception e){
            return null;
        }
    }
}
