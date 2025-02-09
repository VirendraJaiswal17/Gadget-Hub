/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.DemandPojo;
import java.util.List;

/**
 *
 * @author viren
 */
public interface DemandDao {
    public boolean addProduct(DemandPojo demandPojo);
     public boolean removeProduct(String userid, String prodid);
      public List <DemandPojo> havaDemanded(String prodid);
    
}
