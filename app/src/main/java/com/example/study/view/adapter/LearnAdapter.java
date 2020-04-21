package com.example.study.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.study.control.fragments.Fragment_Learn.Fragment_Learn_Child;
import com.example.study.model.bean.LearnInfoWrapper;
import com.kk.securityhttp.domain.ResultInfo;

import java.util.List;

public class LearnAdapter extends FragmentStatePagerAdapter {
    private List<Fragment_Learn_Child> fragmentLearnChildList;

    public LearnAdapter(@NonNull FragmentManager fm, int behavior,List<Fragment_Learn_Child> fragmentLearnChildList ) {
        super(fm, behavior);
        this.fragmentLearnChildList=fragmentLearnChildList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentLearnChildList.get(position);
    }

    @Override
    public int getCount() {

     return    fragmentLearnChildList.size();

    }

}
