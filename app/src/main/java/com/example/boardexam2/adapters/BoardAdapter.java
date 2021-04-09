package com.example.boardexam2.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardexam2.R;
import com.example.boardexam2.models.Board;

import java.text.SimpleDateFormat;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    private List<Board> mBoardList;

    /////////////////////////////////////////////////////
    //Recyclerview 클릭이벤트
    public interface OnListItemLongSelectedInterface {
        void onItemLongSelected(View v, int position);
    }
    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }
    private OnListItemSelectedInterface mListener;
    private OnListItemLongSelectedInterface mLongListener;
    /////Recyclerview 클릭이벤트///////////////////////////

    public BoardAdapter(List<Board> mBoardList) {
        this.mBoardList = mBoardList;
    }

    public BoardAdapter(List<Board> mBoardList, OnListItemSelectedInterface listener, OnListItemLongSelectedInterface longListener) {
        this.mBoardList = mBoardList;
        this.mListener = listener;
        this.mLongListener = longListener;
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        Board data = mBoardList.get(position);
        holder.mTitleTextView.setText(data.getTitle());
        holder.mNameTextView.setText(data.getName());
        holder.mNameContentView.setText(data.getContents());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
        holder.mCreateDate.setText(dateFormat.format(data.getCreatedate()));

        switch (data.getType())
        {
            case "0":
                //png파일은 아래 처럼
                Drawable placeholder = null;
                placeholder = holder.mImage.getContext().getResources().getDrawable(R.drawable.img_notice);
                holder.mImage.setImageDrawable(placeholder);
                break;
            case "1":
                //xml파일은 아래처럼
                holder.mImage.setImageResource(R.drawable.img_image);
                break;
            default:
                holder.mImage.setImageResource(R.drawable.img_image);
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (mBoardList != null)
            return mBoardList.size();
        else
            return 0;
    }


    public class BoardViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;
        public TextView mNameTextView;
        public TextView mNameContentView;
        public TextView mCreateDate;
        public ImageView mImage;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.item_title_text);
            mNameTextView = itemView.findViewById(R.id.item_name_text);
            mNameContentView = itemView.findViewById(R.id.item_contents_text);
            mCreateDate = itemView.findViewById(R.id.item_date);
            mImage = itemView.findViewById(R.id.item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        mListener.onItemSelected(v, pos);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        mLongListener.onItemLongSelected(v, pos);
                    }
                    return false;
                }
            });


        }


    }
}