package com.example.financask.ui.model

import java.math.BigDecimal
import java.util.*

class Transacao (val valor: BigDecimal,
                 val categoria: String,
                 val tipo: Tipo,
                 val data: Calendar = Calendar.getInstance())
