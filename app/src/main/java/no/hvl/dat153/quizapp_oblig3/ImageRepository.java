package no.hvl.dat153.quizapp_oblig3;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ImageRepository {

    private ImageDAO imageDao;

    private LiveData<List<ImageEntity>> images;

    ImageRepository(Application application) {
        ImageDatabase db = ImageDatabase.getDatabase(application);
        imageDao = db.imageDao();
        images = imageDao.getAllImages();
    }

    void deleteAll() {
        ImageDatabase.databaseWriteExecutor.execute(() -> imageDao.deleteAll());
    }

    LiveData<List<ImageEntity>> getAllImages() {
        return images;
    }

    void insert(ImageEntity image) {
        ImageDatabase.databaseWriteExecutor.execute(() -> imageDao.insert(image));
    }

    void deleteWithId(long id) {
        ImageDatabase.databaseWriteExecutor.execute(() -> imageDao.deleteWithId(id));
    }


}
