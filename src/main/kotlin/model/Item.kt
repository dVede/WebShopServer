package model

import kotlinx.serialization.Serializable

@Serializable
data class Item(val name: String, var amount: Int, val price: Double)
