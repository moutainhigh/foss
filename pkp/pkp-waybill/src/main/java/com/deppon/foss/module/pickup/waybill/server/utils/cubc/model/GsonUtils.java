package com.deppon.foss.module.pickup.waybill.server.utils.cubc.model;

import com.google.gson.Gson;

/**
 * Created by mitsui on 2016/12/15.
 */
public class GsonUtils {

    private static class GsonHolder {
        private static final Gson INSTANCE = new Gson();
    }

    /**
     * 获取Gson实例，由于Gson是线程安全的，这里共同使用同一个Gson实例
     */
    public static Gson getInstance() {
        return GsonHolder.INSTANCE;
    }


}
