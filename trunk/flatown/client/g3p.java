package com.flatown.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class g3p implements EntryPoint {
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    final RootPanel _container = RootPanel.get("container");
    _container.addStyleName("container");
    _container.add(GBox.Singleton);
  }
}
