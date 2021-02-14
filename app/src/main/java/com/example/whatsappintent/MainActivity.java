package com.example.whatsappintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;


public class MainActivity extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText phone, message;
    Button sendbtn;
    String messagestr, phonestr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryCodePicker = findViewById(R.id.countryCode);
        phone = findViewById(R.id.phoneNo);
        message = findViewById(R.id.message);
        sendbtn = findViewById(R.id.sendbtn);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                messagestr = message.getText().toString();
                phonestr = phone.getText().toString();

                if (!messagestr.isEmpty() && !phonestr.isEmpty()) {

                    countryCodePicker.registerCarrierNumberEditText(phone);
                    phonestr = countryCodePicker.getFullNumber();

                    if (isWhatappInstalled()){

                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+phonestr+
                                "&text="+messagestr));
                        startActivity(i);
                        message.setText("");
                        phone.setText("");


                    }else {

                        Toast.makeText(MainActivity.this,"Whatsapp is not installed",Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(MainActivity.this, "Please fill in the Phone no. and message it can't be empty", Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    private boolean isWhatappInstalled(){

        PackageManager packageManager = getPackageManager();
        boolean whatsappInstalled;

        try {

            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;


        }catch (PackageManager.NameNotFoundException e){

            whatsappInstalled = false;

        }

        return whatsappInstalled;

    }


}


