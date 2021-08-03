package com.example.financask.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import com.example.financask.ui.adapter.ResumoView
import com.example.financask.ui.extension.formataParaDataBrasileira
import com.example.financask.ui.model.Tipo
import com.example.financask.ui.model.Transacao
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity: AppCompatActivity() {

    private val transacoes : MutableList<Transacao> = mutableListOf()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

            configuraResumo()
            configuraLista()

        lista_transacoes_adiciona_receita
            .setOnClickListener {
                val view = window.decorView
                val viewCriada = LayoutInflater.from(this)
                    .inflate(R.layout.form_transacao, view as ViewGroup, false)
                val hoje = Calendar.getInstance()

                val ano = 2020
                val mes = 0
                val dia = 18

                viewCriada.form_transacao_data.setText(hoje.formataParaDataBrasileira())
                viewCriada.form_transacao_data.setOnClickListener {
                    DatePickerDialog(this,
                        { view, ano, mes, dia ->

                            val dataSelecionada = Calendar.getInstance()
                            dataSelecionada.set(ano, mes, dia)
                            viewCriada
                                .form_transacao_data.setText(dataSelecionada
                                    .formataParaDataBrasileira())

                        }, ano, mes, dia)
                        .show()
                }

                val adapter = ArrayAdapter.
                createFromResource(this,
                    R.array.categorias_de_receita,
                    android.R.layout.simple_spinner_dropdown_item)

                viewCriada.form_transacao_categoria.adapter = adapter

                AlertDialog.Builder(this)
                    .setTitle(R.string.adiciona_receita)
                    .setView(viewCriada)
                    .setPositiveButton("ADICIONAR"
                    ) { dialog, which ->
                        val valorEmTexto = viewCriada.form_transacao_valor.text.toString()
                        val dataEmTexto = viewCriada.form_transacao_data.text.toString()
                        val categoriaEmTexto =
                            viewCriada.form_transacao_categoria.selectedItem.toString()

                        val valor = try{
                            BigDecimal(valorEmTexto)
                            } catch (exception: NumberFormatException){
                            Toast.makeText(this,"Falha na conversão de valor", Toast.LENGTH_LONG).show()
                            BigDecimal.ZERO
                        }




                        val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
                        val dataConvertida : Date = formatoBrasileiro.parse(dataEmTexto)
                        val data = Calendar.getInstance()
                        data.time = dataConvertida

                        val transacaoCriada = Transacao(
                            tipo = Tipo.Receita,
                            valor = valor,
                            data = data,
                           categoria = categoriaEmTexto
                                                                     )

                        atualizaTransacoes(transacaoCriada)
                        lista_transacoes_adiciona_menu.close(true)

                    }
                    .setNegativeButton("CANCELAR", null)
                    .show()

            }


        }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view = window.decorView
        val resumoView = ResumoView(this, view,transacoes)
        resumoView.atualiza()
    }


    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }
}