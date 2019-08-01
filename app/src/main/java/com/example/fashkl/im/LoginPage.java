package com.example.fashkl.im;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fashkl.im.DataWareHouse.RegisteratoinLab;
import com.example.fashkl.im.DataWareHouse.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.fashkl.im.DataWareHouse.DataBaseHelper.C2I;


public class LoginPage extends AppCompatActivity {

    private Button loginBtn, btnLinkToRegisterScreen;
    private EditText passwordFld,phonefld;
    private AutoCompleteTextView userNameFld;
    private String[] loop;
    private ArrayAdapter<String> adapter;
    public Intent selectPageIntent, registerPageIntent;
    String login_url="https://trainsudan.000webhostapp.com/php/login.php";


    private boolean isUserIdValid(String userId) {
        return (userId.length() >= 9 && !userId.trim().isEmpty());
    }


    private boolean isPasswordValid(String password) {
        return (password.length() >= 4 && !password.trim().isEmpty());
    }

    public void reset(EditText ed) {
        ed.setText(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        phonefld = (AutoCompleteTextView) findViewById(R.id.phone);
        passwordFld = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.loginRegistBtnd);
        btnLinkToRegisterScreen = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        selectPageIntent = new Intent(this, SelectPage.class);
        registerPageIntent = new Intent(this, RegisterPage.class);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isUserIdValid(phonefld.getText().toString()) &&
                        isPasswordValid(passwordFld.getText().toString())) {
login();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "must be 6 digits UserId & 4 password ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnLinkToRegisterScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerPageIntent);
                finish();
            }
        });

    }


    public void login()
    {
        if(!HomePage.net)
        {
            Toast.makeText(this, "ماف نت شغل البيانات بلاي ", Toast.LENGTH_LONG).show();
        }
        else {

            // Showing progress dialog at user registration time.
            final ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);
            progressDialog.setMessage("الرجاء الانتظار ...");
            progressDialog.show();

            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            System.out.println("Main response: " + ServerResponse);
                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();

                            // Matching server responce message to our text.
                            if (ServerResponse.startsWith("true")) {
                                // Toast.makeText(LoginPage.this, "Successfully verified code ", Toast.LENGTH_SHORT).show();

                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginPage.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("islogined", true);
                                editor.apply();
                                Intent intent = new Intent(LoginPage.this, Grid_Home.class);
                                startActivity(intent);
                                finish();
                            } else {

                                // Showing Echo Response Message Coming From Server.
                                Toast.makeText(LoginPage.this, ServerResponse, Toast.LENGTH_LONG).show();

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
                            Toast.makeText(LoginPage.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    // Creating Map String Params.
                    Map<String, String> params = new HashMap<String, String>();

                    // Adding All values to Params.
                    // The firs argument should be same sa your MySQL database table columns.
                    params.put("phone", phonefld.getText().toString());
                    params.put("password", passwordFld.getText().toString());


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

    }//login


}
