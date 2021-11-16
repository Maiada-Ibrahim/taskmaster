package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements taskAdapter.OnNoteListener {
    private RecyclerView mRecyclerView;
    // vars
    private ArrayList<Task> tasksList = new ArrayList<>();
    private taskAdapter taskAdapterObj;
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

        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task("task1","do math home work","new"));
        taskList.add(new Task("task2","cook lunch","assigned"));
        taskList.add(new Task("task3","visit my uncle","in progress"));
        taskList.add(new Task("task4","fix the car ","complete"));


        RecyclerView recyclerView = findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new taskAdapter(taskList,this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String userName = sharedPreferences.getString("getUserName","the user didn't add a name yet!");
//        Toast.makeText(this, userName,Toast.LENGTH_LONG).show();
        TextView userNameHolder = findViewById(R.id.userNameLable);
        userNameHolder.setText(userName);
    }


//    @Override
//    public void onNoteClick(int position) {
//        Intent intent = new Intent(this, Detail.class);
////        intent.putExtra("selected_note", tasksList.);
//        startActivity(intent);
//    }

    @Override
    public void onNoteClick(int position, Task task) {
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("detail",task.getTitle());
        startActivity(intent);
    }
}