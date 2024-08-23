package com.chillarcards.dimasys.di.module

import com.chillarcards.dimasys.data.viewModel.*
import com.chillarcards.dimasys.ui.others.merchant.AddMerchantViewModel
import com.chillarcards.dimasys.ui.others.merchant.CashReceiveViewModel
import com.chillarcards.dimasys.ui.others.merchant.MerchantViewModel
import com.chillarcards.dimasys.ui.others.merchant.TransactionHistoryViewModel
import com.chillarcards.dimasys.ui.others.money_collected.MoneyCollectedFragment
import com.chillarcards.dimasys.ui.others.money_collected.MoneyCollectedViewModel
import com.chillarcards.dimasys.ui.others.recent_products.RecentProductsViewModel
import com.chillarcards.dimasys.ui.others.recent_transactions.RecentTransactionsViewModel
import com.chillarcards.dimasys.ui.others.total_billings.TotalBillingViewModel
import com.chillarcards.dimasys.ui.others.total_payment_collected.TotalPaymentCollectedViewModel
import com.chillarcards.dimasys.ui.sales.SalesTeamMemberViewModel
import com.chillarcards.dimasys.ui.subdistributer.SubDistributerViewModel
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
    single {
        SubDistributerViewModel(get(),get())
    }
    single {
        SalesTeamMemberViewModel(get(),get())
    }

    single {
        TotalPaymentCollectedViewModel(get(),get())
    }
    single {
        MerchantViewModel(get(),get())
    }
    single {
        AddMerchantViewModel(get(),get())
    }
    single {
        CashReceiveViewModel(get(),get())
    }

    single {
        TransactionHistoryViewModel(get(),get())
    }
}