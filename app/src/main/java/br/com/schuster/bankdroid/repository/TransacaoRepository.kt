package br.com.schuster.bankdroid.repository

import br.com.schuster.bankdroid.data.Transacao
import br.com.schuster.bankdroid.data.Usuario
import br.com.schuster.bankdroid.data.toModel
import br.com.schuster.bankdroid.services.ApiService

interface TransacaoRepository {

    suspend fun getSaldo(login: String): Usuario

    suspend fun getTransacoes(login: String): List<Transacao>
}

class TransacaoRepositoryImpl(
    private val apiService: ApiService,
) : TransacaoRepository {

    override suspend fun getSaldo(login: String): Usuario = apiService.getSaldo(login)

    override suspend fun getTransacoes(login: String): List<Transacao> {
        val transacoes = apiService.getTransacoes(login).content.toModel()
        return transacoes
    }

}
