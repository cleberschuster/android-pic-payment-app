package br.com.schuster.bankdroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ComponentesViewModel : ViewModel() {

    var temComponentes = Componentes()
        set(value) {
            field = value
            _componentes.value = value
        }

    private val _componentes = MutableLiveData<Componentes>().also {
        it.value = temComponentes
    }
    val componentes: LiveData<Componentes> = _componentes

}

data class Componentes(
    val bottomNavigation: Boolean = false
)