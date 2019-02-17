package com.valzaris.classroomcommunity.Activity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.valzaris.classroomcommunity.R;
import com.valzaris.classroomcommunity.class_source.Answer;
import com.valzaris.classroomcommunity.class_source.Friend;
import com.valzaris.classroomcommunity.class_source.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by valzaris on 11/01/19.
 */

public class QuizActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int mProgressStatus;
    private TextView textScoreJoueur;
    private int myscore;
    List<String> listeAnswer;
    private String name;
    private String adv;
    private String id;
    private String gr;
    private int nbPartie;
    private static Question question;
    private TextView loadingText;
    private Handler handler = new Handler();
    private static Boolean nonRepondu;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        nonRepondu=true;
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        name= bundle.getString("myname");
        adv = bundle.getString("nameAdv");
        id = bundle.getString("id");
        gr= bundle.getString("mdp");
        nbPartie=bundle.getInt("nbPartie")+1;
        myscore = bundle.getInt("score");
        initPlayer();
        getQuestion();
    }

    public void initPlayer(){
        TextView myNameText = (TextView) findViewById(R.id.myName);
        myNameText.setText(name);
        TextView advNameText = (TextView) findViewById(R.id.advName);
        advNameText.setText(adv);
        textScoreJoueur = (TextView) findViewById(R.id.MyScore);
        textScoreJoueur.setText(myscore+"");
    }

    public void initTimerBar(){
        mProgressStatus=question.getDuration();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(mProgressStatus);
        progressBar.setProgress(mProgressStatus);

        loadingText = (TextView) findViewById(R.id.timer);
        loadingText.setText(mProgressStatus+"");
        Log.w("progress",mProgressStatus+"");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus > 0){
                    mProgressStatus--;
                    android.os.SystemClock.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(mProgressStatus);
                            loadingText.setText(mProgressStatus+"");
                        }
                    });
                }
                enableButtonNext();
            }
        }).start();
    }

    public void setQuestion(){
        TextView questionText = (TextView) findViewById(R.id.question);
        questionText.setText(question.getText());
    }

    public void getQuestion(){
        Ion.with(getApplicationContext())
                .load("https://jimtext.000webhostapp.com/getQuestions.php?key="+gr)
                .as(new TypeToken<List<Question>>(){})
                .setCallback(new FutureCallback<List<Question>>() {
                    @Override
                    public void onCompleted(Exception e, List<Question> result) {
                        Log.w("testMap", result.toString());
                        int maxRandval = result.size();
                        Random r = new Random();
                        int questionIndice = r.nextInt(maxRandval);
                        question = result.get(questionIndice);
                        setQuestion();
                        gestionBouton();
                        initTimerBar();
                    }
                });
    }


    public void gestionBouton(){
        ListView listViewAnswer = (ListView) findViewById(R.id.ListAnswerDynamic);
        int buttonsize = question.answers.size();
        listeAnswer =new ArrayList<String>();
        for (int i=0; i<buttonsize;i++){
            listeAnswer.add(question.answers.get(i).getText());
        }
        ArrayAdapter myAd = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listeAnswer){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View  row = super.getView(position,convertView,parent);
                Answer answer = QuizActivity.question.answers.get(position);
                return row;
            }
        };
        listViewAnswer.setAdapter(myAd);
        listViewAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(question.answers.get(position).getIs_right()==1 && nonRepondu){
                    myscore += mProgressStatus+3;
                    view.setBackgroundColor(Color.GREEN);
                    textScoreJoueur.setText(""+myscore);
                }
                else if(nonRepondu){
                    view.setBackgroundColor(Color.RED);
                }
                nonRepondu=false;
                enableButtonNext();
            }
        });
    }
    public void moveView(){
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("mdp",gr);
        if (nbPartie<4){
            Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
            bundle.putInt("nbPartie",nbPartie);
            bundle.putInt("score",myscore);
            intent.putExtras(bundle);
            startActivity(intent);
        }else {
            Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void enableButtonNext(){
        mProgressStatus=0;
        nonRepondu=false;
        Button b = (Button) findViewById(R.id.suivant);
        b.setEnabled(true);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveView();
            }
        });
    }
}
