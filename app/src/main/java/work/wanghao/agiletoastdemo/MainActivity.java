package work.wanghao.agiletoastdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import work.wanghao.agiletoast.utils.AnimationType;
import work.wanghao.agiletoast.widget.AgileToast;
import work.wanghao.agiletoast.widget.ToastType;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button normalButton = (Button) findViewById(R.id.button_normal);
    normalButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AgileToast.Companion.build(MainActivity.this)
            .setToastType(ToastType.BOTTOM).setText("AgileToast send msg...")
            .setAnimationType(AnimationType.ANIMATION_DRAWER_BOTTOM)
            .setToastBackgroundColor(R.color.colorAccent)
            .show();
      }
    });
  }
}
