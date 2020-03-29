package com.example.shoppinglist;

import android.text.Html;

public class Item {
    private String name;
    private int quanity;
    private boolean got;

    public Item(String name, int quanity) {
        this.name = name;
        this.quanity = quanity;
        this.got = false;
    }

    public Item(String name, int quanity, boolean got) {
        this.name = name;
        this.quanity = quanity;
        this.got = got;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public boolean isGot() {
        return got;
    }

    public void setGot(boolean got) {
        this.got = got;
    }

    @Override
    public String toString() {

        String s = " " + String.format("%2d %50s", quanity, name);
        return s;
    }
}
