package com.guaju.sugertea.ui.main;

/**
 * Created by guaju on 2017/8/16.
 */

public class MainPresenter implements MainContract {
    public MainView mainView;

    public MainPresenter(MainView mainView){
        this.mainView=mainView;
    }
    @Override
    public void setLocation() {

    }
}
