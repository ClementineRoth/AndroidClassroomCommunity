package com.valzaris.classroomcommunity.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.valzaris.classroomcommunity.FragmentFriends;
import com.valzaris.classroomcommunity.FragmentQRCode;
import com.valzaris.classroomcommunity.class_source.Friend;
import com.valzaris.classroomcommunity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valzaris on 08/02/19.
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    static List<Friend> lf;
    static List<String> listNameFriend;
    public static int selectedIndexFriend = -1;
    public static String myName;
    public static View selectedViewFriend = null;
    public Friend me;
    public String gr;
    public String myid;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView nav =(NavigationView) findViewById(R.id.nav_view);

        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        myid = bundle.getString("id");
        gr = bundle.getString("mdp");

        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        nav.setNavigationItemSelectedListener(this);
        nav.getMenu().performIdentifierAction(R.id.nav_friends,0);



    }
    public void setListFriend(){
        Ion.with(this).load("https://jimtext.000webhostapp.com/" + "getFriends.php?key="+gr)
                .as(new TypeToken<List<Friend>>(){})
                .setCallback(new FutureCallback<List<Friend>>() {
                    @Override
                    public void onCompleted(Exception e, List<Friend> result) {
                        ListView listViewFriend = (ListView) findViewById(R.id.ListDynamic);
                        lf = result;

                        listNameFriend = new ArrayList<String>();
                        for(Friend f : result) {
                            String t=f.id+"";
                            if(t.equals(myid)){myName=f.getFirst_name()+f.getLast_name();me=f;}
                            listNameFriend.add(f.getFirst_name() + " " + f.getLast_name());
                            Log.w("resB", f.getFirst_name() + " " + f.getLast_name());
                        }
                        ArrayAdapter myAd = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listNameFriend){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View  row = super.getView(position,convertView,parent);
                                Friend f = HomeActivity.lf.get(position);
                                HomeActivity.changeConnected(f,row,null);
                                return row;
                            }
                        };
                        listViewFriend.setAdapter(myAd);
                        listViewFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                if(selectedIndexFriend != -1){
                                    changeConnected(lf.get(selectedIndexFriend),selectedViewFriend,findViewById(R.id.PlayButton));
                                }
                                if(selectedIndexFriend == i){
                                    changeConnected(lf.get(i),view,findViewById(R.id.PlayButton));
                                    selectedIndexFriend = -1;
                                    selectedViewFriend = null;
                                    findViewById(R.id.PlayButton).setEnabled(false);
                                    findViewById(R.id.district_test).setEnabled(false);
                                }else {
                                    selectedIndexFriend = i;
                                    selectedViewFriend = view;

                                    if(lf.get(i).getIs_present() == 1) {
                                        findViewById(R.id.PlayButton).setEnabled(true);
                                        findViewById(R.id.district_test).setEnabled(true);
                                    }else{
                                        findViewById(R.id.PlayButton).setEnabled(false);
                                        findViewById(R.id.district_test).setEnabled(false);
                                    }
                                    view.setBackgroundResource(R.color.grayColor);
                                    Toast.makeText(HomeActivity.this, "clicked item:" + i + " " + lf.get(i).getFirst_name(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Button quiz =(Button) findViewById(R.id.PlayButton);
                        quiz.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(HomeActivity.this,QuizActivity.class);
                                Bundle b = new Bundle();
                                b.putString("myname", myName);
                                b.putString("nameAdv", lf.get(selectedIndexFriend).getFirst_name()+" "+lf.get(selectedIndexFriend).getLast_name());
                                b.putString("id",myid);
                                b.putString("mdp",gr);
                                b.putInt("nbPartie",0);
                                b.putInt("score",0);
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        });


                        Button temp =(Button) findViewById(R.id.district_test);
                        temp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(HomeActivity.this,DistrictActivity.class);
                                startActivity(intent);
                            }
                        });
                    }

                });


    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_friends:
                getFragmentManager().beginTransaction().replace(R.id.contentFL, new FragmentFriends()).commit();
                setListFriend();
                break;
            case R.id.nav_presence:
                getFragmentManager().beginTransaction().replace(R.id.contentFL, new FragmentQRCode()).commit();
                break;
            case R.id.nav_deconnection:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("confirmation");
                builder.setMessage("Voulez vous vraiment partir?")
                        .setCancelable(false)
                        .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))return true;
        else return false;
    }

    public static void changeConnected (Friend f, View row,View playButton){
        if(f.getIs_present() == 1){
            if(playButton != null) {
                playButton.setEnabled(true);
            }
            row.setBackgroundResource(R.color.greenColor);
        } else {
            if(playButton != null) {
                playButton.setEnabled(false);
            }
            row.setBackgroundResource(R.color.redColor);
        }
    }
}
