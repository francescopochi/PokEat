package com.example.pokeat;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogin, btnRegister;
    EditText edTxMail, edTxPassw;
    static final int PASSW_LEN = 6;
    static final String EMAIL_KEY = "email";

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.btn_login) {
            doLogin();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Colleghiamo a questa classe Activity l'XML dell'activity_main
        setContentView(R.layout.activity_main);

        Log.i("Lifecycle", "Activity created");

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_registrazione);
        edTxMail = findViewById(R.id.edTxMail);
        edTxPassw = findViewById(R.id.edTxPassw);
        switchDarkMode = findViewById(R.id.switch_dark);
        final LinearLayout mainLayout = findViewById(R.id.main_layout);

        if(hasInvitationCode())
            btnRegister.setVisibility(View.GONE);

        btnLogin.setOnClickListener(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            /* poichè sono in una classe anonima, uso MainActivity.this per definire il contesto,
            altrimenti tratterebbe this come il contesto della classe anonima*/

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

                String email = edTxMail.getText().toString();

                intent.putExtra(EMAIL_KEY, email);
                startActivity(intent);
            }
        });
    }

    private boolean hasInvitationCode(){
        return false;
    }

    public void doLogin(){

        String email = edTxMail.getText().toString();
        String password = edTxPassw.getText().toString();

        if(!verifyMail(email)){
            showToast(R.string.email_invalid);
            return;
        }
        if(password.length() < PASSW_LEN){
            showToast(R.string.password_invalid);
            return;
        }

        showToast(R.string.login_success);
    }

    private boolean verifyMail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void showToast(@StringRes int redId){
        Toast.makeText(this, getString(redId), Toast.LENGTH_LONG).show();
    }

}
