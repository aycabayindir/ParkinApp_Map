package com.map.parkinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity{


    private static final String TAG = "Main Activity";
    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isServicesUpdated()){
            init();
        }
    }
    private void init(){
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                Intent intent = new Intent(MainActivity.this, MapActivity.class );
                startActivity(intent);
            }
        });
    }

    //Überprüfen der Version der Google Play-Services auf dem Gerät
    public boolean isServicesUpdated(){
        Log.d(TAG , "isServicesUpdated:Überprüfen der Version der Google Play-Services");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //Richtige Version, Kartenanfrage kann gestellt werden
            Log.d(TAG, "isServicesUpdated: GooglePlayServices Funktioniert");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //lösbarer Fehler
            Log.d(TAG, "isServicesUpdated: behebbarer Fehler ist aufgetreten");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "Kartenanfrage kann nicht gestellt werden", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


}