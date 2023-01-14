package com.codeplayon.framework.mvvm.di.module

import com.codeplayon.framework.mvvm.data.repository.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        MainRepository(get())
    }
}