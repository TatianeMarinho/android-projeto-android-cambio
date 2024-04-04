package com.betrybe.currencyview.ui.views.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.betrybe.currencyview.R
import com.betrybe.currencyview.data.models.CurrencySymbolResponse
import com.betrybe.currencyview.common.ApiIdlingResource
import com.betrybe.currencyview.data.api.ApiService
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    val autoCompleteTextView by lazy {
        findViewById<AutoCompleteTextView>(R.id.currency_selection_input_layout)
    }

    val loadCurrencyState by lazy {
        findViewById<MaterialTextView>(R.id.load_currency_state)
    }

    val selectedCurrencyState by lazy {
        findViewById<MaterialTextView>(R.id.select_currency_state)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apilayer.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                ApiIdlingResource.increment()

                // chamada para a API
                val response: CurrencySymbolResponse = apiService.getSymbols()

                if (response.success) {
                    val adapter = ArrayAdapter(
                        baseContext,
                        android.R.layout.simple_list_item_1,
                        response.symbols.keys.toList()
                    )
                    withContext(Dispatchers.Main) {
                        autoCompleteTextView.setAdapter(adapter)
                        loadCurrencyState.visibility = View.GONE
                        selectedCurrencyState.visibility = View.VISIBLE
                    }
                }

                ApiIdlingResource.decrement()
            } catch (e: HttpException) {
                ApiIdlingResource.decrement()
                Log.e("API_ERROR", "Erro HTTP: ${e.message}")
            } catch (e: IOException) {
                ApiIdlingResource.decrement()
                Log.e("NETWORK_ERROR", "Erro de E/S: ${e.message}")
            }
        }
    }
}
