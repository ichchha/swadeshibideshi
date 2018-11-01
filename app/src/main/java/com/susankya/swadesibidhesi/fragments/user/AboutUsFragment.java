package com.susankya.swadesibidhesi.fragments.user;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.susankya.swadesibidhesi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {
    @BindView(R.id.viewMap)
    Button viewMap;
    @BindView(R.id.aboutUs)
    WebView aboutUs;
    private String mapLink = "https://www.google.com/maps?daddr=Oriens+Nepal,Damak+57217";

    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;

    }

    private void initData() {
        getActivity().setTitle("About Us");
        String youtContentStr = String.valueOf(Html
                .fromHtml("<![CDATA[<body style=\"font-size:16px;text-align:justify;color:#222222; \">"
                        + getResources().getString(R.string.about_us)
                        + "</body>]]>"));

        aboutUs.loadData(youtContentStr, "text/html", "utf-8");
        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(mapLink));
                startActivity(intent);
            }
        });
    }

}
