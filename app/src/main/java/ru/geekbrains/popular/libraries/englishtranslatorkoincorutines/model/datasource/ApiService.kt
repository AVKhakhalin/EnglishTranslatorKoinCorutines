package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel

interface ApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<DataModel>>
}