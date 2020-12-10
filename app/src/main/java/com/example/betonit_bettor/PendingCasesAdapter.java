package com.example.betonit_bettor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PendingCasesAdapter extends RecyclerView.Adapter<PendingCasesAdapter.ViewHolder> {

    private Context context;
    private List<Case> posts;

    public PendingCasesAdapter(Context context, List<Case> allPosts) {
        this.context = context;
        this.posts = allPosts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_case, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Case post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout contain;
        private TextView tvUsername;
        private TextView tvDate;
        private TextView tvCaption;
        private ImageView ivPostContent;

        public ViewHolder (@NonNull View itemView)
        {
            super(itemView);
            //tvCaption = itemView.findViewById(R.id.tvCaption);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvDate = itemView.findViewById(R.id.tvDate);
            contain = itemView.findViewById(R.id.contain);

            //ivPostContent = itemView.findViewById(R.id.ivPostContent);
        }

        public void bind(final Case post)
        {
            //tvCaption.setText(post.getCaption());
            tvUsername.setText(post.getKeyCaseStatus());
            tvDate.setText(post.getKeyCaseChallengerEvidence());
            contain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, post.getKeyCaseStatus(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, PendingDetailActivity.class);
                    i.putExtra("status", post.getKeyCaseStatus());
                    i.putExtra("description1", post.getKeyCaseChallengerEvidence());
                    i.putExtra("description2", post.getKeyCaseChallengeeEvidence());
                    context.startActivity(i);
                }
            });
            //ParseFile img = post.getImage();

            /*if(img!= null)
            {
                Glide.with(context).load(post.getImage().getUrl()).into(ivPostContent);
            }*/
        }
    }
    public void addAll(List<Case> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
