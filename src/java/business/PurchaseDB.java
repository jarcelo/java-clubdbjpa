
package business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author josepharcelo
 */
public class PurchaseDB
{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static List<Purchase> getPurchases(String memid) {
          EntityManager em = DBUtil.getEmFactory().createEntityManager();
          //JPA query
          String qS = "SELECT p FROM Purchase p " + 
                  "WHERE p.memid = :memid " +
                  "ORDER BY p.purchdt";
          TypedQuery<Purchase> q = em.createQuery(qS, Purchase.class);
          q.setParameter("memid", memid);
          List<Purchase> p = null;
          try {
            p = q.getResultList();
              if (p == null || p.isEmpty()) {
                  p = null;
              }
        } catch (NoResultException e) {
            p = null;
        } finally {
              em.close();
        }
        return p;
    }

    public static List<Purchase> getPurchases(String memid, Date pd)
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        //JPA query; java persistence query language JPQL
        String qS = "SELECT p FROM Purchase p " + 
                "WHERE p.memid = :memid AND p.purchdt >= :pd " +
                "ORDER BY p.purchdt";
        TypedQuery<Purchase> q = em.createQuery(qS, Purchase.class);
        q.setParameter("memid", memid);
        q.setParameter("pd", pd);
        List<Purchase> p = null;
        try {
          p = q.getResultList();
            if (p == null || p.isEmpty()) {
                p = null;
            }
        } catch (NoResultException e) {
            p = null;
        } finally {
              em.close();
        }
        return p;
    }
    
    public static double getBalanceDue(String memid, Date pd) {
        return getDebitTotal(memid, pd) - getCreditTotal(memid, pd);
    }
    
    private static double getCreditTotal(String memid, Date pd) {
        EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
//        String creditQueryString = "SELECT SUM(p.amt) from Purchase p " +
//                "WHERE p.memid = :memid AND p.transtype = 'C'";
        String sqlWhereForDate = "";
        if (pd != null) {
            sqlWhereForDate = " AND p.purchdt >= :pd";
        }
        String creditQueryString = "SELECT SUM(p.amt) from Purchase p " +
                "WHERE p.memid = :memid AND p.transtype = 'C'" + sqlWhereForDate;
        Query creditQuery = entityManager.createQuery(creditQueryString, Purchase.class);
        creditQuery.setParameter("memid", memid);
        if (pd != null) {
            creditQuery.setParameter("pd", pd);
        }
        double creditAmount = 0;
        try {
            Object creditQueryResult = (Object)creditQuery.getSingleResult();
            creditAmount = Double.parseDouble(creditQueryResult.toString());
        } catch (NoResultException e) {
            creditAmount = 0;
        } finally {
            entityManager.close();
        }
        return creditAmount;
    }
    
    private static double getDebitTotal(String memid, Date pd) {
        EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
        String sqlWhereForDate = "";
        if (pd != null) {
            sqlWhereForDate = " AND p.purchdt >= :pd ";
        }
        String debitQueryString = "SELECT SUM(p.amt) from Purchase p " +
                "WHERE p.memid = :memid AND p.transtype = 'D'" + sqlWhereForDate;
        Query debitQuery = entityManager.createQuery(debitQueryString, Purchase.class);
        debitQuery.setParameter("memid", memid);
        if (pd != null) {
            debitQuery.setParameter("pd", pd);
        }
        double debitAmount = 0;
        try {
            Object debitQueryResult = (Object)debitQuery.getSingleResult();
            debitAmount = Double.parseDouble(debitQueryResult.toString());
        } catch (NoResultException e) {
            debitAmount = 0;
        } finally {
            entityManager.close();
        }
        return debitAmount;
    }
}
