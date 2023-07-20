package com.example.potapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.potapp.entity.Pot;
import com.example.potapp.observer.PotSubject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageActivity extends AppCompatActivity {
    private Uri imageUri;
    private Toolbar toolbar;
    ImageButton takePhoto;
    ImageView photo;
    ImageButton selectPhoto;
    Button confirmBtn, cancelBtn, turnBtn;
    public static final int REQUEST_CODE_TAKE = 1;
    public static final int REQUEST_CODE_CHOOSE = 0;
    Bitmap bitmap = null;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        // ---------------- ActionBar ----------------
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        // ---------------- findViewById -----------------------------------
        takePhoto = findViewById(R.id.take_photo);
        photo = findViewById(R.id.photo);
        selectPhoto = findViewById(R.id.select_photo);
        confirmBtn = findViewById(R.id.confirmBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        turnBtn = findViewById(R.id.turnBtn);
        // ---------------- setListener -----------------------------------
        takePhoto.setOnClickListener(takePhotoEvent);
        selectPhoto.setOnClickListener(selectPhotoEvent);
        confirmBtn.setOnClickListener(confirmEvent);
        cancelBtn.setOnClickListener(cancelEvent);
        turnBtn.setOnClickListener(turnEvent);
    }

    private final View.OnClickListener turnEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            turnAround();
        }
    };

    private final View.OnClickListener takePhotoEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                doTake();
            }else{
                //去申請權限
                ActivityCompat.requestPermissions(ImageActivity.this,new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    };

    private final View.OnClickListener selectPhotoEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                //真正去打開相冊
                openAlbum();
            }else{
                //去申請權限
                ActivityCompat.requestPermissions(ImageActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_CHOOSE);
            }
        }
    };

    private final View.OnClickListener confirmEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            System.out.println("壓縮前圖片大小: " + bitmap.getByteCount() / 1024 / 1024 + "MB" + " 寬度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight());
//
//            Bitmap testPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.add_plant);
//            ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
//            testPhoto.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream3);
//            byte[] bytes3 = byteArrayOutputStream3.toByteArray();
//            Bitmap result3 = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
//            System.out.println("壓縮後圖片大小: " + result3.getByteCount() / 1024 / 1024 + "MB" + " 寬度: " + result3.getWidth() + " 高度: " + result3.getHeight() + " bytes.length: " + bytes3.length / 1024 + "KB quality: " + 100);
//
//            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
//            byte[] bytes2 = byteArrayOutputStream2.toByteArray();
//            Bitmap result2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
//            System.out.println("壓縮後圖片大小: " + result2.getByteCount() / 1024 / 1024 + "MB" + " 寬度: " + result2.getWidth() + " 高度: " + result2.getHeight() + " bytes.length: " + bytes2.length / 1024 + "KB quality: " + 100);

            if (bitmap != null) {
                loadEvent();

                // 儲存圖片
                int position = getIntent().getIntExtra("potPosition", 0);
                PotManager.setPosition(position);
                Pot pot = PotManager.getPot();
                pot.setPlant_img(Arrays.toString(ImageConvert.BitmapToByte(bitmap)));
                RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
                Call<Pot> call = retrofitAPI.changePlantImg(pot);
                call.enqueue(new Callback<Pot>() {
                    @Override
                    public void onResponse(Call<Pot> call, Response<Pot> response) {
                        if (response.code() == 200) {
                            // update list
                            PotManager.setPlantImg(bitmap);
                            Toast.makeText(getApplicationContext(), "圖片更改成功", Toast.LENGTH_SHORT).show();
                            if (PotManager.getPlantImgChangeState() != 1) {
                                changePlantImgState(pot, 1);
                            }
                            // update mainActivity img
                            PotSubject.getInstance().setPot(pot);
                            progressDialog.dismiss();

                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<Pot> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "伺服器故障", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "未選擇圖片", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private final View.OnClickListener cancelEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private Bitmap changePhotoSize(Bitmap b, int newWidth, int newHeight) {
        Bitmap newB = null;
        if (b != null) {
            int width = b.getWidth();
            int height = b.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newB = Bitmap.createBitmap(b, 0, 0, width, height, matrix, true);
        }
        return newB;
    }

    public void doTake() {
        File imageTemp = new File(getExternalCacheDir(),"imageOut.jpeg");
        if(imageTemp.exists()){
            imageTemp.delete();
        }
        try{
            imageTemp.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>24){
            //contentProvider
            imageUri = FileProvider.getUriForFile(this, "com.example.potapp.fileprovider", imageTemp);
        }else{
            imageUri = Uri.fromFile(imageTemp);
        }
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        //指定圖片輸出地方。
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //拍完照startActivityForResult() 結果返回onActivityResult() 函數
        startActivityForResult(intent,REQUEST_CODE_TAKE);
    }

    //接收照片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == REQUEST_CODE_TAKE){
            if(resultCode == RESULT_OK) {
                //獲取拍攝照片
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    //旋轉照片
//                  rotateBitmapByDegree(output,getBitmapDegree(String.valueOf(imageUri)));
//                  //顯示圖片
//                  photo.setImageBitmap(rotateBitmapByDegree(output, getBitmapDegree(String.valueOf(imageUri))));

                    // 壓縮圖片
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    Bitmap result = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    System.out.println("壓縮後圖片大小: " + result.getByteCount() / 1024 / 1024 + "MB" + " 寬度: " + result.getWidth() + " 高度: " + result.getHeight() + " bytes.length: " + bytes.length / 1024 + "KB quality: " + 0);
                    bitmap = result;
                    bitmap = changePhotoSize(bitmap, 100, 100);
                    System.out.println("壓縮後圖片大小: " + bitmap.getByteCount() / 1024 + "KB" + " 寬度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight());
                    turnRound();

                    photo.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                }
            }
        }else if(requestCode == REQUEST_CODE_CHOOSE){
            if(Build.VERSION.SDK_INT<19){
                handleImageBeforeApi19(data);
            }else{
                handleImageOnApi19(data);
            }
        }
    }

    @TargetApi(19)
    private void handleImageBeforeApi19(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }
    private void handleImageOnApi19(Intent data) {
        String imagePath = null;
        try {
            Uri uri = data.getData();
            if (DocumentsContract.isDocumentUri(this, uri)) {
                String documentId = DocumentsContract.getDocumentId(uri);
                if (TextUtils.equals(uri.getAuthority(), "com.android.providers.media.documents")) {
                    String id = documentId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if (TextUtils.equals(uri.getAuthority(), "com.android.providers.downloads.documents")) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(documentId));
                    imagePath = getImagePath(contentUri, null);
                }
            }else if("content".equalsIgnoreCase(uri.getScheme())){
                imagePath = getImagePath(uri, null);
            }else if("file".equalsIgnoreCase(uri.getScheme())){
                imagePath = uri.getPath();

            }
            displayImage(imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    // 展示照片
    private void displayImage(String imagePath){
        if(imagePath != null){
            bitmap = BitmapFactory.decodeFile(imagePath);

            // 壓縮圖片
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            Bitmap result = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            System.out.println("壓縮後圖片大小: " + result.getByteCount() / 1024 / 1024 + "MB" + " 寬度: " + result.getWidth() + " 高度: " + result.getHeight() + " bytes.length: " + bytes.length / 1024 + "KB quality: " + 0);
            bitmap = result;
            bitmap = changePhotoSize(bitmap, 100, 100);
            System.out.println("壓縮後圖片大小: " + bitmap.getByteCount() / 1024 + "KB" + " 寬度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight());
            turnRound();
            photo.setImageBitmap(bitmap);
        }
    }

    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                doTake();
            }else{
                Toast.makeText(this,"你沒有獲得相機權限", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 0){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openAlbum();
            }else{
                openAlbum();
//                Toast.makeText(this,"你沒有獲得相簿權限", Toast.LENGTH_SHORT).show();
                //openAlbum();
            }
        }
    }

    private void turnAround() {
        if (bitmap == null) {
            Toast.makeText(getApplicationContext(), "未選擇照片", Toast.LENGTH_SHORT).show();
        } else {
            Bitmap oBitmap = bitmap;
            Matrix matrix = new Matrix();
            int width = oBitmap.getWidth();
            int height = oBitmap.getHeight();
            matrix.setRotate(90);
            bitmap = Bitmap.createBitmap(oBitmap, 0, 0, width, height, matrix, true);
            photo.setImageBitmap(bitmap);
        }
    }

    private void turnRound(){
        Bitmap oBitmap = bitmap;
        int width = oBitmap.getWidth();
        int height = oBitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;

        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(oBitmap, src, dst, paint);
    }

    private void changePlantImgState(Pot pot1, int state) {
        pot1.setPlant_img_change_state(state);
        RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
        Call<Pot> call = retrofitAPI.changePlantImgState(pot1);
        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {
                if (response.code() == 200) {
                    PotManager.setPlantImgChangeState(state);
//                    Toast.makeText(getApplicationContext(), "更改植物照片狀態成功: " + response.body().getPlant_img_change_state(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "伺服器故障", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadEvent() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}