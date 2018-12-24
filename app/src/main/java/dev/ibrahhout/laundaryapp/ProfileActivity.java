package dev.ibrahhout.laundaryapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.laundaryapp.Utils.Constants;
import dev.ibrahhout.laundaryapp.Utils.MotherActivity;

public class ProfileActivity extends MotherActivity {

    @BindView(R.id.userNameTextView)
    EditText userNameTextView;
    @BindView(R.id.adressTextView)
    EditText adressTextView;
    @BindView(R.id.updateProfile)
    Button updateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).keepSynced(true);

        FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userNameTextView.setText(dataSnapshot.child(Constants.FIREBASE_USERS_USERNAME).getValue(String.class));
                        adressTextView.setText(dataSnapshot.child(Constants.FIREBASE_USERS_ADDRESS).getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.FIREBASE_USERS_USERNAME).setValue(userNameTextView.getText().toString());
            FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.FIREBASE_USERS_ADDRESS).setValue(adressTextView.getText().toString());

                Toast.makeText(ProfileActivity.this, "Done, Profile has changed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

}
