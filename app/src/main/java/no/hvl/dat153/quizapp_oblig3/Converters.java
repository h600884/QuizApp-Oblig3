package no.hvl.dat153.quizapp_oblig3;

import android.net.Uri;
import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static Uri fromString(String value) {
        return value == null ? null : Uri.parse(value);
    }

    @TypeConverter
    public static String uriToString(Uri uri) {
        return uri == null ? null : uri.toString();
    }
}
