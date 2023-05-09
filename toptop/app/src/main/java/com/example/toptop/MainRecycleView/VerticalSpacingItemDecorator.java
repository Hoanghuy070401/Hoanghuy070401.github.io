package com.example.toptop.MainRecycleView;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;

    public  VerticalSpacingItemDecorator (int verticalSpaceHeight){
        this.verticalSpaceHeight= verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state ){

        outRect.top = verticalSpaceHeight;
    }
}
