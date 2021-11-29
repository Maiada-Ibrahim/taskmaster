package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.cognito.activities.HostedUIRedirectActivity;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class MainActivity extends AppCompatActivity implements taskAdapter.OnNoteListener {
    private RecyclerView mRecyclerView;
    // vars
    private ArrayList<Task> tasksList = new ArrayList<>();
    private taskAdapter taskAdapterObj;
    private taskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private Handler handler;
    private SharedPreferences sharedPreferences;
    private String teamName = "";
    ArrayList<com.amplifyframework.datastore.generated.model.Task> tasksFirst =new  ArrayList<>();
    ArrayList<com.amplifyframework.datastore.generated.model.Task> tasksTeam =new  ArrayList<>();

    AtomicReference<List<com.amplifyframework.datastore.generated.model.Task>> tasks1 = new AtomicReference<>(new ArrayList<>());



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addTask = findViewById(R.id.addTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
                Intent goToAddTask = new Intent(MainActivity.this, Add_Task.class);
//                goToAddTask.putExtra("passedValue", "hello");
                startActivity(goToAddTask);
            }
        });




        Button allTask = findViewById(R.id.allTask);
        allTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToallTask = new Intent(MainActivity.this, all_Task.class);
                startActivity(goToallTask);
            }
        });
//        Button detail1 = findViewById(R.id.detail1);
//        detail1.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick (View v){
////                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
//                Intent goToDetail1 = new Intent(MainActivity.this, Detail.class);
//                goToDetail1.putExtra("detail", "detail1");
//                startActivity(goToDetail1);
//            }
//        });

//        Button detail2 = findViewById(R.id.detail2);
//        detail2.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick (View v){
////                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
//                Intent goToDetail2 = new Intent(MainActivity.this, Detail.class);
//                goToDetail2.putExtra("detail", "detail2");
//                startActivity(goToDetail2);
//            }
//        });


//
//        Button detail3 = findViewById(R.id.detail3);
//        detail3.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick (View v){
////                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
//                Intent goToDetail3 = new Intent(MainActivity.this, Detail.class);
//                goToDetail3.putExtra("detail", "detail3");
//                startActivity(goToDetail3);
//            }
//        });



        Button setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
//                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
                Intent goToSetting = new Intent(MainActivity.this, Settings.class);
//                goToDetail3.putExtra("detail", "detail3");
                startActivity(goToSetting);
            }
        });



//        List<Task> taskList = new ArrayList<Task>();
//        taskList.add(new Task("task1","do math home work","new"));
//        taskList.add(new Task("task2","cook lunch","assigned"));
//        taskList.add(new Task("task3","visit my uncle","in progress"));
//        taskList.add(new Task("task4","fix the car ","complete"));
//        DataBase.getDataBaseObj(this);


//---------------------lab32
        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
//        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        Amplify.Auth.signInWithWebUI(
                this,
                result -> Log.i("AuthQuickStart", result.toString()),
                error -> Log.e("AuthQuickStart", error.toString())
        );
        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );


//        Team team1 = Team.builder().name("Team1").build();
//        Team team2 = Team.builder().name("Team2").build();
//        Team team3 = Team.builder().name("Team3").build();
//
//        Amplify.API.mutate(ModelMutation.create(team1),
//                success -> Log.i("Tutorial", "Deleted item API: " + success.getData()),
//                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
//        );
//        Amplify.API.mutate(ModelMutation.create(team2),
//                success -> Log.i("Tutorial", "Deleted item API: " + success.getData()),
//                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
//        );
//        Amplify.API.mutate(ModelMutation.create(team3),
//                success -> Log.i("Tutorial", "Deleted item API: " + success.getData()),
//                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
//        );
//        Button logout = findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick (View v){
////                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
////                Intent goToSetting = new Intent(MainActivity.this, Settings.class);
////                goToDetail3.putExtra("detail", "detail3");
////                startActivity(goToSetting);
//                Amplify.Auth.signOut(
//                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
//                        error -> Log.e("AuthQuickstart", error.toString())
//                );
//            }
//        });
//        Button signOutbutt= findViewById(R.id.logout);
//        signOutbutt.setOnClickListener(v -> signout());
//        Button  Logout = (Button) findViewById(R.id.logout);
//        Intent in = getIntent();
//        String string = in.getStringExtra("message");
//        Logout.setOnClickListener(v -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//            builder.setTitle("Confirmation PopUp!").
//                    setMessage("You sure, that you want to logout?");
//            builder.setPositiveButton("Yes",
//                    (dialog, id) -> {
//                        Amplify.Auth.signOut(
//                                () -> Log.i("AuthQuickstart", "Signed out successfully"),
//                                error -> Log.e("AuthQuickstart", error.toString())
//                        );
//                        Intent i = new Intent(getApplicationContext(),
//                                com.amplifyframework.auth.cognito.activities.HostedUIRedirectActivity.class);
//                        startActivity(i);
//
//                    });
//            builder.setNegativeButton("No",
//                    (dialog, id) -> dialog.cancel());
//            AlertDialog alert11 = builder.create();
//            alert11.show();
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String userName = sharedPreferences.getString("getUserName", "the user didn't add a name yet!");
        String teamName = sharedPreferences.getString("teamName", "the user didn't add a team yet!");

//        Toast.makeText(this, userName,Toast.LENGTH_LONG).show();
        TextView userNameHolder = findViewById(R.id.userNameLable);
        userNameHolder.setText(userName);
        TextView userNameHolder2 = findViewById(R.id.TeamNameId);
        userNameHolder2.setText(teamName);

//        List<Task> tasks ;
//        DataBase db  = DataBase.getDataBaseObj(this.getApplicationContext());
//        Daorep taskDao = db.taskDao();
//        tasks = taskDao.getAll();


        //lab32
        handler = new Handler(Looper.getMainLooper(),
                new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        recyclerView.getAdapter().notifyDataSetChanged();
                        return false;
                    }
                });


//        if (isNetworkAvailable(getApplicationContext()) && tasksFirst.isEmpty()){
        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    System.out.println(teamName);

                    for (com.amplifyframework.datastore.generated.model.Task task : response.getData()) {
//                        System.out.println(task.getTeam().getName());
                        if(task.getTeam().getName().contains(teamName)){

                        Log.i("MyAmplifyApp", task.getTeam().getName());
                        tasksFirst.add(task);}
                    }
//
//
                    handler.sendEmptyMessage(1);

                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
//    }  else {
//
//            recyclerView = findViewById(R.id.tasksRecyclerView);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(new taskAdapter(tasksFirst, this));

//        }





        setTaskAdapter();






    }

    public void setTaskAdapter(){
        recyclerView = findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new taskAdapter(tasksFirst, this));

        }
//    @Override
//    public void onNoteClick(int position) {
//        Intent intent = new Intent(this, Detail.class);
////        intent.putExtra("selected_note", tasksList.);
//        startActivity(intent);
//    }

    @Override
    public void onNoteClick(int position, com.amplifyframework.datastore.generated.model.Task task) {
//        DataBase db  = DataBase.getDataBaseObj(this.getApplicationContext());
//        Daorep taskDao = db.taskDao();
        Intent intent = new Intent(this, Detail.class);


//        tasks.findTaskByUid(task.getId());
//        intent.putExtra("detail1", taskDao.findTaskByUid(task.getId()).getId());

        intent.putExtra("detail",  task.getTitle());
        intent.putExtra("detail_body", ( task.getBody()));
        intent.putExtra("detail_state", ( task.getState()));
        intent.putExtra("team_name", ( task.getTeam().getName()));
        startActivity(intent);
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    private void signout(){
        Amplify.Auth.signOut(
                () ->{
                    Intent ToLogIn = new Intent(MainActivity.this, com.amplifyframework.auth.cognito.activities.HostedUIRedirectActivity.class);
                    startActivity(ToLogIn);
                }
                ,
                error -> Log.e("signout", "error in logging out: "+ error)
        );
    }
}