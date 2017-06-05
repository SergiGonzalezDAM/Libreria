package control;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import model.*;
import persistencia.*;

public class GestioLlibres extends HttpServlet {

    LlibreDao dao;
    private Connection con;
    private ConfiguracioConnexio dbCon;
    private List<Llibre> listaLibros;
    private Llibre libro;

    @Override
    public void init() throws ServletException {
        super.init();
        dbCon = new ConfiguracioConnexio(this.getInitParameter("driver"),
                this.getInitParameter("cadenaConnexioExt"),
                this.getInitParameter("usuari"), this.getInitParameter("contrasenya"));
        con = dbCon.getConnexio();
        dao = new LlibreDao(con);
        listaLibros = new ArrayList();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // LlibreDao dao = new LlibreDao(con);
        String resposta;

        String action = request.getParameter("accio");
        switch (action) {
            case "afegir":
                resposta = afegirLLibre(request, response);
                request.setAttribute("afegit", resposta);
                anarAPagina("afegir.jsp", request, response);
                break;
            case "cercarTots":
                cercarTots();
                request.setAttribute("cercarTots", listaLibros);
                anarAPagina("cercarTots.jsp", request, response);
                break;
            case "cercarTitol":
                libro = cercarLlibreTitol(request, response);
                request.setAttribute("cercarTitol", libro);
                anarAPagina("cercarTitol.jsp", request, response);
                break;
            case "eliminarLlibre":
                resposta = eliminarLlibre(request, response);
                request.setAttribute("eliminarLlibre", resposta);
                anarAPagina("eliminarLlibre.jsp", request, response);
                break;
            case "modificarLlibre":
                resposta  = modificarLlibre(request, response);
                request.setAttribute("modificarLlibre", resposta);
                anarAPagina("modificarLlibre.jsp", request, response);
                break;
        }

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
            throws ServletException, IOException {
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
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void destroy() {
        dbCon.tancar();
        super.destroy(); //To change body of generated methods, choose Tools | Templates.        
    }

    /**
     * Redirecciona la petició(Request) a una altra pàgina
     *
     * @param pagina
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    private void anarAPagina(String pagina, HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        RequestDispatcher dispatcher = req.getRequestDispatcher(pagina);
        if (dispatcher != null) {
            dispatcher.forward(req, res);
        }
    }

    private String afegirLLibre(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String isbn, titol, autor, editorial, any, estok;
        int anyo, estoc;
        boolean validar = true;

        String resposta = "El llibre s'ha afegit correctament";
        if (!(isbn = req.getParameter("isbn_")).matches("[0-9]{13}")) {
            resposta = "ISBN incorrecte, ha d'estar format per 13 dígits";
            validar = false;
        } else if (!(any = req.getParameter("anyo_")).matches("^[1-9][0-9]{1,3}")) {
            resposta = "Any d'edició incorrecte, ha de ser any entre 1000-2999";
            validar = false;
        } else if (!(estok = req.getParameter("estoc_")).matches("[0-9]{1,3}")) {
            resposta = "Estoc incorrecte";
            validar = false;
        } else if ((titol = req.getParameter("titol_")) == null
                || (autor = req.getParameter("autor_")) == null
                || (editorial = req.getParameter("editorial_")) == null) {
            resposta = "s'han d'emplenar tots els camps";
            validar = false;
        } else {
            anyo = Integer.parseInt(any);
            estoc = Integer.parseInt(estok);
            if (!dao.afegir(new Llibre(titol, autor, anyo, isbn, editorial, estoc))) {
                resposta = "Error, no s'ha afegir";
            }
        }

        return resposta;
    }

    private String modificarLlibre(HttpServletRequest request, HttpServletResponse response) {
        String isbn, titol, autor, editorial, any, estok;
        int anyo, estoc;
        String resposta = "";
        if (!(isbn = request.getParameter("isbn_")).matches("[0-9]{13}")) {
            resposta = "ISBN incorrecto, 13 dígitos";
        } else if (!(any = request.getParameter("anyo_")).matches("^[1-9][0-9]{1,3}")) {
            resposta = "Año de edición incorrecto";
        } else if (!(estok = request.getParameter("estoc_")).matches("[0-9]{1,3}")) {
            resposta = "Estoc no válido";
        } else if ((titol = request.getParameter("titol_")) == null
                || (autor = request.getParameter("autor_")) == null
                || (editorial = request.getParameter("editorial_")) == null) {
            resposta = "campos vacios";
        } else {
            anyo = Integer.parseInt(any);
            estoc = Integer.parseInt(estok);
            if (!dao.modificarLlibre(new Llibre(titol, autor, anyo, isbn, editorial, estoc))) {
                resposta = "Error, no se ha añadido";
            }
        }
        return resposta;
    }

    private String eliminarLlibre(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String resposta = "Error al eliminar";
        if (dao.eliminarLlibre(request.getParameter("isbn_"))) {
            resposta = "Llibre eliminat";
        }
        return resposta;
    }

    private Llibre cercarLlibreTitol(HttpServletRequest request, HttpServletResponse response) {
        String titol;
        Llibre llibre;
        titol = request.getParameter("titol_");
        System.out.println("TITULO: " + titol);
        if ((titol = request.getParameter("titol_")) != null) {
            llibre = dao.cercarPerTitol(titol);
        } else {
            llibre = null;
        }
        return llibre;
    }

    private void cercarTots() {
        listaLibros = dao.cercarTots();
    }
}
