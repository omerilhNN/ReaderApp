package com.omrilhn.readerapp.di

import android.app.Application
import coil.ImageLoader
import com.omrilhn.readerapp.network.BooksApi
import com.omrilhn.readerapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader {
        return ImageLoader.Builder(app)
            .crossfade(true)
            .components{
                //TODO: Implementing SvgDecoder problem here
            }
            .build()
    }
    @Provides
    @Singleton
    fun provideBookApi():BooksApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

}