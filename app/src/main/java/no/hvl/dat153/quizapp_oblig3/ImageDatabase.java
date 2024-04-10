package no.hvl.dat153.quizapp_oblig3;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {ImageEntity.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class ImageDatabase extends RoomDatabase {

    public abstract ImageDAO imageDao();

    private static volatile ImageDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    // Kjører database operasjoner asynkront på en bakgrunnstråd
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static ImageDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ImageDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ImageDatabase.class, "image_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
