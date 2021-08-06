package com.example.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.financask.R
import com.example.financask.ui.delegate.TransacaoDelegate
import com.example.financask.ui.extension.formataParaDataBrasileira
import com.example.financask.ui.model.Tipo
import com.example.financask.ui.model.Transacao

class AlteraTransacaoDialog(
   viewGroup: ViewGroup,
    private val context: Context): FormularioTransacaoDialog(context,viewGroup)
{
    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo
       super.chama(tipo, transacaoDelegate)
        inicializaCampos(transacao)
    }

    private fun inicializaCampos(transacao: Transacao) {
        val tipo = transacao.tipo
        InicializaCampoValor(transacao)
        InicializaCampoData(transacao)
        InicializaCampoCategoria(tipo, transacao)
    }

    private fun InicializaCampoCategoria(tipo: Tipo, transacao: Transacao) {
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun InicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaDataBrasileira())
    }

    private fun InicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

    override val tituloBotaoPositivo: String
        get() = "ALTERAR"

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.Receita) {
            return R.string.altera_receita
        }
        return R.string.altera_despesa
    }
}