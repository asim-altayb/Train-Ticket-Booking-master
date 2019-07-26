package com.example.fashkl.project;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.fashkl.project.DataWareHouse.TicketDetails;

public class Grid_Home extends AppCompatActivity{
    GridLayout maingrid;
    int i=0;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "اضغط تاني عشان تتخارج ", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid__home);
        maingrid=(GridLayout) findViewById(R.id.mainGrid);



        click_grid(maingrid);

    }

    private void click_grid(GridLayout gridLayout) {
        for ( i=0;i<gridLayout.getChildCount();i++){
            CardView cardView = (CardView)gridLayout.getChildAt(i);

final int indexx=i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  Toast.makeText(Grid_Home.this, "You are clicked on item: "+indexx ,Toast.LENGTH_SHORT).show();
                   switch (indexx)
                   {
                       case 0:

                           Intent blood_req=new Intent(Grid_Home.this,TicketDetails.class);
                           startActivity(blood_req);
                           break;


                       case 1:
                           Intent donors=new Intent(Grid_Home.this,BookTicketPage.class);
                           startActivity(donors);
                           break;


                       case 2:
                           Intent search=new Intent(Grid_Home.this,AddBalancePage.class);
                           startActivity(search);
                           break;


                       case 3:
                           Intent make_req=new Intent(Grid_Home.this,stations.class);
                           startActivity(make_req);
                           break;


                       case 4:
                           Intent profile=new Intent(Grid_Home.this,Profile.class);
                           startActivity(profile);
                           break;



                   }
                }
            });
        }
    }



    
}
