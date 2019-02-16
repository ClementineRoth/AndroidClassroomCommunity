package com.valzaris.classroomcommunity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by valzaris on 11/02/19.
 */

public class FragmentQRCode extends Fragment implements ZBarScannerView.ResultHandler {
    static ZBarScannerView scannerView;
    public FragmentQRCode(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("QR code");
        View view = inflater.inflate(R.layout.qr_code_fragment,container, false);
        scannerView=new ZBarScannerView(getActivity());
        List<BarcodeFormat> format = new ArrayList<>();
        format.add(BarcodeFormat.QRCODE);
        scannerView.setFormats(format);
        //FrameLayout contentFrame = (FrameLayout) view.findViewById(R.id.contentFL);
        //contentFrame.addView(scannerView);
        return view;
    }
    public void onResume(){
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
    public void onPause(){
        super.onPause();
        scannerView.stopCamera();
    }
    @Override
    public void handleResult(Result result) {
        String qrcode = result.getContents();
        Log.d("attendingFragment", "QRcode" +qrcode);
    }
}
