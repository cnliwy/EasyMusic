package com.liwy.easymusic.controllers.joke;

import com.liwy.easymusic.adapter.JokeAdapter;
import com.liwy.easymusic.base.view.IView;

public interface JokeView extends IView {
    public void setAdapter(JokeAdapter adapter);
    public void finishRefresh();

}
