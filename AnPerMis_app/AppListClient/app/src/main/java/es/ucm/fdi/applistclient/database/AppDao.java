package es.ucm.fdi.applistclient.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {

    @Query("SELECT * FROM Application")
    List<AppEntity> loadAllApplications();

    @Query("SELECT * FROM Application WHERE appPackage = :package_name")
    AppEntity loadApplication(String package_name);

    @Update
    void updateApplication(AppEntity app);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertApplication(AppEntity app);

    @Delete
    void deleteApplication(AppEntity app);

}
