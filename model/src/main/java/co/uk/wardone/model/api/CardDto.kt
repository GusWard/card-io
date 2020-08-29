package co.uk.wardone.model.api

data class Card(
    val id: Long = 0,
    val title: String,
    val description: String,
    val image: String,
    val link: String,
    val timeStamp: Long
)

data class CardResponse(val length: Int, val data: List<Card>)