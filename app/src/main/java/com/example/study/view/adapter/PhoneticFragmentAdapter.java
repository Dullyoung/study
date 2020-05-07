package com.example.study.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.study.control.fragments.Fragment_Phonics.FragmentPhonetic_Info;

import java.util.List;

public class PhoneticFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> phoneticInfos;
    public PhoneticFragmentAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> phoneticInfos) {
        super(fm, behavior);
        this.phoneticInfos=phoneticInfos;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return phoneticInfos.get(position);
    }

    @Override
    public int getCount() {
        return phoneticInfos.size();
    }
}
