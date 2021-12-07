package com.example.taskmaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Detail extends AppCompatActivity {
    private String fileName;
    private URL url;
//    @SuppressLint("SetTextI18n")

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

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
        String TextteamNameId= getIntent().getStringExtra("team_name");
        TextView titleholder4 = findViewById(R.id.team);
        titleholder4.setText(TextteamNameId);
//        titleholder3.setText(task.getState());
        TextView locationText = findViewById(R.id.locationText);
        locationText.setText("Lat: "+intent.getExtras().getString("taskLat")+", Long: "+intent.getExtras().getString("taskLong"));


        fileName = intent.getExtras().getString("taskFileName");

        Amplify.Storage.getUrl(
                fileName,
                result -> {
                    Log.i("MyAmplifyApp", "Successfully generated: " + result.getUrl());
                    url= result.getUrl();

                },
                error -> Log.e("MyAmplifyApp", "URL generation failure", error)
        );

        ImageView imageView = findViewById(R.id.imageView);
        Amplify.Storage.downloadFile(
                fileName,
                new File(getApplicationContext().getFilesDir() +"/"+ fileName),

                result -> {
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getPath());
                    String fileType = null;

                    try {
                        fileType = Files.probeContentType(result.getFile().toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (fileType.split("/")[0].equals("image")){
                        imageView.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));
                    }
                    else {
                        String linkedText = String.format("<a href=\"%s\">download File</a> ", url);

                        TextView test = findViewById(R.id.taskLink);
                        test.setText(Html.fromHtml(linkedText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        test.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                },
                error -> Log.e("MyAmplifyApp",  "Download Failure ",error)
        );

    }
}