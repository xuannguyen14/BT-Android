package com.example.food_order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OrderActivity extends AppCompatActivity {

    RadioGroup rdgSize, rdgTortilla;
    RadioButton rbtnLarge, rbtnMedium, rbtnCorn, rbtnFlour;
    CheckBox cbBeef, cbChicken, cbWFish, cbCheese, cbSeefood, cbRice, cbBeans, cbPico, cbGuaca, cbLBT;
    CheckBox cbSoda, cbCerveza, cbMargarita, cbTequila;
    Button btnPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        init();

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("5556", null, "I WANT A BIG TACO - sms message", null, null);

                String phoneNumber = "5556";
                String message = "HUNGRY AGAIN, more tacos ";

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms: " + phoneNumber));
                intent.putExtra("sms_body", message);
                startActivity(intent);
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
    }
}