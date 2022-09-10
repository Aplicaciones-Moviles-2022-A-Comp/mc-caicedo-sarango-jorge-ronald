package com.example.examen_bim1

import android.os.Parcel
import android.os.Parcelable

class BCancion(
    var idCancion:Int,
    var nombreC: String?,
    var duracion: String?,
    var fechaLanzamieto: String?,
    var isSingle:Boolean,
    var numPista:Int,
    var gananciasSencillo:Double,
    var idBanda:Int?
):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "${idCancion} / ${nombreC} / ${duracion} / ${fechaLanzamieto} / ${isSingle} / ${numPista} / ${gananciasSencillo} / ${idBanda}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idCancion)
        parcel.writeString(nombreC)
        parcel.writeString(duracion)
        parcel.writeString(fechaLanzamieto)
        parcel.writeByte(if (isSingle) 1 else 0)
        parcel.writeInt(numPista)
        parcel.writeDouble(gananciasSencillo)
        if (idBanda != null) {
            parcel.writeInt(idBanda!!)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BCancion> {
        override fun createFromParcel(parcel: Parcel): BCancion {
            return BCancion(parcel)
        }

        override fun newArray(size: Int): Array<BCancion?> {
            return arrayOfNulls(size)
        }
    }
}