package com.gk.testing.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gk.testing.R
import com.gk.testing.data.local.ShoppingDao
import com.gk.testing.data.local.ShoppingItemDatabase
import com.gk.testing.data.remote.PixabayAPI
import com.gk.testing.other.Constant.BASE_URL
import com.gk.testing.other.Constant.DATABASE_NAME
import com.gk.testing.repositories.DefaultShoppingRepository
import com.gk.testing.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context = context,
        klass = ShoppingItemDatabase::class.java,
        name = DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayAPI() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PixabayAPI::class.java)

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
     ) = Glide.with(context).setDefaultRequestOptions(
         RequestOptions()
             .placeholder(R.drawable.ic_image)
             .error(R.drawable.ic_image)
     )
}