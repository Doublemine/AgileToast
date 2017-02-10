package work.wanghao.agiletoastdemo;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import org.jetbrains.annotations.NotNull;
import work.wanghao.agiletoast.callback.OnDismissCallback;
import work.wanghao.agiletoast.utils.AnimationType;
import work.wanghao.agiletoast.widget.AgileToast;
import work.wanghao.agiletoast.widget.Duration;
import work.wanghao.agiletoast.widget.ToastStyle;
import work.wanghao.agiletoast.widget.ToastType;

public class MainActivity extends AppCompatActivity implements OnDismissCallback {

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
            .style(ToastStyle.NORMAL)
            .gravity(Gravity.CENTER | Gravity.BOTTOM)
            .offsetY(200)
            .dismissCallback(MainActivity.this)
            .bgColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null))
            .show();
      }
    });

    Button fillButton = (Button) findViewById(R.id.button_fill);
    fillButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AgileToast.Companion.build(MainActivity.this)
            .type(ToastType.NORMAL)
            .text("AgileToast send msg...")
            .animation(AnimationType.ANIMATION_DRAWER_BOTTOM)
            .duration(Duration.DURATION_CUSTOM, 1000L)
            .style(ToastStyle.FILL)
            .gravity(Gravity.CENTER | Gravity.BOTTOM)
            .offsetY(200)
            .dismissCallback(MainActivity.this)
            .bgColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null))
            .show();
      }
    });

    Button cornerButton = (Button) findViewById(R.id.button_corner);
    cornerButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AgileToast.Companion.build(MainActivity.this)
            .type(ToastType.NORMAL)
            .text("AgileToast send msg...")
            .animation(AnimationType.ANIMATION_DRAWER_BOTTOM)
            .duration(Duration.DURATION_CUSTOM, 1000L)
            .style(ToastStyle.CORNER)
            .gravity(Gravity.CENTER | Gravity.BOTTOM)
            .offsetY(200)
            .dismissCallback(MainActivity.this)
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
            .gravity(Gravity.CENTER | Gravity.BOTTOM)
            .offsetY(200)
            .dismissCallback(MainActivity.this)
            .show();
      }
    });

    Button tipsErrorButton = (Button) findViewById(R.id.button_tips_error);
    tipsErrorButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AgileToast.Companion.build(MainActivity.this)
            .type(ToastType.ERROR)
            .text("This is an error toast.")
            .animation(AnimationType.ANIMATION_DRAWER_TOP)
            .duration(Duration.DURATION_LONG, 1000L)
            .gravity(Gravity.CENTER | Gravity.BOTTOM)
            .dismissCallback(MainActivity.this)
            .offsetY(200)
            .show();
      }
    });

    Button tipsSuccessButton = (Button) findViewById(R.id.button_tips_success);
    tipsSuccessButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AgileToast.Companion.build(MainActivity.this)
            .type(ToastType.SUCCESS)
            .text("Success!")
            .gravity(Gravity.CENTER | Gravity.BOTTOM)
            .offsetY(200)
            .animation(AnimationType.ANIMATION_DRAWER_BOTTOM)
            .duration(Duration.DURATION_LONG, 1000L)
            .dismissCallback(MainActivity.this)
            .show();
      }
    });
  }

  @Override public void onDismissCallback(@NotNull AgileToast toast) {
    AgileToast.Companion.build(MainActivity.this)
        .type(ToastType.NORMAL)
        .text("call onDismiss Callback.").animation(AnimationType.ANIMATION_DRAWER_TOP)
        .duration(Duration.DURATION_CUSTOM, 1000L)
        .style(ToastStyle.FILL)
        .gravity(Gravity.TOP)
        .show();
  }
}
