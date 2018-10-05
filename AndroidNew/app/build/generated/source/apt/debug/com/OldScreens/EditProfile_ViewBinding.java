// Generated code from Butter Knife. Do not modify!
package com.OldScreens;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albaniancircle.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditProfile_ViewBinding<T extends EditProfile> implements Unbinder {
  protected T target;

  private View view2131296906;

  private View view2131296882;

  @UiThread
  public EditProfile_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_profession, "field 'tvProfession' and method 'profession'");
    target.tvProfession = Utils.castView(view, R.id.tv_profession, "field 'tvProfession'", TextView.class);
    view2131296906 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.profession();
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_cancel, "method 'tvCancel'");
    view2131296882 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.tvCancel();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tvProfession = null;

    view2131296906.setOnClickListener(null);
    view2131296906 = null;
    view2131296882.setOnClickListener(null);
    view2131296882 = null;

    this.target = null;
  }
}
