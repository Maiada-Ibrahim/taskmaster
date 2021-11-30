package com.example.taskmaster;
//import androidx.activity.result.ActivityResult;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


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


                        Task task2 = Task.builder()
                                .title(getinputTitle).body(getinputBody)
                                .state(getinputState).team(teams.get("Team2"))
                                .fileName(key)
                                .build();
                        saveToAPI(task2);
                        Log.i("add to team ", "onCreate: "+task2.getTeam().getName());
                        break;
                    case "Team3":
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
        Button pickFileButton = findViewById(R.id.upload);
        pickFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
                Toast.makeText(Add_Task.this, "pick", Toast.LENGTH_SHORT).show();
            }
        });

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
    System.out.println("l");
    System.out.println(data);
    assert data != null;
    dataUri = data.getData();
    Uri uri = data.getData();
//    File file = new File(dataUri.getPath());
//    fileName = file.getName();
    String src = uri.getPath();

//    Log.i("0000", file.toString());
////    uploadFile(file);
//
////    if (requesstCode == CODE_REQUEST && resultCode == RESULT_OK) {
//         file = new File(getApplicationContext().getFilesDir(), "file");
//        Log.i("s", "create stream");
////            key = new Date().toString()+" File";
//    try {
//        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//        writer.append("Example file contents");
//        writer.close();
//    } catch (Exception exception) {
//        Log.e("MyAmplifyApp", "Upload failed", exception);
//    }
    fileType = getContentResolver().getType(uri);
    fileName = new SimpleDateFormat("yyMMddHHmmssZ").format(new Date())+"." + fileType.split("/")[1];
    File source = new File(src);
    String file = uri.getLastPathSegment();
    File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/CustomFolder"+file);
    uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFile");

//    Amplify.Storage.uploadFile(
//            file,
//            uploadFile,
//            result -> {Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey());
//            key= result.getKey();
//
//            },
//            storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
//    );
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
}
