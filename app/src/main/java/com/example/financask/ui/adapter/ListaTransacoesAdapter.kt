package com.example.financask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.financask.R
import com.example.financask.ui.extension.formataParaDataBrasileira
import com.example.financask.ui.extension.formataParaReal
import com.example.financask.ui.extension.limitaEmAte
import com.example.financask.ui.model.Tipo
import com.example.financask.ui.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(
    private val transacoes: List<Transacao>,
    private val context: Context) : BaseAdapter() {

    private val limiteDaCategoria = 30

    override fun getCount(): Int {
        return transacoes.size
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]

    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context)
            .inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[position]

        adicionaCorIcone(transacao, viewCriada)


        viewCriada.transacao_valor.text = transacao.valor.formataParaReal()
        viewCriada.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDaCategoria)
        viewCriada.transacao_data.text= transacao.data.formataParaDataBrasileira()


        return viewCriada
    }

    private fun adicionaCorIcone(
        transacao: Transacao,
        viewCriada: View
    ) {
        var cor = 0
        var tipo = 0
        if (transacao.tipo == Tipo.Receita) {
            cor= ContextCompat.getColor(context,R.color.receita)
            tipo = R.drawable.icone_transacao_item_receita

        } else {
            cor=ContextCompat.getColor(context,R.color.despesa)
            tipo = R.drawable.icone_transacao_item_despesa
        }
        viewCriada.transacao_valor.setTextColor(cor)
        viewCriada.transacao_icone.setBackgroundResource(tipo)
    }


}