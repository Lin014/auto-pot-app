package com.example.potapp.chain;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.example.potapp.PotManager;
import com.example.potapp.R;
import com.example.potapp.command.ChangePlantNameCommand;
import com.example.potapp.command.Command;
import com.example.potapp.command.Constant;
import com.example.potapp.factory.CustomDialog;
import com.example.potapp.factory.CustomDialogFactory;
import com.example.potapp.factory.TwoBtnMsgDialogFactory;

public class CheckPlantName extends PlantHandler {
    protected String message = "植物名稱尚未設定，請先去設定植物名稱。";

    public CheckPlantName(PlantHandler next) {
        super(next);
    }

    @Override
    public void handleRequest(int potPosition, Context context) {
        PotManager.setPosition(potPosition);
        if (PotManager.getPot().getPlant_name_change_state() == 0) {

            // factory
            CustomDialogFactory dialogFactory =
                    new TwoBtnMsgDialogFactory(context, "訊息", message, "前往", "取消");
            CustomDialog customDialog = dialogFactory.createCustomDialog();
            customDialog.showDialog();

            Button okBtn = customDialog.findViewById(R.id.positiveButton);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Command changePlantNameCommand = new ChangePlantNameCommand(potReceiver);
                    potInvoker.addCommand(changePlantNameCommand);
                    potInvoker.run(Constant.ChangePlantName, potPosition, context);
                    customDialog.dismiss();
                }
            });

        } else {
            toNext(potPosition, context);
        }
    }
}
