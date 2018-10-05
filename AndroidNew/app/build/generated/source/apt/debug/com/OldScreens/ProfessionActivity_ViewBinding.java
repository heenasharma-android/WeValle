// Generated code from Butter Knife. Do not modify!
package com.OldScreens;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.albaniancircle.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfessionActivity_ViewBinding<T extends ProfessionActivity> implements Unbinder {
  protected T target;

  private View view2131296307;

  private View view2131296857;

  private View view2131296320;

  @UiThread
  public ProfessionActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.rvProfession = Utils.findRequiredViewAsType(source, R.id.rv_profession, "field 'rvProfession'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.arrow, "method 'arrow'");
    view2131296307 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.arrow();
      }
    });
    view = Utils.findRequiredView(source, R.id.title, "method 'title'");
    view2131296857 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.title();
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_apply, "method 'btApply'");
    view2131296320 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.btApply();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.rvProfession = null;

    view2131296307.setOnClickListener(null);
    view2131296307 = null;
    view2131296857.setOnClickListener(null);
    view2131296857 = null;
    view2131296320.setOnClickListener(null);
    view2131296320 = null;

    this.target = null;
  }
}
