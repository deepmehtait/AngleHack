package hack.com.anglehack.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import hack.com.anglehack.R;

/**
 * Created by Deep on 25-Jun-16.
 */
public class HomeActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;

    CircleImageView scan,voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        scan = (CircleImageView) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        voice = (CircleImageView)findViewById(R.id.alexa);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,voiceActivity.class);
                startActivity(i);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            final File finalFile = new File(getRealPathFromURI(tempUri));
            Log.d("filen pahth", finalFile.toString());


            new UploadImage().execute(finalFile.toString());


        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public class UploadImage extends AsyncTask<String, Void, Integer> {
        String filePath;

        @Override
        protected Integer doInBackground(String... params) {
            try {

                filePath = params[0];
                try {
                    long timeNow = System.currentTimeMillis();
                    FileInputStream stream = new FileInputStream(filePath);
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials("AKIAI2JHWM7SN5KQGW4Q", "nwAjmhmgfk6luCHIU4WNijRdY9YJJlrMggpP4W6X"));
                    PutObjectRequest request = new PutObjectRequest("angel-hacks/", timeNow + ".jpg", stream, objectMetadata);
                    PutObjectResult response = s3Client.putObject(request);
                    Toast.makeText(HomeActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("response", response.toString());
                } catch (Exception e) {
                    Toast.makeText(HomeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Error", e.toString());
                }
            } catch (Exception e) {

            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
        }
    }
}
