package com.example.showweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_getCityId,btn_getWeatherById,btn_getWeatherByCityName;
    EditText et_inputData;
    ListView show_data;
    private Object String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_getCityId=(Button)findViewById(R.id.btn_getCityByid);
        btn_getWeatherById=(Button)findViewById(R.id.btn_getWeatherbyCityId);
        btn_getWeatherByCityName=(Button)findViewById(R.id.btn_getWeatherbyCityName);
        et_inputData=(EditText)findViewById(R.id.et_dataInput);
        show_data=(ListView)findViewById(R.id.show_data);
        WeatherDataService weatherDataService=new WeatherDataService(MainActivity.this);


        btn_getCityId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //request queue is to queue all the request at a time it can handle 4 request .its
             //work is to manage the worker threads(thread other than the main thread) and manage the responses
             //RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                //using singleton class ,this class is instantiated a single time throughout the life of an app...(when we rotate our screen)


//                StringRequest request= new StringRequest(Request.Method.GET,url,new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(java.lang.String response) {
//                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
//                    }
//                },new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
//                    }
//
//                });
//                requestQueue.add(request);
                weatherDataService.getCityId(et_inputData.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(java.lang.String message) {
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(java.lang.String response) {
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                    }
                });



            }

            });




        btn_getWeatherById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_id=et_inputData.getText().toString();
                weatherDataService.getWeatherByCityId(input_id, new WeatherDataService.ForeCastByIdResponse() {
                    @Override
                    public void onError(java.lang.String message) {
                        Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModel) {
                  //  Toast.makeText(MainActivity.this,weatherReportModel.toString(),Toast.LENGTH_LONG).show();
                        ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,weatherReportModel);
                        show_data.setAdapter(arrayAdapter);

                    }
                });


            }
        });

        btn_getWeatherByCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                weatherDataService.getCityId(et_inputData.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(java.lang.String message) {
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(java.lang.String response) {
                        String input_id=response;
                        weatherDataService.getWeatherByCityId(input_id, new WeatherDataService.ForeCastByIdResponse() {
                            @Override
                            public void onError(java.lang.String message) {
                                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onResponse(List<WeatherReportModel> weatherReportModel) {
                                //  Toast.makeText(MainActivity.this,weatherReportModel.toString(),Toast.LENGTH_LONG).show();
                                ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,weatherReportModel);
                                show_data.setAdapter(arrayAdapter);

                            }
                        });

                    }
                });


            }
        });
    }
}