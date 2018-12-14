package com.test.testapp;

public class CityBean {
    private String name;
    private String pinyin;
    private boolean hot;
    private boolean histoty;

    public boolean isHistoty() {
        return histoty;
    }

    public void setHistoty(boolean histoty) {
        this.histoty = histoty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public boolean getHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }
}
