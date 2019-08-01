package com.example.fashkl.im;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fashkl.im.DataWareHouse.Registeration;
import com.example.fashkl.im.DataWareHouse.RegisteratoinLab;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



///////////////////////////////////////////////////////////////////

public class RegisterPage extends AppCompatActivity {

    private EditText
            RnameFld, RpasswordFld,
            Remailfld,
            RmobileNoFld, RcardNoFld;

    private Button RsubmitBtn;
    String reg_url="https://trainsudan.000webhostapp.com/php/register.php";

    public boolean isValid(String s) {
        return (!s.trim().isEmpty());
    }

    public boolean userIdIsValid(String s) {
        return (s.length() >= 5 && isValid(s));
    }

    public boolean passIsValid(String s) {
        return (s.length() >= 5 && isValid(s));
    }


    public int C2I(String st) {
        return Integer.parseInt(st);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        //RuserIdFld = (EditText) findViewById(R.id.RuserIdFld);
        RnameFld = (EditText) findViewById(R.id.RnameFld);
        RpasswordFld = (EditText) findViewById(R.id.RpasswordFld);
        Remailfld = (EditText) findViewById(R.id.Remail);
        RmobileNoFld = (EditText) findViewById(R.id.Rmobile);
        RcardNoFld = (EditText) findViewById(R.id.Rcard);

        RsubmitBtn = (Button) findViewById(R.id.RsubmitBtn);

        RsubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            register();

                Toast.makeText(RegisterPage.this, "sending this data to server : /n"+RmobileNoFld.getText().toString()+" /n:" +
                        RpasswordFld.getText().toString()+" /n: "+Remailfld.getText().toString()
                        +"/n: "+RnameFld.getText().toString()+" /n: "+RcardNoFld.getText().toString(), Toast.LENGTH_SHORT).show();


            }
        });


    }

    public void register()
    {
        if(!HomePage.net)
        {
            Toast.makeText(this, "ماف نت شغل البيانات بلاي ", Toast.LENGTH_LONG).show();
        }
        else {

            // Showing progress dialog at user registration time.
            final ProgressDialog progressDialog = new ProgressDialog(RegisterPage.this);
            progressDialog.setMessage("Please wait ...");
            progressDialog.show();

            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            System.out.println("Main response: " + ServerResponse);
                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            // Matching server responce message to our text.
                            if (ServerResponse.startsWith("true")) {
                                Toast.makeText(RegisterPage.this, "تم التسجيل بنجاح " + RnameFld.getText().toString(), Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegisterPage.this, Grid_Home.class);
                                startActivity(intent);
                                finish();
                            } else {

                                // Showing Echo Response Message Coming From Server.
                                Toast.makeText(RegisterPage.this, ServerResponse, Toast.LENGTH_LONG).show();

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
                            Toast.makeText(RegisterPage.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    // Creating Map String Params.
                    Map<String, String> params = new HashMap<String, String>();

                    // Adding All values to Params.
                    // The firs argument should be same sa your MySQL database table columns.
                    params.put("phone", RmobileNoFld.getText().toString());
                    params.put("password", RpasswordFld.getText().toString());
                    params.put("email", Remailfld.getText().toString());
                    params.put("name", RnameFld.getText().toString());
                    params.put("cardno", RcardNoFld.getText().toString());

                    System.out.println("sending this data to server : " + RmobileNoFld.getText().toString() + " :" +
                            RpasswordFld.getText().toString() + " : " + Remailfld.getText().toString()
                            + ": " + RnameFld.getText().toString() + " : " + RcardNoFld.getText().toString());

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

        }

    }


}

