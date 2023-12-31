package com.gk.testing.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gk.testing.data.local.ShoppingItem
import com.gk.testing.data.remote.responses.ImageResponse
import com.gk.testing.other.Constant
import com.gk.testing.other.Event
import com.gk.testing.other.Resource
import com.gk.testing.repositories.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    val shoppingItems = repository.observeAllShoppingItems()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        _insertShoppingItemStatus

    fun setCurImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem = shoppingItem)
    }

    fun insertShoppingItemIntoDB(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem = shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The field must not be empty", null)))
            return
        }
        if (name.length > Constant.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The name of the item must not exceed " +
                    "${Constant.MAX_NAME_LENGTH} characters",null)))
            return
        }
        if (priceString.length > Constant.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The price of item must not exceed" +
                    "${Constant.MAX_PRICE_LENGTH} Characters", null)))
            return
        }
        val amount = try {
            amountString.toInt()
        }catch (e: Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter a valid amount", null)))
            return
        }
        val shoppingItem = ShoppingItem(name = name, amount = amount, price = priceString.toFloat(), imageUrl = _curImageUrl.value?:"")
        insertShoppingItemIntoDB(shoppingItem = shoppingItem)
        setCurImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(imageQuery:String){
        if (imageQuery.isEmpty()){
            return
        }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery = imageQuery)
            _images.value = Event(response)
        }
    }
}