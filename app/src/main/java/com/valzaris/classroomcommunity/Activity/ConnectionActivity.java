package com.valzaris.classroomcommunity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.valzaris.classroomcommunity.R;

import java.util.Map;


public class ConnectionActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener(){

            public void onClick(final View v){
                EditText id =(EditText) findViewById(R.id.editText2);
                EditText mdp =(EditText) findViewById(R.id.editText4);
                final String ids=id.getText().toString();
                final String mdps=mdp.getText().toString();

                Ion.with(getApplicationContext())
                        .load("https://jimtext.000webhostapp.com/" + "checkAttending.php?key=" + mdps + "&id=" + ids)
                        .as(new TypeToken<Map<String,String>>(){})
                        .setCallback(new FutureCallback<Map<String, String>>() {
                            @Override
                            public void onCompleted(Exception e, Map<String, String> result) {
                                if(result.get("info")!=null){
                                    Log.w("test","check");
                                    Intent intent = new Intent(ConnectionActivity.this,HomeActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("id", ids);
                                    b.putString("mdp",mdps);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                }
                                else {
                                    Log.i("tag", "invalide ");
                                    Toast t = Toast.makeText(v.getContext(), "invalide", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            }
                        });
            }
        });

    }
}
