package com.liwy.easymusic.controllers.music;


import com.liwy.easymusic.adapter.MusicAdapter;
import com.liwy.easymusic.base.view.IView;

public interface MusicView extends IView {
    public void finishRefresh();
    public void finishLoad();
    public void setAdapter(MusicAdapter adapter);

}
