package com.wayyyhoo.dev.ironman.payment.init;

public interface IPayCloudConfig {
    int getIntValue(String key, int defaultVal);

    long getLongValue(String key, long defValue);

    boolean getBooleanValue(String key, boolean defValue);

    String getStringValue(String key, String defValue);

}
