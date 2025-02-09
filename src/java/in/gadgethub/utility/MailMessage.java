/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.utility;

import javax.mail.MessagingException;

/**
 *
 * @author viren
 */
public class MailMessage {
    public static void registrationSuccess(String recipientMailId, String name) throws MessagingException {
        String subject = "Registration Successfull";
        String htmlTextMessage = "" + "<html>" + "<body>"
                + "<h2 style='color:green;'>Welcome to " + AppInfo.appName + "</h2>" + "" + "Hi " + name + ","
                + "<br><br>Thanks for singing up with " + AppInfo.appName + ".<br>"
                + "We are glad that you choose us. We invite you to check out our latest collection of new electonics appliances."
                + "<br>We are providing upto 60% OFF on most of the electronic gadgets. So please visit our site and explore the collections."
                + "<br><br>Our Online electronics is growing in a larger amount these days and we are in high demand so we thanks all of you for "
                + "making us up to that level. We Deliver Product to your house with no extra delivery charges and we also have collection of most of the"
                + "branded items.<br><br>As a Welcome gift for our New Customers we are providing additional 10% OFF Upto 500 Rs for the first product purchase. "
                + "<br>To avail this offer you only have "
                + "to enter the promo code given below.<br><br><br> PROMO CODE: " + "" + AppInfo.appName.toUpperCase() + "500<br><br><br>"
                + "Have a good day!<br>" + "" + "</body>" + "</html>";
        JavaMailUtil.sendMail(recipientMailId, subject, htmlTextMessage);
    }
    
public static void transactionSuccess(String recipientMailId, String name, String transactionId, double amount) throws MessagingException {
        String subject = "Transaction Successful - Transaction ID: " + transactionId;
        String htmlTextMessage = "" +
                "<html>" +
                "<body>" +
                "<h2 style='color:green;'>Payment Successful at " + AppInfo.appName + "!</h2>" +
                "Hi " + name + "," +
                "<br><br>Your transaction has been successfully processed." +
                "<br><br><strong>Transaction Details:</strong><br>" +
                "Transaction ID: <b>" + transactionId + "</b><br>" +
                "Amount Paid: <b>₹" + amount + "</b><br>" +
                "<br>We appreciate your trust in us and look forward to serving you again." +
                "<br><br>If you have any questions, feel free to contact our support team." +
                "<br><br>Best Regards,<br>" +
                AppInfo.appName + " Team" +
                "</body>" +
                "</html>";

        JavaMailUtil.sendMail(recipientMailId, subject, htmlTextMessage);
    }

    public static void shippedSuccess(String recipientMailId, String name, String orderId) throws MessagingException {
        String subject = "Your Order #" + orderId + " Has Been Shipped!";
        String htmlTextMessage = "" +
                "<html>" +
                "<body>" +
                "<h2 style='color:green;'>Good News! Your Order Has Been Shipped</h2>" +
                "Hi " + name + "," +
                "<br><br>Your order with <b>Order ID: " + orderId + "</b> has been successfully shipped." +
                "<br><br>Our team has processed and dispatched your order. It will be delivered to your shipping address soon." +
                "<br><br>We appreciate your trust in " + AppInfo.appName + "!" +
                "<br>If you have any questions, feel free to contact our support team." +
                "<br><br>Best Regards,<br>" +
                AppInfo.appName + " Team" +
                "</body>" +
                "</html>";

        JavaMailUtil.sendMail(recipientMailId, subject, htmlTextMessage);
    }
}



