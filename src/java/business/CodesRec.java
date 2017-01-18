
package business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author josepharcelo
 */
@Entity
@Table(name = "tblCodes")
public class CodesRec
{
    @Id
    @Column(name = "TransCd")
    private String transactionCode;
    
    @Column(name = "TransType")
    private String transactionType;
    
    @Column(name = "TransDesc")
    private String transactionDescription;

    public CodesRec()
    {
        transactionCode = "";
        transactionType = "";
        transactionDescription = "";
    }

    public String getTransactionCode()
    {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode)
    {
        this.transactionCode = transactionCode;
    }

    public String getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType(String transactionType)
    {
        this.transactionType = transactionType;
    }

    public String getTransactionDescription()
    {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription)
    {
        this.transactionDescription = transactionDescription;
    }
}
