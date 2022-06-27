package com.example.movcompjrcs2022a

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(
                BEntrenador("Adrian","a@a.com")
            )
            arregloBEntrenador.add(
                BEntrenador("Jorge","b@b.com")
            )
            arregloBEntrenador.add(
                BEntrenador("Carlos","b@b.com")
            )
        }

    }
}