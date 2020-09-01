package co.uk.wardone.model.utils

fun <T> calculateListDiff(oldList: List<T>, newList: List<T>): List<T> {

    val cardsToDelete = oldList.toHashSet()
    cardsToDelete.removeAll(newList)
    return cardsToDelete.toList()
}