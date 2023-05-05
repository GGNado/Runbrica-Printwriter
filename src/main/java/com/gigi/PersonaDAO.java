package com.gigi;

import java.util.ArrayList;

//DAO (Data Access Object) che definisce i metodi CRUD (Create, Read, Update, Delete)
public interface PersonaDAO {
    public Persona getPersonaById(int id);
    public ArrayList<Persona> getAllPersone();
    public void addPersona(Persona persona);
    public void updatePersona(Persona persona, int id);
    public void deletePersona(int id);
}
