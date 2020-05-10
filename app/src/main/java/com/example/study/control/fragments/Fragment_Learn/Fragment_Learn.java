package com.example.study.control.fragments.Fragment_Learn;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.study.control.fragments.Fragment_Read_To_Me.Fragment_Read_To_Me;
import com.example.study.model.bean.LearnInfo;
import com.example.study.model.bean.LearnInfoWrapper;

import com.example.study.model.bean.PageChanger;
import com.example.study.model.engin.LearnEngin;

import com.example.study.R;
import com.example.study.view.adapter.LearnAdapter;
import com.kk.securityhttp.domain.ResultInfo;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;

import static com.example.study.App.is_vip;

import static com.example.study.model.Cache.Cache_Json.dir;
import static com.example.study.model.Cache.Cache_Json.readTextFile;
import static com.example.study.model.Cache.Cache_Json.saveToSDCard;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Learn extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager learnviewpager;
    private LearnAdapter learn_fragAdapter;
    private int curindex = 0;
    private TextView page;
    private ImageView last, next;
    private JSONObject lesson;
    private LearnEngin learnEngin;
    private LearnInfo learnInfo;
    private int position;
    private int readpage;
    private List<Fragment_Learn_Child> fragmentList;
    private MediaPlayer learnmediaPlayer;
    private View rootView;
    SwipeRefreshLayout refreshLayout;
    private Fragment_Read_To_Me fragment_read_to_me;

    public Fragment_Learn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_learn, null);
        page = rootView.findViewById(R.id.learn_page);
        next = rootView.findViewById(R.id.learn_next);
        next.setOnClickListener(this);
        last = rootView.findViewById(R.id.learn_last);
        last.setOnClickListener(this);
        if (learnmediaPlayer == null) {
            learnmediaPlayer = new MediaPlayer();
            learnmediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        learnviewpager = rootView.findViewById(R.id.vp_learn);//把vp绑定到learn 里的viewpager
        EventBus.getDefault().register(this);
        refreshLayout = rootView.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(() -> {
            fragmentList = new ArrayList<>();
            getData();
        });

        fragmentList = new ArrayList<>();
        getData();
        return rootView;
    }


    private void getData() {

        if (new File(dir, "LearnJson").exists()) {
            String ResultLocal = readTextFile("LearnJson");//本地获取到的Json数据
            Type type = new TypeReference<ResultInfo<LearnInfoWrapper>>() {
            }.getType();
            ResultInfo<LearnInfoWrapper> json = com.alibaba.fastjson.JSONObject.parseObject(ResultLocal, type);
            for (LearnInfo l : json.getData().getLearnInfoList()
            ) {
                fragmentList.add(new Fragment_Learn_Child(l));
            }
            learn_fragAdapter = new LearnAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList);
            //给Viewpager设定适配器
            learnviewpager.addOnPageChangeListener(Fragment_Learn.this);
            learnviewpager.setAdapter(learn_fragAdapter);
            learnviewpager.setOffscreenPageLimit(1);
            learnviewpager.setCurrentItem(0);
            refreshLayout.setRefreshing(false);

        } else {
            LearnEngin engin = new LearnEngin(getContext());
            engin.getLearnInfo().subscribe(new Observer<ResultInfo<LearnInfoWrapper>>() {
                @Override
                public void onCompleted() {
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onNext(ResultInfo<LearnInfoWrapper> learnInfoWrapperResultInfo) {
                    learnInfoWrapperResultInfo.setResponse(null);
                    saveToSDCard(getActivity(), "LearnJson", JSON.toJSONString(learnInfoWrapperResultInfo));
                    for (LearnInfo l : learnInfoWrapperResultInfo.getData().getLearnInfoList()
                    ) {
                        fragmentList.add(new Fragment_Learn_Child(l));
                    }
                    learn_fragAdapter = new LearnAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList);
                    //给Viewpager设定适配器
                    learnviewpager.addOnPageChangeListener(Fragment_Learn.this);
                    learnviewpager.setAdapter(learn_fragAdapter);
                    learnviewpager.setOffscreenPageLimit(1);
                    learnviewpager.setCurrentItem(0);
                }
            });
        }
        setPage();
    }


    public void Last(int position) {
        curindex = position - 1;
        learnviewpager.setCurrentItem(curindex);
        setPage();
    }

    public void Next(int position) {
        curindex = position + 1;
        learnviewpager.setCurrentItem(curindex);
        setPage();
    }

    public void setPage() {
        if (learn_fragAdapter != null && learn_fragAdapter != null) {
            page.setText((learnviewpager.getCurrentItem() + 1) + "/" + learn_fragAdapter.getCount());
        }

    }


    @Override
    public void onClick(View v) {
        position = learnviewpager.getCurrentItem();
        switch (v.getId()) {
            case R.id.learn_next:
                Next(position);
                break;
            case R.id.learn_last:
                Last(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        curindex = position - 1;
        if (curindex == -1) {
            next.setVisibility(View.VISIBLE);
            last.setVisibility(View.INVISIBLE);
        } else if (curindex == learn_fragAdapter.getCount() - 2) {
            last.setVisibility(View.VISIBLE);
            next.setVisibility(View.INVISIBLE);
        } else {
            last.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }
        setPage();//页面切换时更换底部页码
    }

    String TAG = "aaaaa";
    int lastPage = 0;

    @Override
    public void onPageSelected(int position) {//滑到新的界面执行 position为当前页面的索引
        if (position > Integer.parseInt(getString(R.string.freePage))&&!is_vip) {
            EventBus.getDefault().post("pay");
            learnviewpager.setCurrentItem(lastPage);
            return;
        }
        lastPage = position;
        EventBus.getDefault().post("stopVideo");
        EventBus.getDefault().post("stopMusic");
        EventBus.getDefault().post(new PageChanger("learn", position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ChangeLearn(PageChanger pageChanger) {
        if (pageChanger.getString() == "read") {
            learnviewpager.setCurrentItem(pageChanger.getPosition());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
