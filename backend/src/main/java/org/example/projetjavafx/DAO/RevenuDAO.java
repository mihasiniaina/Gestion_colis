package org.example.projetjavafx.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.projetjavafx.Model.Envoyer;
import org.example.projetjavafx.util.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class RevenuDAO {

    public int getRevenuMensuel() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            LocalDateTime debut = LocalDate.now().withDayOfMonth(1).atStartOfDay();
            LocalDateTime fin = LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay();

            TypedQuery<Long> query = em.createQuery(
                    "SELECT SUM(e.frais) FROM Envoyer e WHERE e.date_envoi >= :debut AND e.date_envoi < :fin", Long.class);
            query.setParameter("debut", debut);
            query.setParameter("fin", fin);

            Long result = query.getSingleResult();
            return result != null ? result.intValue() : 0;
        } finally {
            em.close();
        }
    }

    public int getRevenuAnnuel() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            LocalDateTime debut = LocalDate.now().withDayOfYear(1).atStartOfDay();
            LocalDateTime fin = debut.plusYears(1);

            TypedQuery<Long> query = em.createQuery(
                    "SELECT SUM(e.frais) FROM Envoyer e WHERE e.date_envoi >= :debut AND e.date_envoi < :fin", Long.class);
            query.setParameter("debut", debut);
            query.setParameter("fin", fin);

            Long result = query.getSingleResult();
            return result != null ? result.intValue() : 0;
        } finally {
            em.close();
        }
    }

    public int getRevenuTotal() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT SUM(e.frais) FROM Envoyer e", Long.class);
            Long result = query.getSingleResult();
            return result != null ? result.intValue() : 0;
        } finally {
            em.close();
        }
    }

    public Map<String, Integer> getRevenus6DerniersMois() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            Map<String, Integer> revenus = new LinkedHashMap<>();

            for (int i = 5; i >= 0; i--) {
                LocalDate firstDay = LocalDate.now().minusMonths(i).withDayOfMonth(1);
                LocalDateTime debut = firstDay.atStartOfDay();
                LocalDateTime fin = debut.plusMonths(1);

                TypedQuery<Long> query = em.createQuery(
                        "SELECT SUM(e.frais) FROM Envoyer e WHERE e.date_envoi >= :debut AND e.date_envoi < :fin", Long.class);
                query.setParameter("debut", debut);
                query.setParameter("fin", fin);
                Long result = query.getSingleResult();

                String mois = firstDay.getMonth().toString().substring(0, 3);
                revenus.put(mois, result != null ? result.intValue() : 0);
            }

            return revenus;
        } finally {
            em.close();
        }
    }
}
