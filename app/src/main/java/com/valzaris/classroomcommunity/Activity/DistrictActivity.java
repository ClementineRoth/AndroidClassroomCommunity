package com.valzaris.classroomcommunity.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.valzaris.classroomcommunity.class_source.District;
import com.valzaris.classroomcommunity.DistrictAdapter;
import com.valzaris.classroomcommunity.R;

import java.util.ArrayList;

/**
 * Created by valzaris on 14/01/19.
 */

public class DistrictActivity extends FragmentActivity  {

    private GridView GridV;
    //private ArrayAdapter <String> StringAdapter;
    private DistrictAdapter adapter;
    final Context context = this;
    private ArrayList<District> items;
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.district_activity);
        items = new ArrayList<>();
        GridV= (GridView) findViewById(R.id.gridView);
        final String[] nameD = {"louvre", "Bourse", "temple", "Hotel-de-Ville", "panthéon", "Luxembourg", "palais-Bourbon","Elisée",
                "Opéra", "Enclos Saint-Laurent", "popincourt", "Reuilly", "gobelins", "Observatoire", "vaugirard", "Passy",
                "batignolles-Monceau", "Buttes-Montmartre", "Buttes-Chaumont", "Ménilmontant"};

        adapter = new DistrictAdapter(this, nameD,R.layout.item_district);
        GridV.setAdapter(adapter);


        /*StringAdapter=new ArrayAdapter<>(this,android.R.layout.simple_gallery_item, items);
        GridV.setAdapter(StringAdapter);*/
        GridV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(DistrictActivity.this,""+nameD[position], Toast.LENGTH_SHORT).show();
                District d = adapter.getDistricts(position);
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.district_dialog);
                dialog.setTitle("Title...");
                TextView num=(TextView) dialog.findViewById(R.id.nomD);
                num.setText("aaa");
                dialog.show();
               //DistrictDialog builder = new DistrictDialog();
                // builder.show(, "test");

            }
        });


    }
}
