package com.guaju.sugertea.ui.mine;

import com.guaju.sugertea.dao.bean.UserInfo;
import com.guaju.sugertea.model.bean.LoginInfo;
import com.guaju.sugertea.utils.SPUtils;

/**
 * Created by guaju on 2017/8/25.
 */

public interface MineContract {

    public interface MineView {
        void  hideActionBar();
        void  showLoginView(UserInfo user);
        void showLoginError();
    }
    interface MinePresenter{
        //只需要操作逻辑事
        //隐藏actionbar
       void hide();
        //读取缓存的登录信息
       void readSavedLoginInfo(SPUtils spUtils);
        //保存登录状态
       void saveLoginStatus(LoginInfo bean);
    }

}
