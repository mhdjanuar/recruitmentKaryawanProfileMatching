/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.models;

/**
 *
 * @author mhdja
 */
public class GapModel {
    private int idEmployee;
    private String namaEmployee;
    private int idKriteria;
    private String namaKriteria;
    private double nilaiAktual;
    private double nilaiIdeal;
    private double gap;
    private double bobot;
    private String typeKriteria;
    
    public final double konversiGap(double gap) {
        int gapInt = (int) gap;

        double bobot;
        switch (gapInt) {
            case 0:  bobot = 5.0; break;
            case 1:  bobot = 4.5; break;
            case -1: bobot = 4.0; break;
            case 2:  bobot = 3.5; break;
            case -2: bobot = 3.0; break;
            case 3:  bobot = 2.5; break;
            case -3: bobot = 2.0; break;
            case 4:  bobot = 1.5; break;
            case -4: bobot = 1.0; break;
            case 5:  bobot = 0.5; break;
            case -5: bobot = 0.0; break;
            default: bobot = 0.0; break;
        }

        System.out.println("GAP: " + gap + " => Bobot: " + bobot);
        return bobot;
    }



    // Constructor
    public GapModel(int idEmployee, String namaEmployee, int idKriteria, String namaKriteria,
                    double nilaiAktual, double nilaiIdeal, String typeKriteria) {
        this.idEmployee = idEmployee;
        this.namaEmployee = namaEmployee;
        this.idKriteria = idKriteria;
        this.namaKriteria = namaKriteria;
        this.nilaiAktual = nilaiAktual;
        this.nilaiIdeal = nilaiIdeal;
        this.gap = nilaiAktual - nilaiIdeal;
        this.bobot = konversiGap(gap);
        this.typeKriteria = typeKriteria;
    }

    // Getters
    public int getIdEmployee() { return idEmployee; }
    public String getNamaEmployee() { return namaEmployee; }
    public int getIdKriteria() { return idKriteria; }
    public String getNamaKriteria() { return namaKriteria; }
    public double getNilaiAktual() { return nilaiAktual; }
    public double getNilaiIdeal() { return nilaiIdeal; }
    public double getGap() { return gap; }
    public double getBobot() {
        return bobot;
    }
    public String getTypeKriteria() {
        return typeKriteria;
    }
}
