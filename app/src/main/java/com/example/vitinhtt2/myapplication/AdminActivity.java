package com.example.vitinhtt2.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity {

    @BindView(R.id.btn_statis) Button _btn_statis;
    @BindView(R.id.btn_signin) Button _btn_signin;
    @BindView(R.id.btn_signup) Button _btn_signup;
    @BindView(R.id.btn_user) Button _btn_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        final Intent intentStatis = new Intent(this, StatisticActivity.class);
        final Intent intentSignin = new Intent(this, LoginActivity.class);
        final Intent intentSignup = new Intent(this, SignUpActivity.class);
        _btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentSignin);
            }
        });
        _btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentSignup);
            }
        });
        _btn_statis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentStatis);
            }
        });
    }
}
