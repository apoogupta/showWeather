package com.example.showweather;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {
    public static final String Query_For_City_id = "https://www.metaweather.com/api/location/search/?query=";
    public static final String Query_for_Weather_by_city_id = "https://www.metaweather.com/api/location/";
    Context context;
    String city_id="";
    public WeatherDataService(Context context) {
        this.context=context;

    }
    //for using the callBack
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String response);
    }

    public void getCityId(String city_name,VolleyResponseListener volleyResponseListener)
   {
       String url = Query_For_City_id +city_name;

       JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
                city_id="";
               try {
                   JSONObject cityInfo=response.getJSONObject(0);
                   city_id=cityInfo.get("woeid").toString();


               } catch (JSONException e) {
                   e.printStackTrace();
               }

                volleyResponseListener.onResponse(city_id);
               //Toast.makeText(context,"City-Id="+city_id,Toast.LENGTH_LONG).show();

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               volleyResponseListener.onError("Error");
               //Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
           }

       }

       );
       MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        //return  city_id;




   }

    public interface ForeCastByIdResponse {
        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModel);
    }
   public void getWeatherByCityId(String city_id,ForeCastByIdResponse foreCastByIdResponse)
   {
       //we have a object by the name consolidated_weather
       //with a value=array of object of each day
       //consolidated_weather=[{day 1},{day 2},{day 3},{day 4},{day 5},{day 6}]
       /*
       1) get the json Object
       2) get the item with the property =consolidated_weather which is an array
       3)get each item in the array and assign it to a new weatherReportModel object;
*/
       List<WeatherReportModel> report=new ArrayList();
       String url= Query_for_Weather_by_city_id +city_id+"/";
       JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               //1st step done
               //2nd step
               try {
                   JSONArray Consolidated_weather_list=response.getJSONArray("consolidated_weather");

                   //3rd step
                   for(int i=0;i<Consolidated_weather_list.length();i++)
                   {
                       WeatherReportModel One_day_weather=new WeatherReportModel();
                       JSONObject first_day_from_api=Consolidated_weather_list.getJSONObject(i);
                       One_day_weather.setId(first_day_from_api.getInt("id"));
                       One_day_weather.setWeather_state_main(first_day_from_api.getString("weather_state_name"));
                       One_day_weather.setWeather_state_abbr(first_day_from_api.getString("weather_state_abbr"));
                       One_day_weather.setWind_direction_compass(first_day_from_api.getString("wind_direction_compass"));
                       One_day_weather.setCreated(first_day_from_api.getString("created"));
                       One_day_weather.setApplicable_date(first_day_from_api.getString("applicable_date"));
                       One_day_weather.setMin_temp(first_day_from_api.getLong("min_temp"));
                       One_day_weather.setMax_temp(first_day_from_api.getLong("max_temp"));
                       One_day_weather.setWind_direction(first_day_from_api.getLong("wind_direction"));
                       One_day_weather.setThe_temp(first_day_from_api.getLong("the_temp"));
                       One_day_weather.setWind_speed(first_day_from_api.getLong("wind_speed"));
                       One_day_weather.setAir_pressure(first_day_from_api.getInt("air_pressure"));
                       One_day_weather.setHumidity(first_day_from_api.getInt("humidity"));
                       One_day_weather.setVisibility(first_day_from_api.getLong("visibility"));
                       One_day_weather.setPredictability(first_day_from_api.getInt("predictability"));
                       report.add(One_day_weather);
                   }


                   foreCastByIdResponse.onResponse(report);

               } catch (JSONException e) {
                   e.printStackTrace();
               }

                //testing
               //Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();
           }

       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

               Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
           }

       });
       MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);




   }

}
