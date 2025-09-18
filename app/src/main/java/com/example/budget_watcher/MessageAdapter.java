package com.example.budget_watcher;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView txtMessage;
        public Message message;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.txtMessage);
        }
        public void setMessage(Message message){
            this.message = message;
            txtMessage.setText("Message: " + message.getBody() + "\nFrom: " + message.getAddress() + "\nDate: " + message.getDate());
        }
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
