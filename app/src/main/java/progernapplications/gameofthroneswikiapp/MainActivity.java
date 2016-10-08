package progernapplications.gameofthroneswikiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Animation fadeInAnim;
    ImageView gotLogo;
    Handler splashHandler;
    Intent toDrawerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fadeInAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        gotLogo = (ImageView)findViewById(R.id.gameOfThronesLogo);
        gotLogo.startAnimation(fadeInAnim);


        splashHandler = new Handler();
        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                toDrawerActivity = new Intent(getApplicationContext(), MainDrawerActivity.class);
                startActivity(toDrawerActivity);
            }
        }, 4000L);



    }
}
