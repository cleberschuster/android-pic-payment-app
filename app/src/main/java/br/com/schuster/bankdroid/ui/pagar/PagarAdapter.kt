package br.com.schuster.bankdroid.ui.pagar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.schuster.bankdroid.data.Usuario
import br.com.schuster.bankdroid.databinding.ItemContatoBinding

class PagarAdapter(private val usuarios: List<Usuario>, val onClick: (Usuario) -> Unit) :
    RecyclerView.Adapter<PagarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContatoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = usuarios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.bind(usuario)
    }

    inner class ViewHolder(private val binding: ItemContatoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(usuario: Usuario) {
            with(itemView) {
                binding.textViewNomeContato.text = usuario.login
                binding.textViewNomeCompleto.text = usuario.nomeCompleto
                setOnClickListener {
                    onClick(usuario)
                }
            }
        }
    }
}