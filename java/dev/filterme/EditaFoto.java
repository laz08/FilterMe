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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EditaFoto extends ActionBarActivity {
    private static int REQUEST_DIALOG = 1;
    private Bitmap bmp; //Bmp inicial
    private Bitmap bmpF; //Bmp amb filtre
    private Filtre f;
    private boolean modified;
    private ArrayList<Bitmap> accum;
    LayoutAnimationController controller;
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
        modified = false;
        //FOTO
        carregaFoto(foto);
        preparaController();
        preparaMenuInferior();
    }

    private void preparaController(){
        // ANIMACiONS FOTO A FOTO
        AnimationSet setAn = new AnimationSet(true);
        Animation anim = new AlphaAnimation(0.0f, 1.0f); //De invisible a visible
        anim.setDuration(300);
        setAn.addAnimation(anim);
        anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        anim.setDuration(100);
        setAn.addAnimation(anim);
        controller = new LayoutAnimationController(setAn, 0.25f);
    }

    private void preparaMenuInferior(){
        LinearLayout menuInf = (LinearLayout) findViewById(R.id.lmenu);
        menuInf.setLayoutAnimation(controller);
        menuInf.setVisibility(HorizontalScrollView.VISIBLE);
        menuInf = (LinearLayout) findViewById(R.id.layMill);
        menuInf.setVisibility(HorizontalScrollView.GONE);
        HorizontalScrollView menuInf2 = (HorizontalScrollView) findViewById(R.id.filtresMill);
        menuInf2.setVisibility(HorizontalScrollView.GONE);
        menuInf = (LinearLayout) findViewById(R.id.layArt);
        menuInf.setVisibility(HorizontalScrollView.GONE);
        menuInf2 = (HorizontalScrollView) findViewById(R.id.filtresArt);
        menuInf2.setVisibility(HorizontalScrollView.GONE);
    }
    public void mostraMenuInf(View view){
        preparaMenuInferior();
    }
    public void mostraArt(View view){
        LinearLayout menuInf = (LinearLayout) findViewById(R.id.lmenu);
        menuInf.setVisibility(HorizontalScrollView.GONE);
        HorizontalScrollView menuInf2 = (HorizontalScrollView) findViewById(R.id.filtresArt);
        menuInf2.setVisibility(HorizontalScrollView.VISIBLE);
        menuInf = (LinearLayout) findViewById(R.id.layArt);
        menuInf.setLayoutAnimation(controller);
        menuInf.setVisibility(HorizontalScrollView.VISIBLE);
    }
    public void mostraMill(View view){
        LinearLayout menuInf = (LinearLayout) findViewById(R.id.lmenu);
        menuInf.setVisibility(HorizontalScrollView.GONE);
        HorizontalScrollView menuInf2 = (HorizontalScrollView) findViewById(R.id.filtresMill);
        menuInf2.setVisibility(HorizontalScrollView.VISIBLE);
        menuInf = (LinearLayout) findViewById(R.id.layMill);
        menuInf.setLayoutAnimation(controller);
        menuInf.setVisibility(HorizontalScrollView.VISIBLE);
    }

    private void carregaFoto(String foto){
        final ImageView show = (ImageView) findViewById(R.id.foto);
        bmp = decodeSampledBitmapFromFile(foto, 300, 300);
        bmpF = bmp; //Si no hem aplicat cap filtre, s'ha de veure igualment la imatge
        show.setImageBitmap(bmp);

        show.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    show.setImageBitmap(bmp);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    show.setImageBitmap(bmpF);
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
        show.setImageBitmap(bmpF);
    }

    // #### ---- FILTRES ---- #### //

    public void aplicaFiltreBlur(View view){
        f = new Blur(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }


    /*
    public void aplicaFiltreMotionBlur(View view){
        MotionBlur m = new MotionBlur(bmp);
        bmpF = m.aplicaFiltre();
        ImageView show = (ImageView) findViewById(R.id.foto);
        show.setImageBitmap(bmpF);
    }
    */
    public void aplicaGrayScale(View view){
        f = new Grayscale(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }

    public void aplicaFiltreSharpen(View view){
        f = new Sharpen(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFiltreEmboss(View view){
        f = new Emboss(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFiltreEdgeDetection(View view){
        f = new EdgeDetection(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFiltreFindEdges(View view){
        f = new FindEdges(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFiltreEdges(View view){
        f = new Sculpting(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFiltreBlackWhite(View view){
        f = new BlackWhite(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaSepia(View view){
        f = new Sepia(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaCalidDivertit(View view){
        f = new CalidDivertit(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaCalid(View view){
        f = new Calid(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaFred(View view){
        f = new Fred(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaDivertit(View view){
        f = new Divertit(bmp);
        bmpF = f.aplicaFiltre();
        mostraFoto();
    }
    public void aplicaNegatiu(View view){
        f = new Negative(bmp);
        bmpF = f.aplicaFiltre();
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
            bmpF.compress(Bitmap.CompressFormat.JPEG, 100, fo);
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
        modified = true;
        Bitmap af = bmp.copy(bmp.getConfig(), true);
        accum.add(af);
        if(accum.size() > 3)
            accum.remove(0);
        bmp = bmpF;
    }

    private void treuFiltreAccum(){
        if(accum.size() != 0){
            bmp = bmpF = accum.get(accum.size()-1);
            accum.remove(accum.size()-1);
            mostraFoto();
        }
        else {
            if(!bmp.equals(bmpF)){
                bmpF = bmp;
                mostraFoto();
            }else{
                if(!modified)
                    Toast.makeText(getApplicationContext(), "You haven't applied a filter yet!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "You can't undo anymore ", Toast.LENGTH_SHORT).show();
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
