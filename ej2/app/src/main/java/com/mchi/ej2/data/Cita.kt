package com.mchi.ej2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val especialista: String,
    val descripcion: String,
    val fecha: String,
    val estado: String
)