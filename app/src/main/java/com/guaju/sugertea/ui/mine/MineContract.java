package com.guaju.sugertea.ui.mine;

import com.guaju.sugertea.model.bean.UserInfoBean;

/**
 * Created by guaju on 2017/8/25.
 */

public interface MineContract {

    public interface MineView {
        void  hideActionBar();
        void  showLoginView(UserInfoBean user);
        void showLoginError();
    }
    interface MinePresenter{
        //只需要操作逻辑事
       void hide();
    }

}
