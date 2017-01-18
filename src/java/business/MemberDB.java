
package business;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author josepharcelo
 */
public class MemberDB
{
    public static Member getMemberById(String memid){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            Member m = em.find(Member.class, memid);
            return m;
        } finally {
            em.close();
        }
    }
    
    public static String updtMember(Member member) {
        String msg = "";
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(member);
            trans.commit();
            msg += "Member profile successfully updated!<br>";
        } catch (Exception e) {
            trans.rollback();
            msg = "JPA error: " + e.getMessage() + "<br>";
        } finally {
            em.close();
        }
        return msg;
    }
}
