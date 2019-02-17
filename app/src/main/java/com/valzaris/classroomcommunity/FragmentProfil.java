package com.valzaris.classroomcommunity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by valzaris on 17/02/19.
 */

public class FragmentProfil  extends Fragment {
    public FragmentProfil(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Profil");
        View view = inflater.inflate(R.layout.profil_fragment,container, false);
        return view;
    }
}
