package com.bibizhaoji.bibiji.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Toast在显示之前，先隐藏之前的Toast
 * 
 * @author caiyingyuan
 *
 */
public final class ToastUtils
{
    private static final Holder mHolder = new Holder();
    
    private static final Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            if (msg.obj == null || msg.what != 0 || !(msg.obj instanceof Obj))
            {
                return;
            }
            
            Obj obj = (Obj) msg.obj;
            showSingleToast(obj.context, obj.text, obj.duration);
        }
    };
    
    private static class Holder
    {
        public Toast toast;
        public Context context;
    }
    
    private static class Obj
    {
        public Context context;
        public String text;
        public int duration;
    }
    
    /**
     * 显示Toast，显示时长为Toast.LENGTH_SHORT
     * 
     * @param context
     *            上下文
     * @param textId
     *            文本资源ID
     */
    public static void show(Context context, int textId)
    {
        show(context, textId, Toast.LENGTH_SHORT);
    }
    
    /**
     * 显示Toast，显示时长为Toast.LENGTH_SHORT
     * 
     * @param context
     *            上下文
     * @param text
     *            文本
     */
    public static void show(Context context, String text)
    {
        show(context, text, Toast.LENGTH_SHORT);
    }
    
    /**
     * 显示Toast，显示时长为Toast.LENGTH_LONG
     * 
     * @param context
     *            上下文
     * @param text
     *            文本
     */
    public static void showLongTime(Context context, String text)
    {
        show(context, text, Toast.LENGTH_LONG);
    }
    
    /**
     * 显示Toast
     * 
     * @param context
     *            上下文
     * @param textId
     *            文本资源ID
     * @param duration
     *            显示时长
     */
    public static void show(Context context, int textId, int duration)
    {
        if (context == null || duration < 0)
        {
            return;
        }
        
        String text = context.getResources().getString(textId);
        if (text == null)
        {
            return;
        }
        
        show(context, text, duration);
    }
    
    /**
     * 显示Toast
     * 
     * @param context
     *            上下文
     * @param text
     *            文本
     * @param duration
     *            显示时长
     */
    public static void show(final Context context, final String text, final int duration)
    {
        if (context == null || text == null || duration < 0)
        {
            return;
        }
        
        Obj obj = new Obj();
        obj.context = context;
        obj.text = text;
        obj.duration = duration;
        
        Message msg = new Message();
        msg.what = 0;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }
    
    /**
     * 显示单一的Toast
     * 
     * @param context
     *            上下文
     * @param text
     *            文本
     * @param duration
     *            显示时长
     */
    private synchronized static void showSingleToast(Context context, String text, int duration)
    {
        if (mHolder.toast == null)
        {
            mHolder.toast = Toast.makeText(context, text, duration);
            mHolder.context = context;
        }
        else
        {
            if (mHolder.context != context)
            {
                mHolder.toast.cancel();
                mHolder.toast = Toast.makeText(context, text, duration);
                mHolder.context = context;
            }
            else
            {
                mHolder.toast.setText(text);
                mHolder.toast.setDuration(duration);
            }
        }
        
        mHolder.toast.show();
    }
}
