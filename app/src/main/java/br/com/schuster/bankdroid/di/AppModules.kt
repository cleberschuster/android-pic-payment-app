package br.com.schuster.bankdroid.di

import br.com.schuster.bankdroid.ComponentesViewModel
import br.com.schuster.bankdroid.repository.TransacaoRepository
import br.com.schuster.bankdroid.repository.TransacaoRepositoryImpl
import br.com.schuster.bankdroid.services.ApiService
import br.com.schuster.bankdroid.services.RetrofitService
import br.com.schuster.bankdroid.ui.home.HomeViewModel
import br.com.schuster.bankdroid.ui.login.LoginViewModel
import br.com.schuster.bankdroid.ui.pagar.PagarViewModel
import br.com.schuster.bankdroid.ui.transacao.TransacaoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ComponentesViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { PagarViewModel(get()) }
    viewModel { TransacaoViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}

val serviceModule = module {
    single { RetrofitService.create<ApiService>() }
}

val repositoryModule = module {
    single<TransacaoRepository> { TransacaoRepositoryImpl(get()) }
}
