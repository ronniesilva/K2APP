package com.app.k2app.classes;

import com.app.k2app.application.MyApplication;
import com.app.k2app.config.Config;
import com.app.k2app.config.Params;

public class ClassFilter {

    public static Integer getIdMin() {
        int idMin = MyApplication.sharedPrefs.getInt(Config.SHARED_FILTERS_ID_MIN, Params.ID_MIN_TOTAL);
        return idMin;
    }

    public static Integer getIdMax() {
        int idMax = MyApplication.sharedPrefs.getInt(Config.SHARED_FILTERS_ID_MAX, Params.ID_MAX_TOTAL);
        return idMax;
    }

    public static Integer getSexo() {
        /**
         0:Masculino, 1: Feminino, 2:All
         */

        Boolean filterMan = (MyApplication.sharedPrefs.getBoolean(Config.SHARED_FILTERS_MAN, true));
        Boolean filterGirl = (MyApplication.sharedPrefs.getBoolean(Config.SHARED_FILTERS_GIRL, true));
        Integer sexo;

        if ((filterMan) && (filterGirl)){
            sexo = 2;
        }else if(filterMan){
            sexo = 0;
        }else{
            sexo = 1;
        }

        return sexo;
    }

    public static Integer getOrientacaoSexual() {
        /**
         0:He, 1:Ho, 2:Bi, 3:He+Ho, 4:He+Bi, 5:Ho+Bi, 6:All
         */

        Boolean filterHomo = (MyApplication.sharedPrefs.getBoolean(Config.SHARED_FILTERS_HOMO, true));
        Boolean filterHetero = (MyApplication.sharedPrefs.getBoolean(Config.SHARED_FILTERS_HETERO, true));
        Boolean filterBi = (MyApplication.sharedPrefs.getBoolean(Config.SHARED_FILTERS_BI, true));

        Integer oriSex;

        if (filterHetero && !filterHomo && !filterBi){
            oriSex = 0;
        }else if(!filterHetero && filterHomo && !filterBi){
            oriSex = 1;
        }else if(!filterHetero && !filterHomo && filterBi){
            oriSex = 2;
        }else if(filterHetero && filterHomo && !filterBi){
            oriSex = 3;
        }else if(filterHetero && !filterHomo && filterBi){
            oriSex = 4;
        }else if(!filterHetero && filterHomo && filterBi){
            oriSex = 5;
        }else{
            oriSex = 6;
        }

        return oriSex;
    }

}
