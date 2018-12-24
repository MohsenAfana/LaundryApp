package dev.ibrahhout.laundaryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.ibrahhout.laundaryapp.Utils.UtilsClass;

public class LoginAcitivity extends AppCompatActivity {


    FirebaseAuth mFirebaseAuth;
    RelativeLayout relativeLayout;
    ProgressDialog progressDialog;
    @BindView(R.id.sign_in_tv)
    TextView signInTv;
    @BindView(R.id.login_email)
    TextInputLayout loginEmail;
    @BindView(R.id.login_password)
    TextInputLayout loginPassword;
    @BindView(R.id.singin_button)
    Button singinButton;
    @BindView(R.id.sign_in_layout)
    RelativeLayout signInLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.singin_button)
    public void onViewClicked() {
        mFirebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait a second");
        progressDialog.setCanceledOnTouchOutside(false);

        String email = loginEmail.getEditText().getText().toString();
        String password = loginPassword.getEditText().getText().toString();

        UtilsClass.getInstance().hideKeyboard(this);

        if ((!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(password))) {

            progressDialog.show();
            loginUser(email, password);


        } else {
            Snackbar snackbar = Snackbar.make(relativeLayout, "Make sure you filled the spaces", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    private void loginUser(String email, String password){

        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    progressDialog.setMessage("You're ready to go!");
                    progressDialog.dismiss();
                    Intent main = new Intent(LoginAcitivity.this,MainActivity.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(main);
                    finish();

                }else{

                    progressDialog.dismiss();

                    Snackbar snackbar = Snackbar.make(signInLayout,"Something went Wrong, can't log in",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            }
        });



    }

}
