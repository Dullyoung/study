package com.example.study;

import android.app.Application;
import android.content.Context;
import android.os.Build;


import com.kk.securityhttp.domain.GoagalInfo;
import com.kk.securityhttp.net.contains.HttpConfig;


import java.util.HashMap;
import java.util.Map;


public class App extends Application {
    private static App INSTANSE;

    @Override
    public void onCreate() {
        super.onCreate();
        initGoagal(getApplicationContext());
        INSTANSE = this;
    }

    public static App getApp() {
        return INSTANSE;
    }

    public static void initGoagal(Context context) {
        //全局信息初始化
        GoagalInfo.get().init(context);

        //设置http默认参数
        String agent_id = "1";
        Map<String, String> params = new HashMap<>();
        params.put("agent_id", agent_id);
        params.put("imeil", GoagalInfo.get().uuid);
        String sv = getSV();
        params.put("sv", sv);
        params.put("device_type", "2");
        if (GoagalInfo.get().packageInfo != null) {
            params.put("app_version", GoagalInfo.get().packageInfo.versionCode + "");
        }
        HttpConfig.setPublickey("-----BEGIN PUBLIC KEY-----\nMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA1zQ4FOFmngBVc05sg7X5\n" +
                "Z/e3GrhG4rRAiGciUCsrd/n4wpQcKNoOeiRahxKT1FVcC6thJ/95OgBN8jaDzKdd\ncMUti9gGzBDpGSS8MyuCOBXc6KCOYzL6Q4qnlGW2d09blZSpFUluDBBwB86yvOxk\n" +
                "5oEtnf6WPw2wiWtm7JR1JrE1k+adYfy+Cx9ifJX3wKZ5X3n+CdDXbUCPBD63eMBn\ndy1RYOgI1Sc67bQlQGoFtrhXOGrJ8vVoRNHczaGeBOev96/V0AiEY2f5Kw5PAWhw\n" +
                "NrAF94DOLu/4OyTVUg9rDC7M97itzBSTwvJ4X5JA9TyiXL6c/77lThXvX+8m/VLi\nmLR7PNq4e0gUCGmHCQcbfkxZVLsa4CDg2oklrT4iHvkK4ZtbNJ2M9q8lt5vgsMkb\n" +
                "bLLqe9IuTJ9O7Pemp5Ezf8++6FOeUXBQTwSHXuxBNBmZAonNZO1jACfOzm83zEE2\n+Libcn3EBgxPnOB07bDGuvx9AoSzLjFk/T4ScuvXKEhk1xqApSvtPADrRSskV0aE\n" +
                "G5F8PfBF//krOnUsgqAgujF9unKaxMJXslAJ7kQm5xnDwn2COGd7QEnOkFwqMJxr\nDmcluwXXaZXt78mwkSNtgorAhN6fXMiwRFtwywqoC3jYXlKvbh3WpsajsCsbTiCa\n" +
                "SBq4HbSs5+QTQvmgUTPwQikCAwEAAQ==\n-----END PUBLIC KEY-----\n"
        );
        HttpConfig.setDefaultParams(params);



    }

    public static String getSV() {
        return Build.MODEL.contains(Build.BRAND) ? Build.MODEL + " " + Build.VERSION.RELEASE : Build.BRAND + " " + Build.MODEL + " " + Build.VERSION.RELEASE;

    }}







