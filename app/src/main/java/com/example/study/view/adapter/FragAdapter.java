package com.example.study.view.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.study.R;
import com.example.study.control.fragments.Fragment_Learn.Fragment_Learn_Child;
import com.example.study.control.fragments.Fragment_Phonics.FragmentPhonetic_Info;
import com.example.study.model.bean.LearnInfo;
import com.example.study.model.bean.PhoneticIndexBean;
import com.example.study.model.engin.PhoneticIndexEngin;
import com.kk.securityhttp.domain.ResultInfo;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import rx.Observer;

import static com.example.study.model.Cache.Cache_Json.dir;
import static com.example.study.model.Cache.Cache_Json.readTextFile;
import static com.example.study.model.Cache.Cache_Json.saveToSDCard;

public class FragAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    public FragAdapter(FragmentManager fm,int behavior ,List<Fragment> fragmentList){
        super(fm,behavior);
        this.fragmentList=fragmentList;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
