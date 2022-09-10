package com.example.examen_bim1

import android.os.Parcel
import android.os.Parcelable

class BBanda(
    var idBanda:Int?,
    var nombre:String?,
    var fechaCreacion:String?,
    var lugarOrigen:String?,
    var isActive:Boolean,
    var numIntegrantes:Int?
    ):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    )

    override fun toString(): String {
        return "${idBanda} / ${nombre} / ${fechaCreacion} / ${lugarOrigen} / ${isActive} / ${numIntegrantes}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        if (idBanda != null) {
            parcel.writeInt(idBanda!!)
        }
        parcel.writeString(nombre)
        parcel.writeString(fechaCreacion)
        parcel.writeString(lugarOrigen)
        parcel.writeByte(if (isActive) 1 else 0)
        if (numIntegrantes != null) {
            parcel.writeInt(numIntegrantes!!)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BBanda> {
        override fun createFromParcel(parcel: Parcel): BBanda {
            return BBanda(parcel)
        }

        override fun newArray(size: Int): Array<BBanda?> {
            return arrayOfNulls(size)
        }
    }
}