package dev.ibrahhout.laundaryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.ibrahhout.laundaryapp.Utils.UtilsClass;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "test";
    @BindView(R.id.welcome)
    TextView welcome;
    @BindView(R.id.display_name_reg)
    TextInputLayout displayNameReg;
    @BindView(R.id.display_address)
    TextInputLayout display_address_reg;
    @BindView(R.id.email_reg)
    TextInputLayout emailReg;
    @BindView(R.id.password_reg)
    TextInputLayout passwordReg;
    @BindView(R.id.create_reg)
    Button createReg;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;


    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);


        // Preparing the firebase variables
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebaseDatabase.getReference();


    }

    @OnClick(R.id.create_reg)
    public void onViewClicked() {


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait a second");
        progressDialog.setCanceledOnTouchOutside(false);

        String display_name = displayNameReg.getEditText().getText().toString();
        String email = emailReg.getEditText().getText().toString();
        String password = passwordReg.getEditText().getText().toString();
        String address =  display_address_reg.getEditText().getText().toString();

        //// Hide keyboard ///////

        UtilsClass.getInstance().hideKeyboard(this);

        ///////////
        if (!(TextUtils.isEmpty(display_name)) && !(TextUtils.isEmpty(email)) && !(TextUtils.isEmpty(password)&& ! (TextUtils.isEmpty(address))) ){
            progressDialog.setTitle("Registering user");
            progressDialog.setMessage("Please waite a second");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            register_user(display_name, email, password,address);


        } else {

            Snackbar snackbar = Snackbar.make(relativeLayout
                    , "Please Make sure you filled all spaces", Snackbar.LENGTH_LONG);
            snackbar.show();


        }
        //////////

    }

    private void register_user(final String display_name, final String email, final String password,final String address) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userID = firebaseUser.getUid();


                    DatabaseReference users_Reference = databaseReference.child("users").child(userID);

                    HashMap<String, String> user_info_map = new HashMap<>();
                    user_info_map.put("name", display_name);
                    user_info_map.put("status", "Hi there, I Started using Let's Laundry app \n Say hi!");
                    user_info_map.put("image", "default");
                    user_info_map.put("thump_image", "default");
                    user_info_map.put("email", email);
                    user_info_map.put("password", password);
                    user_info_map.put("Address",address);

                    users_Reference.setValue(user_info_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "Registration success", Toast.LENGTH_SHORT).show();
                                Intent loggetIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                loggetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(loggetIntent);
                                finish();

                            } else {

                            }
                        }
                    });


                } else {



                    progressDialog.hide();

                    Log.d(TAG, "onComplete: "+task.getException().toString());
                    Toast.makeText(SignUpActivity.this, "Some Error happend", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}


