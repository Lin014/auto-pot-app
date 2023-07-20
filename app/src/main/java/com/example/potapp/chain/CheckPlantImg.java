package com.example.potapp.chain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.potapp.ImageConvert;
import com.example.potapp.PotManager;
import com.example.potapp.R;
import com.example.potapp.RetrofitAPI;
import com.example.potapp.RetrofitManger;
import com.example.potapp.command.ChangePlantImgCommand;
import com.example.potapp.command.Command;
import com.example.potapp.command.Constant;
import com.example.potapp.entity.Pot;
import com.example.potapp.factory.CustomDialog;
import com.example.potapp.factory.CustomDialogFactory;
import com.example.potapp.factory.ThreeBtnMsgDialogFactory;
import com.example.potapp.observer.PotSubject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckPlantImg extends PlantHandler {
    Bitmap bitmap;

    protected String message = "植物圖片尚未設定，請先去設定植物圖片。";

    private Context context1;

    public CheckPlantImg(PlantHandler next) {
        super(next);
    }

    @Override
    public void handleRequest(int potPosition, Context context) {
        context1 = context;

        PotManager.setPosition(potPosition);
        if (PotManager.getPot().getPlant_img_change_state() == 0) {

            // factory
            CustomDialogFactory dialogFactory =
                    new ThreeBtnMsgDialogFactory(context, "訊息", message,
                            "前往", "取消", "設為預設");
            CustomDialog customDialog = dialogFactory.createCustomDialog();
            customDialog.showDialog();

            Button okBtn = customDialog.findViewById(R.id.positiveButton);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Command ChangePlantImgCommand = new ChangePlantImgCommand(potReceiver);
                    potInvoker.addCommand(ChangePlantImgCommand);
                    potInvoker.run(Constant.ChangePlantImg, potPosition, context);
                    customDialog.dismiss();
                }
            });


            Button setBtn = customDialog.findViewById(R.id.neutralButton);
            setBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bitmap = BitmapFactory.decodeResource(context1.getResources(), R.drawable.icon_potted_plant);
                    bitmap = changePhotoSize(bitmap, 100, 100);
                    turnRound();

                    Pot pot = PotManager.getPot();
                    pot.setPlant_img(Arrays.toString(ImageConvert.BitmapToByte(bitmap)));
                    RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
                    Call<Pot> call = retrofitAPI.changePlantImg(pot);
                    call.enqueue(new Callback<Pot>() {
                        @Override
                        public void onResponse(Call<Pot> call, Response<Pot> response) {
                            PotManager.setPlantImg(bitmap);
                            Toast.makeText(context1, "圖片更改成功", Toast.LENGTH_SHORT).show();
                            changePlantImgState(pot, 1);
                            // update mainActivity img
                            PotSubject.getInstance().setPot(pot);
                            customDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Pot> call, Throwable t) {
                            Toast.makeText(context, "伺服器故障", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        } else {
            toNext(potPosition, context);
        }
    }

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
//                    Toast.makeText(context1, "更改植物照片狀態成功: " + response.body().getPlant_img_change_state(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Toast.makeText(context1, "伺服器故障", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
