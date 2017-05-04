package com.liwy.easymusic.controllers.joke.imgjoke;

import com.liwy.easymusic.adapter.ImgJokeAdapter;
import com.liwy.easymusic.base.view.IView;

public interface ImgJokeView extends IView {
    public void setAdapter(ImgJokeAdapter adapter);
    public void finishRefresh();
}
