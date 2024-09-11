package br.com.schuster.bankdroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schuster.bankdroid.data.State
import br.com.schuster.bankdroid.data.Transacao
import br.com.schuster.bankdroid.data.UsuarioLogado
import br.com.schuster.bankdroid.repository.TransacaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TransacaoRepository) : ViewModel() {

    private val _transacaoState = MutableLiveData<State<List<Transacao>>>()
    val transacaoState: LiveData<State<List<Transacao>>> = _transacaoState

    private val _saldoState = MutableLiveData<State<Double>>()
    val saldoState: LiveData<State<Double>> = _saldoState

    val login = UsuarioLogado.usuario.login

//    init {
//        val login = UsuarioLogado.usuario.login
//        obterSaldo(login)
//        obterHistorico(login)
//    }

    fun obterHistoricoTransacoes(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _transacaoState.postValue(State.Loading())
            try {
                val historico = repository.getTransacoes(login)
                _transacaoState.postValue(State.Success(historico))
            } catch (e: Exception) {
                _transacaoState.postValue(State.Error(e))
            }
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