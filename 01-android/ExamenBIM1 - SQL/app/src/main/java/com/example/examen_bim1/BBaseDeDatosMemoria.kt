package com.example.examen_bim1

class BBaseDeDatosMemoria {
    companion object{
        val arregloBBanda = arrayListOf<BBanda>()
        val arregloCancion = arrayListOf<BCancion>()
        init {
            arregloBBanda.add(BBanda(1,"Modern Talking","17-02-1980","Alemania",false,2))
            arregloBBanda.add(BBanda(2,"Queen","01-06-1970","EEUU",false,5))
            arregloBBanda.add(BBanda(3,"Joy","03-04-1995","EEUU",true,4))
            arregloBBanda.add(BBanda(4,"Mago de Oz","03-04-1999","Nicaragua",false,6))
            arregloCancion.add(BCancion(1,"Jet Airliner","04:35:13","01-01-1998",true,14,14.32,1))
            arregloCancion.add(BCancion(2,"Witchqueen","03:35:13","01-01-1990",false,10,14.32,1))
            arregloCancion.add(BCancion(3,"Bohemiam Rhapsody","04:35:13","01-01-1998",true,14,14.32,2))
            arregloCancion.add(BCancion(4,"Dont stop me now","04:35:13","01-01-1998",true,14,14.32,2))
            arregloCancion.add(BCancion(5,"Touch by touch","04:35:13","01-01-1998",true,14,14.32,3))
            arregloCancion.add(BCancion(6,"Hello","04:35:13","01-01-1998",true,14,14.32,3))
            arregloCancion.add(BCancion(7,"Fiesta Pagana","04:35:13","01-01-1998",true,14,14.32,4))
        }
    }
}