package br.com.schuster.bankdroid.data

fun TransacaoNetwork.toModel(): Transacao = Transacao(
    codigo = codigo.orEmpty(),
    origem = origem ?: Usuario(),
    destino = destino ?: Usuario(),
    dataHora = dataHora.orEmpty(),
    valor = valor ?: 0.0,
)

fun List<TransacaoNetwork>.toModel() = this.map { it.toModel() }
