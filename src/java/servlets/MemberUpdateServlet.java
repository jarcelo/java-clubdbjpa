
package servlets;

import business.Member;
import business.MemberDB;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josepharcelo
 */
@WebServlet(name = "MemberUpdateServlet", urlPatterns = {"/MemberUpdate"})
public class MemberUpdateServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        String msg = "";
        String URL = "/MemberScreen.jsp";

        try {
            Member m = (Member) request.getSession().getAttribute("m");
            Member n = new Member();
            n.setMemid(m.getMemid());
            n.setLastnm(m.getLastnm());
            n.setFirstnm(m.getFirstnm());
            n.setMiddlenm(m.getMiddlenm());
            n.setMemdt(m.getMemdt());
            n.setStatus(m.getStatus());
            n.setPassword(m.getPassword());
            long newpass = 0;
            try {
                String lname = request.getParameter("lastname");
                if (!lname.isEmpty()) {
                    n.setLastnm(lname);
                } else {
                    msg += "Last name field is empty. <br>";
                }
                
                String fname = request.getParameter("firstname");
                if (!fname.isEmpty()) {
                    n.setFirstnm(fname);
                } else {
                    msg += "First name field is empty. <br>";
                }
                
                String mname = request.getParameter("middlename");
                if (!mname.isEmpty()) {
                    n.setMiddlenm(mname);
                } else {
                    msg += "Last name field is empty. <br>";
                }
                // Users can't change status, memdate (and will be read-only in jsp view)
                newpass = Long.parseLong(request.getParameter("psswd"));
                if (newpass > 0) {
                    n.setPassword(newpass);
                } else {
                    msg += "Password is invalid.<br>";
                }
            } catch (Exception e) {
                msg += "Bad data exception: " + e.getMessage() + "<br>";
            }
            
            if (msg.isEmpty()) {
                // Update member via JPA technology
                m = n; //update posted to m
                msg = MemberDB.updtMember(m);
                if (!msg.startsWith("Error")) {
                    m = MemberDB.getMemberById(m.getMemid());
                    m.setPassattempt(newpass);
                    request.getSession().setAttribute("m", m);
                }
            }
        } catch (Exception e) {
            msg += "Servlet error: " + e.getMessage() + "<br>";
        }
        request.setAttribute("msg", msg);
        RequestDispatcher disp = getServletContext().getRequestDispatcher(URL);
        disp.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
