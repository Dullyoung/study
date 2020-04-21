package com.example.study.control.fragments.Fragment_Read_To_Me;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.study.R;
import com.example.study.model.Cache.GlideApp;
import com.example.study.model.bean.LearnInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Read_Phonetic_Img extends Fragment {

    private LearnInfo learnInfo;
    private ImageView read_img;
    View rootView;
    public Fragment_Read_Phonetic_Img(LearnInfo learnInfo) {
        // Required empty public constructor
        this.learnInfo = learnInfo;
    }
    RequestOptions options;
    FrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_read1, null);
        read_img = rootView.findViewById(R.id.readimg);
        frameLayout=rootView.findViewById(R.id.root);
        if (options==null){
            options = new RequestOptions();
            options.placeholder(R.mipmap.ic_player_error);
            options.error(R.mipmap.ic_player_error);
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.skipMemoryCache(true);
        }
        GlideApp.with(this).load(Uri.parse(learnInfo.getImg())).apply(options).thumbnail(0.1f).into(read_img);
        return rootView;
    }
    String TAG="aaa";
    @Override
    public void onDestroyView() {
        System.gc();
        frameLayout.removeAllViews();
        super.onDestroyView();
    }
}
