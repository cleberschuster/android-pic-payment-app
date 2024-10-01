package br.com.schuster.bankdroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.schuster.bankdroid.data.Transacao
import br.com.schuster.bankdroid.databinding.ItemTransacaoBinding
import br.com.schuster.bankdroid.extension.formatarMoeda

class HomeAdapter(private val transacoes: List<Transacao> = listOf()) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransacaoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = transacoes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transacao = transacoes[position]
        holder.bind(transacao)
    }

    inner class ViewHolder(private val binding: ItemTransacaoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transacao: Transacao) {
            with(binding) {
                textViewOrigem.text = transacao.origem.nomeCompleto
                textViewDestino.text = transacao.destino.nomeCompleto
                textViewValor.text = transacao.valor.formatarMoeda()
                textViewData.text = transacao.dataHora
                textViewCirculo.text =
                    transacao.origem.nomeCompleto.first().uppercaseChar().toString()
            }

        }
    }
}