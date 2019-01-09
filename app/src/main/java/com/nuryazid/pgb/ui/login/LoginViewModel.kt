package com.nuryazid.pgb.ui.login

import android.arch.lifecycle.*
import com.google.gson.Gson
import com.nuryazid.pgb.api.APIService
import com.nuryazid.pgb.model.ApiResponse
import com.nuryazid.pgb.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class LoginViewModel : ViewModel() {

    val loginNotifier = MutableLiveData<ApiResponse>()
    val userNotifier = MutableLiveData<User>()
    val stateNotifier = MutableLiveData<Boolean>()

    fun login(username: String?, password: String?) {

        stateNotifier.value = true

        APIService.create().login(
            action = "login",
            username = username,
            password = password
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { it ->
                    stateNotifier.value = false

                    val response = it.string()
                    val responseJson = JSONObject(response)

                    val apiStatus = responseJson.getInt("api_status")
                    val apiMessage = responseJson.getString("api_message")

                    if (apiStatus == 1) {

                        val dataUser = responseJson.getJSONObject("user")

                        val user = Gson().fromJson<User>(dataUser.toString(), User::class.java)
                        loginNotifier.value = ApiResponse(apiStatus, apiMessage)
                        userNotifier.value = user

                    } else {
                        loginNotifier.value = ApiResponse(apiStatus, apiMessage)
                    }
                }
            ) {
                stateNotifier.value = false
                loginNotifier.value = ApiResponse(0, "error network")
            }
    }
}