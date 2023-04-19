package com.gigi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Persona {
    private String nome;
    private String cognome;
    private String telefono;

    private String fiscale;

    public Persona(String nome, String cognome, String telefono, String fiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.fiscale = fiscale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFiscale() {
        return fiscale;
    }

    public void setFiscale(String fiscale) {
        this.fiscale = fiscale;
    }

    public void toDatabase(){
        String url = "jdbc:mysql://localhost:3306/Persone";
        String user = "root";
        String psw = "";

        String sql = " insert into Rubrica (nome, cognome, numero, fiscale)"
                + " values (?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, psw);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, getNome());
            preparedStatement.setString(2, getCognome());
            preparedStatement.setString(3, getTelefono());
            preparedStatement.setString(4, getFiscale());

            preparedStatement.execute();
            connection.close();

        }catch (ClassNotFoundException | SQLException ex){
            System.out.println(ex);
        }
    }
}