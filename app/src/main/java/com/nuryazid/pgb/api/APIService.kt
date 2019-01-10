package com.nuryazid.pgb.api

import android.util.Base64
import com.nuryazid.pgb.BuildConfig
import io.reactivex.Observable
import net.idik.lib.cipher.so.CipherClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.nio.charset.StandardCharsets

/**
 * Created by nuryazid on 4/5/18.
 */

interface APIService {

    companion object Factory {

        fun validate(): String {
            val data = Base64.decode(CipherClient.xurl().substring(10), Base64.DEFAULT)
            return String(data, StandardCharsets.UTF_8)
        }

        fun create(): APIService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(validate())
                    .build()

            return retrofit.create(APIService::class.java)
        }
    }

    @FormUrlEncoded
    @POST("exec")
    fun login(
        @Field("action") action: String?,
        @Field("u") username: String?,
        @Field("p") password: String?
    ): Observable<ResponseBody>
}