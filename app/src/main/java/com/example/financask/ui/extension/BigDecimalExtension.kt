package com.example.financask.ui.extension

import java.math.BigDecimal
import java.util.Locale


fun BigDecimal.formataParaReal() : String {
    val formatoBrasileiro = java.text.DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))
    return formatoBrasileiro.format(this)
}