package com.example.curencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String contentS, rawURL;
    double finalRate;
    ArrayList<Currency> currencies;
    ArrayList<String> nations;

    ArrayAdapter adapter;
    Spinner spnCurrencyFrom, spnCurrencyTo;
    EditText txtCurrencyFrom, txtCurrencyTo;
    TextView history, rate;
    Button btnConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnCurrencyFrom = (Spinner) findViewById(R.id.spnCurrencyFrom);
        spnCurrencyTo = (Spinner)  findViewById(R.id.spnCurrencyTo);
        txtCurrencyFrom = (EditText) findViewById(R.id.txtCurrencyFrom);
        txtCurrencyTo = (EditText) findViewById(R.id.txtCurrencyTo);
        btnConvert = (Button) findViewById(R.id.btnConvert);
        rate = (TextView) findViewById(R.id.rate);
        history = (TextView)  findViewById(R.id.History);

        rawURL = ".fxexchangerate.com/rss.xml";
        contentS = "";
        currencies = new ArrayList<>();
        nations = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nations);
        //adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCurrencyFrom.setAdapter(adapter);
        spnCurrencyTo.setAdapter(adapter);

        new ReadJSON().execute("http://api.geonames.org/countryInfoJSON?username=khoaht");

//        getRateList(currencies.get(findCodeFrom(spnCurrencyFrom.getSelectedItem().toString())).getCode());
//        finalRate = currencies.get(findCodeFrom(spnCurrencyTo.getSelectedItem().toString())).getRate();
//
//        txtCurrencyTo.setEnabled(false);
//        rate.setText(String.valueOf(finalRate));
//
//        spnCurrencyFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                getRateList(currencies.get(findCodeFrom(spnCurrencyFrom.getSelectedItem().toString())).getCode());
//                finalRate = currencies.get(findCodeFrom(spnCurrencyTo.getSelectedItem().toString())).getRate();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) { }
//        });
//
//        spnCurrencyTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                finalRate = currencies.get(findCodeFrom(spnCurrencyTo.getSelectedItem().toString())).getRate();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) { }
//        });
//
//        btnConvert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String checkC = txtCurrencyFrom.getText().toString();
//                String History = "";
//
//                if(!checkC.matches("")) {
//                    boolean isNumber = true;
//
//                    for (int i = 0; i < checkC.length(); i++) {
//                        if (checkC.charAt(i) != '.' && (checkC.charAt(i) < '0' || checkC.charAt(i) > '9')) {
//                            isNumber = false;
//                            break;
//                        }
//                    }
//
//                    if (isNumber) {
//                        double cInput = Double.parseDouble(checkC);
//                        double cOutput = cInput * finalRate;
//                        txtCurrencyTo.setText(String.valueOf(cOutput));
//
//                        Currency from = currencies.get(findCodeFrom(spnCurrencyFrom.getSelectedItem().toString()));
//                        Currency to = currencies.get(findCodeFrom(spnCurrencyFrom.getSelectedItem().toString()));
//                        History += String.valueOf(cInput) + " " + from.getNation() + " " + from.getCode() + " to " + String.valueOf(cOutput) + " " + to.getNation() + " " + to.getCode();
//                        history.setText(History);
//                    } else {
//                        new AlertDialog.Builder(MainActivity.this)
//                                .setTitle("Invalid Input")
//                                .setMessage("Your Input is NOT a NUMBER!")
//                                .setNegativeButton(android.R.string.no, null)
//                                .show();
//                    }
//                }
//                else{
//                    new AlertDialog.Builder(MainActivity.this)
//                            .setTitle("Invalid Input")
//                            .setMessage("Please Give An Input!")
//                            .setNegativeButton(android.R.string.no, null)
//                            .show();
//                }
//            }
//        });
    }

    public int findCodeFrom (String key){

        for(int i=0; i < currencies.size(); i++){
            if(currencies.get(i).getNation().equals(key) || currencies.get(i).getCode().equals(key)){
                return i;
            }
        }
        return -1;
    }

    public void getRateList(String from){
        try {
            URL url = new URL("https://" + from.toLowerCase() + rawURL);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            // We will get the XML from an input stream
            xpp.setInput(getInputStream(url), "UTF_8");

            boolean insideItem = false;
            int position = -1;

            // Returns the type of current event: START_TAG, END_TAG, etc..
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem) {
                            String tempCode = xpp.nextText();
                            String currCode = tempCode.replace("https://", "")
                                    .replace(rawURL, "").toUpperCase();
                            position = findCodeFrom(currCode);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem && position > -1){
                            String tempRate = xpp.nextText();
                            String currRate = "";

                            for(int i= tempRate.indexOf('=') + 2; i < tempRate.length(); i++){
                                currRate += tempRate.charAt(i);
                                if(tempRate.charAt(i) == ' ')
                                    break;
                            }

                            currencies.get(position).setRate(Double.parseDouble(currRate));
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                }

                eventType = xpp.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    private class ReadJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.d("a", "aaa");
            StringBuilder content = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while((line = bufferedReader.readLine())!= null){
                    content.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            contentS = s;

            ReadJSONLanguage();
        }
    }

    private void ReadJSONLanguage() {
        try {
            JSONObject object = new JSONObject(contentS);

            JSONArray arrCountryName = object.getJSONArray("geonames");

            for (int i = 0; i < arrCountryName.length(); i++) {
                JSONObject objectName = arrCountryName.getJSONObject(i);

                Currency currency = new Currency();

                currency.setNation(objectName.getString("countryName"));
                currency.setCode(objectName.getString("currencyCode"));
                currency.setRate(-1);

                currencies.add(currency);
                nations.add(currency.getNation());
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}