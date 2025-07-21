package org.example.projetjavafx.ImplementationDAO;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.example.projetjavafx.DAO.EnvoyerDAO;
import org.example.projetjavafx.Model.Envoyer;
import org.example.projetjavafx.Model.Voiture;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class EnvoyerImpl implements EnvoyerDAO {
    private SessionFactory sessionFactory;

    public EnvoyerImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public int ajouterEnvoi(String idvoit, String colis, String nomEnvoyeur, String emailEnvoyeur,
                               LocalDateTime date_envoi, String nomRecepteur, String contactRecepteur)
    {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();

            Voiture v = session.get(Voiture.class, idvoit);
            int frais = v.getItineraire().getFrais();

            Envoyer e = new Envoyer(v, colis, nomEnvoyeur, emailEnvoyeur, date_envoi, frais, nomRecepteur,
                    contactRecepteur);


            session.persist(e);
            tx.commit();
            System.err.println(e.getIdenvoi());
            return e.getIdenvoi();

        }catch (Exception e){

            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
            return 0;

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
    public Boolean modifierEnvoi(int idenvoi, String colis, String nomEnvoyeur, String emailEnvoyeur,
                                LocalDateTime date_envoi, String nomRecepteur, String contactRecepteur)
    {
        try(Session session = sessionFactory.openSession()){

            Transaction tx = session.beginTransaction();

            Envoyer e = session.get(Envoyer.class, idenvoi);
            e.setColis(colis);
            e.setNomEnvoyeur(nomEnvoyeur);
            e.setEmailEnvoyeur(emailEnvoyeur);
            e.setDate_envoi(date_envoi);
            e.setNomRecepteur(nomRecepteur);
            e.setContactRecepteur(contactRecepteur);

            session.merge(e);

            tx.commit();

            return true;

        }catch (Exception e){
            return false;
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

    @Override
    public void genererPDF(int idenvoi) {
        try (Session session = sessionFactory.openSession()){
            Envoyer en = session.get(Envoyer.class, idenvoi);
            try {
                // Lire le HTML depuis le fichier dans les ressources
                InputStream inputStream = RecevoirImpl.class.getResourceAsStream("/PDF.html");

                if (inputStream == null) {
                    throw new IllegalArgumentException("Fichier non trouvé dans les ressources !");
                }

                String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                LocalDateTime date = en.getDate_envoi();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy 'à' HH:mm:ss", Locale.FRANCE);
                String formatee = date.format(formatter);
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRANCE);
                symbols.setGroupingSeparator(' '); // Séparateur = espace

                DecimalFormat Intformatter = new DecimalFormat("#,###", symbols);

                html = html.replace("${idenvoi}", String.valueOf(idenvoi));
                html = html.replace("${date}", formatee);
                html = html.replace("${expediteur}", en.getNomEnvoyeur());
                html = html.replace("${villedep}", en.getVoiture().getItineraire().getVilledep());
                html = html.replace("${villearr}", en.getVoiture().getItineraire().getVillearr());
                html = html.replace("${colis}", en.getColis());
                html = html.replace("${frais}", Intformatter.format(en.getFrais()));
                html = html.replace("${recepteur}", en.getNomRecepteur());
                html = html.replace("${contact}", en.getContactRecepteur());
                html = html.replace("${voiture}", en.getVoiture().getIdvoit());

                String nomFichier = "Reçu_N°_" + idenvoi+".pdf";



                // Générer le PDF
                try (OutputStream os = new FileOutputStream(nomFichier)) {
                    PdfRendererBuilder builder = new PdfRendererBuilder();
                    builder.useFastMode();
                    builder.withHtmlContent(html, null);
                    builder.toStream(os);
                    builder.useDefaultPageSize(105.5f, 105.8f, PdfRendererBuilder.PageSizeUnits.MM);
                    builder.run();
                }

                System.out.println("PDF généré avec succès ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    @Override
    public List<Envoyer> trierColis(LocalDate debut, LocalDate fin) {
        LocalDateTime deb = debut.atTime(LocalTime.MIDNIGHT);
        LocalDateTime fn = fin.atTime(LocalTime.MIDNIGHT);

        try(Session session = sessionFactory.openSession()){

            Query<Envoyer> query = session.createQuery("FROM Envoyer WHERE date_envoi BETWEEN :deb AND :fn", Envoyer.class);
            query.setParameter("deb", deb);
            query.setParameter("fn", fn);

            return query.list();

        }catch (Exception e){
            return null;
        }
    }
}
