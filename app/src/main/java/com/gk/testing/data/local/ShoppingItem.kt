package com.gk.testing.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo("name")
    var name: String,
    @ColumnInfo("amount")
    var amount: Int,
    @ColumnInfo("price")
    var price: Float,
    @ColumnInfo("imageUrl")
    var imageUrl: String
)
