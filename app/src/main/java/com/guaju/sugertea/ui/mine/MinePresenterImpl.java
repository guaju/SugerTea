package com.guaju.sugertea.ui.mine;

import android.text.TextUtils;

import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.httputil.HttpHelper;
import com.guaju.sugertea.model.bean.LoginInfo;
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
         String phonenum = (String) spUtils.getSp("phonenum", String.class);
         String code = (String) spUtils.getSp("logincode", String.class);
         if ((!TextUtils.isEmpty(phonenum))&&(!TextUtils.isEmpty(code))){
            HttpHelper.getInstance().login(phonenum,code);
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
