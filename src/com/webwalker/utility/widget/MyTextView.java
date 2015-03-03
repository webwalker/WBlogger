/**
 * 
 */
package com.webwalker.utility.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Administrator
 * 
 */

public class MyTextView extends TextView {

    private String  strValue = ""; 
    public static final int GUIUPDATEIDENTIFIER = 0x000000;
    public MyTextView(Context context) {
        super(context);
        //this.setTextColor(Color.BLUE);
        //new Thread(new myThread()).start();
    }
    public MyTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTextColor(Color.RED);
        new Thread(new myRunable()).start();
        strValue = MyTextView.this.getText().toString();
    }
    public MyTextView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        //this.setTextColor(Color.BLUE);
        //new Thread(new myThread()).start();
    }
    Handler handler = new Handler(){
        public void handleMessage(Message msg) {   
            switch (msg.what) {   
            case MyTextView.GUIUPDATEIDENTIFIER:   
                String str = MyTextView.this.getText().toString();
                if(!str.equals("")){
                    str = str.subSequence(1, str.length()).toString();
                    MyTextView.this.setText(str);
                }
                else {
                    MyTextView.this.setText(strValue);
                }
                break; 
            default:

            }   
            super.handleMessage(msg);   
        }   
    };
    
    class myRunable implements Runnable {   
        public void run() {  
            while (!Thread.currentThread().isInterrupted()) {    

                Message message = new Message();   
                message.what = MyTextView.GUIUPDATEIDENTIFIER;   

                MyTextView.this.handler.sendMessage(message);   
                try {   
                    Thread.sleep(500);    
                } catch (InterruptedException e) {   
                    Thread.currentThread().interrupt();   
                }   
            }   
        }   
    }   
}
