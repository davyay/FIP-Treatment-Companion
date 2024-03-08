package com.example.fiptreatmentcompanion.ui.communitylinks;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fiptreatmentcompanion.R;

public class CommunityLinksFragment extends Fragment {

    private CommunityLinksViewModel mViewModel;

    public static CommunityLinksFragment newInstance() {
        return new CommunityLinksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community_links, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CommunityLinksViewModel.class);
        // TODO: Use the ViewModel
    }

}