package com.gigi;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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

        //Bottoni
        JButton aggiungi = new JButton("Aggiungi contatto");
        aggiungi.setBounds(250, 200, 300, 50);
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
                System.out.println("Nome: " + nome.getText() + "\nCognome: " + cognome.getText() + "\nTelefono: " + telefono.getText());
                JOptionPane.showMessageDialog(null, "Nome: " + nome.getText() + "\nCognome: " + cognome.getText() + "\nTelefono: " + telefono.getText(), "Aggiunto correttamente", JOptionPane.INFORMATION_MESSAGE);

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
            }
        });

        add(panel);
        setVisible(true);
    }

}
