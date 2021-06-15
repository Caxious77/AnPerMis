package es.ucm.fdi.applistclient.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryFreDao {

    @Query("SELECT * FROM categoryFrequencies")
    List<CategoryFreEntity> loadAllFrequencies();

    @Query("SELECT * FROM categoryFrequencies WHERE category = :category_name")
    CategoryFreEntity loadCategoryFrequency(String category_name);

    @Update
    void updateCategoryFrequency(CategoryFreEntity c);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoryFrequency(CategoryFreEntity c);

    @Delete
    void deleteCategoryFrequency(CategoryFreEntity c);
}
