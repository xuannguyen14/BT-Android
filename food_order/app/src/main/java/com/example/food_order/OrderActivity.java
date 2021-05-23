package com.example.food_order;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    RadioGroup rdgSize, rdgTortilla;
    RadioButton rbtnLarge, rbtnMedium, rbtnCorn, rbtnFlour;
    CheckBox cbBeef, cbChicken, cbWFish, cbCheese, cbSeefood, cbRice, cbBeans, cbPico, cbGuaca, cbLBT;
    CheckBox cbSoda, cbCerveza, cbMargarita, cbTequila;
    Button btnPlaceOrder;
    TextView txtListfood;
    int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        init();

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
                        Log.d("permission", "permission denied to SEND_SMS - requesting it");
                        String[] permissions = {Manifest.permission.SEND_SMS};
                        requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                    }
                }
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("5556", null, "I WANT A BIG TACO - sms message", null, null);

                String phoneNumber = "5556";
                String message = "HUNGRY AGAIN, more tacos ";

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms: " + phoneNumber));
                intent.putExtra("sms_body", message);
                startActivity(intent);
            }
        });

        txtListfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInfoAct = new Intent(OrderActivity.this, ListFoodActivity.class);

                startActivity(toInfoAct);
            }
        });

    }

    public void init() {
        rdgSize = (RadioGroup) findViewById(R.id.rdgpSize);
        rdgTortilla = (RadioGroup) findViewById(R.id.rbgrTortilla);
        rbtnLarge = (RadioButton) findViewById(R.id.rbtnLarge);
        rbtnMedium = (RadioButton) findViewById(R.id.rbtnMedium);
        rbtnCorn = (RadioButton) findViewById(R.id.rbtnCorn);
        rbtnFlour = (RadioButton) findViewById(R.id.rbtnFlour);
        cbBeef = (CheckBox) findViewById(R.id.cbBeef);
        cbChicken = (CheckBox) findViewById(R.id.cbChicken);
        cbWFish = (CheckBox) findViewById(R.id.cbFish);
        cbCheese = (CheckBox) findViewById(R.id.cbCheese);
        cbSeefood = (CheckBox) findViewById(R.id.cbSeaFood);
        cbRice = (CheckBox) findViewById(R.id.cbRice);
        cbBeans = (CheckBox) findViewById(R.id.cbBeans);
        cbPico = (CheckBox) findViewById(R.id.cbPico);
        cbGuaca = (CheckBox) findViewById(R.id.cbGuaca);
        cbLBT = (CheckBox) findViewById(R.id.cbLBT);
        cbSoda = (CheckBox) findViewById(R.id.cbSoda);
        cbCerveza = (CheckBox) findViewById(R.id.cbCerveza);
        cbMargarita = (CheckBox) findViewById(R.id.cbMargarita);
        cbTequila = (CheckBox) findViewById(R.id.cbTequila);
        btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);
        txtListfood = findViewById(R.id.txtListfood);
    }
}