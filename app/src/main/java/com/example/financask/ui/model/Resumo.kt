package com.example.financask.ui.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {
    val receita get() = somaPor(Tipo.Receita)
//        var totalReceita = BigDecimal.ZERO
//
//        for (transacao in transacoes) {
//            if (transacao.tipo == Tipo.Receita) {
//                totalReceita = totalReceita.plus(transacao.valor)
//            }
//        } MANEIRA DE FAZER O MESMO QUE BAIXO SEM PROJUDICAR


    val despesa get() = somaPor(Tipo.Despesa)

    val total get() = receita.subtract(despesa)

    private fun somaPor(tipo : Tipo): BigDecimal{
        val somaDeTransacoesPeloTipo = transacoes
            .filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble() }
        return BigDecimal(somaDeTransacoesPeloTipo)
    }


}