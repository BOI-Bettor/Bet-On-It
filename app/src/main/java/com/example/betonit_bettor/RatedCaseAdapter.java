package com.example.betonit_bettor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class RatedCaseAdapter extends RecyclerView.Adapter<RatedCaseAdapter.ViewHolder> {
    public static final String TAG = "RatedCaseAdapter";

    private Context context;
    private List<Case> posts;

    public RatedCaseAdapter(Context context, List<Case> allPosts) {
        this.context = context;
        this.posts = allPosts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rated_case, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatedCaseAdapter.ViewHolder holder, int position) {
        Case post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout contain;
        private TextView tvBetName;
        private TextView tvBetChallenger;
        private TextView tvBetChallengee;

        public ViewHolder (@NonNull View itemView)
        {
            super(itemView);
            tvBetName = itemView.findViewById(R.id.tvCaseName);
            tvBetChallenger = itemView.findViewById(R.id.tvBetChallengee);
            tvBetChallengee = itemView.findViewById(R.id.tvBetChallengee);
            contain = itemView.findViewById(R.id.rlRatedCaseItem);
        }

        public void bind(final Case post)
        {
            // Display Bet Name
            tvBetName.setText(post.getObjectId());

            contain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, post.getKeyCaseStatus(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, RatedDetailActivity.class);
                    i.putExtra("tvChallengerEvidence", post.getKeyCaseChallengerEvidence());
                    i.putExtra("tvChallengeeEvidence", post.getKeyCaseChallengeeEvidence());
                    i.putExtra("tvRatedCaseWinnerDesc", post.getKeyCaseBetWinnerDescription());

                    context.startActivity(i);
                }
            });
        }
    }
    public void addAll(List<Case> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
