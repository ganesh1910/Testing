package com.gk.testing.data.remote

import com.gk.testing.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("kay") apiKey: String = "25036276-49ba87238e6146d6f9e2f9459"
    ): Response<ImageResponse>
}