package no.hvl.dat153.quizapp_oblig3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ImageDAO {

    @Insert
    void insert(ImageEntity image);

    @Query("DELETE FROM imageTable")
    void deleteAll();

    @Query("DELETE FROM imageTable WHERE  id = :id")
    void deleteWithId(long id);

    @Query("SELECT * FROM imageTable")
    LiveData<List<ImageEntity>> getAllImages();

}
