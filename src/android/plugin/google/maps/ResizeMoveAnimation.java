package plugin.google.maps;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;

public class ResizeMoveAnimation extends Animation {
  View view;
  int fromLeft;
  int fromTop;
  int fromWidth;
  int fromHeight;
  int toLeft;
  int toTop;
  int toWidth;
  int toHeight;
  boolean absoluteLayout = false;
  boolean linearLayout = false;

  public ResizeMoveAnimation(View v, int toLeft, int toTop, int toWidth, int toHeight) {
    this.view = v;
    this.toLeft = toLeft;
    this.toTop = toTop;
    this.toWidth = toWidth;
    this.toHeight = toHeight;

    fromLeft = v.getLeft();
    fromTop = v.getTop();
    fromWidth = v.getWidth();
    fromHeight = v.getHeight();

    ViewGroup.LayoutParams lParams = v.getLayoutParams();
    if (lParams instanceof android.widget.AbsoluteLayout.LayoutParams) {
      absoluteLayout = true;
      linearLayout = false;
    } else if (lParams instanceof android.widget.LinearLayout.LayoutParams) {
      linearLayout = true;
      absoluteLayout = false;
    }

    setDuration(500);
  }

  @Override
  @SuppressWarnings("deprecation")
  protected void applyTransformation(float interpolatedTime, Transformation t) {
    float left = fromLeft + (toLeft - fromLeft) * interpolatedTime;
    float top = fromTop + (toTop - fromTop) * interpolatedTime;
    float width = fromWidth + (toWidth - fromWidth) * interpolatedTime;
    float height = fromHeight + (toHeight - fromHeight) * interpolatedTime;

    if (absoluteLayout) {
      AbsoluteLayout.LayoutParams p = (AbsoluteLayout.LayoutParams) view.getLayoutParams();
      p.x = (int) left;
      p.y = (int) top;
      p.width = (int) width;
      p.height = (int) height;
    }
    if (linearLayout) {
      LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) view.getLayoutParams();
      p.leftMargin = (int) left;
      p.topMargin = (int) top;
      p.width = (int) width;
      p.height = (int) height;
    }

    view.requestLayout();
  }

  @Override
  public boolean willChangeBounds() {
    return (fromWidth == toWidth) && (fromHeight == toHeight);
  }
}