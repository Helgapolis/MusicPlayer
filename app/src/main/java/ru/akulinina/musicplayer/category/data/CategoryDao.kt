package ru.akulinina.musicplayer.category.data

import androidx.room.Dao
import androidx.room.Query
import ru.akulinina.musicplayer.category.dto.Category

@Dao
interface CategoryDao {
    @Query("SELECT * from category_table")
    fun getAll(): List<Category>

    @Query("SELECT COUNT(*) from category_table")
    fun getCount(): Int

    @Query("SELECT * FROM category_table WHERE name = :categoryName")
    fun findCategoty(categoryName: String) : Category?

    @Query("INSERT INTO category_table (name) VALUES(:categoryName)")
    fun insert(categoryName: String)

    @Query("DELETE FROM category_table WHERE name = :categoryName")
    fun delete(categoryName: String)
}