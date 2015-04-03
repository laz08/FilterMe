package dev.filterme;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {
    private static int IMATGE_CODI = 1;
    private static int REQUEST_IMAGE_TAKE_PHOTO = 1;
    String imgDecodableString;
    Uri newImgPath;
    File file;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        /* Change title's font type */
        /* TextView title = (TextView) findViewById(R.id.title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/LaurenScript.otf");
        title.setTypeface(tf);*/
        }

    public void opGalleryAndToast(View view){
        //Open Gallery and show toast
        Toast.makeText(getApplicationContext(), getString(R.string.gallery_op), Toast.LENGTH_SHORT).show();
        carregarImatge();
    }


    public void carregarImatge() {
        //Agafar només imatges
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMATGE_CODI);
    }

    public void takePhotoAndToast(View view){
        Toast.makeText(getApplicationContext(), getString(R.string.camera_op), Toast.LENGTH_SHORT).show();
        Intent pic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pic.resolveActivity(getPackageManager()) != null) {
            try {
                file = createImageFile();
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), getString(R.string.no_file_created), Toast.LENGTH_LONG).show();
            }
            if(file != null){
                newImgPath = Uri.fromFile(file);
                pic.putExtra(MediaStore.EXTRA_OUTPUT, newImgPath);
                startActivityForResult(pic, REQUEST_IMAGE_TAKE_PHOTO);
            }
        }
    }

    public void goToAbout(View view){
        Intent ab = new Intent(this, About.class);
        startActivity(ab);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        path = "file:" + image.getAbsolutePath();
        return image;
    }


    private void uriToStringOnImgDecodable(Uri img){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        // Get the cursor
        Cursor cursor = getContentResolver().query(img, filePathColumn, null, null, null);
        // Move to first row
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
    }

    public String uriToPath(Uri img){
        return img.getEncodedPath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Image picked from Gallery
            if (requestCode == IMATGE_CODI && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                uriToStringOnImgDecodable(selectedImage);
                Toast.makeText(this, getString(R.string.pic_Loaded), Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, EditaFoto.class);
                i.putExtra("foto", imgDecodableString);
                startActivity(i);
            }
            //Image picked from camera
            else if(requestCode == REQUEST_IMAGE_TAKE_PHOTO && resultCode == RESULT_OK){
                if(newImgPath != null) {
                    //Toast.makeText(this, newImgPath.getEncodedPath(), Toast.LENGTH_LONG).show();
                    Toast.makeText(this, getString(R.string.pic_Loaded), Toast.LENGTH_LONG).show();
                    imgDecodableString = uriToPath(newImgPath);
                }
                Intent i = new Intent(this, EditaFoto.class);
                i.putExtra("foto", imgDecodableString);
                startActivity(i);

            }
            else {
                Toast.makeText(this, getString(R.string.pic_not_selected), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

            Toast.makeText(this, getString(R.string.pic_error_loading), Toast.LENGTH_LONG).show();
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
