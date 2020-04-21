package com.example.study.model.Cache;

import android.app.Activity;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Cache_Json {
    /**
     * 保存json到本地
     *
     * @param mActivity
     * @param filename
     * @param s
     */
    public static File dir=new File(getCachePath() + "/Android/data/com.example.study/cache/Json");//有SD卡
    public static void saveToSDCard(Activity mActivity, String filename, String s) {
        String en = Environment.getExternalStorageState();
        //获取SDCard状态,如果SDCard插入了手机且为非写保护状态
        //安卓4.4以上 非root用户可见的文件就是机内外部储存
        if (en.equals(Environment.MEDIA_MOUNTED)) {
            try {
                dir.mkdirs(); //create folders where write files
                File file = new File(dir, filename);
                OutputStream out = new FileOutputStream(file);
                out.write(s.getBytes());
                out.close();

            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            //提示用户SDCard不存在或者为写保护状态

        }
    }
    private static String getCachePath(){
         String sdRootPath = Environment.getExternalStorageDirectory().getPath();
         String appRootPath = Environment.getDataDirectory().getPath();
           return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?sdRootPath : appRootPath;
    }
    /*
     * 从本地读取json
     * @param mActivity
     * @param filename
     * @param content
     */
    public static String readTextFile(String filename) {
        String result=null;
        try {
            File f=new File(dir,filename);
            int length=(int)f.length();
            byte[] buff=new byte[length];
            FileInputStream fin=new FileInputStream(f);
            fin.read(buff);
            fin.close();
            result=new String(buff,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();

        }
        return result;



    }



}
