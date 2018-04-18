package peacocktech.in.saveimage;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
//Save image from drawable folder to internal Storage  in mobile
    public final int REQUEST_WRITE_PERMISSION = 11;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;

/*
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(f), "image/png");
        startActivity(intent);
*/
        importDB();
    }

    private void importDB() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            SaveImage();
        }
    }

    public void SaveImage() {
        String path = Environment.getExternalStorageDirectory().toString();

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.my);
        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        File mediaStorageDir = new File(path, "MyDirName");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        File f = new File(path + "/MyDirName/image1.png");
        File f1 = new File(path + "/MyDirName/image2.png");

        try {
            FileOutputStream outStream = new FileOutputStream(f);
            FileOutputStream outStream1 = new FileOutputStream(f1);

            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            bm1.compress(Bitmap.CompressFormat.PNG, 100, outStream1);
            outStream.flush();
            outStream.close();
            outStream1.flush();
            outStream1.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SaveImage();
                } else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(activity, "Permission is Required for getting list of files", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
