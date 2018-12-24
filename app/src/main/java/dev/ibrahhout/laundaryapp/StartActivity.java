package dev.ibrahhout.laundaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.register_btn_start)
    Button registerBtnStart;
    @BindView(R.id.sign_in_btn_start)
    Button signInBtnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.register_btn_start, R.id.sign_in_btn_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_btn_start:
                Intent intent = new Intent(this,SignUpActivity.class);
                startActivity(intent);

                break;
            case R.id.sign_in_btn_start:
                Intent intent1 = new Intent(this,LoginAcitivity.class);
                startActivity(intent1);
                break;
        }
    }
}
