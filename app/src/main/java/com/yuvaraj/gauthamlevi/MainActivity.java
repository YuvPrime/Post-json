package com.yuvaraj.gauthamlevi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    String url = "http://levivaant.co.in/cheflocker/index.php/client";
    String tag_json_obj = "json_obj_req";
    ProgressDialog pDialog;
    String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            makeJsonObjReq();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void makeJsonObjReq() throws JSONException {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setTitle("Volley Operation");
        pDialog.setMessage("Loading...");
        pDialog.show();

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("email_id", "development@gmail.com");
            parameters.put("password", "ddd");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject functions = new JSONObject();
        try {
            functions.put("function", "login");
            functions.put("parameters", parameters);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        Log.d("functions", String.valueOf(functions));


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(String.valueOf(functions)),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put( "charset", "utf-8");
                return headers;
            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
