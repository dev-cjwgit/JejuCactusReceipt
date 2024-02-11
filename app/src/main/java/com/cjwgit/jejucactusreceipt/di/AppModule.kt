package com.cjwgit.jejucactusreceipt.di

import com.cjwgit.jejucactusreceipt.model.AuctionBasketModel
import com.cjwgit.jejucactusreceipt.model.AuctionProductModel
import com.cjwgit.jejucactusreceipt.model.CactusBasketModel
import com.cjwgit.jejucactusreceipt.model.CactusProductModel
import com.cjwgit.jejucactusreceipt.repository.AuctionRepository
import com.cjwgit.jejucactusreceipt.repository.CactusRepository
import com.cjwgit.jejucactusreceipt.ui.viewmodel.AuctionFragmentVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.CactusFragmentVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.EditAuctionFragmentVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.EditCactusFragmentVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.SettingFragmentVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.AuctionPrintFormVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.CactusPrintFormVM
import com.cjwgit.jejucactusreceipt.utils.SQLiteHelper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { CactusFragmentVM(get(), get()) }
    viewModel { AuctionFragmentVM(get(), get()) }

    viewModel { SettingFragmentVM() }

    viewModel { EditCactusFragmentVM(get()) }
    viewModel { EditAuctionFragmentVM(get()) }

    viewModel { CactusPrintFormVM(get()) }
    viewModel { AuctionPrintFormVM(get()) }
}

val modelModule = module {
    single { CactusBasketModel() }
    single { AuctionBasketModel() }

    single { CactusProductModel(get()) }
    single { AuctionProductModel(get()) }
}

val repositoryModule = module {
    single { SQLiteHelper(androidContext()) }

    single { CactusRepository(get()) }
    single { AuctionRepository(get()) }
}