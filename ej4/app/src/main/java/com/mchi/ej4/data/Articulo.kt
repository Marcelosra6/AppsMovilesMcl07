package com.mchi.ej4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articulos")
data class Articulo(
    @PrimaryKey
    val codigo: Int,
    val descripcion: String,
    val precio: Double
)