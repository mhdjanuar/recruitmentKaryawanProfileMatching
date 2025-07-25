/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.daoimpl;

import application.dao.AlternatifDao;
import application.models.AlternatifModel;
import application.models.KaryawanModel;
import application.models.NilaiIdeal;
import application.models.NilaiKaryawan;
import application.utils.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mhdja
 */
public class AlternatifDaoImpl implements AlternatifDao {
    private Connection dbConnection = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    private String query;
    
     public AlternatifDaoImpl() {
        dbConnection = DatabaseUtil.getInstance().getConnection();
    }

    @Override
    public List<AlternatifModel> findAll() {
        List<AlternatifModel> alternatifList = new ArrayList<>();
        
        try {
            query = "SELECT p.name, c.id AS id_kriteria, c.nama AS nama_kriteria, " +
                    "sc.jumlah_bobot AS bobot_alternatif " +
                    "FROM alternatif AS a " +
                    "INNER JOIN employees AS p ON a.id_employee = p.id " +
                    "INNER JOIN sub_criteria AS sc ON a.id_sub_kreteria = sc.id " +
                    "INNER JOIN criteria AS c ON sc.id_kreteria = c.id " +
                    "ORDER BY a.created_at DESC, p.name ASC";
            
            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();
            
            while (resultSet.next()) {
                AlternatifModel alternatif = new AlternatifModel(0, 0);
                alternatif.setNameAlternatif(resultSet.getString("name"));
                alternatif.setIdKriteria(resultSet.getInt("id_kriteria"));
                alternatif.setNameKriteria(resultSet.getString("nama_kriteria"));
                alternatif.setBobotAlternatif(resultSet.getInt("bobot_alternatif"));
                // Set other attributes accordingly
                alternatifList.add(alternatif);
            }
	} catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
        
        return alternatifList;
    }
    
    
    private void closeStatement() {
        try {
            if(pstmt != null){
                pstmt.close();
                pstmt = null;
            }
            if(resultSet != null){
                resultSet.close();
                resultSet = null;
            }   
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int create(List<AlternatifModel> alternatifList) {
        String query = "INSERT INTO alternatif(id_employee, id_sub_kreteria) " +
                       "VALUES(?, ?)";

        try (PreparedStatement pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (AlternatifModel alternatif : alternatifList) {
                pstmt.setInt(1, alternatif.getIdKaryawan());
                pstmt.setInt(2, alternatif.getIdSubKriteria());

                pstmt.addBatch();  // Add the current set of parameters to the batch
            }

            int[] result = pstmt.executeBatch();  // Execute the batch insert
            return Arrays.stream(result).sum();  // Sum up the number of affected rows (optional)

        } catch (SQLException e) {
            // Handle exception (logging, etc.)
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public List<AlternatifModel> findNormalisasi() {
        List<AlternatifModel> normalisasiList = new ArrayList<>();

        try {
            // Ambil pembagi (max untuk benefit, min untuk cost)
            Map<Integer, Double> pembagiMap = new HashMap<>();
            Map<Integer, String> tipeMap = new HashMap<>();

            String pembagiQuery = "SELECT c.id, c.type, " +
                    "CASE WHEN c.type = 'benefit' THEN MAX(sc.jumlah_bobot) " +
                    "WHEN c.type = 'cost' THEN MIN(sc.jumlah_bobot) END AS pembagi " +
                    "FROM alternatif AS a " +
                    "JOIN sub_criteria AS sc ON a.id_sub_kreteria = sc.id " +
                    "JOIN criteria AS c ON sc.id_kreteria = c.id " +
                    "GROUP BY c.id, c.type";

            pstmt = dbConnection.prepareStatement(pembagiQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                int idKriteria = resultSet.getInt("id");
                double pembagi = resultSet.getDouble("pembagi");
                String tipe = resultSet.getString("type");

                pembagiMap.put(idKriteria, pembagi);
                tipeMap.put(idKriteria, tipe);
            }
            resultSet.close();
            pstmt.close();

            // Ambil data alternatif dan normalisasikan
            String query = "SELECT p.name, c.id AS id_kriteria, c.nama AS nama_kriteria, sc.jumlah_bobot AS bobot_alternatif " +
                    "FROM alternatif AS a " +
                    "INNER JOIN employees AS p ON a.id_employee = p.id " +
                    "INNER JOIN sub_criteria AS sc ON a.id_sub_kreteria = sc.id " +
                    "INNER JOIN criteria AS c ON sc.id_kreteria = c.id";

            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                AlternatifModel alternatif = new AlternatifModel(0, 0);
                alternatif.setNameAlternatif(resultSet.getString("name"));
                alternatif.setIdKriteria(resultSet.getInt("id_kriteria"));
                alternatif.setNameKriteria(resultSet.getString("nama_kriteria"));

                int idKriteria = resultSet.getInt("id_kriteria");
                double bobot = resultSet.getDouble("bobot_alternatif");
                double pembagi = pembagiMap.get(idKriteria);
                String type = tipeMap.get(idKriteria);

                double normalisasi;
                if ("benefit".equalsIgnoreCase(type)) {
                    normalisasi = bobot / pembagi;
                } else if ("cost".equalsIgnoreCase(type)) {
                    normalisasi = pembagi / bobot;
                } else {
                    normalisasi = 0.0;
                }

                alternatif.setNormalisasi(normalisasi);
                normalisasiList.add(alternatif);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return normalisasiList;
    }
    
    public boolean hasDuplicateAlternatif() {
      String query = "SELECT p.name, c.nama AS nama_kriteria, COUNT(*) as total " +
                     "FROM alternatif AS a " +
                     "INNER JOIN employees AS p ON a.id_employee = p.id " +
                     "INNER JOIN sub_criteria AS sc ON a.id_sub_kreteria = sc.id " +
                     "INNER JOIN criteria AS c ON sc.id_kreteria = c.id " +
                     "GROUP BY p.name, c.nama " +
                     "HAVING COUNT(*) > 1";

      try {
          pstmt = dbConnection.prepareStatement(query);
          resultSet = pstmt.executeQuery();

          // Return true jika ada duplikat, false jika tidak ada
          return resultSet.next();

      } catch (SQLException e) {
          throw new RuntimeException(e);
      } finally {
          closeStatement();
      }
    }

    @Override
    public int deleteBulkByKaryawan(int idPeserta) {
        String query;

        if (hasDuplicateAlternatif()) {
            // Simpan 5 data terbaru, hapus sisanya
            query = "DELETE FROM alternatif " +
                    "WHERE id_employee = ? " +
                    "AND id NOT IN ( " +
                    "    SELECT id FROM ( " +
                    "        SELECT id " +
                    "        FROM alternatif " +
                    "        WHERE id_employee = ? " +
                    "        ORDER BY id DESC " +
                    "        LIMIT 5 " +
                    "    ) AS recent " +
                    ")";
        } else {
            // Hapus semua data milik karyawan tersebut
            query = "DELETE FROM alternatif WHERE id_employee = ?";
        }

        try (PreparedStatement pstmt = dbConnection.prepareStatement(query)) {
            pstmt.setInt(1, idPeserta);

            if (hasDuplicateAlternatif()) {
                pstmt.setInt(2, idPeserta); // Hanya dibutuhkan jika pakai query recent
            }

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Gagal menghapus data alternatif: " + e.getMessage());
            throw new RuntimeException("Database error saat menghapus alternatif.", e);
        } finally {
            closeStatement();
        }
    }




    @Override
    public List<NilaiKaryawan> findNilaiAktual() {
        List<NilaiKaryawan> list = new ArrayList<>();
        String query =
            "SELECT " +
            "    e.id AS id_employee, " +
            "    e.name AS nama_employee, " +
            "    c.id AS id_kriteria, " +
            "    c.nama AS nama_kriteria, " +
            "    sc.jumlah_bobot AS nilai_aktual " +
            "FROM alternatif a " +
            "JOIN employees e ON a.id_employee = e.id " +
            "JOIN sub_criteria sc ON a.id_sub_kreteria = sc.id " +
            "JOIN criteria c ON sc.id_kreteria = c.id;";

        try (PreparedStatement pstmt = dbConnection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int idEmployee = rs.getInt("id_employee");
                String namaEmployee = rs.getString("nama_employee");
                int idKriteria = rs.getInt("id_kriteria");
                String namaKriteria = rs.getString("nama_kriteria");
                double nilaiAktual = rs.getDouble("nilai_aktual");

                NilaiKaryawan nk = new NilaiKaryawan();
                nk.setIdEmployee(idEmployee);
                nk.setNamaKaryawan(namaEmployee);
                nk.setIdKriteria(idKriteria);
                nk.setNamaKriteria(namaKriteria);
                nk.setNilaiAktual(nilaiAktual);

                list.add(nk);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<NilaiIdeal> findNilaiIdeal() {
        List<NilaiIdeal> list = new ArrayList<>();

        String query =
            "SELECT " +
            "    id AS criteria_id, " +
            "    nama AS criteria_name, " +
            "    type AS criteria_type, " +
            "    bobot AS nilai_ideal " +
            "FROM criteria " +
            "ORDER BY id; ";

        try (PreparedStatement pstmt = dbConnection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int idKriteria = rs.getInt("criteria_id");
                String namaKriteria = rs.getString("criteria_name");
                double nilaiIdeal = rs.getDouble("nilai_ideal");
                String typeKriteria = rs.getString("criteria_type");

                list.add(new NilaiIdeal(idKriteria, namaKriteria, nilaiIdeal, typeKriteria));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }



}
