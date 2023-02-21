package com.example.chatcloneapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatcloneapp.R;
import com.example.chatcloneapp.listeners.ConversionListener;
import com.example.chatcloneapp.listeners.UserListener;
import com.example.chatcloneapp.models.ChatMessage;
import com.example.chatcloneapp.models.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder> {

    private final List<ChatMessage> chatMessages;
    private final ConversionListener conversionListener;

    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversionListener conversionListener) {
        this.chatMessages = chatMessages;
        this.conversionListener = conversionListener;
    }

    @NonNull
    @Override
    public RecentConversationsAdapter.ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_container_recent_conversion, parent, false);
        return new ConversionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentConversationsAdapter.ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
        holder.user.setOnClickListener(view -> {
            User user = new User();
            user.id = chatMessages.get(position).conversionId;
            user.name = chatMessages.get(position).conversionName;
            user.image = chatMessages.get(position).conversionImage;
            conversionListener.onConversionClicked(user);
        });
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    class ConversionViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageProfile;
        TextView textName, textRecentMessage;
        ConstraintLayout user;

        public ConversionViewHolder(@NonNull View itemView) {
            super(itemView);
            initialize();
        }

        void setData(ChatMessage chatMessage) {
            textName.setText(chatMessage.conversionName);
            textRecentMessage.setText(chatMessage.message);
            imageProfile.setImageBitmap(getConversionImage(chatMessage.conversionImage));
        }

        void initialize() {
            imageProfile = itemView.findViewById(R.id.imageProfile);
            textName = itemView.findViewById(R.id.textName);
            textRecentMessage = itemView.findViewById(R.id.textRecentMessage);
            user = itemView.findViewById(R.id.user);
        }
    }

    private Bitmap getConversionImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
