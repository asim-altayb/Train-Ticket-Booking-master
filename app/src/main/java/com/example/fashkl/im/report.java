package com.example.fashkl.im;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class report extends AppCompatActivity {

    EditText msg;
    Button send;
    Time today;

    String repo_url="https://trainsudan.000webhostapp.com/php/report.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        msg=(EditText) findViewById(R.id.msg);

        send=(Button) findViewById(R.id.btn_send);
       today = new Time(Time.getCurrentTimezone());
        today.setToNow();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senreport();
            }
        });
    }

    //method to send report

    public void senreport()
    {
        // Showing progress dialog at user registration time.
        final ProgressDialog progressDialog = new ProgressDialog(report.this);
        progressDialog.setMessage("جار ارسال الشكوى ...");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, repo_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        System.out.println("Main response: " + ServerResponse);
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Matching server responce message to our text.
                        if (ServerResponse.startsWith("true")) {
                            // Toast.makeText(LoginPage.this, "Successfully verified code ", Toast.LENGTH_SHORT).show();

                            Toast.makeText(report.this, " تم ارسال الشكوى بنجاح !", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {

                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(report.this, "تعذر ارسال الشكوى . تأكد من وجود انترنت", Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        System.out.println("Error res : " + volleyError.toString());
                        // Showing error message if something goes wrong.
                        Toast.makeText(report.this, "تعذر ارسال الشكوى . تأكد من وجود انترنت", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("text", msg.getText().toString());
                params.put("date",today.monthDay+"/"+(today.month+1)+"/"+today.year);
                params.put("time",today.format("%k:%M:%S"));
                params.put("usrid","12");


                return params;
            }
/*
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240");
                    params.put("Cookie", "__test=" + cookie.content + "; expires=Friday, January 1, 2038 at 1:55:55 AM; path=/");

                    return params;
                }*/
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplication()));
        requestQueue.add(stringRequest);
    }//send repo

    }

