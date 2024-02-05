package com.cjwgit.jejucactusreceipt.di

import com.cjwgit.jejucactusreceipt.model.TestModel
import com.cjwgit.jejucactusreceipt.ui.viewmodel.AuctionFragmentVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.EditAuctionFragmentVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.EditCactusFragmentVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.MainFragmentVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.SettingFragmentVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.CactusAuctionPrintFormVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.CactusPrintFormVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainFragmentVM(get()) }
    viewModel { AuctionFragmentVM() }
    viewModel { SettingFragmentVM() }
    viewModel { EditCactusFragmentVM() }
    viewModel { EditAuctionFragmentVM() }
    viewModel { CactusPrintFormVM() }
    viewModel { CactusAuctionPrintFormVM() }
}


val appModule = module {
    single {
        TestModel()
    }

}