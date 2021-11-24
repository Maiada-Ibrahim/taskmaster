package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.List;

public class Add_Task extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);



        DataBase db  = DataBase.getDataBaseObj(this.getApplicationContext());
        Daorep taskDao = db.taskDao();
        Button addTaskP2=findViewById(R.id.addTaskP2);
        addTaskP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputTitle=findViewById(R.id.titlAdd);
                EditText inputBody=findViewById(R.id.bodyAdd);
                EditText inputState=findViewById(R.id.stateAdd);

                String getinputTitle = inputTitle.getText().toString();
                String getinputBody = inputBody.getText().toString();
                String getinputState = inputState.getText().toString();

//                taskDao.insertAll(new Task(getinputTitle,getinputBody,getinputState));
                Task task = Task.builder()
                        .title(getinputTitle)
                        .body(getinputBody)
                        .state(getinputState)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(task),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()
                        ),

                error -> Log.e("MyAmplifyApp", "Create failed", error)
                );
                Toast.makeText(getApplicationContext(), "submitted!", Toast.LENGTH_LONG).show();
                finish();
            }
        });



    }
}