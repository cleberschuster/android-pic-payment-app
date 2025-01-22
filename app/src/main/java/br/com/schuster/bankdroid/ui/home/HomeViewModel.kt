package br.com.schuster.bankdroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schuster.bankdroid.data.State
import br.com.schuster.bankdroid.data.Status
import br.com.schuster.bankdroid.data.UiState
import br.com.schuster.bankdroid.data.UsuarioLogado
import br.com.schuster.bankdroid.repository.TransacaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TransacaoRepository) : ViewModel() {

    private val _transacaoState = MutableStateFlow(UiState())

    val transacaoState = _transacaoState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = _transacaoState.value
    )

    private val _saldoState = MutableLiveData<State<Double>>()
    val saldoState: LiveData<State<Double>> = _saldoState

    val login = UsuarioLogado.usuario.login

//    init {
//        val login = UsuarioLogado.usuario.login
//        obterSaldo(login)
//        obterHistorico(login)
//    }

    fun obterHistoricoTransacoes(login: String) {

        _transacaoState.update { it.copy(status = Status.LOADING) }

        viewModelScope.launch {
            repository.getTransacoes(login)
                .onEach { result ->
                    _transacaoState.update { currentState ->
                        currentState.copy(
                            status = Status.SUCCESS,
                            data = result,
                        )
                    }
                }.catch {
                    _transacaoState.update { currentState ->
                        currentState.copy(
                            status = Status.ERROR,
                            errorMessage = it.message
                        )
                    }
                }.collect()
        }
    }

    fun obterSaldo(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _saldoState.postValue(State.Loading())
            try {
                val novoSaldo = repository.getSaldo(login).saldo
                UsuarioLogado.setSaldo(novoSaldo)
                _saldoState.postValue(State.Success(novoSaldo))
            } catch (e: Exception) {
                _saldoState.postValue(State.Error(e))
            }
        }
    }

}