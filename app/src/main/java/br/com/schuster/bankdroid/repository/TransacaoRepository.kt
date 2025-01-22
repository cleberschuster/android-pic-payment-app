package br.com.schuster.bankdroid.repository

import br.com.schuster.bankdroid.data.Transacao
import br.com.schuster.bankdroid.data.Usuario
import br.com.schuster.bankdroid.data.toModel
import br.com.schuster.bankdroid.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface TransacaoRepository {

    suspend fun getSaldo(login: String): Usuario

    suspend fun getTransacoes(login: String): Flow<List<Transacao>>
}

class TransacaoRepositoryImpl(
    private val apiService: ApiService,
) : TransacaoRepository {

    override suspend fun getSaldo(login: String): Usuario = apiService.getSaldo(login)

    override suspend fun getTransacoes(login: String): Flow<List<Transacao>> = flow {

        val response = apiService.getTransacoes(login).content.toModel()
        emit(response)

    }.flowOn(Dispatchers.IO)
}
