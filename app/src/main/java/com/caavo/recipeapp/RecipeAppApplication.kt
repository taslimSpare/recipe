package com.caavo.recipeapp


import androidx.multidex.MultiDexApplication
import com.caavo.recipeapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class RecipeAppApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // initialize Koin
        startKoin {
            androidContext(this@RecipeAppApplication)
            modules(appModules)
        }
    }

}
