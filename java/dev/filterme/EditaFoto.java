package dev.filterme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class EditaFoto extends ActionBarActivity {

    private Bitmap bmp;
    private Bitmap r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_foto);
        Intent i = getIntent();
        String foto = i.getStringExtra("foto");
        ImageView show = (ImageView) findViewById(R.id.foto);
        bmp = BitmapFactory.decodeFile(foto);
        show.setImageBitmap(bmp);
        /*
        String text = "H = " + Integer.toString(bmp.getHeight()) + " \n W = " + Integer.toString(bmp.getWidth());
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        */
    }


    public void aplicaFiltreBlur(View view){
        Blur m = new Blur(bmp);
        r = m.aplicaFiltre();
        ImageView show = (ImageView) findViewById(R.id.foto);
        show.setImageBitmap(r);
    }


    public void aplicaFiltreSharpen(View view){
        Sharpen m = new Sharpen(bmp);
        r = m.aplicaFiltre();
        ImageView show = (ImageView) findViewById(R.id.foto);
        show.setImageBitmap(r);
    }
    public void aplicaFiltreEmboss(View view){
        Emboss m = new Emboss(bmp);
        r = m.aplicaFiltre();
        ImageView show = (ImageView) findViewById(R.id.foto);
        show.setImageBitmap(r);
    }
    public void aplicaFiltreEdgeDetection(View view){
        EdgeDetection m = new EdgeDetection(bmp);
        r = m.aplicaFiltre();
        ImageView show = (ImageView) findViewById(R.id.foto);
        show.setImageBitmap(r);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO Opció "Guardar" i "Aplicar". En donar-li a "Aplicar", apliquem el filtre a la foto. Podem desfer un sol cop.
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_edita_foto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
