package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val arrowSettingsBack = findViewById<ImageView>(R.id.settings_screen_arrow_back_like_button)
        arrowSettingsBack.setOnClickListener {
            this.finish()
        }
        val switchDarkTheme = findViewById<SwitchCompat>(R.id.switch_dark_theme)
        darkThemeCheck(switchDarkTheme)
        switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        val userAgreementTextView =
            findViewById<TextView>(R.id.settings_screen_user_agreement_textview)
        userAgreementTextView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_offer)))
            startActivity(browserIntent)
        }
        val shareApp = findViewById<TextView>(R.id.settings_screen_shareapp_textview)
        shareApp.setOnClickListener {
            val shareText = getString(R.string.url_course)
            val shareIntent = Intent.createChooser(
                Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                },
                getString(R.string.share_via)
            )
            startActivity(shareIntent)
        }

        val emailToSupport = findViewById<TextView>(R.id.settings_screen_send_mail_support_textview)
        emailToSupport.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_address)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.text_mail_subject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.text_mail_body))
            }
            try {
                startActivity(Intent.createChooser(emailIntent, "Send email..."))
            } catch (ex: android.content.ActivityNotFoundException) {
                Toast.makeText(this, getString(R.string.no_email_client), Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun darkThemeCheck(switch: SwitchCompat) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        switch.isChecked = (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
    }
}
