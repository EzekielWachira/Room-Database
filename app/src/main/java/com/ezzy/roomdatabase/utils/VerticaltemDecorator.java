package com.ezzy.roomdatabase.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticaltemDecorator extends RecyclerView.ItemDecoration {
    private final int VERTICAL_SPACE_HEIGHT;

    public VerticaltemDecorator(int vertical_space_height) {
        VERTICAL_SPACE_HEIGHT = vertical_space_height;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        outRect.bottom = VERTICAL_SPACE_HEIGHT;
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1){
            outRect.bottom = VERTICAL_SPACE_HEIGHT;
        }
    }
}
