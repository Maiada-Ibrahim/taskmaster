package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;

public class Settings extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        TextView username = findViewById(R.id.username);
        AuthUser authUser = Amplify.Auth.getCurrentUser();
        username.setText(authUser.getUsername());


        spinner = findViewById(R.id.chooseTeamSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Team1", "Team2", "Team3"});
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        Button saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                EditText username = findViewById(R.id.username);
//                String getUserName = username.getText().toString();
                EditText name = findViewById(R.id.username);


                // add value to the shared preferences
                SharedPreferences settingShare= PreferenceManager.getDefaultSharedPreferences(Settings.this);
                // first option
//                settingShare.edit().putString("getUserName",getUserName).apply();
                // second option
                SharedPreferences.Editor sharedPreferencesEditor = settingShare.edit();
//                sharedPreferencesEditor.putString("getUserName", getUserName);
                sharedPreferencesEditor.putString("name", name.getText().toString());

//                sharedPreferencesEditor.apply();
                Toast.makeText(Settings.this,"submitted!", Toast.LENGTH_LONG).show();

                switch(spinner.getSelectedItem().toString()){
                    case "Team1":
                        sharedPreferencesEditor.putString("teamName","Team1");
                        sharedPreferencesEditor.apply();
                        break;
                    case "Team2":
                        sharedPreferencesEditor.putString("teamName","Team2");
                        sharedPreferencesEditor.apply();
                        break;
                    case "Team3":
                        sharedPreferencesEditor.putString("teamName","Team3");
                        sharedPreferencesEditor.apply();
                        break;
                }

                Toast.makeText(getApplicationContext(),"Saved Username!",Toast.LENGTH_SHORT).show();

            }




        });



    }
}