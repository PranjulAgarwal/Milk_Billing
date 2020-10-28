package com.example.milkbill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    Spinner spinner;
    Button cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        EditText vDate = (EditText) findViewById(R.id.editTextDate);
        vDate.setText(currentDate);



        cal = (Button) findViewById(R.id.calculate);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView vBill = (TextView) findViewById(R.id.textBill);
                EditText vPay = (EditText) findViewById(R.id.editTextPayment);
                EditText vQuantity = (EditText) findViewById(R.id.editQuantity);

                if(TextUtils.isEmpty(vQuantity.getText().toString()))
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Quantity Can't Be Empty!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }

                double quantities = Double.parseDouble(vQuantity.getText().toString());
                double cBill = Double.parseDouble(vBill.getText().toString());
                double cPay;

                if(TextUtils.isEmpty(vPay.getText().toString()))
                {
                    cPay=0;
                }

                else
                cPay = Double.parseDouble(vPay.getText().toString());

                cBill+=quantities*50-cPay;
                DecimalFormat formatVal = new DecimalFormat("##.##");
                vBill.setText(formatVal.format(cBill));
            }
        });
    }
}
