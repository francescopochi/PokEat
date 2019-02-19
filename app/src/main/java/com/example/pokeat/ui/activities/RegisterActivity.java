package com.example.pokeat.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.pokeat.R;
import com.example.pokeat.Utils;
import com.example.pokeat.datamodels.User;
import com.example.pokeat.services.RestController;
import com.example.pokeat.ui.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {

    EditText emailEdT, passwEdt, phoneEdt;
    Button regBtn;
    private boolean isEmailValid, isPasswordValid, isPhoneValid;
    RestController restController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        restController = new RestController(this);

        emailEdT = findViewById(R.id.edTxMailRegistration);
        passwEdt = findViewById(R.id.edTxPasswRegistration);
        phoneEdt = findViewById(R.id.edTxPhone);
        regBtn = findViewById(R.id.btn_registrazione2);
        regBtn.setOnClickListener(this);

        // LISTENER PER EMAIL --------------------------------------------------------------
        emailEdT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEmailValid = Utils.verifyEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton(regBtn);
            }

        });

        // LISTENER PER PASSWORD -----------------------------------------------------------
        passwEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPasswordValid = Utils.verifyPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton(regBtn);
            }

        });

        // LISTENER PER NUMERO DI TELEFONO -------------------------------------------------
        phoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPhoneValid = Utils.verifyPhoneNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton(regBtn);
            }

        });
    }

    // Metodo per abilitare o meno il bottone
    private void enableButton(Button btn) {
        btn.setEnabled(isEmailValid && isPhoneValid && isPasswordValid);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registrazione2) {
            Map<String, String> params = new HashMap<>();
            params.put("username", phoneEdt.getText().toString());
            params.put("email", emailEdT.getText().toString());
            params.put("password", passwEdt.getText().toString());

            restController.postRequest(User.REGISTER_ENDPOINT, params, this, this);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("REGISTERACTIVITY", error.getMessage());
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        Log.d("MAINACTIVITY", response);

        try {
            JSONObject responseJSON = new JSONObject(response);
            String accessToken = responseJSON.getString("jwt");
            SharedPreferencesUtils.putValue(this, User.ACCESS_TOKEN_KEY, accessToken);

            User user = new User(responseJSON.getJSONObject("user"), accessToken);

        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
