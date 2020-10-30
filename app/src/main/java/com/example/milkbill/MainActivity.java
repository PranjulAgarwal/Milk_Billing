package com.example.milkbill;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    DatabaseHelper myDB;
    Button cal,hist,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        EditText vDate = (EditText) findViewById(R.id.editTextDate);
        vDate.setText(currentDate);
        TextView vBill = (TextView) findViewById(R.id.textBill);
        EditText vPay = (EditText) findViewById(R.id.editTextPayment);
        EditText vQuantity = (EditText) findViewById(R.id.editQuantity);
        Cursor res =  myDB.getAllData();
        if(res.getCount()!=0) {
            res.moveToLast();
            vBill.setText(res.getString(3));
        }




        cal = (Button) findViewById(R.id.calculate);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    vPay.setText("0");
                }

                else
                cPay = Double.parseDouble(vPay.getText().toString());

                cBill+=quantities*50-cPay;
                DecimalFormat formatVal = new DecimalFormat("##.##");
                vBill.setText(formatVal.format(cBill));

                boolean isInserted = myDB.insertData(vDate.getText().toString(),vQuantity.getText().toString(),vBill.getText().toString(),vPay.getText().toString());
                if(isInserted)
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data is NOT Inserted",Toast.LENGTH_LONG).show();
            }
        });

        hist = (Button) findViewById(R.id.history);
        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cursor res =  myDB.getAllData();
               if(res.getCount()==0){
                   Toast.makeText(MainActivity.this,"NO DATA",Toast.LENGTH_LONG).show();
                   return;
               }
               StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID : "+res.getString(0)+"\n Date : "+res.getString(1)+"\n Quantity = "+res.getString(2)+"kg \n Bill =  "+res.getString(3)+"\n Paid =  "+res.getString(4)+"\n\n");
                }
                showMessage("Data",buffer.toString());
            }
        });

        delete = (Button) findViewById(R.id.reset2);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteData();
                vBill.setText("0");
                Toast.makeText(MainActivity.this, "ALL DATA ERASED",Toast.LENGTH_LONG).show();
                return;
            }
        });

    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
