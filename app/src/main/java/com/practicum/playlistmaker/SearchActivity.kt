package com.practicum.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    // Строка для хранения пользовательского ввода
    private var userInputText: String = ""

    // Определение ключа для передачи данных во время изменения конфигурации приложения
    companion object {
        const val USERTEXT = "USER_INPUT"
    }

    // Основной метод, выполняющийся при создании Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Инициализация UI компонентов
        val editTextSearchActivity = findViewById<EditText>(R.id.search_activity_edittext)
        val searchClearEdittextImageview = findViewById<ImageView>(R.id.search_clear_edittext_imageview)
        val settingsArrowBack = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_activity_toolbar)

        // Устанавливаем слушателя для очистки текста в поле ввода
        searchClearEdittextImageview.setOnClickListener {
            editTextSearchActivity.setText("")
            val view: View? = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        // Создание TextWatcher для мониторинга изменений текста
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchClearEdittextImageview.visibility = clearButtonVisibility(s)
                userInputText = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        // Применяем TextWatcher к текстовому полю для отслеживания ввода
        editTextSearchActivity.addTextChangedListener(simpleTextWatcher)

        // Устанавливаем действие для кнопки "Назад" в Toolbar
        settingsArrowBack.setNavigationOnClickListener {
            this.finish()
        }
    }

    // Метод для сохранения состояния Activity при неожиданных изменениях конфигурации
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(USERTEXT, userInputText)
    }

    // Метод для восстановления состояния Activity после изменения конфигурации
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        userInputText = savedInstanceState.getString(USERTEXT, "")
        findViewById<EditText>(R.id.search_activity_edittext).setText(userInputText)
    }
}

// Функция для определения видимости кнопки очистки на основе содержимого текстового поля
private fun clearButtonVisibility(s: CharSequence?): Int {
    return if (s.isNullOrEmpty()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}