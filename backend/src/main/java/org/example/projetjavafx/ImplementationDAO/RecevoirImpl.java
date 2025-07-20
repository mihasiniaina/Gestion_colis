package org.example.projetjavafx.ImplementationDAO;

import org.example.projetjavafx.DAO.RecevoirDAO;
import org.example.projetjavafx.Model.Envoyer;
import org.example.projetjavafx.Model.Recevoir;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class RecevoirImpl implements RecevoirDAO {
    private SessionFactory sessionFactory;
    private final String exp = "fitahianarabeharison86@gmail.com";
    private final String mdp = "chon sqvy twxx ipkn";
    private final String sujet = "Réception de colis";

    public RecevoirImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void envoyerMail(int idenvoi, LocalDateTime date_recept) {

        try(Session sessionBD = sessionFactory.openSession()){

            Envoyer e = sessionBD.get(Envoyer.class, idenvoi);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dateFormatee = date_recept.format(formatter);

            String emailEnvoyeur = e.getEmailEnvoyeur();
            String recepteur = e.getNomRecepteur();
            String colis = e.getColis();


            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            jakarta.mail.Session session = jakarta.mail.Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(exp, mdp);
                }
            });
            try {
                // Création du message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(exp));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailEnvoyeur));
                message.setSubject(sujet);
                String contenu = Files.readString(Path.of("backend/src/main/resources/email.html"));
                contenu = contenu.replace("{{date}}", dateFormatee).replace("{{recepteur}}", recepteur)
                        .replace("{{colis}}", colis);

                message.setContent(contenu, "text/html; charset=UTF-8");

                // Envoi
                Transport.send(message);
                System.out.println("E-mail envoyé avec succès !");
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }

        }catch (Exception e){

            System.err.println("Erreur lors de l'envoi : " + e.getMessage());

        }
    }

    @Override
    public String ajouterRecu(int idenvoi, LocalDateTime date_recept) {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();

            Envoyer e = session.get(Envoyer.class, idenvoi);

            Recevoir r = new Recevoir(e, date_recept);

            session.persist(r);
            tx.commit();

            envoyerMail(idenvoi, date_recept);

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
