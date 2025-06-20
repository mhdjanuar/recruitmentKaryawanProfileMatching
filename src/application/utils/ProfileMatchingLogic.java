/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.utils;

import application.models.GapModel;
import application.models.NilaiIdeal;
import application.models.NilaiKaryawan;
import application.models.SkorModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author mhdja
 */
public class ProfileMatchingLogic {
    public List<GapModel> hitungGap(List<NilaiKaryawan> nilaiAktualList, List<NilaiIdeal> nilaiIdealList) {
        List<GapModel> gapList = new ArrayList<>();

        for (NilaiKaryawan na : nilaiAktualList) {
            for (NilaiIdeal ni : nilaiIdealList) {
                if (na.getIdKriteria() == ni.getIdKriteria()) {
                    gapList.add(new GapModel(
                        na.getIdEmployee(),
                        na.getNamaKaryawan(),   // pastikan sudah include nama
                        ni.getIdKriteria(),
                        ni.getNamaKriteria(),
                        na.getNilaiAktual(),
                        ni.getNilaiIdeal(),
                        ni.getTypeKriteria()
                    ));
                    break;
                }
            }
        }

        return gapList;
    }
    
    public List<SkorModel> hitungSkorAkhir(List<GapModel> gapList) {
        Map<Integer, List<GapModel>> groupedByEmployee = gapList.stream()
            .collect(Collectors.groupingBy(GapModel::getIdEmployee));

        List<SkorModel> result = new ArrayList<>();

        for (Map.Entry<Integer, List<GapModel>> entry : groupedByEmployee.entrySet()) {
            int id = entry.getKey();
            List<GapModel> gaps = entry.getValue();

            String nama = gaps.get(0).getNamaEmployee();
            System.out.println("=======================================");
            System.out.println("Nama Karyawan : " + nama + " (ID: " + id + ")");

            // Pisahkan core & secondary
            List<Double> coreBobots = gaps.stream()
                .filter(g -> g.getTypeKriteria().equalsIgnoreCase("core"))
                .map(GapModel::getBobot)
                .collect(Collectors.toList());

            List<Double> secondBobots = gaps.stream()
                .filter(g -> g.getTypeKriteria().equalsIgnoreCase("second"))
                .map(GapModel::getBobot)
                .collect(Collectors.toList());

            System.out.println("Nilai GAP Core: " + coreBobots);
            System.out.println("Nilai GAP Secondary: " + secondBobots);

            double cf = coreBobots.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            double sf = secondBobots.stream().mapToDouble(Double::doubleValue).average().orElse(0);

            System.out.printf("Rata-rata Core Factor (CF): %.4f\n", cf);
            System.out.printf("Rata-rata Secondary Factor (SF): %.4f\n", sf);

            // Hitung skor akhir (misal: 60% CF + 40% SF)
            double skorAkhir = (cf * 0.6) + (sf * 0.4);
            System.out.printf("Skor Akhir: %.4f = (%.4f x 60%%) + (%.4f x 40%%)\n", skorAkhir, cf, sf);
            System.out.println("=======================================");

            SkorModel skor = new SkorModel();
            skor.setIdEmployee(id);
            skor.setNamaEmployee(nama);
            skor.setCf(cf);
            skor.setSf(sf);
            skor.setSkorAkhir(skorAkhir);

            result.add(skor);
        }

        return result;
    }
    
    public List<SkorModel> rangkingAkhir(List<SkorModel> skorList) {
        skorList.sort((a, b) -> Double.compare(b.getSkorAkhir(), a.getSkorAkhir()));

        System.out.println("\n=========== RANKING AKHIR ===========");

        int peringkat = 1;
        for (SkorModel skor : skorList) {
            System.out.printf("%d. %s (ID: %d) - Skor Akhir: %.4f\n",
                peringkat++, skor.getNamaEmployee(), skor.getIdEmployee(), skor.getSkorAkhir());
        }

        System.out.println("=====================================\n");

        return skorList;
    }

}
