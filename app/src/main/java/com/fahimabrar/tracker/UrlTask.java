package com.fahimabrar.tracker;

/**
 * Created by FAHIM on 7/5/2018.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;


abstract class Urltask extends AsyncTask<String,String,List<DataModel>> {




    @Override
    protected List<DataModel> doInBackground(String... params) {

        JSONhelper jsonhelper = new JSONhelper();
        List<DataModel>  data = jsonhelper.getdatafromurl(params[0]);


        return data;
    }


}