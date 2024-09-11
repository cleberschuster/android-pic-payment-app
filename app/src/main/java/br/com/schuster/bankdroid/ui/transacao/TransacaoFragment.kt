package br.com.schuster.bankdroid.ui.transacao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.schuster.bankdroid.Componentes
import br.com.schuster.bankdroid.ComponentesViewModel
import br.com.schuster.bankdroid.data.Transacao
import br.com.schuster.bankdroid.data.Usuario
import br.com.schuster.bankdroid.data.UsuarioLogado
import br.com.schuster.bankdroid.databinding.FragmentTransferenciaBinding
import br.com.schuster.bankdroid.extension.formatar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class TransacaoFragment : Fragment() {

    private var _binding: FragmentTransferenciaBinding? = null
    private val binding get() = _binding!!

    private val componentesViewModel: ComponentesViewModel by sharedViewModel()
    private val transacaoViewModel: TransacaoViewModel by viewModel()
    private val argumentos by navArgs<TransacaoFragmentArgs>()
    private val usuario by lazy { argumentos.usuario }
    private val controlador by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferenciaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentesViewModel.temComponentes = Componentes(bottomNavigation = false)
        configuraDadosUsuario()
        configuraBotaoTransferir()
        observarTransferencia()
        observarErro()
    }

    private fun observarErro() {
        transacaoViewModel.onError.observe(viewLifecycleOwner) {
            it?.let { mensagem ->
                Toast.makeText(this.context, mensagem, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observarTransferencia() {
        transacaoViewModel.transacao.observe(viewLifecycleOwner) {
            val direcao =
                TransacaoFragmentDirections.actionNavigationTransferenciaToNavigationPagar()
            controlador.navigate(direcao)
        }
    }

    private fun configuraBotaoTransferir() {
        binding.buttonTransferir.setOnClickListener {
            val transferencia = criarTransferencia()
            transacaoViewModel.realizaTransferencia(transferencia)
        }
    }

    private fun criarTransferencia(): Transacao {
        val usuarioOrigem = UsuarioLogado.usuario
        val dataHora = Calendar.getInstance().formatar()
        val valor = getValor()
        return criarTransacao(usuarioOrigem, dataHora, valor)
    }

    private fun criarTransacao(
        usuarioOrigem: Usuario,
        dataHora: String,
        valor: Double
    ): Transacao {
        return Transacao(
            Transacao.gerarHash(),
            usuarioOrigem,
            usuario,
            dataHora,
            valor
        )
    }

    private fun getValor(): Double {
        val valor = binding.editTextValor.text.toString()
        return if (valor.isEmpty()) {
            0.0
        } else {
            valor.toDouble()
        }
    }

    private fun configuraDadosUsuario() {
        binding.textViewNomeTranferencia.text = usuario.login
        binding.textViewNomeCompletoTranferencia.text = usuario.nomeCompleto
    }
}