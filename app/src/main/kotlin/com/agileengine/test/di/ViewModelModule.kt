package com.agileengine.test.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.agileengine.test.features.repos.ReposViewModel
import com.agileengine.test.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ReposViewModel::class)
    abstract fun bindRepoViewModel(viewModel: ReposViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}