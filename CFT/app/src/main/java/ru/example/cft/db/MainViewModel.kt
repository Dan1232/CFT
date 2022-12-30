package ru.example.cft.db
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import ru.example.cft.entities.LibraryItem
import java.lang.IllegalArgumentException

class MainViewModel(database: MainDB) : ViewModel()  {
    val dao = database.getDao()
    fun insertLibrary(item: LibraryItem) = viewModelScope.launch {
        if(!isLibraryItemExists(item.name)) dao.insertLibrary(LibraryItem(null, item.name))

    }
    val getAllLibraryItems: LiveData<List<LibraryItem>> = dao.getAllLibraryItems().asLiveData()

    private suspend fun isLibraryItemExists(name: String): Boolean{
        return dao.getAllLibraryItems(name).isNotEmpty()
    }

    class MainViewModelFactory(val database: MainDB) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}