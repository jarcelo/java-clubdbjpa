
package business;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author josepharcelo
 */
public class DBUtil
{
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ClubDBJPA_ArceloPU");
    
    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
