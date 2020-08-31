package com.sticollegeiloilo.aqualify;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.Objects;

public class FourthFragment extends Fragment {

    TextView back;
    TextView done;
    ViewPager viewPager;

        public FourthFragment() {
            // Required empty public constructor
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_fourth, container, false);
            viewPager = Objects.requireNonNull(getActivity()).findViewById(R.id.viewPager);

            back = view.findViewById(R.id.slideFourBack);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(2);
                }
            });
            done = view.findViewById(R.id.slideFourDone);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Objects.requireNonNull(getActivity()).finish();
                }
            });
            return view;
        }
    }