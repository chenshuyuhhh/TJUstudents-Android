package com.chenshuyusc.tjustudents.Manage.Service

import com.chenshuyusc.tjustudents.Entity.SelectionPage
import com.chenshuyusc.tjustudents.Util.CoroutineCallAdapterFactory
import com.chenshuyusc.tjustudents.Util.RefreshState
import com.chenshuyusc.tjustudents.Util.awaitAndHandle
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(false)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://172.23.24.203:8888")
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val api: RetrofitService = retrofit.create(RetrofitService::class.java)

//    fun getlist(
//        page: Int,
//        size: Int,
//        callback: suspend (RefreshState<Unit>, selectionPage: SelectionPage?) -> Unit
//    ) {
//        GlobalScope.launch(Dispatchers.Main) {
//            api.getSelectionPage(2, 3).awaitAndHandle {
//                callback(RefreshState.Failure(it), null)
//            }?.let {
//                callback(RefreshState.Success(Unit), it)
//            }
//        }
//    }
}