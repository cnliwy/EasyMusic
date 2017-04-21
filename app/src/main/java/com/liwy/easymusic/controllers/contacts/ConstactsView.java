package com.liwy.easymusic.controllers.contacts;

import com.liwy.easymusic.adapter.ContactsAdapter;
import com.liwy.easymusic.base.view.IView;

public interface ConstactsView extends IView {
    public void setAdapter(ContactsAdapter adapter);
    public void finishRefresh();

}
