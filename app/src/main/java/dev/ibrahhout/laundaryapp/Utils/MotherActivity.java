package dev.ibrahhout.laundaryapp.Utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import dev.ibrahhout.laundaryapp.MainActivity;
import dev.ibrahhout.laundaryapp.R;
import dev.ibrahhout.laundaryapp.StartActivity;

public class MotherActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mFirebaseAuth.getCurrentUser()==null){
            singOutAndUpdateUI();
        }

    }



    public void singOutAndUpdateUI(){

        FirebaseAuth.getInstance().signOut();
        Intent startIntent = new Intent();
        startIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent.setClass(this,StartActivity.class);
        startActivity(startIntent);
        finish();


    }
}
