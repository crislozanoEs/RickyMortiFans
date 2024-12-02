package com.crislozano.rickymortyfans.di

import com.crislozano.rickymortyfans.domain.usecase.GetCharactersUC
import com.crislozano.rickymortyfans.domain.usecase.GetSingleCharacterUC
import org.koin.dsl.module

val domainModule = module {
    factory { GetCharactersUC(get()) }
    factory { GetSingleCharacterUC(get()) }
}