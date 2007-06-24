/*
 * Copyright 2007 Jared Flatow
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.flatown.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.Window;
/** Class for an object containing a single search result
 * 
 */
public class AResult extends DragBar implements PopupWidget {
 
  protected FlowPanel _displayPanel;
  protected HoverLink _save;
  
  public AResult() {
    // an empty result has an empty flowpanel widget
    _displayPanel = new FlowPanel();
    setWidget(_displayPanel);
    setStyleName("aresult");
    _save = new HoverLink("Save Bookmark", "saveToken", BookmarksPanel.Singleton);
  }
  
  public void setSaveText(String text) {
    _save.setText(text);
  }
  
  public void add(Widget w) {
    _displayPanel.add(w);
  }
  
  public boolean remove(Widget w) {
    return _displayPanel.remove(w);
  }
  
  public void clear() {
    _displayPanel.clear();
  }
  
  public void showHover() {}
  
  public void hideHover() {}
  
  public void expand() {}
  
  public void collapse() {}
  
  public void slide(int start, int end) {
    SlideTimer.Singleton.slide(this, start, end);
  }
  
  /** Convenience method for adding Labels as new layers to the AResult */
  public void addLabel(String s) {
    Label label = new Label(s);
    label.setStyleName("aResultFragment");
    add(label);
  }
  
  public void attachToPopupHost(PopupHost host) {
    addMouseListener(host.getPopupListener());
  }
}
