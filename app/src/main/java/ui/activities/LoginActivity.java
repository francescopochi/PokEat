package ui.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokeat.R;
import com.example.pokeat.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_registrazione);
        edTxMail = findViewById(R.id.edTxMail);
        edTxPassw = findViewById(R.id.edTxPassw);

        // listener sul bottone login
        btnLogin.setOnClickListener(this);
        // listener sul bottone register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                String email = edTxMail.getText().toString();

                intent.putExtra(EMAIL_KEY, email);
                startActivity(intent);
            }
        });
    }

    public void doLogin(){

        String email = edTxMail.getText().toString();
        String password = edTxPassw.getText().toString();

        if(!Utils.verifyEmail(email)){
            showToast(R.string.email_invalid);
            return;
        }
        if(password.length() < PASSW_LEN){
            showToast(R.string.password_invalid);
            return;
        }

        showToast(R.string.login_success);
    }

    private void showToast(@StringRes int redId){
        Toast.makeText(this, getString(redId), Toast.LENGTH_LONG).show();
    }

}
