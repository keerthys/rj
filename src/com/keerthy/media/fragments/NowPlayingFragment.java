package com.keerthy.media.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keerthy.media.activities.BaseActivity;
import com.keerthy.media.controller.MusicController;
import com.keerthy.music.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class NowPlayingFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BaseActivity baseActivity = (BaseActivity) getActivity();
        baseActivity.addController(MusicController.getInstance());
        
        SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) getActivity().findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setPanelSlideListener(MusicController.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        MusicController.getInstance().addMusicControllerWidget(view, getActivity());
        return view;
    }
}
