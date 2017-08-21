package com.guaju.sugertea.ui.main;

/**
 * Created by guaju on 2017/8/16.
 */

public class MainPresenter implements MainContract.Presenter {
    public MainContract.MainView mainView;

    public MainPresenter(MainContract.MainView mainView){
        this.mainView=mainView;
    }

    @Override
    public void setLocation() {

    }
}
