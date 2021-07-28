package com.example.financask.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import com.example.financask.ui.model.Transacao
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity: AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = listOf(Transacao(BigDecimal(20.5),
            "comida",
        Calendar.getInstance()),
            Transacao(BigDecimal(100.0),
            "Economia",
                Calendar.getInstance())
        )

        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, transacoes
        )

        lista_transacoes_listview.setAdapter(ListaTransacoesAdapter(transacoes, this))

    }

}