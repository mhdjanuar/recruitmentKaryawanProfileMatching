/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package application.dao;

import application.models.AlternatifModel;
import application.models.NilaiIdeal;
import application.models.NilaiKaryawan;
import java.util.List;

/**
 *
 * @author mhdja
 */
public interface AlternatifDao {
     public List<AlternatifModel> findAll();
     public int create(List<AlternatifModel> alternatifList);
     public List<AlternatifModel> findNormalisasi();
     public int deleteBulkByKaryawan(int idPeserta);
     public List<NilaiKaryawan> findNilaiAktual();
     public List<NilaiIdeal> findNilaiIdeal();

}
