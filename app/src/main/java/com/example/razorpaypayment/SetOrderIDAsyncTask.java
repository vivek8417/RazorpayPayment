package com.example.razorpaypayment;

import android.os.AsyncTask;
import android.util.Log;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

public class SetOrderIDAsyncTask extends AsyncTask<Double, Void, Order> {
    public OrderInterface orderInterface = null;


    @Override
    protected Order doInBackground(Double... doubles) {
        Log.d("aaa", "doInBackground: "+doubles[0].intValue());
        RazorpayClient razorpay = null;

        {
            try {
                razorpay = new RazorpayClient("rzp_test_NyjA1OkH4qjvk8", "6cXEV9zyZlVmCH9geFBaVlEu");
            } catch (RazorpayException e) {
                e.printStackTrace();
            }
        }
        Order order = null;
        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", doubles[0].intValue()); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");


            order = razorpay.Orders.create(orderRequest);
            order = razorpay.Orders.fetch("order_id");
        } catch (RazorpayException | JSONException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    protected void onPostExecute(Order order) {
        super.onPostExecute(order);

        orderInterface.afterTask(order);

        Log.d("aaa", "onPostExecute: "+order.get("id"));
    }
}
