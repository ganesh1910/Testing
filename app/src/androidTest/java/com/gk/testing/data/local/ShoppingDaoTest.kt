package com.gk.testing.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.gk.testing.getOrAwaitValue
import com.gk.testing.launchFragmentInHiltContainer
import com.gk.testing.ui.ShoppingFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()
        shoppingDao = database.shoppingDao()
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runTest {
        val shoppingItem = ShoppingItem(1, "Banana", 50, 5f, "")
        shoppingDao.insertShoppingItem(shoppingItem)
        val allShoppingItems = shoppingDao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val shoppingItem = ShoppingItem(1, "banana", 50, 5f, "")
        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.deleteShoppingItem(shoppingItem = shoppingItem)

        val allShoppingItem = shoppingDao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPrice() = runTest {
        val shoppingItem1 = ShoppingItem(1, "Banana", 2, 5f, "")
        val shoppingItem2 = ShoppingItem(2, "Apple", 3, 20f, "")
        val shoppingItem3 = ShoppingItem(3, "Mango", 5, 10f, "")

        shoppingDao.insertShoppingItem(shoppingItem1)
        shoppingDao.insertShoppingItem(shoppingItem2)
        shoppingDao.insertShoppingItem(shoppingItem3)

        val totalPrice = shoppingDao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(2 * 5 + 3 * 20 + 5 * 10)
    }
}