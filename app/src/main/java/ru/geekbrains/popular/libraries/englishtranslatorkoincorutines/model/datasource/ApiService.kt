package ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.datasource

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.popular.libraries.englishtranslatorkoincorutines.model.data.DataModel

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<DataModel>>
}