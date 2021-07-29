package com.example.financask.ui.adapter

import android.view.View
import com.example.financask.ui.extension.formataParaReal
import com.example.financask.ui.model.Resumo
import com.example.financask.ui.model.Tipo
import com.example.financask.ui.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val view: View,
                 transacoes: List<Transacao>){

    private val resumo : Resumo = Resumo(transacoes)

    fun adicionandoReceita() {
        val totalReceita = resumo.receita()
        view.resumo_card_receita.text = totalReceita.formataParaReal()
    }
    fun adicionandoDespesa() {
        val totalDespesa = resumo.despesa()
        view.resumo_card_despesa.text = totalDespesa.formataParaReal()
    }
    fun adicionaTotal(){
        val total = resumo.total()
        view.resumo_card_total.text = total.formataParaReal()

    }

}