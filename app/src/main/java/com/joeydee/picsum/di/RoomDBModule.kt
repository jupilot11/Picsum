package com.joeydee.picsum.di

import android.content.Context
import androidx.room.Room
import com.joeydee.picsum.db.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomDBModule {

    @Provides
    fun providePersonDao(appDatabase: AppDataBase) = appDatabase.personDao()

    @Provides
    fun provideRemoteDao(appDatabase: AppDataBase) = appDatabase.remoteKeyDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "PicsumDB"
        ).build()
    }
}