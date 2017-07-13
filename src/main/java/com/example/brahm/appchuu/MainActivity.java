/*
MainActivity.Java
Last edited on 6/12/2017 10:25pm
Abraham Schultz

This is the main activity for Apchuu. An app to tell the user
god bless you after they have sneezed. This app utilises google's text to speech api.
 */


package com.example.brahm.appchuu;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private AdView mAdView;


    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private ImageButton mMainButton;
    TextToSpeech t1;
    private String selectedSalutation;
    private Switch mSwitch;
    EditText ed1;

    //method for setting animation of button image.
    private void setButtonImage() {

        mMainButton.setImageResource(R.mipmap.nose);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_main);


        //code for add banner
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //calls method to swith salutations
        mSwitch = (Switch) findViewById(R.id.switch1);
        mSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setButtonImage();
                return false;
            }
        });

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedSalutation = getString(R.string.blessing_2);
                } else {
                    selectedSalutation = getString(R.string.blessing_1);
                }
            }
        });

// calls method from text to speech api
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });


        //main button to tell user bless you upon tapping

        mMainButton = (ImageButton) findViewById(R.id.Apchuu_button);


        mMainButton.setOnClickListener(new View.OnClickListener() {
                                           @Override


                                           public void onClick(View v) {
                                               int Blessing = R.string.blessing_2;


                                               String toSpeak = selectedSalutation;

                                               Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                                               t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                               mMainButton.setImageResource(R.mipmap.nosedrip);

                                           }
                                       }
        );//end ImageButton apchuu click on event
        selectedSalutation = getString(R.string.blessing_1);
    }//end on create

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}// end MainActivity
