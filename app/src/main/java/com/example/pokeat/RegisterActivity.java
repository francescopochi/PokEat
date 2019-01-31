package com.example.pokeat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
public class RegisterActivity extends AppCompatActivity {

    EditText emailEdT, passwEdt, phoneEdt;
    Button regBtn;
    private static final int PASSW_LENGTH = 6, PHONE_LENGTH = 10;
    private boolean isEmailValid, isPasswordValid, isPhoneValid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEdT = findViewById(R.id.edTxMailRegistration);
        passwEdt = findViewById(R.id.edTxPasswRegistration);
        phoneEdt = findViewById(R.id.edTxPhone);
        regBtn = findViewById(R.id.btn_registrazione2);

        // LISTENER PER EMAIL
        emailEdT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEmailValid = Utils.verifyEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton(regBtn);
            }

        });

        // LISTENER PER PASSWORD
        passwEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPasswordValid = Utils.verifyPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton(regBtn);
            }

        });

        // LISTENER PER NUMERO DI TELEFONO
        phoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

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
    private void enableButton(Button btn){
        btn.setEnabled(isEmailValid && isPhoneValid && isPasswordValid);
    }

}
