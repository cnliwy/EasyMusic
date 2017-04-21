package com.liwy.easymusic.base.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liwy on 2017/3/13.
 */

public interface IFragmentPresenter extends IPresenter {
    public void onActivityCreated(@Nullable Bundle savedInstanceState);
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState);
}
