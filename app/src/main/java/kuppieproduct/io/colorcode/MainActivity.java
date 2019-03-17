package kuppieproduct.io.colorcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static int READ_REQUEST_CODE = 100;

    ImageView imageView;
    View colorPreviewView;
    Button selectImageButton;

    ListView colorListView;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        colorPreviewView = findViewById(R.id.colorPreview);
        colorListView = (ListView) findViewById(R.id.colorListView);


        selectImageButton = (Button) findViewById(R.id.selectImage);

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

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                intent.setType("image/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });

        adapter = new CustomAdapter(this, 0, new ArrayList<Item>());
        colorListView.setAdapter(adapter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addColor(View view) {
        int color = ((ColorDrawable) colorPreviewView.getBackground()).getColor();
        String colorCode = "#" + Integer.toHexString(color).toUpperCase().substring(2);
        adapter.add(new Item(colorCode, color));
        adapter.notifyDataSetChanged();
    }
}
