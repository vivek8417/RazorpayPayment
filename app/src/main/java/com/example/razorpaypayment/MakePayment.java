package com.example.razorpaypayment;

import android.app.Activity;
import android.util.Log;

import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

public class MakePayment {

    RazorpayClient razorpay;

    {
        try {
            razorpay = new RazorpayClient("rzp_test_NyjA1OkH4qjvk8", "6cXEV9zyZlVmCH9geFBaVlEu");
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
    }

    public void setOrderID(Double amount) {
        new SetOrderIDAsyncTask().execute(amount);
    }
//    public Order getOrderID(Double amount) throws RazorpayException {
//        Order order = razorpay.Orders.fetch("order_id");
//        Log.d("aaa", "getOrderID: "+order);
//        return order;
//    }

    public void startPayment(Activity activity, String merchantName,String orderID, String amount, String customerEmail, String customerContact) {

        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_NyjA1OkH4qjvk8");

        try {
            JSONObject options = new JSONObject();

            options.put("name", merchantName);
            options.put("description", "Reference No. #123456");
            options.put("order_id", orderID);//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", amount + "00");//pass amount in currency subunits
            options.put("prefill.email", customerEmail);
            options.put("prefill.contact", customerContact);
            checkout.open(activity, options);
        } catch (Exception e) {

        }
    }

}

