package com.example.financask.ui.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import com.example.financask.ui.adapter.ResumoView
import com.example.financask.ui.delegate.TransacaoDelegate
import com.example.financask.ui.dialog.AdicionaTransacaoDialog
import com.example.financask.ui.dialog.AlteraTransacaoDialog
import com.example.financask.ui.model.Tipo
import com.example.financask.ui.model.Transacao
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity: AppCompatActivity() {

    private val transacoes : MutableList<Transacao> = mutableListOf()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

            configuraResumo()
            configuraLista()
            configuraFab()

        }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita
            .setOnClickListener {
                chamaDialogDeAdicao(Tipo.Receita)
            }
        lista_transacoes_adiciona_despesa
            .setOnClickListener {
                chamaDialogDeAdicao(Tipo.Despesa)
            }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
            .chama(tipo, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    transacoes.add(transacao)
                    atualizaTransacoes()
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }

    private fun atualizaTransacoes() {

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
        lista_transacoes_listview.setOnItemClickListener { parent, view, position, id ->
            val transacao = transacoes[position]
            AlteraTransacaoDialog(window.decorView as ViewGroup, this)
                .chama(transacao, object: TransacaoDelegate{
                    override fun delegate(transacao: Transacao) {
                        transacoes[position] = transacao
                        atualizaTransacoes()
                    }

                })
        }
    }
}