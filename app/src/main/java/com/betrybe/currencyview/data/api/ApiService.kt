package com.betrybe.currencyview.data.api

import com.betrybe.currencyview.data.models.CurrencySymbolResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("apikey: gKSrABlHD03DgJxz5bn3CKCC0XK4gY01") // chave de acesso a api
    @GET("exchangerates_data/symbols") // indica que o retrofit faz uma req get para o endopoint /symbols
    suspend fun getSymbols(): CurrencySymbolResponse
    // o metodo getSymbols da interface pode ser chamado por uma corroutine e retorna um objeto do tipo currencySymbolResponse
}