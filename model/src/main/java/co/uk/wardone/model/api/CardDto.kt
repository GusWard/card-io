package co.uk.wardone.model.api

import co.uk.wardone.model.database.Card

data class CardResponse(val length: Int, val data: List<Card>)