package local.uri.criminalintent;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class OpenThePhotoFragment extends DialogFragment {

    private String path;
    private static final String PATH_TO_PHOTO = "photo_path";

    public static OpenThePhotoFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putString(PATH_TO_PHOTO, path);

        OpenThePhotoFragment openThePhotoFragment = new OpenThePhotoFragment();
        openThePhotoFragment.setArguments(args);
        return openThePhotoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        path = getArguments().getString(PATH_TO_PHOTO);
        View v = inflater.inflate(R.layout.dialog_photo, null);
        ImageView imageView = v.findViewById(R.id.dialog_photo_full);
        imageView.setImageBitmap(BitmapFactory.decodeFile(path));
        return v;
    }
}