package com.keerthy.media.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keerthy.media.activities.BaseActivity;
import com.keerthy.media.controller.MusicController;
import com.keerthy.music.R;

public class NowPlayingFragment extends Fragment {
    private final MusicController musicController = new MusicController();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BaseActivity baseActivity = (BaseActivity) getActivity();
        baseActivity.addController(musicController);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        musicController.addMusicControllerWidget(view, getActivity());
        return view;
    }
}
