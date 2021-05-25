package com.example.nationinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ListView lvCountry;
    ArrayList<String> countriesName;
    ArrayAdapter adapter;
    String contentS = "";
    ArrayList<Nation> nations;
    TextView txtName, txtPopulation, txtArea;
    ImageView imgNationFlag, imgCountry;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCountry = findViewById(R.id.listview);
        countriesName = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, countriesName);

        lvCountry.setAdapter(adapter);

        nations = new ArrayList();

        // Dialog hiển thị
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_display);
        txtName = (TextView) dialog.findViewById(R.id.txtCountryName);
        txtPopulation = (TextView) dialog.findViewById(R.id.txtPopulation);
        txtArea = (TextView) dialog.findViewById(R.id.txtArea);
        imgNationFlag = dialog.findViewById(R.id.imgNationalFlag);
        imgCountry = dialog.findViewById(R.id.imgCountry);
        btnOk = dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        new ReadJSON().execute("http://api.geonames.org/countryInfoJSON?username=khoaht");

        // format number
        NumberFormat numberFormat = NumberFormat.getInstance();
        Locale localeVN = new Locale("vi", "VN");
        lvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtName.setText("Country name: " + nations.get(position).getName());

                // load ảnh từ link trên internet
                new NationFlag().execute(nations.get(position).getNational_flag());
                new CountryImage().execute(nations.get(position).getNation_img());

                // format population
                int population = Integer.parseInt(nations.get(position).getPopulation());
                float area = Float.parseFloat(nations.get(position).getArea());
                NumberFormat vn = NumberFormat.getInstance(localeVN);
                String popu = vn.format(population);
                String areaF = vn.format(area);

                txtPopulation.setText("Population: " + popu);
                //txtPopulation.setText("Population: " + Integer.parseInt(nations.get(position).getPopulation()));
                txtArea.setText("Area: " + areaF + " km²");
                //txtArea.setText("Area: " + Float.parseFloat(nations.get(position).getArea()) + " km²");

                dialog.show();
            }
        });
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
                String countryCode = objectName.getString("countryCode");
                Nation nation = new Nation();

                nation.setName(objectName.getString("countryName"));
                String urlFlag = "https://img.geonames.org/flags/l/" + countryCode.toLowerCase() + ".gif";
                String urlCountry = "https://img.geonames.org/img/country/250/" + countryCode + ".png";
                nation.setNational_flag(urlFlag);
                nation.setNation_img(urlCountry);
                nation.setPopulation(objectName.getString("population"));
                nation.setArea(objectName.getString("areaInSqKm"));

                nations.add(nation);
                countriesName.add(nation.getName());
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // lớp lấy ảnh từ internet
    private class NationFlag extends AsyncTask<String, Void, Bitmap>{
        Bitmap bitmap = null;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                //khai báo một đường dẫn nhận vào tham số của mảng strings mặc định
                URL url = new URL(strings[0]);
                // lấy dữ liệu từ URL
                InputStream inputStream = url.openConnection().getInputStream();
                //đổ dữ liệu ra bitmap để decode về
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgNationFlag.setImageBitmap(bitmap);
        }
    }

    private class CountryImage extends AsyncTask<String, Void, Bitmap>{
        Bitmap bitmap = null;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                //khai báo một đường dẫn nhận vào tham số của mảng strings mặc định
                URL url = new URL(strings[0]);
                // lấy dữ liệu từ URL
                InputStream inputStream = url.openConnection().getInputStream();
                //đổ dữ liệu ra bitmap để decode về
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgCountry.setImageBitmap(bitmap);
        }
    }
}