package com.guaju.sugertea.ui.mine;

/**
 * Created by guaju on 2017/8/25.
 */

public class MinePresenterImpl implements MineContract.MinePresenter {
    private MineContract.MineView view;

    public MinePresenterImpl(MineContract.MineView view) {
        this.view = view;
    }

    @Override
    public void hide() {
        view.hideActionBar();

    }
}
