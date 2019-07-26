package com.example.fashkl.project;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    private Button loginBtn, creditBtn;
    public Intent loginPageIntent, registerIntent;
    static boolean net=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

            net=isNetworkAvailable();
        if(!net)
        {
            Toast.makeText(this, "ماف نت شغل البيانات لو ما شغاله ", Toast.LENGTH_SHORT).show();
        }

        loginBtn = (Button) findViewById(R.id.login_Button);
        creditBtn = (Button) findViewById(R.id.credits_Button);

        loginPageIntent = new Intent(this, LoginPage.class);
        registerIntent = new Intent(this, RegisterPage.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginPageIntent);
                finish();
            }
        });
        creditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerIntent);
            }
        });


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
