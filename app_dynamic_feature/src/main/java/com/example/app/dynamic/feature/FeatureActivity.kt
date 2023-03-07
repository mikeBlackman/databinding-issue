package com.example.app.dynamic.feature

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.app.dynamic.feature.databinding.ActivityFeatureBinding
import com.google.android.play.core.splitcompat.SplitCompat

class FeatureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityFeatureBinding = DataBindingUtil.setContentView<ActivityFeatureBinding>(this, R.layout.activity_feature)

        /**
         * Now; on 'activityFeatureBinding' the View 'featureHelloWorld' is null
         * This being null is not the expected result. This should be set as an TextView
         * This does not work since in base ViewDataBinding calls View.getId() and this return a negative number
         *  /**
         *   * Cut-out from line 621 to 630 on ViewDataBinding.java
         *   *    if (!isBound) {
             *        final int id = view.getId();
             *        if (id > 0) {
             *            int index;
             *            if (viewsWithIds != null && (index = viewsWithIds.get(id, -1)) >= 0 &&
             *                bindings[index] == null) {
             *                bindings[index] = view;
             *            }
             *        }
             *    }
         *   */
         * This does however work if we set a binding on 'featureHelloWorld', then it shows up as expected
         */

        activityFeatureBinding.featureHelloWorld.text = "DYNAMIC TEXT"

        val t = ""
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        SplitCompat.install(this)
    }
}
