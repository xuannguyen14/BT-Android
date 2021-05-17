package com.example.candfconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.candfconverter.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String HISTORY_KEY = "history";
    String History;
    TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        history = (TextView) findViewById(R.id.History);
        if (savedInstanceState != null){
            History = savedInstanceState.getString(HISTORY_KEY);
            history.setText(History);
        }
        else
            History = "";

        Button btnCToF = (Button)findViewById(R.id.ctof);
        btnCToF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CToF(v);
            }
        });

        Button btnFToC = (Button)findViewById(R.id.ftoc);
        btnFToC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FToC(v);
            }
        });

        Button btnClear = (Button)findViewById(R.id.clear_text);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clear(v);
            }
        });
    }

    public void CToF(View view){
        EditText editCToF = (EditText)findViewById(R.id.edittf);
        EditText editFToC = (EditText)findViewById(R.id.edittc);
        String checkC = editCToF.getText().toString();

        if(!checkC.matches("")) {
            boolean isNumber = true;

            for (int i = 0; i < checkC.length(); i++) {
                if (checkC.charAt(i) != '.' && checkC.charAt(i) != '+' && checkC.charAt(i) != '-' && (checkC.charAt(i) < '0' || checkC.charAt(i) > '9')) {
                    isNumber = false;
                    break;
                }
            }

            if (isNumber) {
                convert conv = new convert();
                double doC = Double.parseDouble(checkC);
                conv.setDoC(doC);
                double doF = conv.convertCToF();
                editFToC.setText(String.valueOf(doF));
                History += conv.convertString();
                history.setText(History);
            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Invalid Input")
                        .setMessage("Your Input is NOT a NUMBER!")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                Clear(view);
            }
        }
        else{
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Invalid Input")
                    .setMessage("Please Give An Input!")
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
    }

    public void FToC(View view){
        EditText editCToF = (EditText)findViewById(R.id.edittf);
        EditText editFToC = (EditText)findViewById(R.id.edittc);

        String checkF = editFToC.getText().toString();
        if(!checkF.matches("")) {
            boolean isNumber = true;

            for (int i = 0; i < checkF.length(); i++) {
                if (checkF.charAt(i) != '.' && checkF.charAt(i) != '+' && checkF.charAt(i) != '-' && (checkF.charAt(i) < '0' || checkF.charAt(i) > '9')) {
                    isNumber = false;
                    break;
                }
            }

            if (isNumber) {
                convert conv = new convert();
                double doF = Double.parseDouble(checkF);
                conv.setDoF(doF);
                double doC = conv.convertFToC();
                editCToF.setText(String.valueOf(doC));
                History += conv.convertString();
                history.setText(History);
            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Invalid Input")
                        .setMessage("Your Input is NOT a NUMBER!")
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                Clear(view);
            }
        }
        else{
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Invalid Input")
                    .setMessage("Please Give An Input!")
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
    }

    public void Clear(View view){
        EditText editCToF = (EditText)findViewById(R.id.edittf);
        EditText editFToC = (EditText)findViewById(R.id.edittc);
        convert conv = new convert();
        editCToF.setText("");
        editFToC.setText("");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(HISTORY_KEY, History);
    }
}