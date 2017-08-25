package com.guaju.sugertea.ui.mine;

/**
 * Created by guaju on 2017/8/25.
 */

public interface MineContract {

    public interface MineView {
        void  hideActionBar();
    }
    interface MinePresenter{
        //只需要操作逻辑事
       void hide();
    }

}
