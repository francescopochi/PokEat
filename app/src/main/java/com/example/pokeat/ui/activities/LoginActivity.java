package com.example.pokeat.ui.activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.pokeat.R;
import com.example.pokeat.Utils;
import com.example.pokeat.datamodels.Restaurant;
import com.example.pokeat.datamodels.User;
import com.example.pokeat.services.RestController;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<String> {

    Button btnLogin, btnRegister;
    EditText edTxMail, edTxPassw;
    static final int PASSW_LEN = 6;
    static final String EMAIL_KEY = "email";
    private RestController restController;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
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

        restController = new RestController(this);
    }

    public void doLogin() {
        String email = edTxMail.getText().toString();
        String password = edTxPassw.getText().toString();

        if (!Utils.verifyEmail(email)) {
            showToast(R.string.email_invalid);
            return;
        }
        if (password.length() < PASSW_LEN) {
            showToast(R.string.password_invalid);
            return;
        }

        showToast(R.string.login_success);

        Map<String,String> params = new HashMap<>();
        params.put("identifier", email);
        params.put("password", password);

        restController.postRequest(User.LOGIN_ENDPOINT, params, this, this);
    }

    private void showToast(@StringRes int redId) {
        Toast.makeText(this, getString(redId), Toast.LENGTH_LONG).show();
    }
    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        Log.d("LOGINACTIVITY", response);
        Intent intent = new Intent();
        intent.putExtra("response", response);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("LOGINACTIVITY", error.getMessage());
        showToast(error.getMessage());
    }
}
