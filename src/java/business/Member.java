
package business;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author josepharcelo
 */

@Entity
@Table(name="tblmembers")
public class Member
{
    @Id
    @Column(name = "MemID")
    private String memid;
    
    @Column(name = "LastName")
    private String lastnm;
    
    @Column(name = "FirstName")
    private String firstnm;
    
    @Column(name = "MiddleName")
    private String middlenm;
    
    @Column(name = "Status")
    private String status;
    
    @Column(name = "MemDt")
    @Temporal(TemporalType.DATE)
    private Date memdt;
    
    @Column(name = "Password")
    private long password;
    
    @Column(name = "YTD_Total")
    private double ytdtot;
    
    @Column(name = "YTD_Total_Dt")
    @Temporal(TemporalType.DATE)
    private Date ytdtotdt;
    
    @Transient
    private long passattempt;

    public Member()
    {
        memid = "";
        lastnm = "";
        firstnm = "";
        middlenm = "";
        status = "";
        memdt = null;
        ytdtot = 0;
        ytdtotdt = null;
        password = 0;
        passattempt = -1;
    }

    public String getMemid()
    {
        return memid;
    }

    public void setMemid(String memid)
    {
        this.memid = memid;
    }

    public String getLastnm()
    {
        return lastnm;
    }

    public void setLastnm(String lastnm)
    {
        this.lastnm = lastnm;
    }

    public String getFirstnm()
    {
        return firstnm;
    }

    public void setFirstnm(String firstnm)
    {
        this.firstnm = firstnm;
    }

    public String getMiddlenm()
    {
        return middlenm;
    }

    public void setMiddlenm(String middlenm)
    {
        this.middlenm = middlenm;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getMemdt()
    {
        return memdt;
    }

    public void setMemdt(Date memdt)
    {
        this.memdt = memdt;
    }
    
    public String getMemdtS() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        return sdf.format(this.memdt);
    }

    public long getPassword()
    {
        return password;
    }

    public void setPassword(long password)
    {
        this.password = password;
    }

    public long getPassattempt()
    {
        return passattempt;
    }

    public void setPassattempt(long passattempt)
    {
        this.passattempt = passattempt;
    }
    
    public boolean isAuthenticated() {
        if (password > 0) {
            if (password == this.passattempt) {
                return true;
            }
        }
        return false;
    }
}
