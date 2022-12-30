package ru.example.cft

import android.app.Application
import ru.example.cft.db.MainDB

class MainApp : Application() {
    val database by lazy { MainDB.getDB(this) }
}