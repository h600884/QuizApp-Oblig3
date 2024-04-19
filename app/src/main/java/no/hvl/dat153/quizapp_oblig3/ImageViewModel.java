package no.hvl.dat153.quizapp_oblig3;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

public class ImageViewModel extends AndroidViewModel {

    ImageRepository imageRepository;
    private MediatorLiveData<List<ImageEntity>> images;

    public ImageViewModel(Application application) {
        super(application);
        imageRepository = new ImageRepository(application);
        images = new MediatorLiveData<>();
        images.addSource(imageRepository.getAllImages(), imagesList -> images.setValue(imagesList));
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

    public void deleteAll() {
        imageRepository.deleteAll();
    }
}
