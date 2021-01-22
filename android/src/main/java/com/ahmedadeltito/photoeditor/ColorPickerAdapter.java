package com.ahmedadeltito.photoeditor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import ui.photoeditor.R;

/**
 * Created by Ahmed Adel on 5/8/17.
 */

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<Integer> colorPickerColors;
    private OnColorPickerClickListener onColorPickerClickListener;
    private int selectedItemIndex;

    public ColorPickerAdapter(@NonNull Context context, @NonNull List<Integer> colorPickerColors, int selectedItemIndex) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.colorPickerColors = colorPickerColors;
        this.selectedItemIndex = selectedItemIndex;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.color_picker_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        buildColorPickerView(holder.colorPickerView, holder.selectedColorImageView, position);
    }

    @Override
    public int getItemCount() {
        return colorPickerColors.size();
    }

    private void buildColorPickerView(View view, ImageView selectedColorImageView, int position) {
        view.setVisibility(View.VISIBLE);
        int colorCode = colorPickerColors.get(position);
        if (selectedItemIndex == position) {
            selectedColorImageView.setVisibility(View.VISIBLE);
            if (position == 1) {
                selectedColorImageView.setImageResource(R.drawable.tick_black);
            } else {
                selectedColorImageView.setImageResource(R.drawable.tick_white);
            }
        } else {
            selectedColorImageView.setVisibility(View.GONE);
        }

        ShapeDrawable biggerCircle = new ShapeDrawable(new OvalShape());
        biggerCircle.setIntrinsicHeight(20);
        biggerCircle.setIntrinsicWidth(20);
        biggerCircle.setBounds(new Rect(0, 0, 20, 20));
        biggerCircle.getPaint().setColor(colorCode);

        ShapeDrawable smallerCircle = new ShapeDrawable(new OvalShape());
        smallerCircle.setIntrinsicHeight(2);
        smallerCircle.setIntrinsicWidth(2);
        smallerCircle.setBounds(new Rect(0, 0, 5, 5));
        smallerCircle.getPaint().setColor(context.getResources().getColor(R.color.colorPrimary));
        smallerCircle.setPadding(5, 5, 5, 5);
        Drawable[] drawables = {smallerCircle, biggerCircle};
        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        view.setBackground(layerDrawable);
    }

    public void setOnColorPickerClickListener(OnColorPickerClickListener onColorPickerClickListener) {
        this.onColorPickerClickListener = onColorPickerClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View colorPickerView;
        ImageView selectedColorImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            colorPickerView = itemView.findViewById(R.id.color_picker_view);
            selectedColorImageView = itemView.findViewById(R.id.selectedColorImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onColorPickerClickListener != null) {
                        onColorPickerClickListener.onColorPickerClickListener(colorPickerColors.get(getAdapterPosition()));
                        selectedItemIndex = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public interface OnColorPickerClickListener {
        void onColorPickerClickListener(int colorCode);
    }
}
