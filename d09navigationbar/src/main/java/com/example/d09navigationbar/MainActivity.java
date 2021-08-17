package com.example.d09navigationbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.d09navigationbar.navigationbar.DefaultNavigationBar;
import com.example.d09navigationbar.navigationbar.NavigationBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 测试NavigationBar
        /*ViewGroup viewGroup = findViewById(R.id.view_root);
        new NavigationBar.Builder(MainActivity.this,R.layout.navigationbar,viewGroup)
                .setText(R.id.left_text,"AAA")
                .setText(R.id.title,"BBB")
                .setOnClickListener(R.id.left_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"left pressed",Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnClickListener(R.id.title, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"title pressed",Toast.LENGTH_SHORT).show();
                    }
                })
                .create();*/

        // 测试DefaultNavigationBar
        // 为什么需要有DefaultNavigationBar呢？
        // NavigationBar需要开发者知道其使用了哪一个布局 有哪些id 要设置的控件是什么id 显示在什么位置
        // DefaultNavigationBar则提供了更简明的方法 使用者不需要了解DefaultNavigationBar内部的布局 只需要调用暴露的接口即可
        // 当然两种方式个人觉得都没什么问题 看个人选择了
        ViewGroup parent = findViewById(R.id.view_root);
        new DefaultNavigationBar.Builder(this, parent)
                .setLeftText("返回")
                //.hideLeftText()
                .setLeftClickListener(v -> {
                    Toast.makeText(MainActivity.this, "back pressed", Toast.LENGTH_SHORT).show();
                    Log.e("hjcai", "onClick: ");
                })
                //还有一些参数
                .create();
    }
}