package com.ftocs.myapp.utils;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerClickListener";
    Context context;
    public interface OnRecyclerClickListener{
        public void onItemClick(View view, int position);
        public void onLongClick(View view, int position);
    }
    private final OnRecyclerClickListener onRecyclerClickListener;
    private final GestureDetectorCompat gestureDetector;

    public RecyclerClickListener(Context context, final RecyclerView recyclerView, final OnRecyclerClickListener onRecyclerClickListener) {
        this.onRecyclerClickListener = onRecyclerClickListener;
        this.context=context;
        this.gestureDetector=new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                View childView=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if ((childView!=null) && (onRecyclerClickListener!=null))
                {
                    onRecyclerClickListener.onItemClick(childView,recyclerView.getChildAdapterPosition(childView));
                    return true;
                }
                else {
                    return false;
                }
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (childView!=null && onRecyclerClickListener!=null)
                {
                    onRecyclerClickListener.onLongClick(childView,recyclerView.getChildAdapterPosition(childView));
                }

            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent(): is called ");
        if (gestureDetector!=null)
        {

            boolean result;
            result=gestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent(): "+result);
            return  result;
        }
        else {
            return  false;
        }
    }
}
