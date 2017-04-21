package com.liwy.easymusic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.Button;


import com.liwy.easymusic.R;
import com.liwy.easymusic.base.easyrecycler.EasyHolder;
import com.liwy.easymusic.base.easyrecycler.EasyRecyclerAdapter;
import com.liwy.easymusic.entity.Contact;

import java.util.List;
import java.util.Random;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by liwy on 2017/4/20.
 */

public class ContactsAdapter extends EasyRecyclerAdapter<Contact> {
    public static String[] colors = {"#FF427A","#F9B432","#5AD585","#0DB5DA","#FF583D"};
    public ContactsAdapter(Context context) {
        super(context);
    }

    public ContactsAdapter(Context context, List<Contact> list) {
        super(context, list);
    }

    @Override
    public void convert(EasyHolder holder, Contact item) {
        Button head = holder.getView(R.id.btn_head);
        Random random = new Random();

//        head.setBackgroundColor(Color.parseColor(colors[random.nextInt(colors.length)]));
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
            drawable = mContext.getResources().getDrawable(R.drawable.round_button, null);
        }else{
            drawable = mContext.getResources().getDrawable(R.drawable.round_button);
        }
        int myOrder = getMyOrder(item.getName().charAt(0));
        drawable.setColorFilter(Color.parseColor(colors[myOrder]),PorterDuff.Mode.MULTIPLY);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            head.setBackground(drawable);
        }else{
            head.setBackgroundDrawable(drawable);
        }

        head.setText(item.getName());
        holder.setText(R.id.tv_contact_phone,item.getPhone() + "(" + myOrder + ")");
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_contact;
    }

    public static int getMyOrder(char value){
        return ((int)value)%5;
    }
}
