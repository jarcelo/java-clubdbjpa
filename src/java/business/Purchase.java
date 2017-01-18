
package business;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author josepharcelo
 */
@Entity
@Table(name = "tblPurchases")
public class Purchase
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchaseId;
    
    @Column(name = "MemId")
    private String memberId;
    
    @Column(name = "PurchaseDt")
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
    
    @Column(name = "TransType")
    private String transactionType;
    
    @Column(name = "TransCd")
    private String transactionCode;
    
    @Column(name = "Amount")
    private double amount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TransCd", insertable = false, updatable = false)
    private CodesRec codesrec;
    
    public Purchase()
    {
        this.memberId = "";
        this.purchaseDate = null;
        this.transactionType = "";
        this.transactionCode = "";
        this.amount = 0;
        this.purchaseId = 0;
    }

    public long getPurchaseId()
    {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId)
    {
        this.purchaseId = purchaseId;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId(String memberId)
    {
        this.memberId = memberId;
    }

    public Date getPurchaseDate()
    {
        return purchaseDate;
    }

    public String getPurchdtS() {
        return new SimpleDateFormat("MM-dd-yyyy").format(this.purchaseDate);
    }
    
    public void setPurchaseDate(Date purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }

    public String getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType(String transactionType)
    {
        this.transactionType = transactionType;
    }

    public String getTransactionCode()
    {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode)
    {
        this.transactionCode = transactionCode;
    }

    public double getAmount()
    {
        return amount;
    }
    
    public String getFormattedAmount() {
        return NumberFormat.getCurrencyInstance().format(this.amount);
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    
    public String getTransactionDescription() {
        return codesrec.getTransactionDescription();
    }
}
