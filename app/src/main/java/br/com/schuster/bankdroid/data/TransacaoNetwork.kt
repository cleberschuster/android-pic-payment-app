package br.com.schuster.bankdroid.data

data class TransacaoNetwork(
    val codigo: String?,
    val origem: Usuario?,
    val destino: Usuario?,
    val dataHora: String?,
    val valor: Double?,
)