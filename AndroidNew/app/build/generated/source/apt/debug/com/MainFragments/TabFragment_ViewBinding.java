// Generated code from Butter Knife. Do not modify!
package com.MainFragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albaniancircle.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TabFragment_ViewBinding<T extends TabFragment> implements Unbinder {
  protected T target;

  @UiThread
  public TabFragment_ViewBinding(T target, View source) {
    this.target = target;

    target.rvGallery = Utils.findRequiredViewAsType(source, R.id.rv_gallery, "field 'rvGallery'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.rvGallery = null;

    this.target = null;
  }
}
