package com.valzaris.classroomcommunity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.valzaris.classroomcommunity.Activity.DistrictActivity;
import com.valzaris.classroomcommunity.class_source.District;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valzaris on 15/01/19.
 */

public class DistrictAdapter extends BaseAdapter{

    private DistrictActivity activity;
    private List<District> items;
    int resourceId;
    private Context context;

    private Integer[] mThumbIds = {
            R.drawable.img_district1, R.drawable.img_district2,
            R.drawable.img_district3, R.drawable.img_district4,
            R.drawable.img_district5, R.drawable.img_district6,
            R.drawable.img_district7, R.drawable.img_district8,
            R.drawable.img_district9, R.drawable.img_district10,
            R.drawable.img_district11, R.drawable.img_district12,
            R.drawable.img_district13, R.drawable.img_district14,
            R.drawable.img_district15, R.drawable.img_district16,
            R.drawable.img_district17, R.drawable.img_district18,
            R.drawable.img_district19, R.drawable.img_district20
    };

    public DistrictAdapter(Context C, String [] name, int resourceId){
        super();
        this.resourceId=resourceId;
        this.context = C;
        this.items = new ArrayList<>();
        for(int i=0 ; i < name.length ; i++){
            this.items.add(new District(mThumbIds[i],name[i],i+1));
        }
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {return null;}

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public District getDistricts(int position){
        return this.items.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View layout=convertView;
        //ImageView imageView;
        //TextView textView;
        if (convertView==null){
            //imageView = new ImageView(context);
            //textView = new TextView(context);
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
            //LayoutInflater inflater = (LayoutInflater) layout.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.item_district, parent, false);

        }
        TextView num=(TextView) layout.findViewById(R.id.num);
        TextView name=(TextView) layout.findViewById(R.id.name);
        ImageView dist=(ImageView) layout.findViewById(R.id.dist);
        dist.setImageResource(this.items.get(position).getDistrictId());
        //dist.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
        name.setText(this.items.get(position).getName());
        num.setText(this.items.get(position).getDistrictnb()+"");
        //else {
            //imageView = (ImageView) convertView;
            //textView = new TextView(context);
        //}
        //imageView.setImageResource(this.items.get(position).getDistrictId());
        //textView.setText(this.items.get(position).getName());

        return layout;
    }
}
