package com.example.financask.ui.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaDataBrasileira():String{
    val formatoBrasileiro = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBrasileiro)
    return format.format(this.time)
}
