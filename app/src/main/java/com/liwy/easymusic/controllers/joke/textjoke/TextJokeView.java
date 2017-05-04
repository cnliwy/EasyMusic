package com.liwy.easymusic.controllers.joke.textjoke;

import com.liwy.easymusic.adapter.TextJokeAdapter;
import com.liwy.easymusic.base.view.IView;

public interface TextJokeView extends IView {
    public void setAdapter(TextJokeAdapter adapter);
    public void finishRefresh();
}
