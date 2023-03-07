package com.example.application

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.application.databinding.ActivityMainBinding
import com.google.android.play.core.splitcompat.SplitCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        //Now; on 'activityMainBinding' the View mainHelloWorld is set as TextView

        val dynamicFeatureIntent = Intent(this, DynamicFeatureActivity::class.java)

        dynamicFeatureIntent.putExtra("name", "app_dynamic_feature")

        dynamicFeatureIntent.putExtra("className", "com.example.app.dynamic.feature.FeatureActivity")

        startActivity(dynamicFeatureIntent)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        SplitCompat.install(this)
    }
}
