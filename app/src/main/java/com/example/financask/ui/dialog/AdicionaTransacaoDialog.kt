package com.example.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.financask.R
import com.example.financask.ui.delegate.TransacaoDelegate
import com.example.financask.ui.extension.converteParaCalendar
import com.example.financask.ui.extension.formataParaDataBrasileira
import com.example.financask.ui.model.Tipo
import com.example.financask.ui.model.Transacao
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class AdicionaTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {
    private val viewCriada = criaLayout()

    fun configuraDialog(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {


        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)

    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        val titulo = if(tipo == Tipo.Receita){
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }
        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(
                "ADICIONAR"
            ) { _, _ ->
                val valorEmTexto = viewCriada.form_transacao_valor.text.toString()
                val dataEmTexto = viewCriada.form_transacao_data.text.toString()
                val categoriaEmTexto =
                    viewCriada.form_transacao_categoria.selectedItem.toString()

                val valor = converteCampoValor(valorEmTexto)
               val data= dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )

                transacaoDelegate.delegate(transacaoCriada)

           }
            .setNegativeButton("CANCELAR", null)
            .show()
    }


private fun converteCampoValor(valorEmTexto: String): BigDecimal{
    return  try {
        BigDecimal(valorEmTexto)
    } catch (exception: NumberFormatException) {
        Toast.makeText(context, "Falha na conversão de valor", Toast.LENGTH_LONG).show()
        BigDecimal.ZERO
    }
}

    private fun configuraCampoCategoria(tipo: Tipo) {
        val categorias = if(tipo ==Tipo.Receita){
            R.array.categorias_de_receita
            } else {R.array.categorias_de_despesa}
        val adapter = ArrayAdapter.createFromResource(
            context,
            categorias,
            android.R.layout.simple_spinner_dropdown_item
        )

        viewCriada.form_transacao_categoria.adapter = adapter
    }

    private fun criaLayout(): View {

        return LayoutInflater.from(context)
            .inflate(R.layout.form_transacao, viewGroup, false)

    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()


        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        viewCriada.form_transacao_data.setText(hoje.formataParaDataBrasileira())
        viewCriada.form_transacao_data.setOnClickListener {
            DatePickerDialog(
                context,
                { _, ano, mes, dia ->

                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    viewCriada
                        .form_transacao_data.setText(
                            dataSelecionada
                                .formataParaDataBrasileira()
                        )

                }, ano, mes, dia
            )
                .show()
        }
    }

}