package br.com.schuster.bankdroid

import android.app.Application
import br.com.schuster.bankdroid.di.repositoryModule
import br.com.schuster.bankdroid.di.serviceModule
import br.com.schuster.bankdroid.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(viewModelModule, serviceModule, repositoryModule)
        }
    }
}