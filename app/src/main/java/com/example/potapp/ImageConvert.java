package com.example.potapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//圖片轉檔
public class ImageConvert {

    //bitmap 轉 byte(可用)
    public static byte[] BitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        return bytes;
    }

    //byte 轉 bitmap(可用)
    public static Bitmap ByteToBitmap(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        return bitmap;
    }

    public static Bitmap ByteAryStringToBitmap(String imgStr) {
        List<Byte> byteList1 = new ArrayList<>();
        String[] ary = imgStr.split("");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < ary.length - 1; i++) {
            if (ary[i].equals(",")) {
                int a = Integer.parseInt(stringBuilder.toString());
                byteList1.add((byte) a);
                stringBuilder = new StringBuilder();
            } else if (ary[i].equals(" ")) {

            } else {
                stringBuilder.append(ary[i]);
            }
        }
        byte[] imgArray = listToByte(byteList1);
        return ByteToBitmap(imgArray);
    }

    public static byte[] listToByte(List<Byte> list) {
        if (list == null || list.size() < 0) {
            return null;
        }
        byte[] bytes = new byte[list.size()];
        int i  = 0;
        Iterator<Byte> iterator = list.iterator();
        while(iterator.hasNext()) {
            bytes[i] = iterator.next();
            i++;
        }
        return bytes;
    }
}
