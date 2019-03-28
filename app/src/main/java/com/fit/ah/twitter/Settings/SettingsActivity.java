package com.fit.ah.twitter.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fit.ah.twitter.Login.LoginActivity;
import com.fit.ah.twitter.ProcessActivity;
import com.fit.ah.twitter.Profile.model.CountriesVM;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.ResponseVM;
import com.fit.ah.twitter.Settings.model.SettingsVM;
import com.fit.ah.twitter.api.MiscApi;
import com.fit.ah.twitter.api.UsersApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView username, phone, country, password, email;
    LinearLayout usernameLayout, phoneLayout, countryLayout, passwordLayout, emailLayout, deactivateLayout;
    SettingsVM model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setTitle(R.string.settings_title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password_label);
        country = findViewById(R.id.country);

        usernameLayout = findViewById(R.id.username_layout);
        phoneLayout = findViewById(R.id.phone_layout);
        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.password_layout);
        countryLayout = findViewById(R.id.country_layout);
        deactivateLayout = findViewById(R.id.deactivate_layout);

        usernameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MyApp.getContext());
                View myView = li.inflate(R.layout.dialog_settings, null);

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(new ContextThemeWrapper(SettingsActivity.this, R.style.myDialog));

                alertDialogBuilder.setView(myView);

                final EditText userInput = myView.findViewById(R.id.username);
                final TextView txtVal = myView.findViewById(R.id.txtVal1);
                final Button btnApply = myView.findViewById(R.id.btnApply);
                TextView title = myView.findViewById(R.id.title);
                Button btnCancel = myView.findViewById(R.id.btnCancel);

                title.setText("Change username");
                userInput.setText(model.username);
                final AlertDialog alertDialog = alertDialogBuilder.create();

                userInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        final String currentUsername = s.toString();
                        UsersApi.CheckUsername(MyApp.getContext(), new MyRunnable<Boolean>() {
                            @Override
                            public void run(Boolean result) {
                                if (result) {
                                    if (currentUsername.length() == 0 || currentUsername.length() < 3 || currentUsername.length() > 16) {
                                        DrawableCompat.setTint(userInput.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                                        userInput.setTextColor(getResources().getColor(R.color.validation_red));
                                        btnApply.setBackground(getResources().getDrawable(R.drawable.rounded_button_disabled));
                                        btnApply.setEnabled(false);
                                        if (currentUsername.length() < 3)
                                            txtVal.setText("Username must contain more than 3 characters.");
                                        if (currentUsername.length() == 0)
                                            txtVal.setText("Username field is blank.");
                                        if (currentUsername.length() > 16)
                                            txtVal.setText("Username must not contain more than 16 characters.");
                                    } else {
                                        txtVal.setText("");
                                        DrawableCompat.setTint(userInput.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                                        userInput.setTextColor(getResources().getColor(R.color.text_color_primary));
                                        btnApply.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                                        btnApply.setEnabled(true);

                                    }
                                } else {
                                    if(!MySession.logiraniKorisnik.username.equals(currentUsername)){
                                        txtVal.setText("Username is not available.");
                                        DrawableCompat.setTint(userInput.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                                        userInput.setTextColor(getResources().getColor(R.color.validation_red));
                                        btnApply.setBackground(getResources().getDrawable(R.drawable.rounded_button_disabled));
                                        btnApply.setEnabled(false);
                                    }
                                    else{
                                        btnApply.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                                        btnApply.setEnabled(true);
                                    }
                                }
                            }
                        }, currentUsername);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.username = userInput.getText().toString();
                        UsersApi.SettingsPut(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                            @Override
                            public void run(ResponseVM result) {
                                alertDialog.cancel();
                                startActivity(new Intent(MyApp.getContext(), ProcessActivity.class));
                                Toast.makeText(MyApp.getContext(), "You've successfully changed your username",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, MySession.logiraniKorisnik.userID, model);

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();

            }
        });

        emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MyApp.getContext());
                View myView = li.inflate(R.layout.dialog_settings, null);

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(new ContextThemeWrapper(SettingsActivity.this, R.style.myDialog));

                alertDialogBuilder.setView(myView);

                final EditText userInput = myView.findViewById(R.id.username);
                final TextView txtVal = myView.findViewById(R.id.txtVal1);
                final Button btnApply = myView.findViewById(R.id.btnApply);
                TextView title = myView.findViewById(R.id.title);
                Button btnCancel = myView.findViewById(R.id.btnCancel);

                userInput.setText(model.email);
                title.setText("Change email address");
                final AlertDialog alertDialog = alertDialogBuilder.create();

                userInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String emailAddress = s.toString().trim();

                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                            DrawableCompat.setTint(userInput.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                            userInput.setTextColor(getResources().getColor(R.color.text_color_primary));
                            txtVal.setText("");
                            btnApply.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                            btnApply.setEnabled(true);
                        }
                        else{
                            DrawableCompat.setTint(userInput.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                            userInput.setTextColor(getResources().getColor(R.color.validation_red));
                            txtVal.setText("Invalid email format.");
                            btnApply.setBackground(getResources().getDrawable(R.drawable.rounded_button_disabled));
                            btnApply.setEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.email = userInput.getText().toString();
                        UsersApi.SettingsPut(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                            @Override
                            public void run(ResponseVM result) {
                                alertDialog.cancel();
                                startActivity(new Intent(MyApp.getContext(), ProcessActivity.class));
                                Toast.makeText(MyApp.getContext(), "You've successfully changed your email address",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, MySession.logiraniKorisnik.userID, model);

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();

            }
        });

        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MyApp.getContext());
                View myView = li.inflate(R.layout.dialog_settings, null);

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(new ContextThemeWrapper(SettingsActivity.this, R.style.myDialog));

                alertDialogBuilder.setView(myView);

                final EditText userInput = myView.findViewById(R.id.username);
                final TextView txtVal = myView.findViewById(R.id.txtVal1);
                final Button btnApply = myView.findViewById(R.id.btnApply);
                TextView title = myView.findViewById(R.id.title);
                Button btnCancel = myView.findViewById(R.id.btnCancel);

                userInput.setInputType(InputType.TYPE_CLASS_NUMBER);

                userInput.setText(model.phone);
                title.setText("Change phone number");
                final AlertDialog alertDialog = alertDialogBuilder.create();

                userInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String phoneNumber = s.toString().trim();

                        if (phoneNumber.length() < 15 && phoneNumber.length() > 8) {
                            DrawableCompat.setTint(userInput.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                            userInput.setTextColor(getResources().getColor(R.color.text_color_primary));
                            btnApply.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                            btnApply.setEnabled(true);
                        }
                        else if(phoneNumber.isEmpty()){
                            if(phoneNumber.isEmpty())
                                txtVal.setText("No phone number applied.");
                            else
                                txtVal.setText("");
                            txtVal.setTextColor(getResources().getColor(R.color.text_color_secondary));
                            DrawableCompat.setTint(userInput.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                            userInput.setTextColor(getResources().getColor(R.color.text_color_primary));
                            btnApply.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                            btnApply.setEnabled(true);
                        }
                        else{
                            DrawableCompat.setTint(userInput.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                            userInput.setTextColor(getResources().getColor(R.color.validation_red));
                            txtVal.setTextColor(getResources().getColor(R.color.validation_red));
                            if(phoneNumber.length() >= 15)
                                txtVal.setText("Phone field must not contain more than 14 characters.");
                            else if(phoneNumber.length() <= 8)
                                txtVal.setText("Phone field must contain more than 8 characters.");
                            btnApply.setBackground(getResources().getDrawable(R.drawable.rounded_button_disabled));
                            btnApply.setEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.phone = userInput.getText().toString();
                        UsersApi.SettingsPut(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                            @Override
                            public void run(ResponseVM result) {
                                alertDialog.cancel();
                                startActivity(new Intent(MyApp.getContext(), ProcessActivity.class));
                                Toast.makeText(MyApp.getContext(), "You've successfully changed your phone number",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, MySession.logiraniKorisnik.userID, model);

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();

            }
        });

        passwordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MyApp.getContext());
                View myView = li.inflate(R.layout.dialog_settings_password, null);

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(new ContextThemeWrapper(SettingsActivity.this, R.style.myDialog));

                alertDialogBuilder.setView(myView);

                final PassValModel passValModel = new PassValModel();

                final EditText userInput1 = myView.findViewById(R.id.value1);
                final TextView txtVal1 = myView.findViewById(R.id.txtVal1);
                final EditText userInput2 = myView.findViewById(R.id.value2);
                final TextView txtVal2 = myView.findViewById(R.id.txtVal2);
                final EditText userInput3 = myView.findViewById(R.id.value3);
                final TextView txtVal3 = myView.findViewById(R.id.txtVal3);
                final Button btnApply = myView.findViewById(R.id.btnApply);
                TextView title = myView.findViewById(R.id.title);
                Button btnCancel = myView.findViewById(R.id.btnCancel);

                userInput1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                userInput2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                userInput3.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                title.setText("Change password");
                final AlertDialog alertDialog = alertDialogBuilder.create();

                userInput1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String password = s.toString().trim();

                        if (model.password.equals(password)) {
                            DrawableCompat.setTint(userInput1.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                            userInput1.setTextColor(getResources().getColor(R.color.text_color_primary));
                            txtVal1.setText("");
                            passValModel.val1 = true;
                        }
                        else{
                            DrawableCompat.setTint(userInput1.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                            userInput1.setTextColor(getResources().getColor(R.color.validation_red));
                            passValModel.val1 = false;
                            if(password.length() == 0)
                                txtVal1.setText("Old password field is blank.");
                            else
                                txtVal1.setText("Old password field is not correct.");
                        }
                        CheckPassValidation(passValModel, btnApply);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                userInput2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String password = s.toString().trim();

                        if(password.length() == 0 || password.length() < 4){
                            DrawableCompat.setTint(userInput2.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                            userInput2.setTextColor(getResources().getColor(R.color.validation_red));
                            passValModel.val2 = false;
                            if(password.length() < 4)
                                txtVal2.setText("New password field must contain more than 4 characters.");
                            if(password.length() == 0)
                                txtVal2.setText("New password field is blank.");
                        }
                        else{
                            DrawableCompat.setTint(userInput2.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                            userInput2.setTextColor(getResources().getColor(R.color.text_color_primary));
                            txtVal2.setText("");
                            passValModel.val2 = true;
                        }
                        CheckPassValidation(passValModel, btnApply);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                userInput3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String password = s.toString().trim();

                        if(!password.equals(userInput2.getText().toString())){
                            DrawableCompat.setTint(userInput3.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                            userInput3.setTextColor(getResources().getColor(R.color.validation_red));
                            txtVal3.setText("New password fields must match.");
                            passValModel.val3 = false;
                        }
                        else{
                            DrawableCompat.setTint(userInput3.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                            userInput3.setTextColor(getResources().getColor(R.color.text_color_primary));
                            txtVal3.setText("");
                            passValModel.val3 = true;
                        }
                        CheckPassValidation(passValModel, btnApply);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.password = userInput2.getText().toString();
                        UsersApi.SettingsPut(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                            @Override
                            public void run(ResponseVM result) {
                                alertDialog.cancel();
                                startActivity(new Intent(MyApp.getContext(), ProcessActivity.class));
                                Toast.makeText(MyApp.getContext(), "You've successfully changed your password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, MySession.logiraniKorisnik.userID, model);

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();

            }
        });

        countryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MyApp.getContext());
                View myView = li.inflate(R.layout.dialog_settings_spinner, null);

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(new ContextThemeWrapper(SettingsActivity.this, R.style.myDialog));

                alertDialogBuilder.setView(myView);

                final Spinner userInput = myView.findViewById(R.id.username);
                final Button btnApply = myView.findViewById(R.id.btnApply);
                TextView title = myView.findViewById(R.id.title);
                Button btnCancel = myView.findViewById(R.id.btnCancel);

                MiscApi.CountriesLoad(MyApp.getContext(), new MyRunnable<CountriesVM[]>() {
                    @Override
                    public void run(CountriesVM[] result) {
                        if(result != null){
                            List<String> nazivi = new ArrayList<>();
                            for (CountriesVM item : result)
                                nazivi.add(item.CountryName);
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MyApp.getContext(),
                                    R.layout.country_spinner_item, nazivi);
                            userInput.setAdapter(arrayAdapter);
                            userInput.setSelection(model.countryID-1);
                        }
                    }
                });

                userInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        model.countryID = position+1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                title.setText("Change country");
                final AlertDialog alertDialog = alertDialogBuilder.create();

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.countryName = userInput.getSelectedItem().toString();
                        UsersApi.SettingsPut(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                            @Override
                            public void run(ResponseVM result) {
                                alertDialog.cancel();
                                startActivity(new Intent(MyApp.getContext(), ProcessActivity.class));
                                Toast.makeText(MyApp.getContext(), "You've successfully changed your country",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, MySession.logiraniKorisnik.userID, model);

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();

            }
        });

        deactivateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MyApp.getContext());
                View myView = li.inflate(R.layout.dialog_settings_standard, null);

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(new ContextThemeWrapper(SettingsActivity.this, R.style.myDialog));

                alertDialogBuilder.setView(myView);

                final Button btnOk = myView.findViewById(R.id.btnOk);
                Button btnCancel = myView.findViewById(R.id.btnCancel);

                final AlertDialog alertDialog = alertDialogBuilder.create();

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UsersApi.Deactivate(MyApp.getContext(), new MyRunnable<ResponseVM>() {
                            @Override
                            public void run(ResponseVM result) {
                                if(result.responseCode == 200){
                                    alertDialog.cancel();
                                    MySession.logiraniKorisnik = null;
                                    MySession.authToken = null;
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(getString(R.string.session_username), "");
                                    editor.commit();
                                    finish();
                                    startActivity(new Intent(MyApp.getContext(), LoginActivity.class));
                                }
                                Toast.makeText(MyApp.getContext(), result.responseMessage, Toast.LENGTH_SHORT).show();
                            }
                        }, MySession.logiraniKorisnik.userID);
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();

            }
        });

        LoadData();
    }

    private void CheckPassValidation(PassValModel model, Button btn) {
        if(model.IsValid()){
            btn.setEnabled(true);
            btn.setBackground(getResources().getDrawable(R.drawable.rounded_button));
        }
        else{
            btn.setEnabled(false);
            btn.setBackground(getResources().getDrawable(R.drawable.rounded_button_disabled));
        }
    }

    private void LoadData() {
        UsersApi.SettingsLoad(MyApp.getContext(), new MyRunnable<SettingsVM>() {
            @Override
            public void run(SettingsVM result) {
                model = result;
                username.setText(result.username);
                email.setText(result.email);
                country.setText(result.countryName);
                username.setText(result.username);
                if(result.phone == null)
                    phone.setText("Add");
                else
                    phone.setText(result.phone);
            }
        }, MySession.logiraniKorisnik.userID);
    }

    @Override
    protected void onResume() {
        LoadData();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_color));

        return true;
    }

    private class PassValModel {
        public boolean val1;
        public boolean val2;
        public boolean val3;

        public PassValModel(){
            val1 = false;
            val2 = false;
            val3 = false;
        }

        public boolean IsValid() {
            return val1 && val2 && val3;
        }
    }
}
