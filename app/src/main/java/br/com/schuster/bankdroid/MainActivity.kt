package br.com.schuster.bankdroid

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import br.com.schuster.bankdroid.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val controlador by lazy {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    private val componentesViewModel: ComponentesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        componentesViewModel.componentes.observe(this) {
            it?.let { temComponentes ->
                if (temComponentes.bottomNavigation) {
                    navView.visibility = VISIBLE
                } else {
                    navView.visibility = GONE
                }
            }
        }

        navView.setupWithNavController(controlador)
    }
}