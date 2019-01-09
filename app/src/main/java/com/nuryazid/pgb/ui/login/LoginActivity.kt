package com.nuryazid.pgb.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.nuryazid.pgb.R
import com.nuryazid.pgb.model.ApiResponse
import com.nuryazid.pgb.model.User
import com.nuryazid.pgb.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    private val stateNotifier = Observer<Boolean> { value ->
        value?.let { showLoading(value) }
    }

    private val loginNotifier = Observer<ApiResponse> { value ->
        value?.let { apiMessage(value) }
    }

    private val userNotifier = Observer<User> { value ->
        value?.let { login(value) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.loginNotifier.observe(this, loginNotifier)
        viewModel.stateNotifier.observe(this, stateNotifier)
        viewModel.userNotifier.observe(this, userNotifier)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password)
            } else {
                Toast.makeText(this, "fill the form", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun apiMessage(apiResponse: ApiResponse) {
        Toast.makeText(this, apiResponse.message, Toast.LENGTH_SHORT).show()
    }

    private fun login(user: User) {

        val main = Intent(this, ProfileActivity::class.java).apply {
            putExtra("user", Gson().toJson(user))
        }
        startActivity(main)
        finish()
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            btnLogin.visibility = View.VISIBLE
        }
    }
}
