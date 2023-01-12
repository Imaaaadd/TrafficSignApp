package com.carassistant.di.components;

import android.content.Context;

import com.carassistant.di.modules.ApplicationModule;
import com.carassistant.managers.SharedPreferencesManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    Context context();
    SharedPreferencesManager sharedPreferences();

}
