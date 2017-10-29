package com.example.aso.whatsnearme;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View _mw = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(_mw);









    }
    public void btn_arts(View v){

        Intent i=new Intent(MainActivity.this,ArtsAndEntertainmentActivity.class );
        startActivity(i);



    }

    public void btn_cafe(View v){

        Intent i=new Intent(MainActivity.this,CafeActivity.class );
        startActivity(i);



    }

    public void btn_restaurant(View v ){


        Intent i=new Intent(MainActivity.this,RestaurantActivity.class );
        startActivity(i);
    }

    public void btn_mosque(View v ){
        Intent i=new Intent(MainActivity.this,MosqueActivity.class );
        startActivity(i);
    }
    public void btn_museum(View v ){

        Intent i=new Intent(MainActivity.this,MuseumActivity.class );
        startActivity(i);
    }
    public void btn_avm(View v ){

        Intent i=new Intent(MainActivity.this,AvmActivity.class );
        startActivity(i);
    }

    public void btn_cityhalls(View v){
        Intent i=new Intent(MainActivity.this,CityHallsActivity.class );
        startActivity(i);
    }

    public void btn_theaters(View v ){

        Intent i=new Intent(MainActivity.this,TheatersActivity.class );
        startActivity(i);
    }

    public void btn_hospital(View v){
        Intent i=new Intent(MainActivity.this,HospitalActivity.class );
        startActivity(i);

    }


    public void btn_library(View v ){

        Intent i=new Intent(MainActivity.this,LibraryActivity.class );
        startActivity(i);
    }
    public void btn_pharmacy(View v ){

        Intent i=new Intent(MainActivity.this,PharmacyActivity.class );
        startActivity(i);
    }
    public void btn_movie(View v ){

        Intent i=new Intent(MainActivity.this,MovieTheaterActivity.class );
        startActivity(i);
    }

    public void btn_petshop(View v ){

        Intent i=new Intent(MainActivity.this,PetShopActivity.class );
        startActivity(i);
    }

    public void btn_hotel(View v ){

        Intent i=new Intent(MainActivity.this,HotelActivity.class );
        startActivity(i);
    }

    public void btn_stadium(View v ){

        Intent i=new Intent(MainActivity.this,StadiumActivity.class );
        startActivity(i);
    }

    public void btn_university(View v ){

        Intent i=new Intent(MainActivity.this,UniversityActivity.class );
        startActivity(i);
    }

    public void btn_police(View v ){

        Intent i=new Intent(MainActivity.this,PoliceStationActivity.class );
        startActivity(i);
    }
    public void btn_militarybase(View v ){

        Intent i=new Intent(MainActivity.this,MilitaryBaseActivity.class );
        startActivity(i);
    }

    public void btn_shrine(View v ){

        Intent i=new Intent(MainActivity.this,ShrineActivity.class );
        startActivity(i);
    }

    public void btn_bank(View v ){

        Intent i=new Intent(MainActivity.this,BankActivity.class );
        startActivity(i);
    }
    public void btn_boutique(View v ){

        Intent i=new Intent(MainActivity.this,BoutiqueActivity.class );
        startActivity(i);
    }

    public void btn_drycleaner(View v ){

        Intent i=new Intent(MainActivity.this,DryCleanerActivity.class );
        startActivity(i);
    }


    public void btn_flowershop(View v ){

        Intent i=new Intent(MainActivity.this,FlowerShopActivity.class );
        startActivity(i);
    }
}
