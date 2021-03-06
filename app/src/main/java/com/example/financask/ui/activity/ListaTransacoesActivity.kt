package com.example.financask.ui.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financask.R
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import com.example.financask.ui.adapter.ResumoView
import com.example.financask.ui.dao.TransacaoDAO
import com.example.financask.ui.dialog.AdicionaTransacaoDialog
import com.example.financask.ui.dialog.AlteraTransacaoDialog
import com.example.financask.ui.model.Tipo
import com.example.financask.ui.model.Transacao
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import androidx.appcompat.app.AlertDialog as AlertDialog1

class ListaTransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDAO()
    private val transacoes : List<Transacao> = dao.transacoes
    private val viewDaActivity by lazy { window.decorView }
    private val viewGroupDaActivity by lazy { viewDaActivity as ViewGroup }

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
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
            .chama(tipo) { transcaoCriada ->
                adiciona(transcaoCriada)
                lista_transacoes_adiciona_menu.close(true)
            }
    }

    private fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacoes()

    }

    private fun atualizaTransacoes() {

        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewDaActivity, transacoes)
        resumoView.atualiza()
    }


    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)
        with(lista_transacoes_listview) {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transacao = transacoes[position]
                chamaDialogDeAlteracao(transacao, position)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {


        val idDoMenu = item.itemId
        if(idDoMenu==1){
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position

            confirmacaoExclusao(posicaoDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun confirmacaoExclusao(posicaoDaTransacao: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Tem certeza que deseja deletar a transa????o ?")
            .setTitle("Confirma????o")
            .setPositiveButton("Prosseguir", DialogInterface.OnClickListener { dialog, id ->
                remove(posicaoDaTransacao)
            })
            .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            .show()
    }

    fun confirmaRemocao(item: MenuItem) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("R.string.dialog_fire_missiles")
                    .setTitle("titulo")
                     .show()
        }


    private fun remove(posicao: Int) {
        dao.remove(posicao)
        atualizaTransacoes()
    }

    private fun chamaDialogDeAlteracao(
        transacao: Transacao,
        position: Int
    ) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
            .chama(transacao) { transacaoAlterada ->
                altera(transacaoAlterada, position)

            }
    }

    private fun altera(transacao: Transacao, position: Int) {
        dao.altera(transacao, position)
        atualizaTransacoes()
    }
}