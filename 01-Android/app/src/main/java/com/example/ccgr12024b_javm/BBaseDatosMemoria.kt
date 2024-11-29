package com.example.ccgr12024b_javm

class BBaseDatosMemoria{
    companion object{ //para compartir el arreglo con memoria
        //haremos un arreglo dinamico para aumentar o eliminar elementos
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init { //nos ayuda a escribir una lógica al inicializarse
            arregloBEntrenador.add(BEntrenador(1, "Jostin", "jostin@j.com"))
            arregloBEntrenador.add(BEntrenador(2, "Luis", "luis@l.com"))
            arregloBEntrenador.add(BEntrenador(3, "Sebas", "sebas@s.com"))
        }
    }
}