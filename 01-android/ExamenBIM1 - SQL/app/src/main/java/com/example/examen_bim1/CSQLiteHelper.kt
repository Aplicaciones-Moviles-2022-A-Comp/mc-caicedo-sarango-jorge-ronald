package com.example.examen_bim1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class CSQLiteHelper(contexto : Context?):
    SQLiteOpenHelper(contexto,
    "examenSQLite",
    null,
    1)
{
    public
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearBandaT =
            """
                CREATE TABLE banda(
                idBanda INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50) NOT NULL,
                fechaCreacion VARCHAR(20) NOT NULL,
                lugarOrigen VARCHAR(50) NOT NULL,
                isActive BOOLEAN NOT NULL,
                numIntegrantes INTEGER NOT NULL
                )
            """.trimIndent()
        val scriptCrearCancionT=
            """
                CREATE TABLE cancion(
                idCancion INTEGER PRIMARY KEY AUTOINCREMENT,
                nombreC VARCHAR(50) NOT NULL,
                duracion VARCHAR(20) NOT NULL,
                fechaLanzamieto VARCHAR(20) NOT NULL,
                isSingle BOOLEAN,
                numPista INTEGER NOT NULL,
                gananciasSencillo REAL NOT NULL,
                idBanda INTEGER,
                FOREIGN KEY(idBanda) REFERENCES banda
                ON DELETE CASCADE
                ON UPDATE CASCADE
                )
            """.trimIndent()
        db?.execSQL(scriptCrearBandaT)
        db?.execSQL(scriptCrearCancionT)
        insertarDatosBase(db)
    }
    fun insertarDatosBase(db: SQLiteDatabase?){
        //Datos bandas
        val registroBanda = """
            INSERT INTO banda(nombre,fechaCreacion,lugarOrigen,isActive,numIntegrantes) VALUES("Modern Talking","17-02-1980","Alemania",false,2);
        """.trimIndent()
        db?.execSQL(registroBanda)
        val registroBanda2 = """
            INSERT INTO banda(nombre,fechaCreacion,lugarOrigen,isActive,numIntegrantes) VALUES("Queen","01-03-1970","EEUU",false,4);
        """.trimIndent()
        db?.execSQL(registroBanda2)
        val registroBanda3 = """
            INSERT INTO banda(nombre,fechaCreacion,lugarOrigen,isActive,numIntegrantes) VALUES("Mago de Oz","10-12-2000","Chile",true,5);
        """.trimIndent()
        db?.execSQL(registroBanda3)

        //Datos canciones
        val registroCancion = """
            INSERT INTO cancion(nombreC,duracion,fechaLanzamieto,isSingle,numPista,gananciasSencillo,idBanda) VALUES("Fiesta Pagana","00:03:45","13-03-2001",true,1,14500,3);
        """.trimIndent()
        db?.execSQL(registroCancion)
        val registroCancion2 = """
            INSERT INTO cancion(nombreC,duracion,fechaLanzamieto,isSingle,numPista,gananciasSencillo,idBanda) VALUES("Jet Airliner","00:04:45","13-03-2005",false,5,13500,1);
        """.trimIndent()
        db?.execSQL(registroCancion2)
        val registroCancion3 = """
            INSERT INTO cancion(nombreC,duracion,fechaLanzamieto,isSingle,numPista,gananciasSencillo,idBanda) VALUES("I Want To Break Free","00:04:32","13-08-2004",true,1,146500,2);
        """.trimIndent()
        db?.execSQL(registroCancion3)
    }

    fun seleccionarTodasB():ArrayList<BBanda>{
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM banda"
        var isActive = true
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val bandasEncontradas = arrayListOf<BBanda>()
        var bandaEncontrada = BBanda(0,"","","",true,0)

        if(existeUsuario){
            do{
                val idB = resultadoConsultaLectura.getInt(0) // columna indice 0 -> ID
                val nombreB = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val fechaC = resultadoConsultaLectura.getString(2) // Columna indice 2 -> DESCRIPCION
                val lugarO = resultadoConsultaLectura.getString(3)
                val isActiv = resultadoConsultaLectura.getInt(4)
                isActive = isActiv==1
                val numInteg = resultadoConsultaLectura.getInt(5)
                bandaEncontrada = BBanda(idB,nombreB,fechaC,lugarO,isActive,numInteg)
                bandasEncontradas.add(bandaEncontrada)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return bandasEncontradas
    }

    fun seleccionarTodasC(idBand: Int):ArrayList<BCancion>{
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM cancion WHERE idBanda=${idBand}"
        var cIsSingle = true
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val bandasEncontradas = arrayListOf<BCancion>()
        var bandaEncontrada = BCancion(0,"","","",true,0,0.0,0)

        if(existeUsuario){
            do{
                val idCancion = resultadoConsultaLectura.getInt(0) // columna indice 0 -> ID
                val nombreC = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val duracion = resultadoConsultaLectura.getString(2) // Columna indice 2 -> DESCRIPCION
                val fechaLanzamieto = resultadoConsultaLectura.getString(3)
                val isSingle = resultadoConsultaLectura.getInt(4)
                cIsSingle= isSingle==1
                val numPista = resultadoConsultaLectura.getInt(5)
                val gananciasSencillo = resultadoConsultaLectura.getDouble(6)
                val idBanda = resultadoConsultaLectura.getInt(7)
                bandaEncontrada = BCancion(idCancion,nombreC,duracion,fechaLanzamieto,cIsSingle,numPista,gananciasSencillo,idBanda)
                bandasEncontradas.add(bandaEncontrada)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return bandasEncontradas
    }

    fun insertarBanda(
        nombre:String,
        fechaCreacion:String,
        lugarOrigen:String,
        isActive:Boolean,
        numIntegrantes:Int
    ): Boolean {
        val baseDatosEscritura =  writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("fechaCreacion",fechaCreacion)
        valoresAGuardar.put("lugarOrigen",lugarOrigen)
        valoresAGuardar.put("isActive",isActive)
        valoresAGuardar.put("numIntegrantes",numIntegrantes)

        val resultadoGuardar = baseDatosEscritura.insert(
            "banda",
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun insertarCancion(
        nombreC:String,
        duracion:String,
        fechaLanzamieto:String,
        isSingle:Boolean,
        numPista:Int,
        gananciasSencillo:Double,
        idBanda:Int
    ): Boolean {
        val baseDatosEscritura =  writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombreC",nombreC)
        valoresAGuardar.put("duracion",duracion)
        valoresAGuardar.put("fechaLanzamieto",fechaLanzamieto)
        valoresAGuardar.put("isSingle",isSingle)
        valoresAGuardar.put("numPista",numPista)
        valoresAGuardar.put("gananciasSencillo",gananciasSencillo)
        valoresAGuardar.put("idBanda",idBanda)

        val resultadoGuardar = baseDatosEscritura.insert(
            "cancion",
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarBanda(id: Int): Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "banda",
                "idBanda=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1
    }

    fun eliminarCancion(id: Int): Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "cancion",
                "idCancion=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1
    }

    fun actualizarBanda(
        idBanda: Int,
        nombre: String,
        fechaCreacion: String,
        lugarOrigen: String,
        isActive: Boolean,
        numIntegrantes: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()

        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaCreacion", fechaCreacion)
        valoresAActualizar.put("lugarOrigen", lugarOrigen)
        valoresAActualizar.put("isActive", isActive)
        valoresAActualizar.put("numIntegrantes", numIntegrantes)

        val resultadoActualizacion = conexionEscritura
            .update(
                "banda", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "idBanda=?", // Clausula Where
                arrayOf(
                    idBanda.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun actualizarCancion(
        idCancion: Int,
        nombreC: String,
        duracion: String,
        fechaLanzamieto: String,
        isSingle: Boolean,
        numPista: Int,
        gananciasSencillo: Double,
        idBanda:Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()

        valoresAActualizar.put("nombreC", nombreC)
        valoresAActualizar.put("duracion", duracion)
        valoresAActualizar.put("fechaLanzamieto", fechaLanzamieto)
        valoresAActualizar.put("isSingle", isSingle)
        valoresAActualizar.put("numPista", numPista)
        valoresAActualizar.put("gananciasSencillo", gananciasSencillo)
        valoresAActualizar.put("idBanda", idBanda)

        val resultadoActualizacion = conexionEscritura
            .update(
                "cancion", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "idCancion=?", // Clausula Where
                arrayOf(
                    idCancion.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun consultarBanda(id: Int): BBanda{
        // val baseDatosLectura = this.readableDatabase
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM banda WHERE idBanda = ${id}"
        var isActiv=true
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BBanda(0,"","","",true,0)
        if(existeUsuario){
            do{
                val idBanda = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaCreacion = resultadoConsultaLectura.getString(2)
                val lugarOrigen = resultadoConsultaLectura.getString(3)
                val isActive = resultadoConsultaLectura.getInt(4)
                isActiv= isActive==1
                val numIntegrantes = resultadoConsultaLectura.getInt(5)

                if(id!=null){
                    usuarioEncontrado.idBanda = idBanda
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.fechaCreacion = fechaCreacion
                    usuarioEncontrado.lugarOrigen = lugarOrigen
                    usuarioEncontrado.isActive = isActiv
                    usuarioEncontrado.numIntegrantes = numIntegrantes
                }
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun consultarCancion(id: Int): BCancion{
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM cancion WHERE idCancion = ${id}"
        var cisSingle=true
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BCancion(0,"","","",true,0,0.0,0)
        if(existeUsuario){
            do{
                val idCancion = resultadoConsultaLectura.getInt(0)
                val nombreC = resultadoConsultaLectura.getString(1)
                val duracion = resultadoConsultaLectura.getString(2)
                val fechaLanzamieto = resultadoConsultaLectura.getString(3)
                val isSingle = resultadoConsultaLectura.getInt(4)
                cisSingle=isSingle==1
                val numPista = resultadoConsultaLectura.getInt(5)
                val gananciasSencillo = resultadoConsultaLectura.getDouble(6)
                val idBanda = resultadoConsultaLectura.getInt(7)

                if(id!=null){
                    usuarioEncontrado.idCancion = idCancion
                    usuarioEncontrado.nombreC = nombreC
                    usuarioEncontrado.duracion = duracion
                    usuarioEncontrado.fechaLanzamieto = fechaLanzamieto
                    usuarioEncontrado.isSingle = cisSingle
                    usuarioEncontrado.numPista = numPista
                    usuarioEncontrado.gananciasSencillo = gananciasSencillo
                    usuarioEncontrado.idBanda = idBanda
                }
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE  cancion")
        db?.execSQL("DROP TABLE  banda")
        onCreate(db)
    }

}