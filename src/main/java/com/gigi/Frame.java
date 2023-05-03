package com.gigi;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.util.Scanner;

public class Frame extends JFrame {

    public Frame(){
        setTitle("Rubrica");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);

        //TextField
        JTextField nome = new JTextField("Leonardo");
        nome.setBounds(300, 25, 150, 40);
        panel.add(nome);

        JTextField cognome = new JTextField("Rossi");
        cognome.setBounds(nome.getX(), nome.getY() + 50, nome.getBounds().width, nome.getBounds().height);
        panel.add(cognome);

        JTextField telefono = new JTextField("3668397915");
        telefono.setBounds(cognome.getX(), cognome.getY() + 50, nome.getBounds().width, nome.getBounds().height);
        panel.add(telefono);

        JTextField codiceFIscale = new JTextField("AAABBBCCCEEEDDDF");
        codiceFIscale.setBounds(telefono.getX(), telefono.getY() + 50, nome.getBounds().width, nome.getBounds().height);
        panel.add(codiceFIscale);

        //labels
        JLabel nomeLabel = new JLabel("Nome");
        nomeLabel.setBounds(nome.getX() - 100, nome.getY() + 10, nomeLabel.getPreferredSize().width, nomeLabel.getPreferredSize().height);
        panel.add(nomeLabel);

        JLabel cognomeLabel = new JLabel("Cognome");
        cognomeLabel.setBounds(cognome.getX() - 100, cognome.getY() + 10, cognomeLabel.getPreferredSize().width, cognomeLabel.getPreferredSize().height);
        panel.add(cognomeLabel);

        JLabel telefonoLabel = new JLabel("Telefono");
        telefonoLabel.setBounds(telefono.getX() - 100, telefono.getY() + 10, telefonoLabel.getPreferredSize().width, telefonoLabel.getPreferredSize().height);
        panel.add(telefonoLabel);

        JLabel codFiscale = new JLabel("Cod. Fiscale");
        codFiscale.setBounds(codiceFIscale.getX() - 100, codiceFIscale.getY() + 10, codFiscale.getPreferredSize().width, codFiscale.getPreferredSize().height);
        panel.add(codFiscale);

        //Bottoni
        JButton aggiungi = new JButton("Aggiungi contatto");
        aggiungi.setBounds(250, 400, 300, 50);
        panel.add(aggiungi);

        //ActionLsitener
        aggiungi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nome.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Inserire nome", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (cognome.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Inserire Cognome", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (telefono.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Inserire Telefono", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Persona persona = new Persona(nome.getText(), cognome.getText(), telefono.getText(), codiceFIscale.getText());
                Connection connection = Connessione.getConnessione();
                if (connection == null) return;

                persona.toDatabase(connection);


                //- - - - - - FILE - - - - - -
                File file = new File("Contatti.txt");
                //Se file non esiste, crealo
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                try {
                    PrintWriter printWriter = new PrintWriter(new FileOutputStream(file, true));
                    printWriter.append("\nNome: " + nome.getText() + "\nCognome: " + cognome.getText() + "\nTelefono: " + telefono.getText());
                    printWriter.flush();
                    printWriter.close();
                    nome.setText("");
                    codiceFIscale.setText("");
                    telefono.setText("");
                    cognome.setText("");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton visualizza = new JButton("Visualizza Rubrica");
        visualizza.setBounds(aggiungi.getX(), aggiungi.getY() + 50, aggiungi.getBounds().width, aggiungi.getBounds().height);
        panel.add(visualizza);
        visualizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("Contatti.txt");
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                    System.out.println("- - - Elenco Contatti - - -");
                    while (scanner.hasNextLine()){
                        System.out.println(scanner.nextLine());
                    }

                }catch (FileNotFoundException ex){
                    ex.printStackTrace();
                } finally {
                    if (scanner != null)
                    scanner.close();
                }

                Connessione.getAll();
            }

        });

        JButton cerca = new JButton("Cerca");
        cerca.setBounds(visualizza.getX(), visualizza.getY() + 50, visualizza.getBounds().width, visualizza.getBounds().height);
        panel.add(cerca);

        cerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int i = Integer.parseInt(JOptionPane.showInputDialog(null, "Inserisci id"));
                    Connessione.findById(i);
                }catch (NumberFormatException ex){
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Non puoi inserire caratteri o numero nullo");
                }
            }
        });
        add(panel);
        setVisible(true);
    }

}
