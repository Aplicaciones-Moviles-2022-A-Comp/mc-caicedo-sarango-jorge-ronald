import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date


fun main(){

    //Paths donde se almacenarán los archivos
    val pathB:Path = FileSystems.getDefault().getPath(
        "C:\\Users\\caice\\OneDrive - Escuela Politécnica Nacional\\Septimo Semestre\\Movil\\GitHub\\mc-caicedo-sarango-jorge-ronald" +
                "\\ColeccionBandasCanciones\\src\\main\\resources","archivoBandas.txt")
    val pathC:Path = FileSystems.getDefault().getPath(
        "C:\\Users\\caice\\OneDrive - Escuela Politécnica Nacional\\Septimo Semestre\\Movil\\GitHub\\mc-caicedo-sarango-jorge-ronald" +
                "\\ColeccionBandasCanciones\\src\\main\\resources","archivoCanciones.txt")


    //Creación de archivos en los paths
    crearArchivos(pathB,pathC)


    var op:Int = 0
    println("Bienvenido a su gestor de Bandas y Canciones :-)")


    //Bucle del programa principal
    do {

        println("Seleccione la opción que desea realizar: ")
        desplegarMenu()
        op = readLine()!!.toInt()
        programa(op,pathC,pathB)

    }while (op!=0)

}

fun crearArchivos(path1: Path, path2: Path):Unit{
    if (!Files.exists(path1)||!Files.exists(path2)){
        Files.createFile(path1)
        Files.createFile(path2)
        val encabezadoB = "id_banda\tnombre_banda\tfecha_creacion\tlugar_creacion\tis_active\tnumero_integrantes"
        val encabezadoC = "id_cancion\tnombre_cancion\tduracion_cancion\tfecha_lanzamiento\tis_single\tnumero_pista\tganancias_sencillo\tid_banda"
        Files.write(path1,encabezadoB.toByteArray(),StandardOpenOption.APPEND)
        Files.write(path2,encabezadoC.toByteArray(),StandardOpenOption.APPEND)
    }
}

fun desplegarMenu():Unit{
    val menu = "1. Ingresar una nueva cancion..."+
               "\n2. Ingresar una nueva banda... "+
                "\n3. Actualizar una cancion basado en el id..."+
                "\n4. Actualizar una banda basado en el id..."+
                "\n5. Eliminar una cancion basado en el id..."+
                "\n6. Eliminar una banda basado en el id..."+
                "\n7. Seleccionar una cancion basado en el id..."+
                "\n8. Seleccionar una banda basado en el id..."+
                "\n9. Visualizar el archivo de canciones..."+
                "\n10. Visualizar el archivo de bandas..."+
                "\n0. Salir ...\n"
    println(menu)
}

fun programa(op:Int, pathC:Path, pathB:Path):Unit{
    when(op){
        0->{
            println("Adios...")
        }
        1->{
            insertarDatosArchivo(solicitarDatosCancion(1),pathC)
        }
        2->{
            insertarDatosArchivo(solicitarDatosBanda(1),pathB)
        }
        3->{
            println("Ingrese el código del registro a actualizar: ")
            var codigo = readLine()!!.toString()
            updateRegistro(codigo,solicitarDatosCancion(2),pathC.toString())
        }
        4->{
            println("Ingrese el código del registro a actualizar: ")
            var codigo = readLine()!!.toString()
            updateRegistro(codigo,solicitarDatosBanda(2),pathB.toString())
        }
        5->{
            println("Ingrese el código del registro a eliminar: ")
            var codigo = readLine()!!.toString()
            deleteRegistro(codigo,pathC.toString())
        }
        6->{
            println("Ingrese el código del registro a eliminar: ")
            var codigo = readLine()!!.toString()
            deleteRegistro(codigo,pathB.toString())
        }
        7->{
            println("Ingrese el código del registro a consultar en el registro de Canciones: ")
            var codigo = readLine()!!.toString()
            println(selectRegistro(codigo,pathC.toString()))
            println("----------------------------------------")
        }
        8->{
            println("Ingrese el código del registro a consultar en el registro de Bandas: ")
            var codigo = readLine()!!.toString()
            println(selectRegistro(codigo,pathB.toString()))
            println("----------------------------------------")
        }
        9->{
            println("El archivo de CANCIONES contiene los registros: "+"\n----------------------------------------")
            selectArchivo(pathC)
            println("----------------------------------------")
        }
        10->{
            println("El archivo de BANDAS contiene los registros: "+"\n----------------------------------------")
            selectArchivo(pathB)
            println("----------------------------------------")
        }
        else->{
            println("Entrada incorrecta, por favor elije una entrada(0-10) valida")
        }
    }
}

fun solicitarDatosBanda(controlador:Int):String{
    val datos:String

    println("A continuación ingresa la información sobre la banda\n----------------------------------------")

    println("Ingresa el id de la Banda: ")
    val idBanda = readLine()!!.toString()

    println("Ingresa el Nombre de la Banda: ")
    val nombreBanda = readLine()!!.toString()

    println("Ingresa la fecha de creación de la banda(AA-MM-DD): ")
    val fechaCreacion = readLine()!!.toString()
    val parsedDate = LocalDate.parse(fechaCreacion)

    println("Ingresa el lugar donde se conformó la banda: ")
    val lugarCreacion= readLine()!!.toString()

    println("¿-Sigue la banda activa en 2022? (s/n): ")
    val isActive = readLine()!!.toString()
    var estaActiva = isActive.equals("s")

    println("Ingresa el número de integrantes: ")
    val numeroIntegrantes = readLine()!!.toInt()


    if (controlador==1){
        datos = "\n"+idBanda+
                " "+nombreBanda+
                " "+parsedDate+
                " "+lugarCreacion+
                " "+estaActiva+
                " "+numeroIntegrantes
        return datos
    }else{
        datos = ""+idBanda+
                " "+nombreBanda+
                " "+parsedDate+
                " "+lugarCreacion+
                " "+estaActiva+
                " "+numeroIntegrantes
        return datos
    }

}

fun solicitarDatosCancion(controlador:Int):String{
    val datos:String

    println("A continuación ingresa la información sobre la canción\n----------------------------------------")
    println("Ingresa el id de la Cancion: ")
    val idCancion = readLine()!!.toString()

    println("Ingresa el Nombre de la Cancion: ")
    val nombreCancion = readLine()!!.toString()

    println("Ingresa la duración de la canción(HHmmss.00): ")
    val duracionPista = readLine()!!.toString()
    val formatoOrigen = SimpleDateFormat("HHmmss.00");
    val formatoDestino = SimpleDateFormat("HH:mm:ss");
    val fecha:Date = formatoOrigen.parse(duracionPista);
    val parsedDuration:String = formatoDestino.format(fecha);

    println("Ingresa la fecha de lanzamiento de la canción(AA-MM-DD): ")
    val fechaLanzamiento= readLine()!!.toString()
    val parsedDate = LocalDate.parse(fechaLanzamiento)

    println("¿Es la canción un sencillo?(s/n): ")
    val isSingle = readLine()!!.toString()
    var esSencillo = isSingle.equals("s",true)

    println("Ingresa el número de pista: ")
    val numeroPista = readLine()!!.toInt()

    println("Ingresa las ganacias del sencillo (Ej: 3000000.5): ")
    val ganancias = readLine()!!.toDouble()

    println("Ingresa el código de la banda compositora: (10N)")
    val idBanda = readLine()!!.toString()

    if (controlador==1){
        datos = "\n"+idCancion+
                " "+nombreCancion+
                " "+parsedDate+
                " "+parsedDuration+
                " "+esSencillo+
                " "+numeroPista+
                " "+ganancias+
                " "+idBanda
        return datos
    }else{
        datos = ""+idCancion+
                " "+nombreCancion+
                " "+parsedDate+
                " "+parsedDuration+
                " "+esSencillo+
                " "+numeroPista+
                " "+ganancias+
                " "+idBanda
        return datos
    }
}

fun selectArchivo(path:Path):Unit{
    val datos:List<String> = Files.readAllLines(path)
    val respuestaForEach: Unit = datos.forEach{ //Sólo para Iterar
            valorActual: String -> println("${valorActual}")}
}

fun selectRegistro(codigo:String,path: String):String{

    val archivo = File(path)
    val lineas = archivo.readLines()
    var id: String

    println("El registro es:  \n----------------------------------------")

    lineas.forEach { it ->
        id = it.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        if (id.equals(codigo)) {
            return it
        }
    }
    return "No se encontro"
}

fun insertarDatosArchivo(datos:String, path: Path):Unit{
    try {
        Files.write(path,datos.toByteArray(),StandardOpenOption.APPEND)
        println("Datos ingresados correctamente\n----------------------------------------\n")
    }catch (error:IOException){
        println(error.stackTraceToString())
    }
}

fun updateRegistro(codigo:String,nuevaExp: String, path: String): Unit{

    var cadena:String
    var auxBuilder: java.lang.StringBuilder
    var pos:Long=0
    var indice:Int
    val fichero = RandomAccessFile(path.toString(),"rw")

    try {
        cadena = fichero.readLine()
        while (cadena!=null){
            indice = cadena.indexOf(codigo)
            while (indice!=-1){
                auxBuilder= StringBuilder(cadena)
                auxBuilder.replace(indice,indice+cadena.length,cadena.replace(cadena,nuevaExp))
                cadena = auxBuilder.toString()

                fichero.seek(pos)
                fichero.writeBytes(cadena)
                indice = cadena.indexOf(codigo)
            }
            pos = fichero.filePointer
            cadena = fichero.readLine()
        }
    }catch (ex:java.lang.Exception){
        println("Datos actualizados correctamente\n----------------------------------------")
    }finally {
        try {
            if (fichero != null) {
                fichero.close()
            }
        } catch (e: IOException) {
            println(e.message)
        }
    }

}

fun deleteRegistro(codigo:String, path: String):Unit{
    var cadena:String
    var auxBuilder: java.lang.StringBuilder
    var pos:Long=0
    var indice:Int
    val fichero = RandomAccessFile(path.toString(),"rw")
    var nuevaExp = java.lang.StringBuilder()

    try {

        cadena = fichero.readLine()
        while (cadena!=null){
            indice = cadena.indexOf(codigo)
            while (indice!=-1){
                for ( i in 0 until cadena.length-1){
                    nuevaExp.append(" ")
                }

                auxBuilder= StringBuilder(cadena)
                auxBuilder.replace(indice,indice+cadena.length,cadena.replace(cadena,"-"+nuevaExp.toString()))
                cadena = auxBuilder.toString()

                fichero.seek(pos)
                fichero.writeBytes(cadena)
                indice = cadena.indexOf(codigo)
            }
            pos = fichero.filePointer
            cadena = fichero.readLine()
        }
    }catch (ex:java.lang.Exception){
        println("Eliminación del registro con id = "+codigo+" finalizada correctamente\n" +
                "----------------------------------------")
    }finally {
        try {
            if (fichero != null) {
                fichero.close()
            }
        } catch (e: IOException) {
            println(e.message)
        }
    }
}
