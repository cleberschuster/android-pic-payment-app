package br.com.schuster.bankdroid.ui.transacao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schuster.bankdroid.data.Transacao
import br.com.schuster.bankdroid.services.ApiService
import kotlinx.coroutines.launch

class TransacaoViewModel(private val apiService: ApiService) : ViewModel() {

    private val _transferencia = MutableLiveData<Transacao>()
    val transacao: LiveData<Transacao> = _transferencia
    val onError = MutableLiveData<String>()

    fun realizaTransferencia(transacao: Transacao) {
        viewModelScope.launch {
            try {
                val transferenciaRealziada = apiService.realizarTransacao(transacao)
                _transferencia.value = transferenciaRealziada
            } catch (e: Exception) {
                onError.value = "Não Foi possível fazer transferência"
            }
        }
    }
}