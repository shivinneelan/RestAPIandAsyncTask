package com.mca.restapiandasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UsingVolleyActivity extends AppCompatActivity {


    Button volleybtn;
    TextView name,address,phone,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_volley);

        // add dependency in app gradle
        //      implementation 'com.android.volley:volley:1.1.1'

        String url="http://192.168.43.136/RestAPIandAsyncTask.php";
        volleybtn=(Button) findViewById(R.id.volleybtn);
        name=(TextView) findViewById(R.id.name);
        address=(TextView) findViewById(R.id.address);
        phone=(TextView) findViewById(R.id.phone);
        email=(TextView) findViewById(R.id.email);


        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(UsingVolleyActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        try {
                            name.setText(response.getString("name"));
                            address.setText(response.getString("address"));
                            phone.setText(response.getString("phone"));
                            email.setText(response.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(UsingVolleyActivity.this,error.toString(),Toast.LENGTH_LONG).show();

                    }
                }
        );

        volleybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestQueue.add(jsonObjectRequest);
            }
        });


    }
}
