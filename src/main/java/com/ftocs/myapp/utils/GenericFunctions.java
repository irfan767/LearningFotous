package com.ftocs.myapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GenericFunctions {

    private static final String TAG = "GenericFunctions";
    private static final int IO_BUFFER_SIZE = 4 * 1024;


    public static String ConvertIntoDateTimeFormat(String apiDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy");
        SimpleDateFormat converDateFormat = new SimpleDateFormat("MM/d/yyyy");
        try {
            Date date = simpleDateFormat.parse(apiDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return converDateFormat.format(c.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "ConvertIntoDateTimeFormat: " + e.getMessage());

            return null;
        }

    }

    public static String ConvertApiIntoDateTimeFormat(String apiDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d'T'hh:mm:ss");
        SimpleDateFormat converDateFormat = new SimpleDateFormat("MM/d/yyyy");
        try {
            Date date = simpleDateFormat.parse(apiDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return converDateFormat.format(c.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "ConvertIntoDateTimeFormat: " + e.getMessage());

            return null;
        }

    }

    public static String ConvertApiIntoMonth(String apiDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d'T'hh:mm:ss");
        SimpleDateFormat converDateFormat = new SimpleDateFormat("MM");
        try {
            Date date = simpleDateFormat.parse(apiDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return converDateFormat.format(c.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "ConvertIntoDateTimeFormat: " + e.getMessage());

            return null;
        }

    }

    public static String ConvertApiIntoTime(String apiDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d'T'hh:mm:ss");
        SimpleDateFormat converDateFormat = new SimpleDateFormat("hh:mm a");
        try {
            Date date = simpleDateFormat.parse(apiDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return converDateFormat.format(c.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "ConvertIntoDateTimeFormat: " + e.getMessage());

            return null;
        }

    }

    public static String ConvertApiIntoMonthWord(String apiDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d'T'hh:mm:ss");
        SimpleDateFormat converDateFormat = new SimpleDateFormat("MMMM");
        try {
            Date date = simpleDateFormat.parse(apiDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return converDateFormat.format(c.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "ConvertIntoDateTimeFormat: " + e.getMessage());

            return null;
        }

    }

    public static String ConvertApiIntoMonthWordDayYear(String apiDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d'T'hh:mm:ss");
        SimpleDateFormat converDateFormat = new SimpleDateFormat("MMMM d,yyyy");
        try {
            Date date = simpleDateFormat.parse(apiDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return converDateFormat.format(c.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "ConvertIntoDateTimeFormat: " + e.getMessage());

            return null;
        }

    }

    public static String ConvertApiIntoWordDay(String apiDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d'T'hh:mm:ss");
        SimpleDateFormat converDateFormat = new SimpleDateFormat("EEEE");
        try {
            Date date = simpleDateFormat.parse(apiDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return converDateFormat.format(c.getTime());
        } catch (ParseException e) {
            Log.e(TAG, "ConvertIntoDateTimeFormat: " + e.getMessage());

            return null;
        }

    }

    public static String[] splitString(String stringToSplit, String delimiting) {

        if (stringToSplit.contains(delimiting)) {
            String[] parts = stringToSplit.split(delimiting);

            return parts;
        } else {
            throw new IllegalArgumentException("String " + stringToSplit + " does not contain" + delimiting);
        }
    }

    public static Bitmap downloadImage(String urlImage) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            URL url = new URL(urlImage);
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(new FlushedInputStream(stream), null, bmOptions);
            if (stream!=null) {
                stream.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }

    private static InputStream getHttpConnection(URL url)
            throws IOException {
        InputStream stream = null;
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }
    }

    public static Bitmap downloadImage2(String fileUrl){
        Bitmap bmImg = null;
        URL myFileUrl =null;
        try {
            myFileUrl= new URL(fileUrl);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bmImg = BitmapFactory.decodeStream(new FlushedInputStream(is));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bmImg;
    }

    public static Bitmap getImageFromUrl(String urlImage) {
        InputStream in;
        Bitmap bmp = null;
        try {
            in = new URL(urlImage).openStream();
            bmp = BitmapFactory.decodeStream(new FlushedInputStream(in));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bmp;
    }

    public static Bitmap grabImageFromUrl(String imageUrlInput)
    {
        Bitmap bmp = null;
        try {
            String url1 = imageUrlInput;
            URL ulrn = new URL(url1);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if (null == bmp) System.out.println("The Bitmap is NULL");
            con.disconnect();
        } catch(Exception e) {

        }
        return bmp;
    }



    public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        } catch (IOException e) {
            Log.e(TAG, "Could not load Bitmap from: " + url);
        } finally {
            closeStream(in);
            closeStream(out);
        }

        return bitmap;
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close stream", e);
            }
        }
    }

    /**
     * Copy the content of the input stream into the output stream, using a
     * temporary byte array buffer whose size is defined by
     * {@link #IO_BUFFER_SIZE}.
     *
     * @param in The input stream to copy from.
     * @param out The output stream to copy to.
     * @throws IOException If any error occurs during the copy.
     */
    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
}
