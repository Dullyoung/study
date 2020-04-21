package com.example.study.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.study.control.fragments.Fragment_Read_To_Me.Fragment_Read_Phonetic_Img;
import com.example.study.model.bean.LearnInfoWrapper;
import com.kk.securityhttp.domain.ResultInfo;

public class ReadToMeAdapter extends FragmentStatePagerAdapter {
private ResultInfo<LearnInfoWrapper> learnInfoWrapperResultInfo;
    public ReadToMeAdapter(FragmentManager fm,int behavior, ResultInfo<LearnInfoWrapper> learnInfoWrapperResultInfo) {
        super(fm,behavior);
        this.learnInfoWrapperResultInfo=learnInfoWrapperResultInfo;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new Fragment_Read_Phonetic_Img(learnInfoWrapperResultInfo.getData().getLearnInfoList().get(position));
    }

    @Override
    public int getCount() {
        return learnInfoWrapperResultInfo.getData().getLearnInfoList().size();
    }
}
