package com.liwy.easymusic.controllers.contacts;


import com.liwy.easymusic.adapter.ContactsAdapter;
import com.liwy.easymusic.base.presenter.BasePresenter;
import com.liwy.easymusic.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class ConstactsPresenter extends BasePresenter<ConstactsView> {
    private List<Contact> datas = new ArrayList<Contact>();

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initData(){
        datas = new ArrayList<Contact>();
        datas.add(new Contact("姬发","15899999999"));
        datas.add(new Contact("嬴稷","15899999999"));
        datas.add(new Contact("项羽","15899999999"));
        datas.add(new Contact("刘邦","15899999999"));
        datas.add(new Contact("张良","15899999999"));
        datas.add(new Contact("韩信","15899999999"));
        datas.add(new Contact("诸葛亮","15899999999"));
        datas.add(new Contact("杨坚","15899999999"));
        datas.add(new Contact("李世民","15899999999"));
        datas.add(new Contact("郭子仪","15899999999"));
        ContactsAdapter adapter = new ContactsAdapter(mContext,datas);
        mView.setAdapter(adapter);

    }
}
