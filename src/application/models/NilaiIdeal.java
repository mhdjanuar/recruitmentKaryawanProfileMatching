/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.models;

/**
 *
 * @author mhdja
 */
public class NilaiIdeal {
    private int idKriteria;
    private String namaKriteria;
    private double nilaiIdeal;
    private String typeKriteria;

    // Constructor
    public NilaiIdeal(int idKriteria, String namaKriteria, double nilaiIdeal, String typeKriteria) {
        this.idKriteria = idKriteria;
        this.namaKriteria = namaKriteria;
        this.nilaiIdeal = nilaiIdeal;
        this.typeKriteria = typeKriteria;
    }

    // Getter & Setter
    public int getIdKriteria() {
        return idKriteria;
    }

    public void setIdKriteria(int idKriteria) {
        this.idKriteria = idKriteria;
    }

    public String getNamaKriteria() {
        return namaKriteria;
    }

    public void setNamaKriteria(String namaKriteria) {
        this.namaKriteria = namaKriteria;
    }

    public double getNilaiIdeal() {
        return nilaiIdeal;
    }

    public void setNilaiIdeal(double nilaiIdeal) {
        this.nilaiIdeal = nilaiIdeal;
    }
    
    /**
    * @return the typeKriteria
    */
    public String getTypeKriteria() {
        return typeKriteria;
    }

    /**
     * @param typeKriteria the typeKriteria to set
     */
    public void setTypeKriteria(String typeKriteria) {
        this.typeKriteria = typeKriteria;
    }
}

