package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Add_Task extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Handler handler;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private Map<String, Team> teams =  new HashMap<>();
    private Spinner teamSpinner;


    ArrayList<Team> chooseTeam =new  ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

//        handler = new Handler(Looper.getMainLooper(),
//                new Handler.Callback() {
//                    @Override
//                    public boolean handleMessage(@NonNull Message message) {
//                        recyclerView.getAdapter().notifyDataSetChanged();
//                        return false;
//                    }
//                });


//        DataBase db  = DataBase.getDataBaseObj(this.getApplicationContext());
//        Daorep taskDao = db.taskDao();



        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Team1", "Team2", "Team3"});
        teamSpinner = findViewById(R.id.teamSpinner);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(dataAdapter2);
        getTeams();
        radioGroup=findViewById(R.id.radioGroup);
        Button addTaskP2=findViewById(R.id.addTaskP2);
        addTaskP2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                EditText inputTitle=findViewById(R.id.titlAdd);
                EditText inputBody=findViewById(R.id.bodyAdd);
                EditText inputState=findViewById(R.id.stateAdd);
                int radioCheckedId =radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(radioCheckedId);

                String getinputTitle = inputTitle.getText().toString();
                String getinputBody = inputBody.getText().toString();
                String getinputState = inputState.getText().toString();
                String getinputeam = radioButton.getText().toString();

//                System.out.println(getinputeam);
//                Amplify.API.query(
//                        ModelQuery.list(Team.class,Team.NAME.contains(getinputeam)),
//                        response -> {
//                            for (Team team : response.getData()) {
//                                Log.i("MyAmplifyApp", team.getName());
//                                chooseTeam.add(team);
//                                teams.put(getinputeam,team);
//
//                            }
//                    tasks1.set(tasksFirst);

////                            handler.sendEmptyMessage(1);
//                            System.out.println(teams.get(getinputeam));
//                        },
//                        error -> Log.e("MyAmplifyApp", "Query failure", error)
//                );
//                taskDao.insertAll(new Task(getinputTitle,getinputBody,getinputState));
//getTeams();
//         Team team10 =Team.builder()
//        .name(chooseTeam.get(0).getName())
//        .id(chooseTeam.get(0).getId())
//        .build();

//                Task task = Task.builder()
//                        .title(getinputTitle)
//                        .body(getinputBody)
//                        .state(getinputState)
////                        .team ()
//                        .build();
//
//                Amplify.API.mutate(
//                        ModelMutation.create(task),
//                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()
//                        ),
//
//                error -> Log.e("MyAmplifyApp", "Create failed", error)
//                );
//                Toast.makeText(getApplicationContext(), "submitted!", Toast.LENGTH_LONG).show();
//                finish();
//                saveToAPI(task);


                switch(teamSpinner.getSelectedItem().toString()){
                    case "Team1":
                        Task task = Task.builder().title(getinputTitle).body(getinputBody).state(getinputState).team(teams.get("Team1")).build();
                        saveToAPI(task);
                        Log.i("add to team ", "onCreate: "+task.getTeam().getName());

                        break;
                    case "Team2":
                        Task task2 = Task.builder().title(getinputTitle).body(getinputBody).state(getinputState).team(teams.get("Team2")).build();
                        saveToAPI(task2);
                        Log.i("add to team ", "onCreate: "+task2.getTeam().getName());
                        break;
                    case "Team3":
                        Task task3 = Task.builder().title(getinputTitle).body(getinputBody).state(getinputState).team(teams.get("Team3")).build();
                        saveToAPI(task3);

                        Log.i("add to team ", "onCreate: "+task3.getTeam().getName());

                        break;
                }

                Toast toast = Toast.makeText(getApplicationContext(),"Submitted!", Toast.LENGTH_SHORT);
                toast.show();


            }
        });




    }
    public void checkButton(View v){
       int radioCheckedId =radioGroup.getCheckedRadioButtonId();
       radioButton=findViewById(radioCheckedId);

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


}
