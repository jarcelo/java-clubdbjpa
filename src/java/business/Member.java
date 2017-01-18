
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
    private String memberId;
    
    @Column(name = "LastName")
    private String lastName;
    
    @Column(name = "FirstName")
    private String firstName;
    
    @Column(name = "MiddleName")
    private String middleName;
    
    @Column(name = "Status")
    private String status;
    
    @Column(name = "MemDt")
    @Temporal(TemporalType.DATE)
    private Date membershipDate;
    
    @Column(name = "Password")
    private long password;
    
    @Column(name = "YTD_Total")
    private double YTDTotal;
    
    @Column(name = "YTD_Total_Dt")
    @Temporal(TemporalType.DATE)
    private Date YTDTotalDate;
    
    @Transient
    private long passwordAttempt;

    public Member()
    {
        memberId = "";
        lastName = "";
        firstName = "";
        middleName = "";
        status = "";
        membershipDate = null;
        YTDTotal = 0;
        YTDTotalDate = null;
        password = 0;
        passwordAttempt = -1;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId(String memberId)
    {
        this.memberId = memberId;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getMembershipDate()
    {
        return membershipDate;
    }

    public void setMembershipDate(Date membershipDate)
    {
        this.membershipDate = membershipDate;
    }
    
    public String getMemdtS() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        return sdf.format(this.membershipDate);
    }

    public long getPassword()
    {
        return password;
    }

    public void setPassword(long password)
    {
        this.password = password;
    }

    public long getPasswordAttempt()
    {
        return passwordAttempt;
    }

    public void setPasswordAttempt(long passwordAttempt)
    {
        this.passwordAttempt = passwordAttempt;
    }
    
    public boolean isAuthenticated() {
        if (password > 0) {
            if (password == this.passwordAttempt) {
                return true;
            }
        }
        return false;
    }
}
