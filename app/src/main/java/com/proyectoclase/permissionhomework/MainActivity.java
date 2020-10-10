package com.proyectoclase.permissionhomework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 200;

    private SwitchCompat storage,location,camera,phone,contacts;
    private List<SwitchCompat> compatList;
    private ArrayList<String> permissions = new ArrayList<>();
    private String[] savedPermissions = new String[]{READ_EXTERNAL_STORAGE,ACCESS_FINE_LOCATION,CAMERA,CALL_PHONE,READ_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compatList = Arrays.asList(new SwitchCompat[]{storage = findViewById(R.id.storage), location = findViewById(R.id.location), camera = findViewById(R.id.camera)
        ,phone = findViewById(R.id.phone),contacts = findViewById(R.id.contacts)
        });
        onStartPermissions();
        /*storage = findViewById(R.id.storage);
        location = findViewById(R.id.location);
        camera = findViewById(R.id.camera);
        phone = findViewById(R.id.phone);
        contacts = findViewById(R.id.contacts);*/


    }

    public void requestPermission(){
        String[] arrPermission = null;
        arrPermission = permissions.toArray(new String[permissions.size()]);
        ActivityCompat.requestPermissions(this,arrPermission,REQUEST_CODE_PERMISSION);
    }

    public Boolean checkPermission(String permission){
        int permissionCode = ContextCompat.checkSelfPermission(getApplicationContext(),permission);
        return permissionCode == PackageManager.PERMISSION_GRANTED;
    }

    public void setPermissions(View v){
        Intent intent = new Intent(this,PermissionsActivity.class);
        startActivity(intent);
        int place = 0;
        Boolean selection = false;
        permissions = new ArrayList<>();
        for (SwitchCompat aux:compatList){
            if(aux.isChecked()){

                switch (place){
                    case 0 :
                        if( !(checkPermission(READ_EXTERNAL_STORAGE))){
                            permissions.add(READ_EXTERNAL_STORAGE);
                            selection = true;
                        }
                        break;
                    case 1 :
                        if( !(checkPermission(ACCESS_FINE_LOCATION))){
                            permissions.add(ACCESS_FINE_LOCATION);
                            selection = true;
                        }
                        break;
                    case 2 :
                        if( !(checkPermission(CAMERA))) {
                            permissions.add(CAMERA);
                            selection = true;
                        }
                        break;
                    case 3 :
                        if( !(checkPermission(CALL_PHONE))) {
                            permissions.add(CALL_PHONE);
                            selection = true;
                        }

                        break;
                    default:
                        if( !(checkPermission(READ_CONTACTS))) {
                            permissions.add(READ_CONTACTS);
                            selection = true;
                        }
                }
            }
            place++;
        }
        if(selection)
            requestPermission();



    }

    public void onStartPermissions (){
        //SwitchCompat selected = ((SwitchCompat) v);
        int place = 0;
        for (String aux:savedPermissions){
            if(checkPermission(aux)){
                compatList.get(place).setChecked(true);
                permissions.add(aux);
            }
            place++;
        }
    }



    public void havePermission(View v){
        int place = 0;
        SwitchCompat selected = ((SwitchCompat) v);

        for (SwitchCompat aux:compatList){
            if(selected.getId() == aux.getId()){
                if(checkPermission(savedPermissions[place])){
                    Snackbar.make(v,"Permission alredy granted",Snackbar.LENGTH_LONG).show();
                    selected.setChecked(true);
                }
            }
            place++;
        }


    }

    public void exit(View v){

        System.exit(0);
       // finish();
    }

}