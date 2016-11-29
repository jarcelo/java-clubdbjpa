
package servlets;

import business.Member;
import business.Purchase;
import business.PurchaseDB;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josepharcelo
 */
public class ShowPurchasesServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        String msg = "", URL = "/MemberScreen.jsp";
        String month = "", day = "", year = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        
        try {
            Member m = (Member) request.getSession().getAttribute("m");
            
            try {
                month = request.getParameter("month");
            } catch (Exception e) {
                msg += "Month error: + " + e.getMessage() + "<br>";
                month = "";
            }
            try {
                day = request.getParameter("day");
            } catch (Exception e) {
                msg += "Day error: + " + e.getMessage() + "<br>";
                day = "";
            }
            try {
                year = request.getParameter("year");
            } catch (Exception e) {
                msg += "Year error: + " + e.getMessage() + "<br>";
                year = "";
            }
            
            Date pd = null;
            Date purchaseDate = null;
            if (!month.isEmpty() && !day.isEmpty() && !year.isEmpty()){
                try {
                    pd = sdf.parse(month+ "-" + day + "-" + year);
                    purchaseDate = sdf.parse(year + "-" + month + "-" + day);
                } catch (ParseException e) {
                        pd = null;
                        purchaseDate = null;
                }
            }
            
            List<Purchase> purchases = null;
            if (pd == null) {
                purchases = PurchaseDB.getPurchases(m.getMemid());
            } else {
                purchases = PurchaseDB.getPurchases(m.getMemid(), pd);
            }
            
            if (purchases == null) {
                msg = "Purchases returned as null<br>";
            } else {
                msg = "Purchase(s) found: " + purchases.size() + "<br>";
                URL = "/Purchases.jsp";
                request.setAttribute("pur", purchases);
                
                NumberFormat curr = NumberFormat.getCurrencyInstance();
                double balanceDue = PurchaseDB.getBalanceDue(m.getMemid(), pd);
                request.setAttribute("balanceDue", curr.format(balanceDue));
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
