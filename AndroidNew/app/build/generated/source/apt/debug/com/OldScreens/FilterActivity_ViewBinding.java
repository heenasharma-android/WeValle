// Generated code from Butter Knife. Do not modify!
package com.OldScreens;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albaniancircle.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FilterActivity_ViewBinding<T extends FilterActivity> implements Unbinder {
  protected T target;

  private View view2131296610;

  private View view2131296609;

  @UiThread
  public FilterActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.txtShowMe = Utils.findRequiredViewAsType(source, R.id.txt_show_me, "field 'txtShowMe'", TextView.class);
    target.rvHeritage = Utils.findRequiredViewAsType(source, R.id.recycler_view_heritage, "field 'rvHeritage'", RecyclerView.class);
    target.goInvisible = Utils.findRequiredViewAsType(source, R.id.goInvis, "field 'goInvisible'", Switch.class);
    target.txtAge = Utils.findRequiredViewAsType(source, R.id.txt_min_max_age, "field 'txtAge'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_show_me, "method 'showMe'");
    view2131296610 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showMe();
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_show_age, "method 'showAge'");
    view2131296609 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showAge();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.txtShowMe = null;
    target.rvHeritage = null;
    target.goInvisible = null;
    target.txtAge = null;

    view2131296610.setOnClickListener(null);
    view2131296610 = null;
    view2131296609.setOnClickListener(null);
    view2131296609 = null;

    this.target = null;
  }
}
