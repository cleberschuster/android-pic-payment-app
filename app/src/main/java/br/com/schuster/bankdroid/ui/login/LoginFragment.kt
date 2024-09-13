package br.com.schuster.bankdroid.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.schuster.bankdroid.Componentes
import br.com.schuster.bankdroid.ComponentesViewModel
import br.com.schuster.bankdroid.data.Login
import br.com.schuster.bankdroid.data.State
import br.com.schuster.bankdroid.databinding.FragmentLoginBinding
import br.com.schuster.bankdroid.extension.esconder
import br.com.schuster.bankdroid.extension.getString
import br.com.schuster.bankdroid.extension.mostrar
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val componentesViewModel: ComponentesViewModel by activityViewModel()
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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
        configurarBotaoLogin()
        observarEstadosUsuario()
    }

    private fun observarEstadosUsuario() {
        viewModel.usuarioState.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    binding.progressBar.mostrar()
                }

                is State.Success -> {
                    binding.progressBar.esconder()
                    val direcao = LoginFragmentDirections.actionLoginFragmentToNavigationHome()
                    findNavController().navigate(direcao)
                }

                is State.Error -> {
                    binding.progressBar.esconder()
                    Toast.makeText(
                        requireContext(),
                        "Falha ao autenticar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun configurarBotaoLogin() {
        binding.buttonLogin.setOnClickListener {
            val usuario = binding.textInputLayoutUsuario.getString()
            val senha = binding.textInputLayoutSenha.getString()
            val login = Login(usuario, senha)
            viewModel.login(login)
        }
    }

}