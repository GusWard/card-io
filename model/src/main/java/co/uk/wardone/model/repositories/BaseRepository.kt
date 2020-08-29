package co.uk.wardone.model.repositories

abstract class BaseRepository<T> {

    abstract fun getDao(): T?

    protected abstract fun refresh()

    fun getDaoAndRefresh(): T? {

        refresh()
        return getDao()
    }
}