// Generated code from Butter Knife. Do not modify!
package com.OldScreens;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.albaniancircle.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WebviewActivity_ViewBinding<T extends WebviewActivity> implements Unbinder {
  protected T target;

  @UiThread
  public WebviewActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.webView = Utils.findRequiredViewAsType(source, R.id.webview, "field 'webView'", WebView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.webView = null;
    target.progressBar = null;

    this.target = null;
  }
}
