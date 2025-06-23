/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package application.views;

import application.dao.AlternatifDao;
import application.dao.RangkingDao;
import application.daoimpl.AlternatifDaoImpl;
import application.daoimpl.RangkingDaoImpl;
import application.models.AlternatifModel;
import application.models.GapModel;
import application.models.NilaiIdeal;
import application.models.NilaiKaryawan;
import application.models.RangkingModel;
import application.models.SkorModel;
import application.utils.DatabaseUtil;
import application.utils.ProfileMatchingLogic;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author mhdja
 */
public class PerhitunganSAW extends javax.swing.JPanel {
    public final AlternatifDao alternatifDao;
    public final RangkingDao rangkingDao;
    
    public void getDataNilaiAktual() {
        List<NilaiKaryawan> nilaiAktualList = alternatifDao.findNilaiAktual(); // Ambil data nilai aktual

        // Buat model tabel
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Nama Peserta", "Nama Kriteria", "Nilai Aktual"});

        // Isi data ke tabel
        for (NilaiKaryawan alternatif : nilaiAktualList) {
            model.addRow(new Object[]{
                alternatif.getNamaKaryawan(),
                alternatif.getNamaKriteria(),
                String.format("%.4f", alternatif.getNilaiAktual())
            });
        }

        // Tampilkan di JTable
        jTable1.setModel(model);
    }
    
    public void getNilaiIdeal() {
        List<NilaiIdeal> data = alternatifDao.findNilaiIdeal();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Nama Kriteria", "Nilai Ideal"});

        for (NilaiIdeal ni : data) {
            model.addRow(new Object[]{
                ni.getNamaKriteria(),
                String.format("%.4f", ni.getNilaiIdeal())
            });
        }

        jTable5.setModel(model); // sesuaikan dengan JTable kamu
    }
    
    public void getNilaiPerhitunganGAP() {
        ProfileMatchingLogic pm = new ProfileMatchingLogic();
        
        List<GapModel> gapList = pm.hitungGap(alternatifDao.findNilaiAktual(), alternatifDao.findNilaiIdeal());

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Nama Karyawan", "Nama Kriteria", "Aktual", "Ideal", "GAP", "Bobot"});

        for (GapModel gap : gapList) {
            model.addRow(new Object[]{
                gap.getNamaEmployee(),
                gap.getNamaKriteria(),
                String.format("%.4f", gap.getNilaiAktual()),
                String.format("%.4f", gap.getNilaiIdeal()),
                String.format("%.4f", gap.getGap()),
                String.format("%.2f", gap.getBobot())
            });
        }

        jTable7.setModel(model);
    }
    
    public void getNilaiCFAndSF() {
        ProfileMatchingLogic pm = new ProfileMatchingLogic();
        
        List<GapModel> gapList = pm.hitungGap(alternatifDao.findNilaiAktual(), alternatifDao.findNilaiIdeal());
        
        List<SkorModel> skorList = pm.hitungSkorAkhir(gapList);
        
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Nama Karyawan", "CF", "SF", "Skor Akhir"});

        for (SkorModel skor : skorList) {
            model.addRow(new Object[]{
                skor.getNamaEmployee(),
                String.format("%.2f", skor.getCf()),
                String.format("%.2f", skor.getSf()),
                String.format("%.2f", skor.getSkorAkhir())
            });
        }

        jTable6.setModel(model);
    }
    
    public void getRangking() {
        ProfileMatchingLogic pm = new ProfileMatchingLogic();
        
        List<GapModel> gapList = pm.hitungGap(alternatifDao.findNilaiAktual(), alternatifDao.findNilaiIdeal());
        
        List<SkorModel> skorList = pm.hitungSkorAkhir(gapList);
        
        List<SkorModel> rangking = pm.rangkingAkhir(skorList);
        
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Nama Karyawan", "Skor Akhir"});

        for (SkorModel skor : rangking) {
            model.addRow(new Object[]{
                skor.getNamaEmployee(),
                String.format("%.2f", skor.getSkorAkhir())
            });
        }

        jTable8.setModel(model);
    }


    /**
     * Creates new form PerhitunganSAW
     */
    public PerhitunganSAW() {
        this.alternatifDao = new AlternatifDaoImpl();
        this.rangkingDao = new RangkingDaoImpl();
        
        initComponents();
        
        String rumus = "<html>" +
            "<b>Rumus Perhitungan:</b><br>" +
            "CF = Rata-rata Bobot dari kriteria <i>Core Factor</i><br>" +
            "SF = Rata-rata Bobot dari kriteria <i>Secondary Factor</i><br>" +
            "Skor Akhir = (CF × 60%) + (SF × 40%)" +
            "</html>";
        jLabelRumus.setText(rumus);

        
        getDataNilaiAktual();
        getNilaiIdeal();
        getNilaiPerhitunganGAP();
        getNilaiCFAndSF();
        getRangking();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelRumus = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("NILAI AKTUAL");

        jButton1.setText("CETAK LAPORAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable6);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("NILAI IDEAL");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("PERHITUNGAN GAP DAN BOBOT KONVERSI ");

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable7);

        jLabel4.setText("pengurangan dari nilai aktual dan ideal (aktual - ideal)");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("RATA-RATA CORE & SECONDARY + SKOR AKHIR");

        jLabelRumus.setText("jLabel6");

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(jTable8);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("RANGKING AKHIR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 584, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane7)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabelRumus)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel5)
                .addGap(2, 2, 2)
                .addComponent(jLabelRumus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1111, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // Inisialisasi logic dan ambil data rangking
            ProfileMatchingLogic pm = new ProfileMatchingLogic();

            List<GapModel> gapList = pm.hitungGap(
                    alternatifDao.findNilaiAktual(),
                    alternatifDao.findNilaiIdeal()
            );

            List<SkorModel> skorList = pm.hitungSkorAkhir(gapList);
            List<SkorModel> rangking = pm.rangkingAkhir(skorList);

            // Bangun konten HTML untuk parameter
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<div style='font-family: Arial, sans-serif; font-size: 12px;'>");
            htmlContent.append("<ol style='padding-left: 15px;'>");

            for (SkorModel skor : rangking) {
                htmlContent.append("<li style='margin-bottom: 5px;'>");
                htmlContent.append("<strong style='color: #2E86C1;'>")
                           .append(skor.getNamaEmployee())
                           .append("</strong>");
                htmlContent.append(" - <span style='color: #28B463;'>Skor: ")
                           .append(String.format("%.2f", skor.getSkorAkhir()))
                           .append("</span>");
                htmlContent.append("</li>");
            }

            htmlContent.append("</ol>");
            htmlContent.append("</div>");


            
            
            // Load template Jasper
            String templateName = "ReportRangkingBIG.jrxml";
            InputStream reportStream = ReportView.class.getResourceAsStream("/resources/reports/" + templateName);
            JasperDesign jd = JRXmlLoader.load(reportStream);

            // Koneksi database
            Connection dbConnection = DatabaseUtil.getInstance().getConnection();

            // Kompilasi laporan
            JasperReport jr = JasperCompileManager.compileReport(jd);

            // Kirim parameter ke Jasper
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("PATH", "src/resources/images/");
            parameter.put("Parameter1", htmlContent.toString()); // ini isi ranking-nya

            // Generate dan tampilkan laporan
            JasperPrint jp = JasperFillManager.fillReport(jr, parameter, dbConnection);
            JasperViewer.viewReport(jp, false);

        } catch (JRException ex) {
            Logger.getLogger(ReportView.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelRumus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    // End of variables declaration//GEN-END:variables
}
