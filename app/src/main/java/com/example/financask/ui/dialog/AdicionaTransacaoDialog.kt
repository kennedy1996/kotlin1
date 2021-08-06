package com.example.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.financask.R
import com.example.financask.ui.model.Tipo

class AdicionaTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) : FormularioTransacaoDialog(context,viewGroup) {
    override val tituloBotaoPositivo: String
        get() = "ADICIONAR"

    override fun tituloPor(tipo: Tipo): Int {

        if (tipo == Tipo.Receita) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }

}