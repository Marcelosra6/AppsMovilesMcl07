package com.mchi.ej2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mchi.ej2.data.Cita

@Dao
interface CitaDao {

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insertar(cita: Cita): Long

    @Update
    suspend fun actualizar(cita: Cita): Int

    @Query("DELETE FROM citas WHERE id = :id")
    suspend fun eliminarPorId(id: Int): Int

    @Query("SELECT * FROM citas WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): Cita?

    @Query("SELECT * FROM citas ORDER BY id ASC")
    suspend fun listarTodos(): List<Cita>
}