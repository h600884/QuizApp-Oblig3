package no.hvl.dat153.quizapp_oblig3;

import android.app.Application;
import android.media.Image;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;

    private LiveData<List<ImageEntity>> images;

    public ImageViewModel(Application application) {
        super(application);
        imageRepository = new ImageRepository(application);
        images = imageRepository.getAllImages();
    }

    LiveData<List<ImageEntity>> getAllImages() {
        return images;
    }

    public void insert(ImageEntity image) {
        imageRepository.insert(image);
    }

    public void deleteWithId(long id) {
        imageRepository.deleteWithId(id);
    }

    public LiveData<List<ImageEntity>> getAllImagesDesc() {
        return imageRepository.getAllImageDesc();
    }

    public LiveData<List<ImageEntity>> getAllImagesAsc() {
        return imageRepository.getAllImageAsc();
    }

    public void deleteAll() {
        imageRepository.deleteAll();
    }

}
