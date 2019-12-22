package com.alsc.utils.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by Mirko on 2016/11/30.
 *
 */

public abstract class BaseFragment extends RxFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(savedInstanceState);
        initAttrs();
        loadData();
    }

    /**定义三个抽象方法，用来初始化相关定义，加载数据*
     * initViews 定义一些与View相关的初始化
     *
     * initAttrs 加载定义的一些属性
     *
     * loadData 加载页面所需要的数据
     */

    public abstract void initViews(Bundle savedInstanceState);
    public abstract void initAttrs();
    public abstract void loadData();


}
