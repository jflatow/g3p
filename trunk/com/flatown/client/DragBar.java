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

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.MouseListener;

import com.google.gwt.user.client.DOM;

/** Interface for draggable elements */
public class DragBar extends FocusPanel {
  
  private Widget _dragWidget;
  
  public DragBar() {
    _dragWidget = null;
  }
  
  public void setDragWidget(Widget dragWidget) {
    _dragWidget = dragWidget;
  }
  
  public Widget getDragWidget() {
    return (_dragWidget == null) ? this : _dragWidget;
  }
  
  public void attachToDragHost(DragHost host) {
    addMouseListener(host.getDragListener());
  }
  
  public Widget getWidget() {
    return (_dragWidget == null) ? this : _dragWidget;
  }
}