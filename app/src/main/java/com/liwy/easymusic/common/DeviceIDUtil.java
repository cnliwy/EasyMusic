package com.liwy.easymusic.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.UUID;


/**
 * 获取唯一设备号，通过androidid+deviceid+uuid综合获取，保证唯一设备号的稳定获取
 * Created by liwy on 2017/3/24.
 */

public class DeviceIDUtil {
    public static UUID uuid;
    public static final String PREFS_FILE = "UUID";
    public static String filename = Environment.getExternalStorageDirectory() + "/ecrf/liwy/cache/a.txt";
    public static String TAG = "DeviceIDUtil";
    public static String getDeviceUuid(Context context, String userId){
        if (uuid == null) {
            File file = new File(filename);
            if (!file.exists()){
                FileUtil.createNewFile(file);
            }
            synchronized (DeviceIDUtil.class) {
                if (uuid == null) {
                    final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
                    String id = prefs.getString(userId, null);
                    if (id != null) {
                        uuid = UUID.fromString(id);
                    } else {
                        String content = FileUtil.readFile(file);
                        if (content != null && !"".equals(content)) {
                            uuid = UUID.fromString(content);
                        } else {
                            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                            try {
                                if (!"9774d56d682e549c".equals(androidId)) {
                                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                                    try {
                                        FileUtil.write(file,uuid.toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                                    try {
                                        FileUtil.write(file,uuid.toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (UnsupportedEncodingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        prefs.edit().putString(userId, uuid.toString()).commit();
                    }
                }
            }
        }
        return uuid.toString();
    }
}
