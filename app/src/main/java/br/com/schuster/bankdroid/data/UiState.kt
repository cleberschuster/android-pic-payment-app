package br.com.schuster.bankdroid.data


data class UiState(
    val status: Status = Status.LOADING,
    val data: List<Transacao>? = null,
    val errorMessage: String ? = null,
)

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

