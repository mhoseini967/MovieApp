
package ir.mohamadhoseini.movieapp.adapters;

import android.os.Handler;


public class CardController {

    public void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                onCreate();
            }
        },50);
    }

    protected void onCreate() {}

}
