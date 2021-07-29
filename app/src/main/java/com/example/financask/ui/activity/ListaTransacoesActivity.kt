package com.example.financask.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import com.example.financask.ui.model.Tipo
import com.example.financask.ui.model.Transacao
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity: AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = listOf(Transacao(
                valor =BigDecimal(20.5),
                categoria ="comida",
                tipo = Tipo.Despesa,
            ), Transacao(
                valor =
                BigDecimal(100.0),
                categoria =
                "Economia",
                tipo = Tipo.Receita,
            )
            , Transacao(
                valor =
                BigDecimal(800.0),
                categoria =
                "Salario mensalita especialista asjkl hasduo12",
                tipo = Tipo.Receita,
            )
        )

        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, transacoes
        )

            lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)

    }

}