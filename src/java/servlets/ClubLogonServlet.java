
package servlets;

import business.Member;
import business.MemberDB;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josepharcelo
 */
public class ClubLogonServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        String msg = "";
        String userid= "";
        String URL = "/Logon.jsp";
        long passwordatt = 0;
        Member m;
        
        try {
            userid = request.getParameter("userid").trim();
            passwordatt = Long.parseLong(request.getParameter("password").trim());
            
            m = MemberDB.getMemberById(userid);
            if (m == null) {
                msg = "No member record returned.<br>";
            } else {
                m.setPassattempt(passwordatt);
                if (!m.isAuthenticated()) {
                    msg = "Member found but not authenticated.<br>";
                } else {
                    msg = "Member authenticated!<br>";
                    URL = "/MemberScreen.jsp";
                    request.getSession().setAttribute("m", m);
                }
            }
        } catch (Exception e) {
            msg = "Servlet error :" + e.getMessage();
        }
       
        // Add cookie for userID
        Cookie uid = new Cookie("userid", userid);
        uid.setMaxAge(60*10);
        uid.setPath("/");
        response.addCookie(uid);
        
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
