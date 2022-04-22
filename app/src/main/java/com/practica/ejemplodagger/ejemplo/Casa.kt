package com.practica.ejemplodagger.ejemplo

import javax.inject.Inject

class Casa @Inject constructor(val ventana:Ventana, val puerta:Puerta){
    fun entrar(){
        puerta.abrirPuerta()
        puerta.cerrarPuerta()
    }

    fun ventilar() = ventana.abrirVentana()

}

class Puerta @Inject constructor() {
    fun abrirPuerta() = println("Abrir puerta")
    fun cerrarPuerta() = println("Abrir puerta")
}

class Ventana @Inject constructor() {
    fun abrirVentana() = println("Abrir ventana")
    fun cerrarVentana() = println("Abrir ventana")
}
