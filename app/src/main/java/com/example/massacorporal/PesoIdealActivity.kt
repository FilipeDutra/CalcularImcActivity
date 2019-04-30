@file:Suppress("DEPRECATION")

package com.example.massacorporal


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_peso_ideal.*

class PesoIdealActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peso_ideal)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var objetoIntent: Intent=intent
        var peso = objetoIntent.getFloatExtra("key_peso", 0f)
        var altura = objetoIntent.getFloatExtra("key_altura", 0f)
        var genero = objetoIntent.getStringExtra("key_genero")

        //txtPesoIdeal.text = genero

        if(peso != 0f && altura != 0f){
            calcularPesoIdeal(peso, altura, genero)
        }else{
            txtResultadoSpinner.text = "Não foi identificado os valores para altura e peso"
        }

        //Chamar Spinner
        var atividadeEscolhida = ""
        //lista de atividades
        var alAtividade = arrayOf("Corrida", "Bicicleta", "Crossfit")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, alAtividade)

        spnAtividade.adapter = adapter
        spnAtividade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when (position) {
                    0 -> {
                        atividadeEscolhida = "Corrida"
                    }
                    1 -> {
                        atividadeEscolhida = "Bicicleta"
                    }
                    2 -> {
                        atividadeEscolhida = "Crossfit"
                    }
                }
            }
        }

        btnVerificar.setOnClickListener {
            if(atividadeEscolhida == "Corrida"){
                txtResultadoSpinner.text = "Uma caminhada de 60 min a 6,4km/h gasta 243 calorias. Uma caminhada de 60 min " +
                        "a 7,4km/h gasta 270 calorias. Uma corrida de 30 min a 9,6km/h gasta 270 calorias."
            }else if(atividadeEscolhida == "Bicicleta"){
                txtResultadoSpinner.text = "Uma pessoa de 70 quilos perde cerca de 270 calorias andando de bicicleta " +
                        "por meia hora entre 19 e 22 km/h, e cerca de 340 calorias em meia hora andando de bicicleta " +
                        "entre 22 e 25 km/h. Quanto maior seu peso, mais calorias você perde, e o mesmo vale para a " +
                        "intensidade do exercício"
            }else{
                txtResultadoSpinner.text = "A prática do CrossFit leva o organismo a queimar energia em forma de gordura. " +
                        "Estima-se que o gasto calórico é de 800 a 1500 calorias por treino, que dura em média 60 minutos e é " +
                        "realizado em partes."
            }

        }

    }

    private fun calcularPesoIdeal(peso: Float, altura: Float, genero: String){
        //var generoActivity  = genero
        //altura acima de 152,4
        val alt = altura - 152.4
        //condicional verificar se é homem ou mulher
        if(genero == "Homem"){//homem
            // Homens: Peso Ideal = 50 kg + 2,3 kg para cada 2,54cm acima dos 152,4 cm.

            val pesoCalculado = ((alt / 2.54)*2.30)+50.toFloat()
            val pesoIdeal : Float = pesoCalculado.toFloat()
            txtSexo.text = genero
            txtPesoIdeal.text = String.format("Seu peso ideal é %.2f kg", pesoIdeal)
        }else{
            //Mulheres: Peso Ideal = 45,5 kg + 2,3 kg para cada 2,54cm acima dos 152,4 cm.
            val pesoCalculado = ((alt / 2.54)*2.30)+45.5.toFloat()
            val pesoIdeal : Float = pesoCalculado.toFloat()
            txtSexo.text = genero
            txtPesoIdeal.text = String.format("Seu peso ideal é %.2f kg", pesoIdeal)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if(item?.itemId == android.R.id.home){
            // fecha a atividade
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }


}
