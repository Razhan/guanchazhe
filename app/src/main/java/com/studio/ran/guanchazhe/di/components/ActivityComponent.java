package com.studio.ran.guanchazhe.di.components;

import android.app.Activity;

import com.alp.library.di.PerActivity;
import com.studio.ran.guanchazhe.di.modules.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
interface ActivityComponent {

    Activity activity();

}
