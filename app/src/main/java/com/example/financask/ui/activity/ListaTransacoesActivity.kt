package com.example.financask.ui.activity

import android.os.Bundle
import android.view.View
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
    private lateinit var viewDaActivity: View

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

            viewDaActivity = window.decorView

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
        AdicionaTransacaoDialog(viewDaActivity as ViewGroup, this)
            .chama(tipo, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    adiciona(transacao)
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }

    private fun adiciona(transacao: Transacao) {
        transacoes.add(transacao)
        atualizaTransacoes()

    }

    private fun atualizaTransacoes() {

        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewDaActivity,transacoes)
        resumoView.atualiza()
    }


    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes,this)
       with(lista_transacoes_listview){
           adapter = listaTransacoesAdapter
           setOnItemClickListener { _, _, position, _ ->
               val transacao = transacoes[position]
               chamaDialogDeAlteracao(transacao, position)
       }
        }
    }

    private fun chamaDialogDeAlteracao(
        transacao: Transacao,
        position: Int
    ) {
        AlteraTransacaoDialog(viewDaActivity as ViewGroup, this)
            .chama(transacao, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    altera(transacao, position)
                    atualizaTransacoes()
                }

            })
    }

    private fun altera(transacao: Transacao, position: Int) {
        transacoes[position] = transacao
    }
}