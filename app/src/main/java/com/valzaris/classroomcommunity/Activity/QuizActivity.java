package com.valzaris.classroomcommunity.Activity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.valzaris.classroomcommunity.R;
import com.valzaris.classroomcommunity.class_source.Question;

import java.util.Map;

/**
 * Created by valzaris on 11/01/19.
 */

public class QuizActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int mProgressStatus = 0;
    private int max;
    private TextView textScoreJoueur;
    private int myscore;
    private Button bouton4;
    private Button bouton3;
    private Button bouton2;
    private Button bouton1;
    private String name;
    private String adv;
    private String id;
    private String gr;
    private int nbPartie;
    private TextView loadingText;
    private Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        Intent intent = getIntent();
        final Bundle bundle=intent.getExtras();
        name= bundle.getString("myname");
        adv = bundle.getString("nameAdv");
        id = bundle.getString("id");
        gr= bundle.getString("mdp");
        nbPartie=bundle.getInt("nbPartie")+1;
        myscore = bundle.getInt("score");
        TextView myNameText = (TextView) findViewById(R.id.myName);
        myNameText.setText(name);
        TextView advNameText = (TextView) findViewById(R.id.advName);
        advNameText.setText(adv);
        textScoreJoueur = (TextView) findViewById(R.id.MyScore);
        textScoreJoueur.setText(myscore+"");
        //getQuestion();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        loadingText = (TextView) findViewById(R.id.timer);
        max = progressBar.getMax();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < max){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(mProgressStatus);
                            loadingText.setText(mProgressStatus+"");
                        }
                    });
                }
                desableButton(bouton1, bouton2, bouton3, bouton4);
                goback();
            }
        }).start();
        gestionBouton();
    }

    public Question getQuestion(){

        Ion.with(getApplicationContext())
                .load("https://jimtext.000webhostapp.com/getQuestions.php?key="+gr)
                .as(new TypeToken<Map<String,String>>(){})
                .setCallback(new FutureCallback<Map<String, String>>() {
                    @Override
                    public void onCompleted(Exception e, Map<String, String> result) {
                       int maxRandval = result.size()+1;
                       int questionIndice = (int)Math.random()*maxRandval;
                       Log.w("indice question",questionIndice+"");
                    }
                });
        return null;
    }


    public void gestionBouton(){
        bouton1 = (Button) findViewById(R.id.button);
        bouton2 = (Button) findViewById(R.id.button2);
        bouton3 = (Button) findViewById(R.id.button3);
        bouton4 = (Button) findViewById(R.id.button4);
        setListenerBouton(bouton1,false);
        setListenerBouton(bouton2,false);
        setListenerBouton(bouton3,true);
        setListenerBouton(bouton4,false);
    }
    public void setListenerBouton(final Button b, final Boolean valide){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valide){
                    b.setBackgroundColor(Color.parseColor("#00FF00"));
                    int total = 20-mProgressStatus+3;

                    textScoreJoueur.setText(""+total);
                }
                else{
                    b.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                desableButton(bouton1, bouton2, bouton3, bouton4);
                goback();
            }
        });
    }
    public void desableButton(Button b1, Button b2, Button b3, Button b4){
        b1.setClickable(false);
        b2.setClickable(false);
        b3.setClickable(false);
        b4.setClickable(false);
    }
    public void goback(){
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("mdp",gr);
        if (nbPartie<4){
            Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
            bundle.putInt("nbPartie",nbPartie);
            intent.putExtras(bundle);
            startActivity(intent);
        }else {
            Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}
