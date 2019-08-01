package com.example.fashkl.im;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.fashkl.im.DataWareHouse.*;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


public class BookTicketPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener{


    private Spinner bkclasssp, bkFromStationSpnr, bkToStationSpnr;
    private RadioGroup bkJournyTypeRB;
    private RadioButton bksingle, bkreturn;
   // private EditText  bkBalanceFld;
    private Button bkBookBtn;
    private BookingTicket booking;
    private Bundle bundle;
    private Registeration registeration;
    private TicketDetails ticketDetails;
    private Intent ticketDIntent;
    ImageView dat;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;
    TextView dateview,timeview;
    String search_url="https://trainsudan.000webhostapp.com/php/get_tic.php";
    ticket_info ticket_info;
    Map<String, String> map = new HashMap<String, String>();
 RadioButton single,twice;
    private JSONArray result;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket_page);
        registeration = new Registeration();
        ticketDetails = new TicketDetails();

        dateview=(TextView) findViewById(R.id.datetv);//date
       // timeview=(TextView) findViewById(R.id.timetv);//time
        single=(RadioButton) findViewById(R.id.BksingleRBtn);
        twice=(RadioButton) findViewById(R.id.BkreturnRBtn);

        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        dat=(ImageView) findViewById(R.id.date_chooser);
       // tim=(ImageView) findViewById(R.id.time_chooser);

        dat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog = DatePickerDialog.newInstance(BookTicketPage.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#0072BA"));

                datePickerDialog.setTitle("اختار موعد الرحلة");

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

            }
        });

       /* tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                timePickerDialog = TimePickerDialog.newInstance(BookTicketPage.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),false);

                timePickerDialog.setThemeDark(false);



                timePickerDialog.setAccentColor(Color.parseColor("#0072BA"));

                timePickerDialog.setTitle("اختار الزمن البريحك");

                timePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });*/

        //iniaite the Spinners view
        bkclasssp = (Spinner) findViewById(R.id.BKclass);//class
        bkclasssp.setPrompt("--Select Line--");
//        bkLineTypeSpnr.setPopupBackgroundDrawable(R.drawable.bicycles);

        bkFromStationSpnr = (Spinner) findViewById(R.id.BKfromStationSpnr);//from
        bkFromStationSpnr.setPrompt("--Select Station--");

        bkToStationSpnr = (Spinner) findViewById(R.id.BKtostationSpnr);//to
        bkToStationSpnr.setPrompt("--Select Station--");


        //initiate the RadioGroup
        bkJournyTypeRB = (RadioGroup) findViewById(R.id.BkjournyTypeRB);//journey


        //iniaite the EditTexts


        //initiate
        booking = new BookingTicket();

        //
        bkBookBtn = (Button) findViewById(R.id.BKBookBtn);

        //start search in database
       bkBookBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        search_trip();

    }
     });//btn search





    }









    /**
     * @param view        The view associated with this listener.
     * @param year        The year that was set.
     * @param monthOfYear The month that was set (0-11) for compatibility
     *                    with {@link Calendar}.
     * @param dayOfMonth  The day of the month that was set.
     */
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
dateview.setText(dayOfMonth+"_"+(monthOfYear+1)+"_"+(year-1));
dateview.setVisibility(View.VISIBLE);
    }

    /**
     * @param view      The view associated with this listener.
     * @param hourOfDay The hour that was set.
     * @param minute    The minute that was set.
     * @param second    The second that was set
     */
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        timeview.setText(hourOfDay+":"+minute+" AM");
        timeview.setVisibility(View.VISIBLE);
    }




    public void search_trip()
    {


        // Showing progress dialog at user registration time.
        final ProgressDialog progressDialog=new ProgressDialog(BookTicketPage.this);
        progressDialog.setMessage("الرجاء الانتظار ...");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        System.out.println("Main response: "+ServerResponse);
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        parseRespone(ServerResponse);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        System.out.println("Error res : "+volleyError.toString());
                        // Showing error message if something goes wrong.
                        Toast.makeText(BookTicketPage.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("from_city", bkFromStationSpnr.getSelectedItem().toString());
                params.put("to_city", bkToStationSpnr.getSelectedItem().toString());
                params.put("date_go", dateview.getText().toString());
                //params.put("time_go", timeview.getText().toString());
                params.put("class_train", bkclasssp.getSelectedItem().toString());



                return params;
            }
/*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240");
                params.put("Cookie", "__test="+cookie.content+"; expires=Friday, January 1, 2038 at 1:55:55 AM; path=/");

                return params;
            }*/
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplication()));
        requestQueue.add(stringRequest);



    }



    private void parseRespone(String response) {


        JSONObject j = null;
        try {
            j = new JSONObject(response);
            result = j.getJSONArray("result");
            System.out.println(result.toString());
            getinfo(result);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getinfo(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                if(json.get("price").equals(0)&&json.get("chair_no").equals(0)&&json.get("go_time").equals(0)){
                    Toast.makeText(this, "لا توجد مقاعد فارغه في هذه الدرجه !", Toast.LENGTH_LONG).show();
                }
                else{
                ticket_info.setDate(dateview.getText().toString());
                ticket_info.setTime(json.getString("go_time"));
                ticket_info.setChair_no(json.getString("chair_no"));
                ticket_info.setClass_trip(bkclasssp.getSelectedItem().toString());
                ticket_info.setFrom_station(bkFromStationSpnr.getSelectedItem().toString());
                ticket_info.setTo_station(bkToStationSpnr.getSelectedItem().toString());
                if(bkJournyTypeRB.getCheckedRadioButtonId()==R.id.BksingleRBtn){
                ticket_info.setPrice(json.getString("price"));}
                else{
                    ticket_info.setPrice( String.valueOf(Integer.parseInt(json.getString("price").toString() )*2) );
                }

                Intent im =new Intent(BookTicketPage.this,TicketDetailsPage.class);
                startActivity(im);
                }
                /*
                * 'id_trip'=>0,
                                            'number_train'=>0,
                                             'time_arrival'=>0,
                        in server                      'go_time'=>0,
                                               'price'=>0,
                                            	'chair_no'=>0*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
