package com.mchi.ej4

import kotlinx.coroutines.flow.Flow
import com.mchi.ej4.data.Articulo
import com.mchi.ej4.data.ArticuloDao

//el repository es una capa más de seguridad de datos, ya no junta la lógica del rom con la UI en el main directamente
class Repository(private val dao: ArticuloDao) {
    suspend fun insertar(articulo: Articulo):Long = dao.insertar(articulo)
    suspend fun actualizar(articulo: Articulo):Int = dao.actualizar(articulo)
    suspend fun buscarPorCodigo(codigo: Int): Articulo? = dao.buscarPorCodigo(codigo)
    suspend fun eliminarPorCodigo(codigo: Int):Int = dao.eliminarPorCodigo(codigo)
    fun listarTodos(): Flow<List<Articulo>> = dao.listarTodos()
}