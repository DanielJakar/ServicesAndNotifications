package danandroid.course.servicesandnotifications;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.picasso.Picasso;
import pl.aprilapps.easyphotopicker.EasyImage;

public class ChooserActivity extends AppCompatActivity {

    private static final int RC_WRITE = 1;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.fabPick)
    FloatingActionButton fabPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {

            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                if (imageFiles.size() > 0) {
                    File file = imageFiles.get(0);
                    //String name = file.getName();
                    Uri uri = Uri.fromFile(file);
                    //Bitmap vs file -> file.
                    //String absolutePath = file.getAbsolutePath();
                    Picasso.with(getApplicationContext()).load(file).into(ivPhoto);


                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    @OnClick(R.id.fabPick)
    public void onFabPick() {
        if (!checkStoragePermission())return;
        EasyImage.openChooserWithGallery(this, "Pick it...", 0);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_WRITE && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            onFabPick();
    }

    private boolean checkStoragePermission (){
        int resultCode = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        boolean granted = resultCode == PackageManager.PERMISSION_GRANTED;

        if (!granted){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RC_WRITE);
        }

        return granted;
    }
}
