package com.example.xykf.tanchukuang.listener;

import android.view.View;

/**
 * author: liul
 * date: 2019/7/1 14:03
 * des:  点击监听
 **/
public interface onClickListener<T> {

    void onClick(View view, int position, T item);

}
