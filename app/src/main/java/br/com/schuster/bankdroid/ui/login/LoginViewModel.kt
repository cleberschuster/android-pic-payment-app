package br.com.schuster.bankdroid.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schuster.bankdroid.data.Login
import br.com.schuster.bankdroid.data.State
import br.com.schuster.bankdroid.data.Usuario
import br.com.schuster.bankdroid.data.UsuarioLogado
import br.com.schuster.bankdroid.services.ApiService
import kotlinx.coroutines.launch

class LoginViewModel(private val apiService: ApiService) : ViewModel() {

    val usuarioState = MutableLiveData<State<Usuario>>()

    fun login(login: Login) {
        usuarioState.value = State.Loading()
        viewModelScope.launch {
            try {
                val token = apiService.autenticar(login)
                UsuarioLogado.token = token
                val usuario = apiService.getUsuario(login.usuario)
                UsuarioLogado.usuario = usuario
                usuarioState.value = State.Success(usuario)
            } catch (e: Exception) {
                usuarioState.value = State.Error(e)
            }
        }
    }

}