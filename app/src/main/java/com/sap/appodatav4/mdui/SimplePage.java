package com.sap.appodatav4.mdui;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.sap.appodatav4.R;
import com.sap.appodatav4.service.OfflineWorkerUtil;
import com.sap.cloud.android.odata.container.Container;

import com.sap.cloud.mobile.odata.DataQuery;
import com.sap.cloud.mobile.odata.EntitySet;
import com.sap.cloud.mobile.odata.core.Action0;
import com.sap.cloud.mobile.odata.core.Action1;
import com.sap.cloud.mobile.odata.http.HttpHeaders;

import java.util.List;

public class SimplePage extends AppCompatActivity {

    private EntitySet entitySet;
    private Container container;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_page);

        getSomeData(() -> {
                    Log.i("suc data", "hi");
                },
                error -> {
                    Log.i("fail data", "bye");
                });
    }

    public void getSomeData(@Nullable Action0 successHandler, @NonNull Action1<RuntimeException> failureHandler){

        DataQuery dq = new DataQuery().from(entitySet);

        Gson json = new Gson();

        container = OfflineWorkerUtil.getContainer();

        container.executeQueryAsync(dq,
                result -> {
                    Log.i("result data", json.toJson(result.getEntityList()));
                },
                failureHandler,
                HttpHeaders.empty);

    }
}
