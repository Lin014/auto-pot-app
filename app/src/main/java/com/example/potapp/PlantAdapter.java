package com.example.potapp;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.potapp.chain.CheckIsDataNull;
import com.example.potapp.chain.CheckPlantImg;
import com.example.potapp.chain.CheckPlantName;
import com.example.potapp.chain.PlantHandler;
import com.example.potapp.command.ChangePlantImgCommand;
import com.example.potapp.command.ChangePlantNameCommand;
import com.example.potapp.command.ChangePotNameCommand;
import com.example.potapp.command.Command;
import com.example.potapp.command.Constant;
import com.example.potapp.command.DelPotCommand;
import com.example.potapp.command.PotInvoker;
import com.example.potapp.command.PotReceiver;
import com.example.potapp.entity.Pot;

import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {

    private List<Pot> potList = new ArrayList<>();
    PlantHandler plantHandler;

//    public PlantAdapter(List<String> potNameList, List<Bitmap> imageList, List<String> plantNameList) {
//        this.potNameList = potNameList;
//        this.imageList = imageList;
//        this.plantNameList = plantNameList;
//    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener{
        final private TextView potName;
        final private ImageButton plantImg;
        final private TextView plantName;
        final private TextView createDate;
        final private ImageButton editPotButton;

        // command
        PotReceiver potReceiver;
        PotInvoker potInvoker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            potName = itemView.findViewById(R.id.potName);
            plantImg = itemView.findViewById(R.id.plantImg);
            plantName = itemView.findViewById(R.id.plantName);
            createDate = itemView.findViewById(R.id.createDate);
            editPotButton = itemView.findViewById(R.id.editPot);
            editPotButton.setOnClickListener(this);

            // command
            potReceiver = new PotReceiver();
            potInvoker = new PotInvoker();
            Command changePotNameCommand = new ChangePotNameCommand(potReceiver);
            Command changePlantNameCommand = new ChangePlantNameCommand(potReceiver);
            Command changePlantImgCommand = new ChangePlantImgCommand(potReceiver);
            Command delPotCommand = new DelPotCommand(potReceiver);
            potInvoker.addCommand(changePotNameCommand);
            potInvoker.addCommand(changePlantNameCommand);
            potInvoker.addCommand(changePlantImgCommand);
            potInvoker.addCommand(delPotCommand);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.editPotName:
                    // ChangePotNameCommand
                    potInvoker.run(Constant.ChangePotName, getAdapterPosition(), itemView.getContext());
                    return true;
                case R.id.editPlantName:
                    // ChangePlantNameCommand
                    potInvoker.run(Constant.ChangePlantName, getAdapterPosition(), itemView.getContext());
                    return true;
                case R.id.editPlantImg:
                    // ChangePlantImgCommand
                    potInvoker.run(Constant.ChangePlantImg, getAdapterPosition(), itemView.getContext());
                    return true;
                case R.id.delete:
                    // DelPotCommand
                    potInvoker.run(Constant.DeletePot, getAdapterPosition(), itemView.getContext());
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.context_menu_edit_pot_info);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.potted_plant_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.potName.setText(potList.get(position).getPot_name());
        holder.plantImg.setImageBitmap(ImageConvert.ByteAryStringToBitmap(potList.get(position).getPlant_img()));
        if (potList.get(position).getPlant_name() == null) {
            holder.plantName.setText("未設定");
        } else {
            holder.plantName.setText(potList.get(position).getPlant_name());
        }

        String date = potList.get(position).getPot_create_time();
        holder.createDate.setText(date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent ();
//                intent.setClass (view.getContext(), MainActivity.class);
//                intent.putExtra("position", holder.getAdapterPosition());
//                intent.putExtra("potId", potList.get(holder.getAdapterPosition()).getPot_id());
//                intent.putExtra("potName", holder.potName.getText().toString());
//                view.getContext().startActivity (intent);
                plantHandler = new CheckPlantName(new CheckPlantImg(new CheckIsDataNull(null)));
                plantHandler.handleRequest(holder.getAdapterPosition(), PottedPlantMenu.pottedPlantMenuContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return potList.size();
    }

    public List<Pot> getPotList() {
        return potList;
    }

    public void addPottedPlant(Pot pot) {
        potList.add(pot);
        notifyItemInserted(potList.size() - 1);
    }

    public void removePottedPlant(int position) {
        potList.remove(position);
        notifyItemRemoved(position);
    }

    public void setPlantNameChangeState(int position, int state) {
        potList.get(position).setPlant_name_change_state(state);
    }

    public void setPlantImgChangeState(int position, int state) {
        potList.get(position).setPlant_img_change_state(state);
    }

    public void setPotName(int position, String potName) {
        potList.get(position).setPot_name(potName);
    }

    public void setPlantName(int position, String plantName) {
        potList.get(position).setPlant_name(plantName);
    }

    public void setPlantImg(int position, String plantImg) {
        potList.get(position).setPlant_img(plantImg);
    }

    public void setPlantType(int position, String plantType) {
        potList.get(position).setPlant_type(plantType);
    }

    public void setWateringMode(int position, String wateringMode) {
        potList.get(position).setWatering_mode(wateringMode);
    }

    public void setWateringStartTime(int position, String wateringStartTime) {
        potList.get(position).setWatering_start_time(wateringStartTime);
    }

    public void setWateringEndTime(int position, String wateringEndTime) {
        potList.get(position).setWatering_end_time(wateringEndTime);
    }

    public void setLightMode(int position, String lightMode) {
        potList.get(position).setLight_mode(lightMode);
    }

    public void setLightStartTime(int position, String lightStartTime) {
        potList.get(position).setLight_start_time(lightStartTime);
    }

    public void setLightEndTime(int position, String lightEndTime) {
        potList.get(position).setLight_end_time(lightEndTime);
    }
}
