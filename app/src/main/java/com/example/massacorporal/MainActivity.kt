@file:Suppress("DEPRECATION")

package com.example.massacorporal

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var genero = ""
        //critério 1
        swGenero.setOnClickListener{
            if (swGenero.isChecked) {
                swGenero.setText("Homem")
                genero = "Homem"
            } else {
                swGenero.setText("Mulher")
                genero = "Mulher"
            }
        }
        var idade = 0
        skIdade.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                // quando alterar o valor do progresso
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    txIdade.text = "Idade $progress"
                    idade = progress
                }

                // quando clicar no seekbar
                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                // quando parar de clicar no seekbar
                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            }
        )

        var  peso: Float = 0f
        skPeso.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                // quando alterar o valor do progresso
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    txPeso.text = "Peso $progress kg"
                    peso = progress.toFloat()
                }

                // quando clicar no seekbar
                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                // quando parar de clicar no seekbar
                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            }
        )

        var altura: Float = 0f
        skAltura.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                // quando alterar o valor do progresso
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    txAltura.text = "Altura $progress"
                    altura = progress.toFloat()
                }

                // quando clicar no seekbar
                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                // quando parar de clicar no seekbar
                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            }
        )
        //validar preenchimento dos campos
        btnCalcular.setOnClickListener {
            if (idade > 0 && peso > 0 && altura > 0) {
                if (rbIntenso.isChecked || rbModerado.isChecked || rbSedentario.isChecked) {
                    calculo(peso.toFloat(), (altura.toFloat()/100))
                    //txtResultado.text = "passou"
                } else {
                    txtResultado.text = "Por favor selecione também sua grau de atividade física"
                }
            } else {
                txtResultado.text = "Você deve preencher todos os campos"
            }
        }
        //passar parâmetro para outra activity

        //chama PesoIdealActivity
        btnPesoIdeal.setOnClickListener {
            var pesoActivity: Float = peso
            var alturaActivity: Float = altura
            var generoActivity = genero

            val intent = Intent(this, PesoIdealActivity::class.java)
            //passar dados para outra activity
            intent.putExtra("key_peso", pesoActivity)
            intent.putExtra("key_altura", alturaActivity)
            intent.putExtra("key_genero", generoActivity)
            startActivity(intent)
        }

    }

    //realizar o calculo do imc
    private  fun calculo(peso: Float, altura: Float){
        var altQuad = altura * altura
        var imc = peso / altQuad
        Log.d("imc:", "$imc")
        return apresentacao(imc)
    }

    //Apresentar informção ao usuário
    private fun apresentacao(imc: Float){

        txtResultado.text = imc.toString()

        //comparação com atilidade e imc
        //swicth desligado
        var cont = 0
        if(rbSedentario.isChecked){
            cont = 1
        }

        if(rbModerado.isChecked){
            cont = 2
        }

        if(rbIntenso.isChecked){
            cont = 3
        }

        if(imc.toFloat() < 18.5){
            if(rbSedentario.isChecked){
                txtResultado.text = "Melhore sua saúde: "+ imc.toString() +" IMC"
                ratingBar.rating = 1.0f
            }else if(rbModerado.isChecked){
                txtResultado.text = "Melhore a alimentação: "+ imc.toString() +" IMC"
                ratingBar.rating = 1.0f
            }else{
                txtResultado.text = "Melhore sua massa muscalar: "+ imc.toString() +" IMC"
                ratingBar.rating = 5.0f
            }
        }

        if(imc.toFloat() >18.5 && imc.toDouble() >= 24.9   ){
            if(rbSedentario.isChecked){
                txtResultado.text = "Pratique excercícios: " + imc.toString() +" IMC"
                ratingBar.rating = 1.0f
            }else if(rbModerado.isChecked){
                txtResultado.text = "Mantenha assim: "+ imc.toString() +" IMC"
                ratingBar.rating = 3.0f
            }else{
                txtResultado.text = "Muito bom: "+ imc.toString() +" IMC"
                ratingBar.rating = 5.0f
            }
        }

        if(imc.toFloat() > 24.9 && imc.toDouble() >= 30){
            if(rbSedentario.isChecked){
                txtResultado.text = "Pratique execícios: "+ imc.toString() +" IMC"
                ratingBar.rating = 1.0f
            }else if(rbModerado.isChecked){
                txtResultado.text = "Melhore a alimentação: "+ imc.toString() +" IMC"
                ratingBar.rating = 2.0f
            }else{
                txtResultado.text = "Melhore sua massa muscalar: "+ imc.toString() +" IMC"
                ratingBar.rating = 5.0f
            }
        }

        if(imc.toFloat() > 30){
            if(rbSedentario.isChecked){
                txtResultado.text = "Cuide da sua saúde, pratique exercícios: "+ imc.toString() +" IMC"
                ratingBar.rating = 1.0f
            }else if(rbModerado.isChecked){
                txtResultado.text = "Melhore a alimentação: "+ imc.toString() +" IMC"
                ratingBar.rating = 3.0f
            }else{
                txtResultado.text = "Continue treinando: "+ imc.toString() +" IMC"
                ratingBar.rating = 5.0f
            }
        }

        //homem
        if(imc.toFloat() < 18.5){
            if(rbSedentario.isChecked){
                txtResultado.text = "Melhore sua saúde: "+ imc.toString() +" IMC"
                ratingBar.rating = 1.0f
            }else if(rbModerado.isChecked){
                txtResultado.text = "Melhore a alimentação: "+ imc.toString() +" IMC"
                ratingBar.rating = 2.0f
            }else{
                txtResultado.text = "Melhore sua massa muscalar: "+ imc.toString() +" IMC"
                ratingBar.rating = 3.0f
            }
        }

        if(imc.toFloat() >18.5 && imc.toDouble() >= 24.9   ){
            if(rbSedentario.isChecked){
                txtResultado.text = "Pratique excercícios: " + imc.toString() +" IMC"
                ratingBar.rating = 2.0f
            }else if(rbModerado.isChecked){
                txtResultado.text = "Mantenha assim: "+ imc.toString() +" IMC"
                ratingBar.rating = 3.0f
            }else{
                txtResultado.text = "Muito bom: "+ imc.toString() +" IMC"
                ratingBar.rating = 5.0f
            }
        }

        if(imc.toFloat() > 24.9 && imc.toDouble() >= 30){
            if(rbSedentario.isChecked){
                txtResultado.text = "Pratique execícios: "+ imc.toString() +" IMC"
                ratingBar.rating = 2.0f
            }else if(rbModerado.isChecked){
                txtResultado.text = "Melhore a alimentação: "+ imc.toString() +" IMC"
                ratingBar.rating = 2.0f
            }else{
                txtResultado.text = "Melhore sua massa muscalar: "+ imc.toString() +" IMC"
                ratingBar.rating = 5.0f
            }
        }

        if(imc.toFloat() > 30){
            if(rbSedentario.isChecked){
                txtResultado.text = "Cuide da sua saúde, pratique exercícios: "+ imc.toString() +" IMC"
                ratingBar.rating = 1.0f
            }else if(rbModerado.isChecked){
                txtResultado.text = "Melhore a alimentação: "+ imc.toString() +" IMC"
                ratingBar.rating = 3.0f
            }else{
                txtResultado.text = "Continue treinando: "+ imc.toString() +" IMC"
                ratingBar.rating = 5.0f
            }
        }

    }

}
