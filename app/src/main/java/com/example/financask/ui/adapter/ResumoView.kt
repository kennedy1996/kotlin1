package com.example.financask.ui.adapter

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.financask.R
import com.example.financask.ui.extension.formataParaReal
import com.example.financask.ui.model.Resumo
import com.example.financask.ui.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val context: Context,
                 private val view: View,
                 transacoes: List<Transacao>){

    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)
    private val resumo : Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)

   private  fun adicionandoReceita() {
        val totalReceita = resumo.receita()
        with(view.resumo_card_receita){
            setTextColor(corReceita)
            text = totalReceita.formataParaReal()
        }
     }

     private fun adicionandoDespesa() {
        val totalDespesa = resumo.despesa()
         with(view.resumo_card_despesa){
             setTextColor(corDespesa)
             text = totalDespesa.formataParaReal()
         }
     }
    private fun adicionaTotal(){
        val total = resumo.total()
        val cor = corPor(total)
        with(view.resumo_card_total){
            setTextColor(cor)
            text = total.formataParaReal()
        }
    }

    private fun corPor(valor: BigDecimal): Int {
        if (valor >= BigDecimal.ZERO) {
            return corReceita
        } else {
            return corDespesa

        }
    }

    fun atualiza (){
        adicionandoDespesa()
        adicionandoReceita()
        adicionaTotal()

    }

}