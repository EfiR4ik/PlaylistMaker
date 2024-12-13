package com.practicum.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    // Метод onCreate вызывается, когда создается Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Получаем кнопку поиска по её ID и сохраняем в переменной
        val buttonSearch: View = findViewById<Button>(R.id.button_search)

        // Способ 1: использование анонимного класса для обработки нажатий
        val buttonSearchClickListener: View.OnClickListener = View.OnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }

        // Применяем обработчик кликов для кнопки поиска
        buttonSearch.setOnClickListener(buttonSearchClickListener)

        // Способ 2: использование лямбда-выражения для установки обработчика клика
        val buttonLibrary: View = findViewById(R.id.button_library)
        buttonLibrary.setOnClickListener {
            startActivity(Intent(this, MediaActivity::class.java))
        }

        // Способ 1: использование анонимного класса для кнопки настроек
        val buttonSettings: View = findViewById(R.id.button_settings)
        val buttonSettingsClicklistener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                val settingsActivity = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(settingsActivity)
            }
        }
        // Назначаем обработчик клика для кнопки настроек
        buttonSettings.setOnClickListener(buttonSettingsClicklistener)
    }
}