package com.gk.testing.di

import android.content.Context
import androidx.room.Room
import com.gk.testing.data.local.ShoppingDao
import com.gk.testing.data.local.ShoppingItemDatabase
import com.gk.testing.data.remote.PixabayAPI
import com.gk.testing.repositories.DefaultShoppingRepository
import com.gk.testing.repositories.ShoppingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context = context, klass = ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()


    @Provides
    @Named("test_repo")
    fun provideShoppingRepository(
         dao: ShoppingDao,
         pixabayAPI: PixabayAPI
    ) = DefaultShoppingRepository(dao,pixabayAPI) as ShoppingRepository

    @Provides
    @Named("test_dao")
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Provides
    @Named("test_pixa")
    fun providePixabayAPI()=Retrofit.Builder()
        .baseUrl("")
        .build()
}