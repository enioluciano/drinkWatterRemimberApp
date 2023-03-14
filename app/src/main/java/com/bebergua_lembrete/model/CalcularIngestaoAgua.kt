package com.bebergua_lembrete.model

class CalcularIngestaoAgua {
    private val mlJovem = 40.0

    private val mlAdulto = 35.0

    private val mlIdoso = 30.0

    private val mlMaisDe66 = 25.0

    private var resultadoMl = 0.0

    private var resultado_total_ml = 0.0

    fun calcularTotalMl(peso: Double, idade: Int){
        if(idade <= 17){
            resultadoMl = peso * mlJovem
            resultado_total_ml = resultadoMl
        }else if (idade <= 55){
            resultadoMl = peso * mlAdulto
            resultado_total_ml = resultadoMl
        }else if(idade <=65){
            resultadoMl = peso * mlIdoso
            resultado_total_ml = resultadoMl
        }else{
            resultadoMl = peso * mlMaisDe66
            resultado_total_ml = resultadoMl
        }

    }

    fun ResultadoMl(): Double{
        return resultado_total_ml
    }


}