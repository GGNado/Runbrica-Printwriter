package com.gigi;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Connessione {
    private static String url = "jdbc:mysql://localhost:3306/Persone";
    private static String user = "root";
    private static String psw = "";

    public static Connection getConnessione(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, psw);

            return connection;

        }catch (ClassNotFoundException | SQLException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Errore nel collegamento del DataBase", "Errore", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public static void getAll(){
        Connection connection = getConnessione();
        if (connection == null) return;

        String sql = "SELECT * FROM Rubrica";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println(resultSet.getString("nome") + " " + resultSet.getString("cognome") + " " + resultSet.getString("numero") + " " + resultSet.getString("fiscale"));
            }

        }catch (SQLException ex){

        }

    }

    public static void findById(int id) {
        Connection connection = getConnessione();
        if (connection == null) return;
        
        String sql = "select * from Rubrica";

    try {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            if (resultSet.getInt(1) == id) {
                Persona persona = new Persona(resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("numero"), resultSet.getString("fiscale"));
                JOptionPane.showMessageDialog(null, "Nome: " + persona.getNome()+ "\nCognome: " + persona.getCognome()+ "\nTelefono: " + persona.getTelefono(), "Trovato!", JOptionPane.INFORMATION_MESSAGE);
                connection.close();
                return;
            }
        }
        connection.close();
    } catch (Exception e){
        System.out.println(e);
    }


        JOptionPane.showMessageDialog(null, "Utente non trovato");

    }

}
