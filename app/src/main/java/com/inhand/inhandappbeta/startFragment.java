package com.inhand.inhandappbeta;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;

public class startFragment extends Fragment {
    private static TextView topTextView;
    private static TextView bottomTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Comment
        View view = inflater.inflate(R.layout.activity_main, container, false);

        return view;
    }


}