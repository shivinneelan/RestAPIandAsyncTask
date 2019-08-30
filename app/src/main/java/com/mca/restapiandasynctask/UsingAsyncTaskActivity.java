package com.mca.restapiandasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UsingAsyncTaskActivity extends AppCompatActivity {

    TextView name,address,phone,email;
    GetData getData;
    Button asybcbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_async_task);

        asybcbtn=(Button) findViewById(R.id.asyncbtn);
        name=(TextView) findViewById(R.id.name);
        address=(TextView) findViewById(R.id.address);
        phone=(TextView) findViewById(R.id.phone);
        email=(TextView) findViewById(R.id.email);
        getData=new GetData();



        asybcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call asyncTask
                getData.execute();

            }
        });
    }


    class GetData extends AsyncTask<Void, Void,String>   // 1. pass any arguments. 3. return any arguments
    {

        ProgressDialog dialog = new ProgressDialog(UsingAsyncTaskActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading...");
            dialog.show();
        }
        @Override
        protected void onPostExecute(String s) {

            dialog.cancel();
            try {
                JSONObject response=new JSONObject(s);

                name.setText(response.getString("name"));
                address.setText(response.getString("address"));
                phone.setText(response.getString("phone"));
                email.setText(response.getString("email"));
            } catch (JSONException e) {
                Toast.makeText(UsingAsyncTaskActivity.this,e.toString(),Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result="";

            String connstr = " http://192.168.43.136/RestAPIandAsyncTask.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                // !=======  use encode while transfer data to the php file
                //String data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                //                writer.write(data);
                //
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
                String line ="";
                while ((line = reader.readLine()) != null)
                {
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
            return result;

        }
    }
}
