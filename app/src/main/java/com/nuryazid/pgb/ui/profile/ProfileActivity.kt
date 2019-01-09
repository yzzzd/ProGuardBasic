package com.nuryazid.pgb.ui.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.nuryazid.pgb.databinding.ActivityProfileBinding
import com.nuryazid.pgb.model.User
import android.databinding.DataBindingUtil
import com.nuryazid.pgb.R


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityProfileBinding>(this, R.layout.activity_profile)
        binding.user = Gson().fromJson<User>(intent.getStringExtra("user"), User::class.java)
    }
}
