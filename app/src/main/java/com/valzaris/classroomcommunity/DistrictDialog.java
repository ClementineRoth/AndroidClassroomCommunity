package com.valzaris.classroomcommunity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by valzaris on 04/02/19.
 */

public class DistrictDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.district_dialog, null);

        TextView num=(TextView) layout.findViewById(R.id.nomD);
        num.setText("aaa");

        builder.setView(layout);
        return  builder.create();
    }
}
