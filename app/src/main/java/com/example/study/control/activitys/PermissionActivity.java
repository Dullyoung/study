package com.example.study.control.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.study.R;
import com.example.study.view.widget.Exit_Dialog;
import com.example.study.view.widget.PermissionDialog;

public class PermissionActivity extends AppCompatActivity {
    String[] allpermissions = new String[]{    //需要申请的权限
            Manifest.permission.INTERNET, //网络权限 由于不是高危权限 不会弹窗
            Manifest.permission.WRITE_EXTERNAL_STORAGE, //读写权限
            Manifest.permission.ACCESS_NETWORK_STATE //获取网络状态

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        applypermission();
    }

    public void applypermission() {//  安卓6.0以上即SDK>=23需要动态获取权限
        if (Build.VERSION.SDK_INT >= 23) {
            boolean needapply = false;
            for (int i = 0; i < allpermissions.length; i++) {
                int chechpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                        allpermissions[i]);
                if (chechpermission != PackageManager.PERMISSION_GRANTED) {
                    needapply = true;
                }
            }
            if (needapply) {//如果需要获取权限就弹窗获取权限
                PermissionDialog permissionDialog=new PermissionDialog(this,allpermissions);
                permissionDialog.setCanceledOnTouchOutside(false);
                permissionDialog.show();
            }//如果不需要获取权限（权限已有），就进入主界面
            else {
                GoMain();
            }
        }
        else {
            GoMain();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

               // Toast.makeText(PermissionActivity.this, permissions[i] + "已授权", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PermissionActivity.this,  "拒绝授权，程序可能无法启动", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        //如果没有读取权限则不能进去主页
            if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                GoMain();
            }
        }


    private void GoMain() {//跳转到主界面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}
