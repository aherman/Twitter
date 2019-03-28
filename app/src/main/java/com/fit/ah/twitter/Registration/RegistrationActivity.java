package com.fit.ah.twitter.Registration;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fit.ah.twitter.InterestsActivity;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.Profile.model.CountriesVM;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.Registration.model.RegistrationEditVM;
import com.fit.ah.twitter.Registration.model.RegistrationVM;
import com.fit.ah.twitter.api.MiscApi;
import com.fit.ah.twitter.api.UsersApi;
import com.fit.ah.twitter.helper.MyRunnable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner spinner;
    RegistrationVM user;
    TextView tvUsername, tvEmail, tvPassword, tvConfPassword, tvDate;
    EditText etUsername, etEmail, etPassword, etConfPassword;
    DatePicker dpDate;
    Button btnRegister;
    RegistrationEditVM model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setTitle("Registration");
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        model = new RegistrationEditVM();

        spinner = findViewById(R.id.spCountry);
        tvUsername = findViewById(R.id.tvValUsername);
        tvEmail = findViewById(R.id.tvValEmail);
        tvPassword = findViewById(R.id.tvValPass);
        tvConfPassword = findViewById(R.id.tvValConfPass);
        tvDate = findViewById(R.id.tvValDate);

        dpDate = findViewById(R.id.dpDate);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfPassword = findViewById(R.id.etConfPassword);
        etEmail = findViewById(R.id.etEmail);

        btnRegister = findViewById(R.id.btnRegister);

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final CharSequence seq = s;
                UsersApi.CheckUsername(MyApp.getContext(), new MyRunnable<Boolean>() {
                    @Override
                    public void run(Boolean result) {
                        if(result){
                            if(seq.length() == 0 || seq.length() < 3 || seq.length() > 16){
                                DrawableCompat.setTint(etUsername.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                                etUsername.setTextColor(getResources().getColor(R.color.validation_red));
                                model.username = false;
                                if(seq.length() < 3)
                                    tvUsername.setText("Username must contain more than 3 characters.");
                                if(seq.length() == 0)
                                    tvUsername.setText("Username field is blank.");
                                if(seq.length() > 16)
                                    tvUsername.setText("Username must not contain more than 16 characters.");
                            }else{
                                DrawableCompat.setTint(etUsername.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                                etUsername.setTextColor(getResources().getColor(R.color.text_color_primary));
                                model.username = true;
                                tvUsername.setText("");
                            }
                        }else{
                                tvUsername.setText("Username is not available.");
                                DrawableCompat.setTint(etUsername.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                                etUsername.setTextColor(getResources().getColor(R.color.validation_red));
                                model.username = false;
                        }
                        ButtonValid();
                    }
                }, etUsername.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0 || s.length() < 4){
                    DrawableCompat.setTint(etPassword.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                    etPassword.setTextColor(getResources().getColor(R.color.validation_red));
                    model.password = false;
                    if(s.length() < 4)
                        tvPassword.setText("New password field must contain more than 4 characters.");
                    if(s.length() == 0)
                        tvPassword.setText("New password field is blank.");
                }
                else{
                    DrawableCompat.setTint(etPassword.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                    etPassword.setTextColor(getResources().getColor(R.color.text_color_primary));
                    model.password = true;
                    tvPassword.setText("");
                }
                ButtonValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etConfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(etPassword.getText().toString())){
                    DrawableCompat.setTint(etConfPassword.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                    etConfPassword.setTextColor(getResources().getColor(R.color.validation_red));
                    model.confirmPass = false;
                    tvConfPassword.setText("New password fields must match.");
                }
                else{
                    DrawableCompat.setTint(etConfPassword.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                    etConfPassword.setTextColor(getResources().getColor(R.color.text_color_primary));
                    model.confirmPass = true;
                    tvConfPassword.setText("");
                }
                ButtonValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String emailAddress = s.toString().trim();

                if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                    DrawableCompat.setTint(etEmail.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                    etEmail.setTextColor(getResources().getColor(R.color.text_color_primary));
                    model.email = true;
                    tvEmail.setText("");
                }
                else{
                    DrawableCompat.setTint(etEmail.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                    etEmail.setTextColor(getResources().getColor(R.color.validation_red));
                    model.email = false;
                    tvEmail.setText("Invalid email format.");
                }
                ButtonValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        MiscApi.CountriesLoad(MyApp.getContext(), new MyRunnable<CountriesVM[]>() {
            @Override
            public void run(CountriesVM[] result) {
                if(result != null){
                    List<String> nazivi = new ArrayList<>();
                    for (CountriesVM item : result)
                        nazivi.add(item.CountryName);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MyApp.getContext(),
                            R.layout.country_spinner_item, nazivi);
                    spinner.setAdapter(arrayAdapter);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = true;

                if(etUsername.getText().toString().trim().equals(""))
                    valid = false;
                if(etPassword.getText().toString().trim().equals(""))
                    valid = false;
                if(etConfPassword.getText().toString().trim().equals(""))
                    valid = false;
                if(etEmail.getText().toString().trim().equals(""))
                    valid = false;

                if(valid) {
                    Date date = getDateFromDatePicker(dpDate);
                    if(!date.after(new Date()))
                        Register();
                    else
                        Toast.makeText(MyApp.getContext(), "Not valid. Date mustn't be later than today!", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(MyApp.getContext(), "Not valid. Fill all fields/see validation error messages!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Register() {
        user = new RegistrationVM();
        user.countryID = spinner.getSelectedItemPosition()+1;
        user.birthDate = getDateFromDatePicker(dpDate);
        user.nickname = etUsername.getText().toString() + "101";
        user.username = etUsername.getText().toString();
        user.email = etEmail.getText().toString();
        user.password = etPassword.getText().toString();

        UsersApi.Register(MyApp.getContext(), new MyRunnable<RegistrationVM>() {
            @Override
            public void run(RegistrationVM result) {
                finish();
                Intent i = new Intent(MyApp.getContext(), InterestsActivity.class);
                i.putExtra("username", user.username);
                startActivity(i);
                Toast.makeText(MyApp.getContext(), "You have registered successfully, now find some people.",
                        Toast.LENGTH_SHORT).show();
            }
        }, user);
    }

    private void ButtonValid() {
        btnRegister.setEnabled(model.IsValid());
        if(model.IsValid())
            btnRegister.setBackground(getResources().getDrawable(R.drawable.rounded_button));
        else
            btnRegister.setBackground(getResources().getDrawable(R.drawable.rounded_button_disabled));
    }

    public static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_color));

        return true;
    }
}
