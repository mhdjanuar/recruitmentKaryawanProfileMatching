/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package application.dao;

import application.models.RangkingModel;
import application.models.SkorModel;
import java.util.List;

/**
 *
 * @author mhdja
 */
public interface RangkingDao {
    List<RangkingModel> findRangking();
    public void saveAllSkorAkhirToDatabase(List<SkorModel> data);
}
