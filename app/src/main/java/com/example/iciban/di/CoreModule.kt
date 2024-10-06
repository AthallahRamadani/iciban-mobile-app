package com.example.iciban.di



import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.iciban.data.datasource.api.service.ApiService
import com.example.iciban.data.datasource.preference.UserDataStore
import com.example.iciban.data.repository.AppRepository
import com.example.iciban.data.repository.UserRepository
import com.example.iciban.data.repository.impl.AppRepositoryImpl
import com.example.iciban.data.repository.impl.UserRepositoryImpl
import com.example.iciban.data.util.Constant
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module


val repositoryModule = module {
    single { UserRepositoryImpl(get(), get()) } bind UserRepository::class
    single { AppRepositoryImpl(get()) } bind AppRepository::class

}
val apiModule = module {
    single<Retrofit> {
        return@single Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        OkHttpClient.Builder().apply {
            addInterceptor(get<ChuckerInterceptor>())
        }.build()
    }

    single {
        ChuckerInterceptor.Builder(androidApplication())
            .collector(ChuckerCollector(androidApplication()))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "APPLICATION_PREFERENCE")


val preferenceModule = module {
    single { androidContext().dataStore }
    single { UserDataStore(get()) }
}