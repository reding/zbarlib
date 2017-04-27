package com.example.zbarlib;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.io.IOException;
import java.io.InputStream;

public class TestDecodeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_decode);

        try {
            InputStream image = getAssets().open("qrcode.png");
            Bitmap qrcode = BitmapFactory.decodeStream(image);
            byte[] bytes = RGBToYUV.getYUV420sp(qrcode.getWidth(), qrcode.getHeight(), qrcode);
            Image qrimage = new Image(qrcode.getWidth(), qrcode.getHeight(), "Y800");
            qrimage.setData(bytes);
            ImageScanner scanner = new ImageScanner();
            scanner.scanImage(qrimage);
            SymbolSet syms = scanner.getResults();
            for (Symbol sym : syms) {
                Log.d("GJP", sym.getData());
                ((TextView)findViewById(R.id.result)).setText(sym.getData());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Image image = new Image()
    }

}
