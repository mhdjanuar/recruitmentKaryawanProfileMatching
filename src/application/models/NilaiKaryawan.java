/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.models;

/**
 *
 * @author mhdja
 */
public class NilaiKaryawan {
    private int idEmployee;
    private int idKriteria;
    private double nilaiAktual;
    private String namaKaryawan;
    private String namaKriteria;
    
    /**
     * @return the idEmployee
     */
    public int getIdEmployee() {
        return idEmployee;
    }

    /**
     * @param idEmployee the idEmployee to set
     */
    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    /**
     * @return the idKriteria
     */
    public int getIdKriteria() {
        return idKriteria;
    }

    /**
     * @param idKriteria the idKriteria to set
     */
    public void setIdKriteria(int idKriteria) {
        this.idKriteria = idKriteria;
    }

    /**
     * @return the nilaiAktual
     */
    public double getNilaiAktual() {
        return nilaiAktual;
    }

    /**
     * @param nilaiAktual the nilaiAktual to set
     */
    public void setNilaiAktual(double nilaiAktual) {
        this.nilaiAktual = nilaiAktual;
    }
    
     /**
     * @return the namaKaryawan
     */
    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    /**
     * @param namaKaryawan the namaKaryawan to set
     */
    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    /**
     * @return the namaKriteria
     */
    public String getNamaKriteria() {
        return namaKriteria;
    }

    /**
     * @param namaKriteria the namaKriteria to set
     */
    public void setNamaKriteria(String namaKriteria) {
        this.namaKriteria = namaKriteria;
    }
    
}
