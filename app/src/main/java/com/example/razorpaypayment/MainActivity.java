package com.example.razorpaypayment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayException;

public class MainActivity extends AppCompatActivity implements PaymentResultWithDataListener, OrderInterface {
    Button pay;
    TextView payment_id, order_id, signature;
    EditText et_amount, et_email, et_contact;
    MakePayment makePayment = new MakePayment();
    String merchantName;
    String email;
    String contact;
    Double amount;

    //OrderInterface orderInterface = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());

        payment_id = findViewById(R.id.tv_razorpay_payment_id);
        order_id = findViewById(R.id.tv_razorpay_order_id);
        signature = findViewById(R.id.tv_razorpay_signature);
        et_amount = findViewById(R.id.et_amount);
        et_email = findViewById(R.id.et_email);
        et_contact = findViewById(R.id.et_contact);

        //orderInterface=this;
        pay = findViewById(R.id.btn_pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchantName = "Dayal Infosystems";
                amount = Double.parseDouble(et_amount.getText().toString());
                email = et_email.getText().toString();
                contact = et_contact.getText().toString();
                SetOrderIDAsyncTask setOrderIDAsyncTask = new SetOrderIDAsyncTask();
                setOrderIDAsyncTask.orderInterface = MainActivity.this;
                setOrderIDAsyncTask.execute(amount*100);
                Log.d("aaa", "onClick: ");

//                makePayment.startPayment(MainActivity.this, merchantName, String.valueOf(amount*100), email, contact);
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        try {

            Log.d("payment", "onPaymentSuccess: " + Thread.currentThread());
            //Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // set title
            builder.setTitle("Payment ID");
            // set message
            builder.setMessage(s);

            builder.setPositiveButton("ok", null);

            builder.show();
            payment_id.setText("payment Id is: " + paymentData.getPaymentId());
            order_id.setText("order Id is " + paymentData.getOrderId());
            signature.setText("razorpay signature: " + paymentData.getSignature());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
            Toast.makeText(this, "Transaction failed :" + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTask(Order order) {
        Log.d("aaa", "afterTask: " + order);

        makePayment.startPayment(MainActivity.this, merchantName, order.get("id"), String.valueOf(amount * 100), email, contact);

    }
}