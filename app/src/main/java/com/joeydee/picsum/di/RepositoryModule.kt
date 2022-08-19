package com.joeydee.picsum.di

import com.joeydee.picsum.repository.HomeRepository
import com.joeydee.picsum.repository.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindHomeRepository(repo: HomeRepositoryImpl): HomeRepository

}
