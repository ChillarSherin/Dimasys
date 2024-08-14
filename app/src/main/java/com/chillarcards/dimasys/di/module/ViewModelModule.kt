package com.chillarcards.dimasys.di.module

import com.chillarcards.dimasys.data.viewModel.*
import com.chillarcards.dimasys.ui.others.money_collected.MoneyCollectedFragment
import com.chillarcards.dimasys.ui.others.money_collected.MoneyCollectedViewModel
import com.chillarcards.dimasys.ui.others.recent_products.RecentProductsViewModel
import com.chillarcards.dimasys.ui.others.recent_transactions.RecentTransactionsViewModel
import com.chillarcards.dimasys.ui.others.total_billings.TotalBillingViewModel
import org.koin.dsl.module


/**
 * @Author: Sherin Jaison
 * @Date: 25-06-2024
 * Chillar
 */
val viewModelModule = module {
    single {
        LoginViewModel(get(), get())
    }
    single {
        LandingViewModel(get(), get())
    }
    single {
        RecentTransactionsViewModel(get(), get())
    }

    single {
        RecentProductsViewModel(get(), get())
    }

    single {
        TotalBillingViewModel(get(), get())
    }

    single {
        MoneyCollectedViewModel(get(), get())
    }
}