package kuppieproduct.io.colorcode;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    View colorPreviewView;

    ListView colorListView;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        colorPreviewView = findViewById(R.id.colorPreview);
        colorListView = (ListView) findViewById(R.id.colorListView);

        imageView.setImageResource(R.drawable.sample_image);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ImageView img = (ImageView) view;

                final int evX = (int) motionEvent.getX();
                final int evY = (int) motionEvent.getY();

                img.setDrawingCacheEnabled(true);
                Bitmap imgbmp = Bitmap.createBitmap(img.getDrawingCache());
                img.setDrawingCacheEnabled(false);

                try {
                    int pxl = imgbmp.getPixel(evX, evY);
                    colorPreviewView.setBackgroundColor(pxl);
                } catch (Exception ignore) {
                    // 何もしない
                }

                imgbmp.recycle();

                return true;
            }
        });

        adapter = new CustomAdapter(this, 0, new ArrayList<Item>());
        colorListView.setAdapter(adapter);
    }

    public void addColor(View view) {
        int color = ((ColorDrawable) colorPreviewView.getBackground()).getColor();
        adapter.add(new Item("#FFFFFF", color));
        adapter.notifyDataSetChanged();
    }
}
