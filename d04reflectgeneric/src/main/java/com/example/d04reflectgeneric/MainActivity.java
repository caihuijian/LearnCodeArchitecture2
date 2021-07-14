package com.example.d04reflectgeneric;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.example.d04reflectgeneric.reflect.ReflectTestClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testReflect();
    }

    private void testReflect() {
        Log.e(TAG, "testReflect: start ======>");
        getPrivateConstructor();
        Log.e(TAG, "testReflect: end ======>");
        getPrivateField();
        Log.e(TAG, "getPrivateMethod: start ======>");
        getPrivateMethod();
        Log.e(TAG, "getPrivateMethod: end ======>");
        getHideMethod();
    }

    // 1 反射调用构造方法
    private void getPrivateConstructor() {
        try {
            // 1.1 直接调用newInstance创建对象
            // 只能获取无参 public 的构造方法来构建对象 如果没有无参构造方法 则抛出异常
            ReflectTestClass reflectTestClassObj = ReflectTestClass.class.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        try {
            // 1.2 调用getDeclaredConstructor来创建对象
            Constructor<?> reflectTestClassObj = ReflectTestClass.class.getDeclaredConstructor();
            reflectTestClassObj.setAccessible(true);
            reflectTestClassObj.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            // 1.3获取带参数的构造方法构建的对象
            // 注意区分 ReflectTestClass.class.getConstructor() 这个方法返回的是public的构造方法
            Constructor<?> reflectTestClassObj = ReflectTestClass.class.getDeclaredConstructor(String.class);
            reflectTestClassObj.setAccessible(true);
            reflectTestClassObj.newInstance("AAAA");
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // 2 反射调用filed
    private void getPrivateField() {
        ReflectTestClass object = null;
        try {
            Constructor<?> reflectTestClassObj = ReflectTestClass.class.getDeclaredConstructor(String.class);
            reflectTestClassObj.setAccessible(true);
            object = (ReflectTestClass) reflectTestClassObj.newInstance("AAAA");
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (object == null){
            return;
        }

        // 开始访问私有变量
        try {
            Field field = ReflectTestClass.class.getDeclaredField("mName");
            field.setAccessible(true);
            String name = (String) field.get(object);
            Log.e(TAG, "getPrivateField: mName "+name);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 3 反射调用method
    private void getPrivateMethod() {
        ReflectTestClass object = null;
        try {
            Constructor<?> reflectTestClassObj = ReflectTestClass.class.getDeclaredConstructor(String.class);
            reflectTestClassObj.setAccessible(true);
            object = (ReflectTestClass) reflectTestClassObj.newInstance("AAAA");
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (object == null){
            return;
        }

        // 开始访问私有method
        // 3.1 调用无参方法
        try {
            Method method = ReflectTestClass.class.getDeclaredMethod("getName");
            method.setAccessible(true);
            method.invoke(object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 3.2 调用含有参数的方法
        try {
            Method method = ReflectTestClass.class.getDeclaredMethod("setName",String.class);
            method.setAccessible(true);
            method.invoke(object,"CCCC");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // 4 反射调用hide的方法或类
    // AssetManager addAssetPath(String path)是一个hide的方法 调用方式其实和普通方法反射调用一样
    private void getHideMethod() {
        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = AssetManager.class.getDeclaredMethod("addAssetPath",String.class);// addAssetPath(String path)
            addAssetPathMethod.invoke(assetManager,"sdcard/app/red.skin");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}