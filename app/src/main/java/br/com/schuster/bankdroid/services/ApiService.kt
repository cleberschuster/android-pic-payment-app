package br.com.schuster.bankdroid.services

import br.com.schuster.bankdroid.data.Login
import br.com.schuster.bankdroid.data.PageTransacao
import br.com.schuster.bankdroid.data.Token
import br.com.schuster.bankdroid.data.Transacao
import br.com.schuster.bankdroid.data.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @POST("/autenticacao")
    suspend fun autenticar(@Body login: Login): Token

    @GET("/usuarios/contatos")
    suspend fun getTodosUsuarios(@Query("login") login: String): List<Usuario>

    @GET("/usuarios/{login}")
    suspend fun getUsuario(@Path("login") login: String): Usuario

    @GET("/usuarios/{login}/saldo")
    suspend fun getSaldo(@Path("login") login: String): Usuario

    @POST("/transacoes")
    suspend fun realizarTransacao(@Body transacao: Transacao): Transacao

    @GET("/transacoes")
    suspend fun getTransacoes(@Query("login") login: String): PageTransacao

}