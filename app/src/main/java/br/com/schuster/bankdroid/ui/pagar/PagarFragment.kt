package br.com.schuster.bankdroid.ui.pagar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.schuster.bankdroid.Componentes
import br.com.schuster.bankdroid.ComponentesViewModel
import br.com.schuster.bankdroid.data.Usuario
import br.com.schuster.bankdroid.databinding.FragmentPagarBinding
import br.com.schuster.bankdroid.extension.desaparecer
import br.com.schuster.bankdroid.extension.mostrar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PagarFragment : Fragment() {

    private var _binding: FragmentPagarBinding? = null
    private val binding get() = _binding!!

    private val componentesViewModel: ComponentesViewModel by sharedViewModel()
    private val pagarViewModel: PagarViewModel by viewModel()
    private val controlador by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagarBinding.inflate(inflater, container, false)
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

        observarContatos()
        observarLoading()
    }

    private fun observarLoading() {
        pagarViewModel.onLoading.observe(viewLifecycleOwner) { onLoading ->
            if (onLoading) {
                binding.progressBarPagar.mostrar()
                binding.recyclerViewPagar.desaparecer()
            } else {
                binding.progressBarPagar.desaparecer()
                binding.recyclerViewPagar.mostrar()
            }
        }
    }

    private fun observarContatos() {
        pagarViewModel.contatos.observe(viewLifecycleOwner) {
            configuraRecyclerView(it)
        }
    }

    private fun configuraRecyclerView(usuarios: List<Usuario>) {
        binding.recyclerViewPagar.adapter = PagarAdapter(usuarios) {
            val direcao = PagarFragmentDirections.actionNavigationPagarToNavigationTransferencia(it)
            controlador.navigate(direcao)
        }
    }
}