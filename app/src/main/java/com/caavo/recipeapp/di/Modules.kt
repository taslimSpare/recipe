package com.caavo.recipeapp.di

import androidx.room.Room
import com.caavo.recipeapp.BuildConfig
import com.caavo.recipeapp.data.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.dsl.module
import retrofit2.Retrofit
import com.caavo.recipeapp.data.FirebaseHelper
import com.caavo.recipeapp.data.RoomDB
import com.caavo.recipeapp.room.dao.AccountDao
import com.caavo.recipeapp.room.dao.MyItemsDao
import com.caavo.recipeapp.room.AppDatabase
import com.caavo.recipeapp.viewmodels.AuthViewModel
import com.caavo.recipeapp.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.converter.gson.GsonConverterFactory



/* The fields below are saved in the buildConfig for easier management and scalability.
   This would also be useful if there are different values per buildType in the future
 */
private const val API_BASE_URL = BuildConfig.BASE_URL



val appModules = module {
    single { createApiService() }
    single { FirebaseHelper() }
    single { RoomDB(get() as AccountDao, get() as MyItemsDao) }
    single { Room.databaseBuilder(androidContext(),
        AppDatabase::class.java, "recipe_db")
        .allowMainThreadQueries()
        .build() } // using allowMainThreadQueries() is highly discouraged as running on the UI thread can lead to ANRs. A better alternative would have been to run my DB queries on a network thread using Coroutines.
    single { get<AppDatabase>().accountDao() }
    single { get<AppDatabase>().myItemsDao() }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get(), get()) }
}


private fun createApiService(): ApiService {
    val retrofit = initRetrofit()
    return retrofit.create(ApiService::class.java)
}

private fun initRetrofit(): Retrofit {

    return Retrofit.Builder()
        .client(getOkHttpClient())
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


private fun getOkHttpClient() : OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(TokenInterceptor())
        .build()
}



/* The main purpose of this interceptor is to pass in the
   access_key parameter since it's required for every API call to fixer.io.
   This approach is preferable to manually adding it in
   every call.
 */
class TokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain
            .request()
            .url
            .newBuilder()
            .build()

        return chain.proceed(chain.request().newBuilder().url(url).build())

    }
}
