package com.sibelyiildz.firabaseremoteconfigsample

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class MainActivity : AppCompatActivity() {

    companion object {
        private const val THEME_COLOR_KEY = "theme_color"
        private const val WELCOME_TEXT_KEY = "welcome_text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val colorParameter = remoteConfig[THEME_COLOR_KEY].asString()
                val textParameter = remoteConfig[WELCOME_TEXT_KEY].asString()

                findViewById<TextView>(R.id.textView).text = textParameter
                findViewById<FrameLayout>(R.id.root).setBackgroundColor(
                    Color.parseColor(
                        colorParameter
                    )
                )
            } else {
                Log.v("LogTag", "Fetch failed")
            }
        }
    }
}