package com.example.sixprinciple.sample5;

/**
 * Created by hjcai on 2021/7/7.
 */
public interface ICache {
    void saveCache(String finalUrl,String resultJson);

    String getCache(String finalUrl);
}
