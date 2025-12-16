package services.DTOs;

import java.util.ArrayList;
import java.util.List;

public class Grupo {

    private String id;
    private String nombre;
    private List<String> miembros;

    public Grupo() {
        this.miembros = new ArrayList<>();
    }

    public Grupo(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }

    public Grupo(String id, String nombre, List<String> miembros) {
        this.id = id;
        this.nombre = nombre;
        this.miembros = miembros;
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public List<String> getMiembros() {return miembros;}
    public void setMiembros(List<String> miembros) {this.miembros = miembros; }

}

