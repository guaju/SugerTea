package com.guaju.sugertea.app;

import android.app.Application;
import android.content.Context;

import com.guaju.sugertea.dao.bean.DaoMaster;
import com.guaju.sugertea.dao.bean.DaoSession;
import com.mob.MobSDK;

import org.greenrobot.greendao.database.Database;

/**
 * Created by guaju on 2017/8/29.
 */

public class App extends Application {
   public static Context appContext;
    //操作数据库的对象
   private static DaoSession daoSession;
    @Override
    public void onCreate() {

        super.onCreate();
        appContext=getApplicationContext();
        MobSDK.init(this);
        //初始化数据库名称，必须在创建出实体类之后才能调用此类
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,  "userinfo");
        //拿到一个数据库
        Database db =  helper.getWritableDb();
        //关联数据库，返回操作数据库的session对象
        daoSession = new DaoMaster(db).newSession();
    }
    //对外提供获取本数据的操作对象的方法
    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
