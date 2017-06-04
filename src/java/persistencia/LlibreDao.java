package persistencia;

import java.io.*;
import java.sql.*;
import java.util.*;
import model.*;

public class LlibreDao {

    private Connection con;

    public LlibreDao(Connection con) {
        this.con = con;
    }

    public boolean afegir(Llibre llib) {
        boolean afegit = true;
        PreparedStatement pt = null;
        String sentencia = "INSERT INTO LLIBRE(TITOL,AUTOR,ANYO,ISBN,EDITORIAL,ESTOC)"
                + " VALUES(?,?,?,?,?,?)";
        try {
            pt = con.prepareStatement(sentencia);
            pt.setString(1, llib.getTitol());
            pt.setString(2, llib.getAutor());
            pt.setInt(3, llib.getAnyEdicio());
            pt.setString(4, llib.getIsbn());
            pt.setString(5, llib.getEditorial());
            pt.setInt(6, llib.getEstoc());

            if (pt.executeUpdate() == 0) {
                afegit = false;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage() + "error");
            afegit = false;
        } finally {
            tancarRecurs(pt);
        }
        return afegit;
    }

    public Llibre cercarPerTitol(String titol) {
        String consulta = "SELECT * FROM llibre WHERE titol='" + titol + "'";
        Statement st;
        ResultSet rs;
        Llibre llib = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery(consulta);
            if (rs.next()) {
                llib = new Llibre(rs.getString(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5), rs.getInt(6));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return llib;
    }

    public List<Llibre> cercarTots() {
        String consulta = "SELECT * FROM LLIBRE";
        Statement st;
        ResultSet rs;
        List<Llibre> llista = new ArrayList<>();
        try {
            st = con.createStatement();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                llista.add(new Llibre(rs.getString(1),
                        rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return llista;
    }

    public boolean eliminarLlibre(String isbn) {
        String consulta = " DELETE FROM llibre WHERE isbn='" + isbn + "'";
        System.out.println(consulta);
        Statement st;
        Boolean rs = false;
        try {
            st = con.createStatement();
            rs = st.executeUpdate(consulta) != 0;
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rs;
    }

    private void tancarRecurs(AutoCloseable r) {
        try {
            r.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {

        }

    }

}
