
package business;

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
    private long pid;
    
    @Column(name = "MemId")
    private String memid;
    
    @Column(name = "PurchaseDt")
    @Temporal(TemporalType.DATE)
    private Date purchdt;
    
    @Column(name = "TransType")
    private String transtype;
    
    @Column(name = "TransCd")
    private String transcd;
    
    @Column(name = "Amount")
    private double amt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TransCd", insertable = false, updatable = false)
    private CodesRec codesrec;
    
    //private double balanceDue;
    
    public Purchase()
    {
        this.memid = "";
        this.purchdt = null;
        this.transtype = "";
        this.transcd = "";
        this.amt = 0;
        this.pid = 0;
        //this.balanceDue = 0;
    }

    public long getPid()
    {
        return pid;
    }

    public void setPid(long pid)
    {
        this.pid = pid;
    }

    public String getMemid()
    {
        return memid;
    }

    public void setMemid(String memid)
    {
        this.memid = memid;
    }

    public Date getPurchdt()
    {
        return purchdt;
    }

    public String getPurchdtS() {
        return new SimpleDateFormat("MM-dd-yyyy").format(this.purchdt);
    }
    public void setPurchdt(Date purchdt)
    {
        this.purchdt = purchdt;
    }

    public String getTranstype()
    {
        return transtype;
    }

    public void setTranstype(String transtype)
    {
        this.transtype = transtype;
    }

    public String getTranscd()
    {
        return transcd;
    }

    public void setTranscd(String transcd)
    {
        this.transcd = transcd;
    }

    public double getAmt()
    {
        return amt;
    }

    public void setAmt(double amt)
    {
        this.amt = amt;
    }
    
    public String getTransdesc() {
        return codesrec.getTransdesc();
    }
    
//    public double getBalanceDue() {
//        return balanceDue;
//    }
}
