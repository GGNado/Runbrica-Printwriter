package com.gigi;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class ConnessioneDAO implements PersonaDAO{
    private Connection connection;
    public ConnessioneDAO(Connection connection){
        this.connection = connection;
    }
    @Override
    public Persona getPersonaById(int idd) {
        Persona persona = null;
        try {
            String sql = "SELECT * FROM Rubrica WHERE id=" + idd;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                persona = new Persona(resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("numero"), resultSet.getString("fiscale"));
                persona.toJOptionpane();
            }
            resultSet.close();
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return persona;
    }

    @Override
    public ArrayList<Persona> getAllPersone() {
        ArrayList<Persona> persone = new ArrayList<>();

        String sql = "SELECT * FROM Rubrica";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Persona persona = new Persona(resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("numero"), resultSet.getString("fiscale"));
                persone.add(persona);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persone;
    }

    @Override
    public void addPersona(Persona persona) {
        String sql = "INSERT INTO Rubrica(nome, cognome, numero, fiscale) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, persona.getNome());
            preparedStatement.setString(2, persona.getCognome());
            preparedStatement.setString(3, persona.getTelefono());
            preparedStatement.setString(4, persona.getFiscale());
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

            JOptionPane.showMessageDialog(null, "Aggiunto a database");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore");
            e.printStackTrace();
        }


    }

    @Override
    public void updatePersona(Persona persona, int idd) {
        String sql = "UPDATE Rubrica SET nome=?,cognome=?,numero=?,fiscale=? WHERE id=" + idd;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, persona.getNome());
            preparedStatement.setString(2, persona.getCognome());
            preparedStatement.setString(3, persona.getTelefono());
            preparedStatement.setString(4, persona.getFiscale());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

            JOptionPane.showMessageDialog(null, "Modificato nel database");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore");
            e.printStackTrace();
        }
    }

    @Override
    public void deletePersona(int id) {
        String sql = "DELETE FROM `Rubrica` WHERE id =" + id;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            JOptionPane.showMessageDialog(null, "Cancellato");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore");
        }
    }
}
