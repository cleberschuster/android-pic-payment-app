package br.com.schuster.bankdroid.ui.pagar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schuster.bankdroid.data.Usuario
import br.com.schuster.bankdroid.data.UsuarioLogado
import br.com.schuster.bankdroid.services.ApiService
import kotlinx.coroutines.launch

class PagarViewModel(private val apiService: ApiService) : ViewModel() {

    private val _contatos = MutableLiveData<List<Usuario>>()
    val contatos: LiveData<List<Usuario>> = _contatos
    val onLoading = MutableLiveData<Boolean>()
    val onError = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            onLoading.value = true
            try {
                val usuarios = apiService.getTodosUsuarios(UsuarioLogado.usuario.login)
                _contatos.value = usuarios
            } catch (e: Exception) {
                onError.value = e.message
            }
            onLoading.value = false
        }
    }

}