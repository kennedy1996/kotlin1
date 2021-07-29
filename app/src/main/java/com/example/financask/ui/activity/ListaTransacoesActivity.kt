package com.example.financask.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import com.example.financask.ui.adapter.ResumoView
import com.example.financask.ui.model.Tipo
import com.example.financask.ui.model.Transacao
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.resumo_card.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity: AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

            configuraResumo(transacoes)

            configuraLista(transacoes)

    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val view = window.decorView
        val resumoView = ResumoView(view,transacoes)
        resumoView.adicionandoReceita()
        resumoView.adicionandoDespesa()
        resumoView.adicionaTotal()


    }


    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo() = listOf(
        Transacao(
            valor = BigDecimal(20.5),
            categoria = "comida",
            tipo = Tipo.Despesa,
        ), Transacao(
            valor =
            BigDecimal(100.0),
            categoria =
            "Economia",
            tipo = Tipo.Receita,
        ), Transacao(
            valor =
            BigDecimal(800.0),
            categoria =
            "Salario mensalita especialista asjkl hasduo12",
            tipo = Tipo.Receita,
        )
    )

}