// Generated code from Butter Knife. Do not modify!
package com.NewChanges;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albaniancircle.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatActivity_ViewBinding<T extends ChatActivity> implements Unbinder {
  protected T target;

  @UiThread
  public ChatActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.rvChat = Utils.findRequiredViewAsType(source, R.id.rv_chat, "field 'rvChat'", RecyclerView.class);
    target.rvFav = Utils.findRequiredViewAsType(source, R.id.rv_fav, "field 'rvFav'", RecyclerView.class);
    target.rvView = Utils.findRequiredViewAsType(source, R.id.rv_view, "field 'rvView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.rvChat = null;
    target.rvFav = null;
    target.rvView = null;

    this.target = null;
  }
}
