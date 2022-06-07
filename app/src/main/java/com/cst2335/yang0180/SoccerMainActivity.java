package com.cst2335.yang0180;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SoccerMainActivity extends AppCompatActivity {

    private static final String HISTORY = "history";
    private static final String EMAIL_HISTORY = "email_history";
    public static final String EMAIL_KEY = "emailKey";
    SharedPreferences sp;
    EditText emailInput;
    EditText passwordInput;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_lab3);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        sp = getSharedPreferences(HISTORY, Context.MODE_PRIVATE);
        String emailHistory = sp.getString(EMAIL_HISTORY,"");
        emailInput.setText(emailHistory);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(SoccerMainActivity.this, SoccerProfileActivity.class);
                goToProfile.putExtra(EMAIL_KEY, emailInput.getText().toString());
                startActivity(goToProfile);
            }
        });

//        Button btn =findViewById(R.id.btn);
//        btn.setOnClickListener(e->{
//            Toast.makeText(this,R.string.information,Toast.LENGTH_LONG).show();
//        });
//        Switch sth = findViewById(R.id.sth);
////        sth.setText("The switch is now on");
////        sth.setTextOff("The switch is now off");
//        sth.setOnCheckedChangeListener((s, checked)->{
//            Snackbar snackbar = null;
//
//            if (checked){
//                snackbar = Snackbar.make(s,R.string.on,Snackbar.LENGTH_LONG);
//            }else{
//                snackbar = Snackbar.make(s,R.string.off,Snackbar.LENGTH_LONG);
//            }
//            snackbar.setAction(R.string.undo, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    sth.setChecked(!checked);
//                }
//            });
//
//            if(snackbar != null){
//                snackbar.show();
//            }
//
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor= sp.edit();
        String email = emailInput.getText().toString();
        editor.putString(EMAIL_HISTORY, email);
        editor.commit();
    }
}