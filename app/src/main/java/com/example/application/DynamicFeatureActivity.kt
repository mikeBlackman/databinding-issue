package com.example.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.content.ContextCompat
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

class DynamicFeatureActivity : AppCompatActivity() {
    private lateinit var manager: SplitInstallManager

    private val listener = SplitInstallStateUpdatedListener { state ->
        val multiInstall = state.moduleNames().size > 1

        state.moduleNames().forEach { name ->
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    startIntentSender(state.resolutionIntent()?.intentSender, null, 0, 0, 0)
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    onSuccessfulLoad(name, launch = !multiInstall)
                }
                SplitInstallSessionStatus.FAILED -> {
                }
            }
        }
    }

    lateinit var moduleName: String

    lateinit var moduleClassName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myIntent = intent

        this.moduleName = myIntent.getStringExtra("name")!!

        this.moduleClassName = myIntent.getStringExtra("className")!!

        AsyncLayoutInflater(this).inflate(
            R.layout.activity_dynamic_feature,
            null
        ) { view, _, _ ->
            this.setContentView(view)
        }

        this.manager = SplitInstallManagerFactory.create(this)

        this.loadAndLaunchModule(moduleName)
    }

    override fun onResume() {
        manager.registerListener(listener)

        super.onResume()
    }

    override fun onPause() {
        manager.unregisterListener(listener)

        super.onPause()
    }

    private fun loadAndLaunchModule(name: String) {
        if (manager.installedModules.contains(name)) {
            onSuccessfulLoad(name, launch = true)

            return
        }

        val request = SplitInstallRequest.newBuilder()
            .addModule(name)
            .build()

        manager.startInstall(request)
    }

    private fun onSuccessfulLoad(moduleName: String, launch: Boolean) {
        if (launch) {
            launchActivity(moduleClassName)
        }
    }

    private fun launchActivity(className: String) {
        Handler().post {
            SplitInstallHelper.updateAppInfo(this);

            Intent(Intent.ACTION_VIEW).setClassName(this, className)
                .also {
                    ContextCompat.startActivity(this, it, null)
                }

            this.finish()
        }
    }
}
