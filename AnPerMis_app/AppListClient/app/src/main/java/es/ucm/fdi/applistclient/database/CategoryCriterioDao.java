package es.ucm.fdi.applistclient.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryCriterioDao {

    @Query("SELECT * FROM categoryCriterio")
    List<CategoryCriterioEntity> loadAllCategories();

    @Query("SELECT * FROM categoryCriterio WHERE category = :category_name")
    CategoryCriterioEntity loadCategory(String category_name);

    @Update
    void updateCategory(CategoryCriterioEntity c);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(CategoryCriterioEntity c);

    @Delete
    void deleteCategory(CategoryCriterioEntity c);
}
