package dev.filterme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EditaFoto extends ActionBarActivity {
    private static int REQUEST_DIALOG = 1;
    private Bitmap bmp;
    private Bitmap r;
    private Filtre f;
    private ArrayList<Bitmap> accum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_foto);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String foto = i.getStringExtra("foto");

        accum = new ArrayList<>();
        //FOTO
        carregaFoto(foto);
    }


    private void carregaFoto(String foto){
        final ImageView show = (ImageView) findViewById(R.id.foto);
        bmp = decodeSampledBitmapFromFile(foto, 300, 300);
        r = bmp; //Si no hem aplicat cap filtre, s'ha de veure igualment la imatge
        show.setImageBitmap(bmp);

        show.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    show.setImageBitmap(bmp);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    show.setImageBitmap(r);
                }
                return true;
            }
        });
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String file,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, options);

    }

    private void mostraFoto(){
        ImageView show = (ImageView) findViewById(R.id.foto);
        show.setImageBitmap(r);
    }

    // #### ---- FILTRES ---- #### //

    public void aplicaFiltreBlur(View view){
        f = new Blur(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }


    /*
    public void aplicaFiltreMotionBlur(View view){
        MotionBlur m = new MotionBlur(bmp);
        r = m.aplicaFiltre();
        ImageView show = (ImageView) findViewById(R.id.foto);
        show.setImageBitmap(r);
    }
    */
    public void aplicaGrayScale(View view){
        f = new Grayscale(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }

    public void aplicaFiltreSharpen(View view){
        f = new Sharpen(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFiltreEmboss(View view){
        f = new Emboss(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFiltreEdgeDetection(View view){
        f = new EdgeDetection(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFiltreFindEdges(View view){
        f = new FindEdges(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFiltreEdges(View view){
        f = new Sculpting(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFiltreBlackWhite(View view){
        f = new BlackWhite(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaSepia(View view){
        f = new Sepia(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaCalidDivertit(View view){
        f = new CalidDivertit(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaCalid(View view){
        f = new Calid(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFred(View view){
        f = new Fred(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaDivertit(View view){
        f = new Divertit(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaNegatiu(View view){
        f = new Negative(bmp);
        r = f.aplicaFiltre();
        mostraFoto();
    }

    // ### ---- FILTRES (fi) ---- #### //
    private void guardaImatge() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = Environment.getExternalStoragePublicDirectory(
        //Environment.DIRECTORY_PICTURES);s
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "FilterMe");
        try {
            if(!dir.exists()){
                System.out.println(dir.mkdirs());
            }
            File image = new File(dir, File.separator + imageFileName + ".jpg");
            FileOutputStream fo = new FileOutputStream(image);
            r.compress(Bitmap.CompressFormat.JPEG, 100, fo);
            fo.flush();
            fo.close();
            //System.out.println("L'arxiu està a: " + image.getAbsolutePath());

            //Per a que la galeria ho trobi
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(image);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
        }
        catch (Exception e){
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void afegeixFiltreAccum(){
        Bitmap af = r.copy(r.getConfig(), true);
        accum.add(af);
        if(accum.size() > 3)
            accum.remove(0);
        bmp = r;
    }

    private void treuFiltreAccum(){
        if(accum.size() != 0) {
            r = bmp;
            //TODO No ho desfà
            bmp = accum.get(accum.size()-1);
            accum.remove(accum.size()-1);

            ImageView show = (ImageView) findViewById(R.id.foto);
            show.setImageBitmap(r);
        }
        else {
            if(!bmp.equals(r)){
                r = bmp;
                ImageView show = (ImageView) findViewById(R.id.foto);
                show.setImageBitmap(r);
            }else{
                Toast.makeText(getApplicationContext(), "You haven't applied a filter yet!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showSaveDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message);
        builder.setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                guardaImatge();
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.dialog_saved_true, Toast.LENGTH_LONG).show();
            }

        });

        builder.setNegativeButton(R.string.dialog_reject, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        Button reject = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        reject.setTextColor(getResources().getColor(R.color.maroon));
/*
        Button accept = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        accept.setTextColor(Color.GREEN);*/
    }


    private void showBackDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.dialog_title_b);
        builder.setMessage(R.string.dialog_message_b);
        builder.setPositiveButton(R.string.dialog_confirm_b, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        builder.setNegativeButton(R.string.dialog_reject_b, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        Button reject = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        reject.setTextColor(getResources().getColor(R.color.maroon));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO Opció "Guardar" i "Aplicar". En donar-li a "Aplicar", apliquem el filtre a la foto. Podem desfer un sol cop.
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_edita_foto, menu);
        getMenuInflater().inflate(R.menu.menu_edita_foto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if(id == R.id.action_save){
            showSaveDialog();
        }
        if(id == R.id.action_apply){
            afegeixFiltreAccum();
        }

        if(id == R.id.action_undo){
            treuFiltreAccum();
        }
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showBackDialog();
    }

}
