package com.example.app_farmacia.productoadapter

import com.example.app_farmacia.Producto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductoProvider {

    companion object{
        val productoList = arrayListOf<Producto>(
            Producto("Aspirina",
                "Aspirina 100 mg Genfar",
                "img.jpg",
                0.20,
                "Lorem....."),
            Producto("Alcazeltser",
                "Alcazetser 100 mg Genfar",
                "img.jpg",
                0.20,
                "Lorem....."),
            Producto("Finalin",
                "Finalin 100 mg Genfar",
                "img.jpg",
                0.20,
                "Lorem....."),
            Producto("Mulgatol",
                "Mulgatol 150 ml Genfar",
                "img.jpg",
                0.20,
                "Lorem....."),
            Producto("Pharmaton",
                "Pharmaton Vitalityy 100 caps Bayer",
                "img.jpg",
                0.20,
                "Lorem.....")
        )
    }
}