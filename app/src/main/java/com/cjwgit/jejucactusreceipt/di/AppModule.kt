package com.cjwgit.jejucactusreceipt.di

import com.cjwgit.jejucactusreceipt.domain.AuctionBasketVO
import com.cjwgit.jejucactusreceipt.domain.CactusBasketVO
import com.cjwgit.jejucactusreceipt.model.AuctionBasketModel
import com.cjwgit.jejucactusreceipt.model.AuctionProductModel
import com.cjwgit.jejucactusreceipt.model.CactusBasketModel
import com.cjwgit.jejucactusreceipt.model.CactusProductModel
import com.cjwgit.jejucactusreceipt.model.common.BasketModel
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
import org.koin.core.qualifier.named
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { CactusFragmentVM(get(named("CactusBasket"))) }
    viewModel { AuctionFragmentVM(get(named("AuctionBasket"))) }

    viewModel { SettingFragmentVM() }

    viewModel { EditCactusFragmentVM() }
    viewModel { EditAuctionFragmentVM() }

    viewModel { CactusPrintFormVM(get(named("CactusBasket"))) }
    viewModel { AuctionPrintFormVM(get(named("AuctionBasket"))) }
}

val modelModule = module {
    single<BasketModel<CactusBasketVO>>(named("CactusBasket")) { CactusBasketModel() }
    single<BasketModel<AuctionBasketVO>>(named("AuctionBasket")) { AuctionBasketModel() }

    single { CactusProductModel(get()) }
    single { AuctionProductModel(get()) }
}

val repositoryModule = module {
    single { SQLiteHelper(androidContext()) }

    single { CactusRepository(get()) }
}