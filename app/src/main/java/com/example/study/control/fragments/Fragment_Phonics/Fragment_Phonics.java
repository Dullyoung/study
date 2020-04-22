package com.example.study.control.fragments.Fragment_Phonics;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.study.R;
import com.example.study.model.bean.PhoneticIndexBean;
import com.example.study.model.engin.PhoneticIndexEngin;
import com.example.study.view.adapter.FragAdapter;
import com.example.study.view.adapter.PhonticAdapter;
import com.kk.securityhttp.domain.ResultInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;

import static com.example.study.model.Cache.Cache_Json.dir;
import static com.example.study.model.Cache.Cache_Json.readTextFile;
import static com.example.study.model.Cache.Cache_Json.saveToSDCard;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Phonics extends Fragment implements ViewPager.OnPageChangeListener {
    ImageView btn1;
    View view;
    RecyclerView spell, symbol;
    TextView spell_desp, symbol_desp;
    PhonticAdapter spell_adapter, symbol_adapter;
    String TAG = "aaaa";
    List<String> spell_id;
    List<String> symbol_id;
    LinearLayout show;
    ViewPager spell_vp, symbol_vp;
    List<Fragment> symbolList, spellList;
    SwipeRefreshLayout refreshLayout;
    int[] Images  = {
        R.mipmap.category1, R.mipmap.category2, R.mipmap.category3,
                R.mipmap.category4, R.mipmap.category5, R.mipmap.category6,
                R.mipmap.category7, R.mipmap.category8, R.mipmap.category9,
                R.mipmap.category10, R.mipmap.category11, R.mipmap.category12
    };;
    public Fragment_Phonics() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_phonics, null);
        EventBus.getDefault().register(this);
        show = view.findViewById(R.id.show_class_list);
        spell_vp = view.findViewById(R.id.spell_info_vp);
        symbol_vp = view.findViewById(R.id.symbol_info_vp);
        spell_vp.addOnPageChangeListener(this);
        symbol_vp.addOnPageChangeListener(this);
        refreshLayout=view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(() -> getData());
        getData();
        return view;
    }

    private void getData() {
        spell = view.findViewById(R.id.spell_rv);
        symbol = view.findViewById(R.id.symbol_rv);
        spell_desp = view.findViewById(R.id.spell_text_desp);
        symbol_desp = view.findViewById(R.id.symbol_text_desp);

        spell_id = new ArrayList<>();
        symbol_id = new ArrayList<>();
        symbol_adapter = new PhonticAdapter();
        spell_adapter = new PhonticAdapter();
        spellList = new ArrayList<>();
        symbolList = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 6, RecyclerView.VERTICAL, false);
        spell.setLayoutManager(layoutManager);
        symbol.setLayoutManager(new GridLayoutManager(getContext(), 6, RecyclerView.VERTICAL, false));
        if (new File(dir, "PhoneticIndex").exists()) {
            String ResultLocal = readTextFile("PhoneticIndex");//本地获取到的Json数据
            Type type = new TypeReference<ResultInfo<PhoneticIndexBean>>() {
            }.getType();
            ResultInfo<PhoneticIndexBean> json = JSONObject.parseObject(ResultLocal, type);
            spell_desp.setText(getContext().getString(R.string.space) + json.getData().getSpelling().getDesp());
            symbol_desp.setText(getContext().getString(R.string.space) + json.getData().getSymbol().getDesp());
            for (PhoneticIndexBean.Symbol.Detail detail : json.getData().getSymbol().getDetailList()) {
                symbol_id.add(detail.getId());
                symbolList.add(new FragmentPhonetic_Info(detail.getId()));
            }
            for (PhoneticIndexBean.Spelling.Detail detail : json.getData().getSpelling().getDetailList()) {
                spell_id.add(detail.getId());
                spellList.add(new FragmentPhonetic_Info(detail.getId()));
            }
            refreshLayout.setRefreshing(false);

            if (spell_id.size()>0&&symbol_id.size()>0){
                symbol_adapter.setConfig(getContext(), Images, symbol_id);
                spell_adapter.setConfig(getContext(), Images, spell_id);
                spell_adapter.setOnImgClickListener((position, id) -> {
                    FragAdapter adapter = new FragAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, spellList);
                    spell_vp.setAdapter(adapter);
                    spell_vp.setCurrentItem(position);
                    spell_vp.setOffscreenPageLimit(1);
                    spell_vp.setVisibility(View.VISIBLE);
                    show.setVisibility(View.GONE);
                });
                symbol_adapter.setOnImgClickListener((position, id) -> {
                    FragAdapter adapter1 = new FragAdapter(getChildFragmentManager(),
                            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,symbolList);
                    symbol_vp.setAdapter(adapter1);
                    symbol_vp.setCurrentItem(position);
                    symbol_vp.setOffscreenPageLimit(1);
                    symbol_vp.setVisibility(View.VISIBLE);
                });

            }
            spell.setAdapter(spell_adapter);
            symbol.setAdapter(symbol_adapter);
        } else {
            getNetData();
        }


    }

    private void getNetData() {
        PhoneticIndexEngin engin = new PhoneticIndexEngin(getContext());
        engin.getPhonticIndex(getContext()).subscribe(new Observer<ResultInfo<PhoneticIndexBean>>() {
            @Override
            public void onCompleted() {
                refreshLayout.setRefreshing(false);

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(), "数据获取失败", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);

            }

            @Override
            public void onNext(ResultInfo<PhoneticIndexBean> phoneticIndexBeanResultInfo) {
                if (!new File(dir, "PhoneticIndex").exists()) { //如果文件不存在 就缓存
                    phoneticIndexBeanResultInfo.setResponse(null);
                    saveToSDCard(getActivity(), "PhoneticIndex", JSON.toJSONString(phoneticIndexBeanResultInfo));
                }
                spell_desp.setText(getContext().getString(R.string.space) + phoneticIndexBeanResultInfo.getData().getSpelling().getDesp());
                symbol_desp.setText(getContext().getString(R.string.space) + phoneticIndexBeanResultInfo.getData().getSymbol().getDesp());
                for (PhoneticIndexBean.Symbol.Detail detail : phoneticIndexBeanResultInfo.getData().getSymbol().getDetailList()) {
                    symbol_id.add(detail.getId());
                    symbolList.add(new FragmentPhonetic_Info(detail.getId()));
                }
                for (PhoneticIndexBean.Spelling.Detail detail : phoneticIndexBeanResultInfo.getData().getSpelling().getDetailList()) {
                    spell_id.add(detail.getId());
                    spellList.add(new FragmentPhonetic_Info(detail.getId()));
                }
                if (spell_id.size()>0&&symbol_id.size()>0){
                    symbol_adapter.setConfig(getContext(), Images, symbol_id);
                    spell_adapter.setConfig(getContext(), Images, spell_id);
                    spell_adapter.setOnImgClickListener((position, id) -> {
                        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, spellList);
                        spell_vp.setAdapter(adapter);
                        spell_vp.setCurrentItem(position);
                        spell_vp.setOffscreenPageLimit(1);
                        spell_vp.setVisibility(View.VISIBLE);
                        show.setVisibility(View.GONE);
                    });
                    symbol_adapter.setOnImgClickListener((position, id) -> {
                        FragAdapter adapter1 = new FragAdapter(getChildFragmentManager(),
                                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,symbolList);
                        symbol_vp.setAdapter(adapter1);
                        symbol_vp.setCurrentItem(position);
                        symbol_vp.setOffscreenPageLimit(1);
                        symbol_vp.setVisibility(View.VISIBLE);
                    });

                }
                spell.setAdapter(spell_adapter);
                symbol.setAdapter(symbol_adapter);
                refreshLayout.setRefreshing(false);

            }

        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnReceiveMessage(String s) {
        if (s.equals("back")) {
            symbol_vp.setVisibility(View.GONE);
            spell_vp.setVisibility(View.GONE);
            show.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }
    private void unbindDrawables(View view)
    {
        if (view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
