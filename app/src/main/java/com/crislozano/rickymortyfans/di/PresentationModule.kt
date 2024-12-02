package com.crislozano.rickymortyfans.di

import com.crislozano.rickymortyfans.presentation.details.DetailsFragmentVM
import com.crislozano.rickymortyfans.presentation.list.ListFragmentVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { DetailsFragmentVM(get()) }
    viewModel { ListFragmentVM(get()) }
}