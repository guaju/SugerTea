package com.guaju.sugertea.factory;

import com.guaju.sugertea.ui.home.HomeFragment;
import com.guaju.sugertea.ui.mine.MineFragment;

/**
 * Created by guaju on 2017/8/30.
 */

public class FragmentFactory {
    private static FragmentFactory factory=new FragmentFactory();
    private  HomeFragment homeFragment;
    private  MineFragment mineFragment;

    private FragmentFactory(){

    }
    public static FragmentFactory getInstance(){
         return factory;
    }
    public  HomeFragment createHome(){
       if (homeFragment==null){
           homeFragment=new HomeFragment();
       }
           return homeFragment;
    }
    public  MineFragment createMine(){
        if (mineFragment==null){
            mineFragment=new MineFragment();
        }
        return mineFragment;
    }

}
