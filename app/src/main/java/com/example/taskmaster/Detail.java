package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DataBase db  = DataBase.getDataBaseObj(this.getApplicationContext());
        Daorep taskDao = db.taskDao();





//        int ido =Integer.parseInt(TextTitle);
//        Task task=  taskDao.gettaskobj(ido);
        String TextTitle= getIntent().getStringExtra("detail");
        TextView titleholder = findViewById(R.id.detailTitle);
        titleholder.setText(TextTitle);


        String Textdes= getIntent().getStringExtra("detail_body");
        TextView titleholder2 = findViewById(R.id.desId);
        titleholder2.setText(Textdes);

        String TextState= getIntent().getStringExtra("detail_state");
        TextView titleholder3 = findViewById(R.id.statLable);
        titleholder3.setText(TextState);
    }
}