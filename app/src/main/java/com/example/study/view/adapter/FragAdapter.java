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
import com.example.study.control.fragments.Fragment_Learn.Fragment_Learn;
import com.example.study.control.fragments.Fragment_Learn.Fragment_Learn_Child;
import com.example.study.control.fragments.Fragment_Phonics.FragmentPhonetic_Info;
import com.example.study.control.fragments.Fragment_Phonics.Fragment_Phonics;
import com.example.study.control.fragments.Fragment_Read_To_Me.Fragment_Read_To_Me;
import com.example.study.control.fragments.Fragment_index.Fragment_Main_Index;
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
    Fragment_Main_Index main_index;
    Fragment_Learn learn;
    Fragment_Read_To_Me readToMe;
    Fragment_Phonics phonics;

    public FragAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (main_index == null) {
                    main_index = new Fragment_Main_Index();
                }
                return main_index;

            case 1:
                if (learn == null) {
                    learn = new Fragment_Learn();
                }
                return learn;

            case 2:
                if (readToMe == null) {
                    readToMe = new Fragment_Read_To_Me();
                }
                return readToMe;

            case 3:
                if (phonics == null) {
                    phonics = new Fragment_Phonics();
                }
                return phonics;
            default:
                return main_index;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
