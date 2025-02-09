/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.TransactionPojo;
import java.util.List;

/**
 *
 * @author viren
 */
public interface OrderDao {
    public boolean addOrder(OrderPojo order);
    public boolean addTransaction(TransactionPojo transaction);
    public List <OrderPojo> getAllOrders();
    public List<OrderDetailsPojo> getAllOrdersDetails(String userEmailId);
    public String shipNow(String orderId, String prodId);
    public String paymentSuccess(String username, double paidAmount);
    public int getSoldQuantity(String prodId);
    
    public String getUserIdByOrderId(String orderId);
    public String getUserAddressByUserId(String userId);

}
