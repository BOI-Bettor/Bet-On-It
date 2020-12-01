package com.example.betonit_bettor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CWFragment extends Fragment {

    private EditText etCAWusername, etCAWamount, etCAWtype, etCAWstartdate, etCAWstarttime, etCAWrescdate, etCAWresctime;
    private Button pick_date_button, pick_time_button, pick_rescdate_button, pick_resctime_button, CAW_confirm_button;
    private Toolbar CAWtoolbar;
    private TextView tvCreateWager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_createwager, container, false);
    }
}
