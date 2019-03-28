package com.fit.ah.twitter.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fit.ah.twitter.Profile.model.EditValidationModel;
import com.fit.ah.twitter.Profile.model.UsersEditVM;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.api.UsersApi;
import com.fit.ah.twitter.helper.MyApp;
import com.fit.ah.twitter.helper.MyBitmapConverter;
import com.fit.ah.twitter.helper.MyRunnable;
import com.fit.ah.twitter.helper.MySession;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private static final int OPEN_DOCUMENT_CODE = 2;
    Spinner countriesSpinner;
    EditText etNickname;
    Toolbar toolbar;
    Button btnEdit, btnPicture, btnHeader;
    UsersEditVM user;
    EditValidationModel model;
    TextView tvNickname;
    CircleImageView imgProfile;
    ImageView imgHeader;
    Bitmap pictureProfile, pictureHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setTitle("Edit profile");
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        countriesSpinner = findViewById(R.id.spCountry);
        etNickname = findViewById(R.id.etNickname);
        btnEdit = findViewById(R.id.btnEdit);
        tvNickname = findViewById(R.id.tvValNickname);
        btnPicture = findViewById(R.id.btnPicture);
        imgProfile = findViewById(R.id.imgPreview);
        imgHeader = findViewById(R.id.imgHeaderPreview);
        btnHeader = findViewById(R.id.btnHeaderPicture);

        model = new EditValidationModel();

        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, OPEN_DOCUMENT_CODE);
            }
        });

        btnHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 3);
            }
        });

        UsersApi.ProfileLoadEditData(MyApp.getContext(), new MyRunnable<UsersEditVM>() {
            @Override
            public void run(UsersEditVM result) {
                etNickname.setText(result.nickname);
                if(result.imageProfile != null)
                    imgProfile.setImageBitmap(MyBitmapConverter.StringToBitmap(result.imageProfile));
                else
                    imgProfile.setImageResource(R.mipmap.ic_launcher_round);
                if(result.imageHeader != null)
                    imgHeader.setImageBitmap(MyBitmapConverter.StringToBitmap(result.imageHeader));
                else
                    imgHeader.setImageResource(R.drawable.sample_header);
                user = new UsersEditVM(result);
            }
        }, MySession.logiraniKorisnik.userID);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    user.nickname = etNickname.getText().toString();
                    if(pictureProfile != null)
                        user.imageProfile = MyBitmapConverter.BitmapToString(pictureProfile);
                    if(pictureHeader != null)
                        user.imageHeader = MyBitmapConverter.BitmapToString(pictureHeader);
                    UsersApi.ProfileEdit(EditProfileActivity.this, new MyRunnable<UsersEditVM>() {
                        @Override
                        public void run(UsersEditVM result) {
                            finish();
                            Toast.makeText(MyApp.getContext(), "User edited successfully.", Toast.LENGTH_SHORT).show();
                        }
                    }, user, MySession.logiraniKorisnik.userID);
                }
        });
        AddTextListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPEN_DOCUMENT_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    bitmap = MyBitmapConverter.resize(bitmap);
                    imgProfile.setImageBitmap(bitmap);
                    pictureProfile = bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    bitmap = MyBitmapConverter.resize(bitmap);
                    imgHeader.setImageBitmap(bitmap);
                    pictureHeader = bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void AddTextListener() {
        etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0 || s.length() > 16){
                    DrawableCompat.setTint(etNickname.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.validation_red));
                    etNickname.setTextColor(getResources().getColor(R.color.validation_red));
                    model.nickname = false;
                    if(s.length() == 0)
                        tvNickname.setText("Nickname field must not be blank.");
                    if(s.length() > 16)
                        tvNickname.setText("Nickname must not contain more than 16 characters.");
                }
                else{
                    DrawableCompat.setTint(etNickname.getBackground(), ContextCompat.getColor(MyApp.getContext(), R.color.text_color_secondary));
                    etNickname.setTextColor(getResources().getColor(R.color.text_color_primary));
                    model.nickname = true;
                    tvNickname.setText("");
                }
                ButtonValid();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void ButtonValid() {
        btnEdit.setEnabled(model.isValid());
        if(model.isValid())
            btnEdit.setBackground(getResources().getDrawable(R.drawable.rounded_button));
        else
            btnEdit.setBackground(getResources().getDrawable(R.drawable.rounded_button_disabled));
    }
}
