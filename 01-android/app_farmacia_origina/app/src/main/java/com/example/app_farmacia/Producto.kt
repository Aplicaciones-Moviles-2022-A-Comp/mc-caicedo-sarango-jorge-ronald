package com.example.app_farmacia

data class Producto(
    val nombreShort: String,
    val nombreExt:String,
    val imagen:String,
    val precioUnit:Double,
    val descriptcion:String
) {
}