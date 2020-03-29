package com.example.shoppinglist;

import android.app.Application;
import android.content.Context;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Main {
    boolean editable = false;
    ArrayList<Item> items = new ArrayList<>();
    private Utils util = new Utils();

    String rand = String.valueOf(Math.round(Math.random() * 100));

    void changeText(TextView tx) {
        if (tx != null) {
            System.out.println(rand);
            tx.setText(rand);
        }
    }

    void addToList(final LinearLayout list, final Context context, final Item i) {
        final TextView newItem = new TextView(context);
        items.add(i);
        newItem.setTextSize(25);
        newItem.setText(i.toString());
        if (i.isGot()) {
            newItem.setPaintFlags(newItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            newItem.setPaintFlags(newItem.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editable){
                    MainActivity.getInstance().onButtonShowPopupWindowClickEdit(v,context, list,i);
                }
                else {
                    i.setGot(!i.isGot());
                    if (i.isGot()) {
                        newItem.setPaintFlags(newItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        newItem.setPaintFlags(newItem.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                    newItem.setText(i.toString());
                }
            }
        });
        list.addView(newItem);
    }


    void clearList(LinearLayout list, Context context){
        items = new ArrayList<>();
        refreshList(list,context);
    }

    void refreshList(LinearLayout list, Context context){
        list.removeAllViews();
        List<Item> temp = items;
        items = new ArrayList<>();
        for (Item i:temp) {
            addToList(list,context,i);
        }
    }

    Item findItem(String name){
        for (Item i: items) {
            System.out.println(name + " : " + i.getName() + " : " + i.getName().equals(name));
            if(i.getName().equals(name)){
                items.remove(i);
                return i;
            }
        }
        return null;
    }

    void removeItem (Item i){
        items.remove(i);
    }



}
