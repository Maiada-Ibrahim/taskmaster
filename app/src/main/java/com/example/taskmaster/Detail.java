package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        DataBase db  = DataBase.getDataBaseObj(this.getApplicationContext());
//        Daorep taskDao = db.taskDao();
//        Task task = taskDao.findTaskByUid(getIntent().getExtras().getInt("detail1"));
//        TextView titleholder5 = findViewById(R.id.detailTitle);
//        titleholder5.setText(task.getTitle());
        String TextTitle= getIntent().getStringExtra("detail");
        TextView titleholder = findViewById(R.id.detailTitle);
        titleholder.setText(TextTitle);
        String Textdes= getIntent().getStringExtra("detail_body");
        TextView titleholder2 = findViewById(R.id.desId);
        titleholder2.setText(Textdes);
//        titleholder2.setText(task.getBody());
        String TextState= getIntent().getStringExtra("detail_state");
        TextView titleholder3 = findViewById(R.id.statLable);
                titleholder3.setText(TextState);

//        titleholder3.setText(task.getState());
    }
}