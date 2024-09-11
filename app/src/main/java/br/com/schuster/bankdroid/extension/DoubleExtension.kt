package br.com.schuster.bankdroid.extension

import java.text.NumberFormat
import java.util.Locale

fun Double?.formatarMoeda(): String = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this) ?: "R$ 0,00"
