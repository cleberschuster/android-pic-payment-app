package br.com.schuster.bankdroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.schuster.bankdroid.Componentes
import br.com.schuster.bankdroid.ComponentesViewModel
import br.com.schuster.bankdroid.data.State
import br.com.schuster.bankdroid.data.Transacao
import br.com.schuster.bankdroid.data.UsuarioLogado
import br.com.schuster.bankdroid.databinding.FragmentHomeBinding
import br.com.schuster.bankdroid.extension.desaparecer
import br.com.schuster.bankdroid.extension.esconder
import br.com.schuster.bankdroid.extension.formatarMoeda
import br.com.schuster.bankdroid.extension.mostrar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val componentesViewModel: ComponentesViewModel by sharedViewModel()
    private val homeViewModel: HomeViewModel by viewModel()
    private val controlador by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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

        if (UsuarioLogado.isNaoLogado()) {
            val direcao = HomeFragmentDirections.actionGlobalLoginFragment()
            controlador.navigate(direcao)
            return
        }
        observarEstadoSaldo()
        observarEstadoTransacoes()
    }

    private fun observarEstadoTransacoes() {
        homeViewModel.obterHistoricoTransacoes(homeViewModel.login)
        homeViewModel.transacaoState.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    mostrarProgresTransacao()
                }

                is State.Success -> {
                    esconderProgresTransacao()
                    configuraRecyclerView(it.data)
                }

                is State.Error -> {
                    esconderProgresTransacao()
                    configuraRecyclerView(mutableListOf())
                    Toast.makeText(this.context, it.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observarEstadoSaldo() {
        homeViewModel.obterSaldo(homeViewModel.login)
        homeViewModel.saldoState.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    mostrarProgresSaldo()
                }

                is State.Success -> {
                    esconderProgresSaldo()
                    binding.textViewSaldo.text = it.data.formatarMoeda()
                }

                is State.Error -> {
                    esconderProgresSaldo()
                    Toast.makeText(this.context, it.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun esconderProgresSaldo() {
        binding.progressBarSaldo.desaparecer()
        binding.textViewSaldo.mostrar()
        binding.textViewLabelSaldo.mostrar()
    }

    private fun mostrarProgresSaldo() {
        binding.progressBarSaldo.mostrar()
        binding.textViewSaldo.esconder()
        binding.textViewLabelSaldo.esconder()
    }

    private fun mostrarProgresTransacao() {
        binding.progressBarTransferencia.mostrar()
        binding.recyclerViewHome.desaparecer()
    }

    private fun esconderProgresTransacao() {
        binding.progressBarTransferencia.desaparecer()
        binding.recyclerViewHome.mostrar()
    }

    private fun configuraRecyclerView(transferencais: List<Transacao>) {
        binding.recyclerViewHome.adapter = HomeAdapter(transferencais)
    }

}