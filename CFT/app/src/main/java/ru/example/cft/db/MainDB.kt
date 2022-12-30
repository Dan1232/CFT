package ru.example.cft.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.example.cft.entities.LibraryItem

@Database(entities = [LibraryItem::class], version = 1)
abstract class MainDB: RoomDatabase() {
    abstract fun getDao(): Dao

    companion object{
        @Volatile
        private var INSTANCE: MainDB? = null
        fun getDB(context: Context): MainDB{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDB::class.java,
                    "shopping_list.db"
                ).build()
                instance
            }
        }
    }
}