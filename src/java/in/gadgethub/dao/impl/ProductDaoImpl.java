/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.ProductDao;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDutil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author viren
 */
public class ProductDaoImpl implements ProductDao {
    
      public String addProduct(ProductPojo product){
        String status="Product Registration is failed";
        if(product.getProdId()==null){
            product.setProdId(IDutil.generateProdId());
        }
        Connection conn=DBUtil.provideConnection();
       PreparedStatement ps=null;
       try{
           ps=conn.prepareStatement("Insert into Products values(?,?,?,?,?,?,?,?)");
           ps.setString(1,product.getProdId());
           ps.setString(2,product.getProdName());
           ps.setString(3,product.getProdType());
           ps.setString(4,product.getProdInfo());
           ps.setDouble(5,product.getProdPrice());
           ps.setInt(6,product.getProdQuantity());
           ps.setBlob(7,product.getProdImage());
           ps.setString(8, "Y");
           int count = ps.executeUpdate();
           if(count==1){
               status="Product Added Successful With Id:"+product.getProdId();
               // code email
           }
    }
       catch(SQLException ex){
           System.out.println("error in addProduct"+ex);
           ex.printStackTrace();
            }
       DBUtil.closeStatement(ps);
       return status;
}

      public String updateProduct (ProductPojo prevProduct, ProductPojo updatedProduct){
          String status="Updation failed";
        if(prevProduct.getProdId().equals(updatedProduct.getProdId())){
            status="Product Id's DO NOT MATCH";
            return status;
                    }
        Connection conn=DBUtil.provideConnection();
       PreparedStatement ps=null;
       try{
       ps=conn.prepareStatement("Update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=?,image=? where pid=?");

       ps.setString(1,updatedProduct.getProdName());
       ps.setString(2,updatedProduct.getProdType()); 
       ps.setString(3,updatedProduct.getProdInfo());
       ps.setDouble(4,updatedProduct.getProdPrice());
       ps.setInt(5,updatedProduct.getProdQuantity());
       ps.setBlob(6,updatedProduct.getProdImage()); 
       ps.setString(7,updatedProduct.getProdId());

       int count=ps.executeUpdate();

       if(count==1){
          status= "Product Updated Successfully";
        }
      }
     catch(SQLException ex){
        System.out.println("Error in update Product:"+ex);
        ex.printStackTrace();
      }
        DBUtil.closeStatement(ps);

        return status;
      }
      
      public String updateProductPrice(String prodId, double prodPrice){
          String status="Price updation failed";
        Connection conn=DBUtil.provideConnection();
       PreparedStatement ps=null;
       try{
           ps=conn.prepareStatement("Update products set pprice=? where pid=?");
           ps.setDouble(1,prodPrice);
           ps.setString(2,prodId);
           int count = ps.executeUpdate();
           if(count==1){
               status="Product Updated Successfully";
               // code email
           }
    }
       catch(SQLException ex){
           System.out.println("error in addProduct"+ex);
           ex.printStackTrace();
            }
       DBUtil.closeStatement(ps);
       return status;
      }

       public List<ProductPojo>getAllProducts(){
           List<ProductPojo> productList=new ArrayList();
           
           Connection conn=DBUtil.provideConnection();
           Statement st=null;
           ResultSet rs=null;
           
           try{
               st=conn.createStatement();
               rs=st.executeQuery("select * from Products where available='Y'" );
               while(rs.next()){
                   ProductPojo product=new ProductPojo();
                   product.setProdId(rs.getString("pid"));
                   product.setProdName(rs.getString("pname"));
                   product.setProdPrice(rs.getDouble("pprice"));
                   product.setProdType(rs.getString("ptype"));
                   product.setProdInfo(rs.getString("pinfo"));
                   product.setProdQuantity(rs.getInt("pquantity"));
                   product.setProdImage(rs.getAsciiStream("image"));
                   productList.add(product);
           }
           }
           catch(SQLException ex){
               System.out.println("Error in getAllProducts: "+ex);
               ex.printStackTrace();
           }
           DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(st);
          return productList;
       }
       
       public List <ProductPojo>getAllProductsByType(String ptype){
            List<ProductPojo> productList=new ArrayList();
           Connection conn=DBUtil.provideConnection();
           PreparedStatement ps=null;
           ResultSet rs=null;
                     
           try{
               ps=conn.prepareStatement("select * from Products where lower(ptype) like ? and available='Y'");
               ps.setString(1,"%"+ptype+"%");
               rs=ps.executeQuery();
               while(rs.next()){
                   ProductPojo product=new ProductPojo();
                   product.setProdId(rs.getString("pid"));
                   product.setProdName(rs.getString("pname"));
                   product.setProdPrice(rs.getDouble("pprice"));
                   product.setProdType(rs.getString("ptype"));
                   product.setProdInfo(rs.getString("pinfo"));
                   product.setProdQuantity(rs.getInt("pquantity"));
                   product.setProdImage(rs.getAsciiStream("image"));
                   productList.add(product);
           }
           }
           catch(SQLException ex){
               System.out.println("Error in getAllProductsByType: "+ex);
               ex.printStackTrace();
           }
           DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(ps);
          return productList;
       }
       
       public List<ProductPojo> searchAllProducts (String searchTerm){
            List<ProductPojo> productList=new ArrayList();
           Connection conn=DBUtil.provideConnection();
           PreparedStatement ps=null;
           ResultSet rs=null;
            searchTerm=searchTerm.toLowerCase(); 
           
           try{
               ps=conn.prepareStatement("select * from Products where lower(ptype) like ? or lower(pname) like ? or lower(pinfo)  like ? and available='Y'");
               
               ps.setString(1,"%"+searchTerm+"%");
               ps.setString(2,"%"+searchTerm+"%");
               ps.setString(3,"%"+searchTerm+"%");
               rs=ps.executeQuery();
               while(rs.next()){
                   ProductPojo product=new ProductPojo();
                   product.setProdId(rs.getString("pid"));
                   product.setProdName(rs.getString("pname"));
                   product.setProdPrice(rs.getDouble("pprice"));
                   product.setProdType(rs.getString("ptype"));
                   product.setProdInfo(rs.getString("pinfo"));
                   product.setProdQuantity(rs.getInt("pquantity"));
                   product.setProdImage(rs.getAsciiStream("image"));
                   productList.add(product);
           }
           }
           catch(SQLException ex){
               System.out.println("Error in searchAllProducts: "+ex);
               ex.printStackTrace();
           }
           DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(ps);
          return productList;
       
       }
       
       public ProductPojo getProductDetails(String prodId){
           ProductPojo product=null;
           Connection conn=DBUtil.provideConnection();
           PreparedStatement ps=null;
           ResultSet rs=null;
           
            try{
               ps=conn.prepareStatement("select * from products where pid=?");
               ps.setString(1, prodId);
               rs=ps.executeQuery();
               if(rs.next()){
                   product=new ProductPojo();
                   product.setProdId(rs.getString("pid"));
                   product.setProdName(rs.getString("pname"));
                   product.setProdPrice(rs.getDouble("pprice"));
                   product.setProdType(rs.getString("ptype"));
                   product.setProdInfo(rs.getString("pinfo"));
                   product.setProdQuantity(rs.getInt("pquantity"));
                   product.setProdImage(rs.getAsciiStream("image"));
           }
           }
           catch(SQLException ex){
               System.out.println("Error in getProductDetails: "+ex);
               ex.printStackTrace();
           }
           DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(ps);
          return product;
       }
       
       public int getProductQuantity(String prodid){
           int quantity =0;
           Connection conn=DBUtil.provideConnection();
           PreparedStatement ps=null;
           ResultSet rs=null;
        try{
           ps=conn.prepareStatement("select pquantity from products where pid=?");
            ps.setString(1,prodid);
            rs=ps.executeQuery();
           if(rs.next()){
               quantity=rs.getInt(1);
           }
    }
       catch(SQLException ex){
           System.out.println("error in getProductQuantity"+ex);
           ex.printStackTrace();
            }
       DBUtil.closeStatement(ps);
       DBUtil.closeResultSet(rs);
       return quantity;
      
       }
       
       public String updateProductWithoutImage(String prevProdId, ProductPojo updatedProduct){
           String status="Updation failed:";
           int prevQuantity=0;
           
            if(!prevProdId.equals(updatedProduct.getProdId())){
                status="Product ID's Do not match. Updation failed:";
                        return status;
        }
           Connection conn=DBUtil.provideConnection();
           PreparedStatement ps=null;
           
          try{
              prevQuantity=getProductQuantity(prevProdId);
              ps=conn.prepareStatement("Update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=? where pid=?");

              ps.setString(1,updatedProduct.getProdName());
              ps.setString(2,updatedProduct.getProdType()); 
              ps.setString(3,updatedProduct.getProdInfo());
              ps.setDouble(4,updatedProduct.getProdPrice());
              ps.setInt(5,updatedProduct.getProdQuantity());
//              ps.setBlob(6,updatedProduct.getProdImage()); 
              ps.setString(6,updatedProduct.getProdId());

              int count=ps.executeUpdate();
              if(count==1 && prevQuantity<updatedProduct.getProdQuantity()){
              status= "Product Updated Successfully";
             }
              else if(count==1){
                  status= "Product Updated Successfully";
              }
              
           }
            catch(SQLException ex){
              System.out.println("Error in update ProductWithoutImage:"+ex);
              ex.printStackTrace();
            }
             DBUtil.closeStatement(ps);
             return status;
       }
       
       public double getProductPrice(String prodId){
           double price =0.0;
           Connection conn=DBUtil.provideConnection();
           PreparedStatement ps=null;
           ResultSet rs=null;
        try{
           ps=conn.prepareStatement("select pprice from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
           if(rs.next()){
               price=rs.getDouble(1);
           }
    }
       catch(SQLException ex){
           System.out.println("error in getProductPrice"+ex);
           ex.printStackTrace();
            }
       DBUtil.closeStatement(ps);
       DBUtil.closeResultSet(rs);
       return price;
       }
       
       public Boolean sellNProduct(String prodId, int n){
           boolean result =false;
        Connection conn=DBUtil.provideConnection();
       PreparedStatement ps=null;
       try{
           ps=conn.prepareStatement("Update products set pquantity=(pquantity-?) where pid=? and available='Y'");
           ps.setInt(1, n);
           ps.setString(2,prodId);
           int count = ps.executeUpdate();
           if(count==1){
               return true;
           }
    }
       catch(SQLException ex){
           System.out.println("error in sellNProduct"+ex);
           ex.printStackTrace();
            }
       DBUtil.closeStatement(ps);
       return result;
       }
       
       public List<String>getAllProductsType(){
            List<String> productTypeList=new ArrayList<>();
           Connection conn=DBUtil.provideConnection();
           Statement st=null;
           ResultSet rs=null;
           try{
               st=conn.createStatement();
                rs=st.executeQuery("select distinct ptype from products");
               while(rs.next()){
                   
                   productTypeList.add(rs.getString(1));
           }
           }
           catch(SQLException ex){
               System.out.println("Error in getAllProductsType:"+ex);
               ex.printStackTrace();
           }
           DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(st);
          return productTypeList;
       }
       
       public byte[] getImage(String prodId){
           byte[]arr=null;
           Connection conn=DBUtil.provideConnection();
           PreparedStatement ps=null;
           ResultSet rs=null;
        try{
           ps=conn.prepareStatement("select image from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
           if(rs.next()){
               arr=rs.getBytes(1);
           }
    }
       catch(SQLException ex){
           System.out.println("error in getImage: "+ex);
           ex.printStackTrace();
            }
       DBUtil.closeStatement(ps);
       DBUtil.closeResultSet(rs);
       return arr;
       }
       
       public String removeProduct(String prodId){
           System.out.println(prodId);
           String status="Product Removal Failed";
           Connection conn = DBUtil.provideConnection();
           PreparedStatement ps1=null;
           PreparedStatement ps2=null;
           
           try{
           ps1=conn.prepareStatement("update Products set available='N' where pid=? and available='Y'");
           ps1.setString(1, prodId);
           int k=ps1.executeUpdate();
//               System.out.println(k);
               
           if(k>0){
               status="Product Remove Successfully";
               ps2=conn.prepareStatement("Delete from usercart where prodid=?");
               ps2.setString(1, prodId);
               k=ps2.executeUpdate();
           }
//           else{
//               System.out.println("Hiiii giii");}
       }
           catch(SQLException ex){
               status="Error" + ex.getMessage();
               System.out.println("Error in remove product "+ ex);
               ex.printStackTrace();
           }
           
           DBUtil.closeStatement(ps1);
            DBUtil.closeStatement(ps2);
            
            return status;
       }
}
