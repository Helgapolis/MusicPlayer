package ru.akulinina.musicplayer.category.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(@PrimaryKey(autoGenerate = true) var id: Int,
                    @ColumnInfo(name = "name") var name: String)