package com.example.taskmaster;
//import androidx.activity.result.ActivityResult;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class Add_Task extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Handler handler;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private Map<String, Team> teams =  new HashMap<>();
    private Spinner teamSpinner;




    private String key;


     String fileName;
     Uri dataUri;
    private static final int CODE_REQUEST =55 ;
    private static final String TAG = "upload";



    private String fileType;
    private File uploadFile;

    EditText taskTitleInput;
    EditText taskDescriptionInput;
    String imgSrc;


    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    String longitude;
    String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Team1", "Team2", "Team3"});
        teamSpinner = findViewById(R.id.teamSpinner);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(dataAdapter2);



        getTeams();
//        radioGroup=findViewById(R.id.radioGroup);
        Button addTaskP2=findViewById(R.id.addTaskP2);
        addTaskP2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                EditText inputTitle=findViewById(R.id.titlAdd);
                EditText inputBody=findViewById(R.id.bodyAdd);
                EditText inputState=findViewById(R.id.stateAdd);
//                int radioCheckedId =radioGroup.getCheckedRadioButtonId();
//                radioButton=findViewById(radioCheckedId);

                String getinputTitle = inputTitle.getText().toString();
                String getinputBody = inputBody.getText().toString();
                String getinputState = inputState.getText().toString();
//                String getinputeam = radioButton.getText().toString();

                switch(teamSpinner.getSelectedItem().toString()){
                    case "Team1":
                        uploadFile();
                        Task task = Task.builder()
                                .title(getinputTitle).body(getinputBody)
                                .state(getinputState)

                                .fileName(fileName)

                                .team(teams.get("Team1"))

                                .build();
                        saveToAPI(task);
                        Log.i("add to team ", "onCreate: "+task.getTeam().getName());

                        break;
                    case "Team2":
                        uploadFile();

                        Task task2 = Task.builder()
                                .title(getinputTitle).body(getinputBody)
                                .state(getinputState).team(teams.get("Team2"))
                                .fileName(key)

                                .build();
                        saveToAPI(task2);
                        Log.i("add to team ", "onCreate: "+task2.getTeam().getName());
                        break;
                    case "Team3":
                        uploadFile();
                        Task task3 = Task.builder()
                                .title(getinputTitle)
                                .body(getinputBody)
                                .state(getinputState)
                                .team(teams.get("Team3"))
                                .fileName(key)
                                .build();
                        saveToAPI(task3);

                        Log.i("add to team ", "onCreate: "+task3.getTeam().getName());

                        break;
                }

                Toast toast = Toast.makeText(getApplicationContext(),"Submitted!", Toast.LENGTH_SHORT);
                toast.show();
            }

        });


        //----------------------------------------------------------
        Button pickFileButton = findViewById(R.id.upload);
        pickFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
                Toast.makeText(Add_Task.this, "pick", Toast.LENGTH_SHORT).show();
            }
        });


       //--------------------------------------------------------------------
//      Intent intent = getIntent();
//      Bundle bundle=intent.getExtras();
//      Uri uri= (Uri) bundle.get(Intent.EXTRA_STREAM);
//      if (intent.getType()!= null){
//
//
//
//      }
    }
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){


            Log.i("INTENT in RESUME", "onResume: "+intent.getData());

            if (intent.getType().equals("text/plain")){
                taskTitleInput.setText(extras.get("android.intent.extra.SUBJECT").toString());
                taskDescriptionInput.setText(extras.get("android.intent.extra.TEXT").toString());
            }else if (intent.getType().contains("image/")){

                Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                String src = uri.getPath();

                imgSrc =src;

                fileType = getContentResolver().getType(uri);
                fileName = new SimpleDateFormat("yyMMddHHmmssZ").format(new Date())+"." + fileType.split("/")[1];

                File source = new File(src);
                String file = uri.getLastPathSegment();
                File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/CustomFolder"+file);
                uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFile");
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    FileUtils.copy(inputStream, new FileOutputStream(uploadFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (imgSrc != null){
            TextView imgSrc = findViewById(R.id.imgSrc);
            imgSrc.setText(this.imgSrc);
            imgSrc.setVisibility(View.VISIBLE);
        }
    }
    private void getTeams() {
        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team team : response.getData()) {
                        switch(team.getName()) {
                            case "Team1":
                                this.teams.put("Team1", team);
                                break;
                            case "Team2":
                                this.teams.put("Team2", team);
                                break;
                            case "Team3":
                                this.teams.put("Team3", team);
                                break;
                        }
                        System.out.println(teams.get("Team1"));
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
    }

    public void saveToAPI(Task task){
        Amplify.API.mutate(ModelMutation.create(task),
                success -> Log.i("Tutorial", "Saved item: " + success.getData()),
                error -> Log.e("Tutorial", "Could not save item to API", error)
        );
    }
  @SuppressLint("IntentReset")
    private  void  pickFile(){
    @SuppressLint("IntentReset")  Intent selecteFile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    selecteFile.setType(("*/*"));
    selecteFile=Intent.createChooser(selecteFile,"Select File");
    startActivityForResult(selecteFile,1234);

  }

@Override
    protected void onActivityResult(int requesstCode,int resultCode, @Nullable Intent data) {
    super.onActivityResult(requesstCode, resultCode, data);
    assert data != null;
    dataUri = data.getData();
    Uri uri = data.getData();

    String src = uri.getPath();
    fileType = getContentResolver().getType(uri);
    fileName = new SimpleDateFormat("yyMMddHHmmssZ").format(new Date())+"." + fileType.split("/")[1];
    File source = new File(src);
    String file = uri.getLastPathSegment();
    File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/CustomFolder"+file);
    uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFile");

    try {
        InputStream inputStream = getContentResolver().openInputStream(data.getData());
        FileUtils.copy(inputStream, new FileOutputStream(uploadFile));
    } catch (IOException e) {
        e.printStackTrace();
    }

}

    private void uploadFile(){
        Amplify.Storage.uploadFile(
                fileName,
                uploadFile,
                success -> {
                    Log.i(TAG, "uploadFileToS3: succeeded " + success.getKey());
                },
                error -> {
                    Log.e(TAG, "uploadFileToS3: failed " + error.toString());
                }
        );
    }
    //-------location----------------
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            longitude = String.valueOf(location.getLongitude());
                            latitude = String.valueOf(location.getLatitude());

                        }
                    }

                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

    }
    private LocationCallback mLocationCallback = new LocationCallback() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            longitude = String.valueOf(mLastLocation.getLongitude());
            latitude = String.valueOf(mLastLocation.getLatitude());
        }
    };

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }
}
