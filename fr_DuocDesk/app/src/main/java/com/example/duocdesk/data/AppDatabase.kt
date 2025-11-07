package com.example.duocdesk.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.duocdesk.model.Usuario

// 1. Define las entidades (tablas) que tendrá esta base de datos.
@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Expone el DAO para que el Repositorio pueda usarlo.
    abstract fun usuarioDao(): UsuarioDao

    // 2. Singleton: Esto asegura que solo exista UNA instancia de la base de datos
    // en toda la app, para evitar problemas de concurrencia y rendimiento.
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // 3. Si la instancia ya existe, la devuelve.
            return INSTANCE ?: synchronized(this) {
                // 4. Si no, la crea.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "duocdesk_database" // Nombre del archivo de la base de datos
                )
                    .fallbackToDestructiveMigration() // Útil para desarrollo
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}