/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.models;

/**
 *
 * @author mhdja
 */
public class SkorModel {
    private int idEmployee;
    private String namaEmployee;
    private double cf;  // Core factor
    private double sf;  // Secondary factor
    private double skorAkhir;
    
    
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
     * @return the namaEmployee
     */
    public String getNamaEmployee() {
        return namaEmployee;
    }

    /**
     * @param namaEmployee the namaEmployee to set
     */
    public void setNamaEmployee(String namaEmployee) {
        this.namaEmployee = namaEmployee;
    }

    /**
     * @return the cf
     */
    public double getCf() {
        return cf;
    }

    /**
     * @param cf the cf to set
     */
    public void setCf(double cf) {
        this.cf = cf;
    }

    /**
     * @return the sf
     */
    public double getSf() {
        return sf;
    }

    /**
     * @param sf the sf to set
     */
    public void setSf(double sf) {
        this.sf = sf;
    }

    /**
     * @return the skorAkhir
     */
    public double getSkorAkhir() {
        return skorAkhir;
    }

    /**
     * @param skorAkhir the skorAkhir to set
     */
    public void setSkorAkhir(double skorAkhir) {
        this.skorAkhir = skorAkhir;
    }
}
