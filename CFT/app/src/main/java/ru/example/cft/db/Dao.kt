package ru.example.cft.db
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.example.cft.entities.LibraryItem


@Dao
interface Dao {
    @Insert
    suspend fun insertLibrary(item: LibraryItem)

    @Query("SELECT * FROM library")
    fun getAllLibraryItems(): Flow<List<LibraryItem>>

    @Query ("SELECT * FROM library WHERE name LIKE :name")
    suspend fun getAllLibraryItems(name: String): List<LibraryItem>
}