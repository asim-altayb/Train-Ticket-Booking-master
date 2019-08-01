package com.example.fashkl.im;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fashkl.im.DataWareHouse.TicketDetails;
import com.example.fashkl.im.DataWareHouse.bookingLab;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TicketDetailsPage extends AppCompatActivity {

    private EditText TKuserIdFld, TKtoFld, TKfromFld, TKclassFld, TKdateFld , TKpriceFld;
    private Button submit;
    ScrollView scrollView;
    String conf_url="https://trainsudan.000webhostapp.com/php/confirm.php";
ImageView err  , bac;
TextView emsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details_page);

        try {
     scrollView =(ScrollView) findViewById(R.id.scroll2);
        err=(ImageView) findViewById(R.id.error);
        emsg = (TextView) findViewById(R.id.ermsg);
        TKuserIdFld = (EditText) findViewById(R.id.TKuserIdFld);
        TKtoFld = (EditText) findViewById(R.id.tofld);
        TKfromFld = (EditText) findViewById(R.id.fromfld);
        TKclassFld = (EditText) findViewById(R.id.classfld);
        TKdateFld = (EditText) findViewById(R.id.datefld);
        TKpriceFld= (EditText) findViewById(R.id.pricefld);
        bac=(ImageView) findViewById(R.id.bacg);

        submit = (Button) findViewById(R.id.TKsubmitBtn);

    TKtoFld.setText(ticket_info.getTo_station());
    TKfromFld.setText(ticket_info.getFrom_station());
    TKclassFld.setText(ticket_info.getClass_trip());
    TKdateFld.setText(ticket_info.getDate());
    TKpriceFld.setText(ticket_info.getPrice());
            if(TKtoFld.getText().toString().isEmpty()&&TKpriceFld.getText().toString().isEmpty()){
                scrollView.setVisibility(View.INVISIBLE);
                err.setVisibility(View.VISIBLE);
                emsg.setVisibility(View.VISIBLE);
                bac.setVisibility(View.INVISIBLE);
            }




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TicketDetailsPage.this, "Done !", Toast.LENGTH_SHORT).show();
              //  submitrev();
            }
        });


        }catch (Exception e){
            scrollView.setVisibility(View.INVISIBLE);
            err.setVisibility(View.VISIBLE);
            emsg.setVisibility(View.VISIBLE);

        }
    }


    public void submitrev()
    {

        // Showing progress dialog at user registration time.
        final ProgressDialog progressDialog = new ProgressDialog(TicketDetailsPage.this);
        progressDialog.setMessage("الرجاء الانتظار ...");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, conf_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        System.out.println("Main response: " + ServerResponse);
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Matching server responce message to our text.
                        if (ServerResponse.startsWith("true")) {
                            // Toast.makeText(LoginPage.this, "Successfully verified code ", Toast.LENGTH_SHORT).show();

                            Toast.makeText(TicketDetailsPage.this, "تم تأكيد الحجز !", Toast.LENGTH_SHORT).show();
                        } else {

                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(TicketDetailsPage.this, ServerResponse, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(TicketDetailsPage.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
               // params.put("phone", phonefld.getText().toString());
               // params.put("password", passwordFld.getText().toString());


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
