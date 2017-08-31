package com.guaju.sugertea.ui.mine;

import android.text.TextUtils;

import com.guaju.sugertea.app.App;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.dao.bean.DaoSession;
import com.guaju.sugertea.dao.bean.UserInfo;
import com.guaju.sugertea.dao.bean.UserInfoDao;
import com.guaju.sugertea.model.bean.LoginInfo;
import com.guaju.sugertea.model.bean.UserInfoBean;
import com.guaju.sugertea.utils.SPUtils;

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

    @Override
    public void readSavedLoginInfo(SPUtils spUtils) {
        //拿到最近登录的账号
        String phonenum = (String) spUtils.getSp("phonenum", String.class);
        //从数据库中拿缓存数据
        DaoSession daoSession = App.getDaoSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        if (!TextUtils.isEmpty(phonenum)) {
            //拿到数据库中的数据
            UserInfo load = userInfoDao.load(phonenum);

            view.showLoginView(load);
        }

    }

    @Override
    public void saveLoginStatus(LoginInfo bean) {
        MineFragment view = (MineFragment) this.view;
        //三个保存 ，状态，手机号，验证码
        String phonenum = bean.getPhonenum();
        String code = bean.getCode();
        SPUtils instance = SPUtils.getInstance(view.getActivity(), Constant.SPNAME);
        instance.putSp("phonenum",phonenum);
        instance.putSp("logincode",code);
        instance.putSp("islogin",true);

    }
}
