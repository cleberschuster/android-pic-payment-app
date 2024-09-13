package br.com.schuster.bankdroid.ui.ajuste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.schuster.bankdroid.Componentes
import br.com.schuster.bankdroid.ComponentesViewModel
import br.com.schuster.bankdroid.data.UsuarioLogado
import br.com.schuster.bankdroid.databinding.FragmentAjusteBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AjusteFragment : Fragment() {

    private var _binding: FragmentAjusteBinding? = null
    private val binding get() = _binding!!

    private val componentesViewModel: ComponentesViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAjusteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentesViewModel.temComponentes = Componentes(bottomNavigation = true)

        configuraBotaoSair()
        configuraDadosUsuario()
    }

    private fun configuraDadosUsuario() {
        UsuarioLogado.usuario.let { usuario ->
            binding.textViewLoginPrincipal.text = usuario.login
            binding.textViewNomeCompletoAjuste.text = usuario.nomeCompleto
            binding.textViewLogin.text = usuario.login
            binding.textViewEmail.text = usuario.email
            binding.textViewNumero.text = usuario.numeroTelefone
        }
    }

    private fun configuraBotaoSair() {
        binding.buttonSair.setOnClickListener {

        }
    }
}