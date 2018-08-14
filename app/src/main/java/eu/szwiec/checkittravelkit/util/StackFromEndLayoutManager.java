package eu.szwiec.checkittravelkit.util;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

public class StackFromEndLayoutManager extends LinearLayoutManager {
    public StackFromEndLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setStackFromEnd(true);
    }
}