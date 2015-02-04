package com.judopay.android.library.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * Custom font utils for retrieving fonts
 * Created by scottab on 28/10/2014.
 */
public class Typefaces {
    private static final String TAG = "Typefaces";

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    /**
     * Work around for leak bug https://code.google.com/p/android/issues/detail?id=9904
     * @param c
     * @param assetPath
     * @return
     */
    public static Typeface get(Context c, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(),
                            assetPath);
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + assetPath
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }


    /**
     * Helper method to load (and cache font in app private folder) typeface from raw dir this is needed because /assets from library projects are not merged in Eclipse
     * @param c
     * @param resource ID of the font.ttf in /raw
     * @return
     */
    public static Typeface loadTypefaceFromRaw(Context c, int resource)
    {
        Typeface tf = null;

        InputStream is = c.getResources().openRawResource(resource);
        String path = c.getFilesDir() + "/judo_fonts";
        File f = new File(path);
        if (!f.exists())
        {
            if (!f.mkdirs())
                return null;
        }

        String outPath = path + "/courier.ttf";

        File fontFile = new File(outPath);
        if (fontFile.exists()) {
            tf = Typeface.createFromFile(fontFile);
        }else{
            try
            {
                byte[] buffer = new byte[is.available()];
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outPath));
                int l = 0;
                while((l = is.read(buffer)) > 0)
                {
                    bos.write(buffer, 0, l);
                }
                bos.close();
                tf = Typeface.createFromFile(outPath);
            }
            catch (IOException e)
            {
                return null;
            }
        }
        return tf;
    }

}
