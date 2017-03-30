package com.studio.ran.guanchazhe;

import android.app.Application;

import com.alp.library.utils.Prefs;
import com.facebook.stetho.Stetho;
import com.studio.ran.guanchazhe.di.components.ApplicationComponent;
import com.studio.ran.guanchazhe.di.components.DaggerApplicationComponent;
import com.studio.ran.guanchazhe.di.modules.ApplicationModule;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.drakeet.library.CrashWoodpecker;
import me.drakeet.library.PatchMode;

public final class NewsApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeInjector();
        Prefs.init(this);

        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(config);

        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());

            CrashWoodpecker.instance()
                    .setPatchMode(PatchMode.SHOW_LOG_PAGE)
                    .setPassToOriginalDefaultHandler(true)
                    .flyTo(this);
        }

    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}