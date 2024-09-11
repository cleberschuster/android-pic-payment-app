package br.com.schuster.bankdroid.data

import java.util.UUID

data class Transacao(
    val codigo: String = "",
    val origem: Usuario = Usuario(),
    val destino: Usuario = Usuario(),
    val dataHora: String = "",
    val valor: Double = 0.0,
) {
    companion object {
        fun gerarHash(): String = UUID.randomUUID().toString()
    }
}
