package com.wendy.popularmovieapp;

import android.app.Application;
import android.content.Context;

import com.orm.SugarContext;
import com.orm.SugarRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by SRIN on 8/7/2017.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        App.context = getApplicationContext();
        SugarContext.init(this);

        enableChecking();
    }

    public static Context getContext(){
        return App.context;
    }

    private void enableChecking() {
        try {
            File dbFile = this.getDatabasePath("popular_movie.db");
            File backupDbFile = new File(getExternalCacheDir(), dbFile.getName());
            if (dbFile.exists()) {
                FileChannel src = new FileInputStream(dbFile).getChannel();
                FileChannel dst = new FileOutputStream(backupDbFile).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
