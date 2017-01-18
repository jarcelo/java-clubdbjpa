
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
    public static List<Purchase> getPurchases(String memberId) {
          EntityManager em = DBUtil.getEmFactory().createEntityManager();
          //JPA query
          String query = "SELECT p FROM Purchase p " + 
                  "WHERE p.memberId = :memid " +
                  "ORDER BY p.purchaseDate";
          TypedQuery<Purchase> q = em.createQuery(query, Purchase.class);
          q.setParameter("memid", memberId);
          List<Purchase> purchases = null;
          try {
            purchases = q.getResultList();
              if (purchases == null || purchases.isEmpty()) {
                  purchases = null;
              }
        } catch (NoResultException e) {
            purchases = null;
        } finally {
              em.close();
        }
        return purchases;
    }

    public static List<Purchase> getPurchases(String memberId, Date purchaseDate)
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        //JPA query; java persistence query language JPQL
        String query = "SELECT p FROM Purchase p " + 
                "WHERE p.memberId = :memid AND p.purchaseDate >= :pd " +
                "ORDER BY p.purchaseDate";
        TypedQuery<Purchase> q = em.createQuery(query, Purchase.class);
        q.setParameter("memid", memberId);
        q.setParameter("pd", purchaseDate);
        List<Purchase> purchases = null;
        try {
          purchases = q.getResultList();
            if (purchases == null || purchases.isEmpty()) {
                purchases = null;
            }
        } catch (NoResultException e) {
            purchases = null;
        } finally {
              em.close();
        }
        return purchases;
    }
    
    public static double getBalanceDue(String memberId, Date purchaseDate) {
        return getDebitTotal(memberId, purchaseDate) - getCreditTotal(memberId, purchaseDate);
    }
    
    private static double getCreditTotal(String memberId, Date purchaseDate) {
        EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
        String whereStatement = "";
        if (purchaseDate != null) {
            whereStatement = " AND p.purchaseDate >= :pd";
        }
        String creditQueryString = "SELECT SUM(p.amount) from Purchase p " +
                "WHERE p.memberId = :memid AND p.transactionType = 'C'" + whereStatement;
        Query creditQuery = entityManager.createQuery(creditQueryString, Purchase.class);
        creditQuery.setParameter("memid", memberId);
        if (purchaseDate != null) {
            creditQuery.setParameter("pd", purchaseDate);
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
    
    private static double getDebitTotal(String memberId, Date purchaseDate) {
        EntityManager entityManager = DBUtil.getEmFactory().createEntityManager();
        String sqlWhereForDate = "";
        if (purchaseDate != null) {
            sqlWhereForDate = " AND p.purchaseDate >= :pd ";
        }
        String debitQueryString = "SELECT SUM(p.amount) from Purchase p " +
                "WHERE p.memberId = :memid AND p.transactionType = 'D'" + sqlWhereForDate;
        Query debitQuery = entityManager.createQuery(debitQueryString, Purchase.class);
        debitQuery.setParameter("memid", memberId);
        if (purchaseDate != null) {
            debitQuery.setParameter("pd", purchaseDate);
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
