package work.wanghao.agiletoastdemo;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import work.wanghao.agiletoast.utils.AnimationType;
import work.wanghao.agiletoast.widget.AgileToast;
import work.wanghao.agiletoast.widget.Duration;
import work.wanghao.agiletoast.widget.ToastType;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button normalButton = (Button) findViewById(R.id.button_normal);

    normalButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AgileToast.Companion.build(MainActivity.this)
            .type(ToastType.NORMAL)
            .text("AgileToast send msg...")
            .animation(AnimationType.ANIMATION_DRAWER_BOTTOM)
            .duration(Duration.DURATION_CUSTOM, 1000L)
            .bgColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null))
            .show();
      }
    });

    Button tipsInfoButton = (Button) findViewById(R.id.button_tips_info);
    tipsInfoButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AgileToast.Companion.build(MainActivity.this)
            .type(ToastType.WARNING)
            .text("Here is some info for you.")
            .animation(AnimationType.ANIMATION_SCALE)
            .duration(Duration.DURATION_LONG, 1000L)
            .show();
      }
    });
    Button tipsErrorButton = (Button) findViewById(R.id.button_tips_error);
    tipsErrorButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AgileToast.Companion.build(MainActivity.this)
            .type(ToastType.ERROR)
            .text("This is an error toast.")
            .animation(AnimationType.ANIMATION_SCALE)
            .duration(Duration.DURATION_LONG, 1000L)
            .show();
      }
    });
    Button tipsSuccessButton = (Button) findViewById(R.id.button_tips_success);
    tipsSuccessButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AgileToast.Companion.build(MainActivity.this)
            .type(ToastType.SUCCESS)
            .text("Success!")
            .animation(AnimationType.ANIMATION_SCALE)
            .duration(Duration.DURATION_LONG, 1000L)
            .show();
      }
    });
  }
}
