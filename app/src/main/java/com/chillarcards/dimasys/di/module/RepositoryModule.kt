package com.chillarcards.dimasys.di.module

import com.chillarcards.dimasys.data.api.ApiHelper
import com.chillarcards.dimasys.data.api.ApiHelperImpl
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.data.repository.InnerRepository
import org.koin.dsl.module

/**
 * @Author: Sherin Jaison
 * @Date: 25-06-2024
 * Chillar
 */
val repoModule = module {
    single {
        AuthRepository(get())
    }
    single {
        InnerRepository(get())
    }
    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }
}