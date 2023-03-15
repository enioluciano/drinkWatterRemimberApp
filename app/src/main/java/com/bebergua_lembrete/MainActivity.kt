package com.bebergua_lembrete

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bebergua_lembrete.databinding.ActivityMainBinding
import com.bebergua_lembrete.model.CalcularIngestaoAgua
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var edit_peso: EditText

    private lateinit var edit_idade: EditText

    private lateinit var bt_calcular : Button

    private  lateinit var txt_resultador_ml : TextView

    private  lateinit var ic_redefinir_dados : ImageView

    private lateinit var calcularIngestaoAgua: CalcularIngestaoAgua

    private var resultadoMl = 0.0

    private lateinit var bt_lembrete: Button

    private lateinit var  bt_alarme: Button

    private lateinit var txt_hora: TextView

    private lateinit var txt_minutos: TextView

    lateinit var timePickerDialog: TimePickerDialog

    lateinit var calendario: Calendar

    var horaAtual = 0

    var minutosAtuais = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

//        iniciatComponentes()
        calcularIngestaoAgua = CalcularIngestaoAgua()
        //val bt_calcular = binding.bitCalcular

        binding.bitCalcular.setOnClickListener{
            if(binding.editPeso.text.toString().isEmpty()){
                Toast.makeText(this,getString(R.string.toast_informe_peso), Toast.LENGTH_SHORT).show()
            }else  if(binding.editIdade.text.toString().isEmpty()){
                Toast.makeText(this,getString(R.string.toast_informe_idade), Toast.LENGTH_SHORT).show()
            }else{
                val peso = binding.editPeso.text.toString().toDouble();
//                val peso = edit_peso.text.toString().toDouble()
                val idade = binding.editIdade.text.toString().toInt()
//                val idade = edit_idade.text.toString().toInt()
                calcularIngestaoAgua.calcularTotalMl(peso,idade)
               resultadoMl= calcularIngestaoAgua.ResultadoMl()
                var formatar = NumberFormat.getInstance(Locale("pt", "BR"))
                formatar.isGroupingUsed = false

                binding.txtResultadoMl.text = formatar.format(resultadoMl)+" "+"ml"
            }
        }

        binding.icRedefinir.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_title)
            alertDialog.setMessage(R.string.dialog_message)
            alertDialog.setPositiveButton("ok", { dialogInterface, i ->
                binding.editPeso.setText("")
                binding.editIdade.setText("")
                binding.txtResultadoMl.text = ""
            })
            alertDialog.setNegativeButton("cancelar",{dialogInterface, i->

            })
            val dialog = alertDialog.create()
            dialog.show()
        }

        binding.btDefinirLembrete.setOnClickListener{
            calendario = Calendar.getInstance()
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
            minutosAtuais = calendario.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this,{
                timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                binding.txtHora.text = String.format("%02d", hourOfDay)
                binding.txtMinutos.text= String.format("%02d", minutes)

            },horaAtual,minutosAtuais,true)

            timePickerDialog.show()
        }

        binding.btDefinirAlarme.setOnClickListener {
            val txt_hora = binding.txtHora
            val txt_minutos = binding.txtMinutos
            if(!txt_hora.text.toString().isEmpty() && !txt_minutos.text.toString().isEmpty()){
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR,txt_hora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, txt_minutos.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarm_message))
                startActivity(intent)
                if(intent.resolveActivity(packageManager)!= null){

                }


            }
        }
    }

//    private fun iniciatComponentes (){
//        edit_peso = findViewById(R.id.edit_peso)
//        edit_idade = findViewById(R.id.edit_idade)
//        bt_calcular = findViewById(R.id.bit_calcular)
//        txt_resultador_ml= findViewById(R.id.txt_resultado_ml)
//        ic_redefinir_dados = findViewById(R.id.ic_redefinir)
//        bt_lembrete = findViewById(R.id.bt_definir_lembrete)
//        bt_alarme = findViewById(R.id.bt_definir_alarme)
//        txt_hora = findViewById(R.id.txt_hora)
//        txt_minutos = findViewById(R.id.txt_minutos)
//
//    }
}