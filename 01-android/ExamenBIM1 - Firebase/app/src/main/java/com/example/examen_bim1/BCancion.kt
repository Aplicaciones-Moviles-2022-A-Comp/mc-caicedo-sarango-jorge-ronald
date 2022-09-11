package com.example.examen_bim1

import android.os.Parcel
import android.os.Parcelable

class BCancion(
    var idCancion:Long,
    var nombreC: String?,
    var duracion: String?,
    var fechaLanzamieto: String?,
    var isSingle:Boolean,
    var numPista:Long,
    var gananciasSencillo:Double,
    var idBanda:Long?
):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong(),
        parcel.readDouble(),
        parcel.readLong()
    ) {
    }

    override fun toString(): String {
        return "${idCancion} / ${nombreC} / ${duracion} / ${fechaLanzamieto} / ${isSingle} / ${numPista} / ${gananciasSencillo} / ${idBanda}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(idCancion)
        parcel.writeString(nombreC)
        parcel.writeString(duracion)
        parcel.writeString(fechaLanzamieto)
        parcel.writeByte(if (isSingle) 1 else 0)
        parcel.writeLong(numPista)
        parcel.writeDouble(gananciasSencillo)
        if (idBanda != null) {
            parcel.writeLong(idBanda!!)
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