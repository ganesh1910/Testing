package com.gk.testing.repositories

import androidx.lifecycle.LiveData
import com.gk.testing.data.local.ShoppingItem
import com.gk.testing.data.remote.responses.ImageResponse
import com.gk.testing.other.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}