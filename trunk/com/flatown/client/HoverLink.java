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
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ClickListener;

public class HoverLink extends FlowPanel {
  
  private Hyperlink _link;
  
  public HoverLink(String text, String targetHistoryToken, ClickListener listener) {
    this(text, targetHistoryToken, listener, "hoverLink");
  }
  
  public HoverLink(String text, String targetHistoryToken, ClickListener listener, String styleName) {
    _link = new Hyperlink(text, targetHistoryToken);
    _link.addClickListener(listener);
    setStyleName(styleName);
    add(_link);
  }
  
  public void setText(String text) {
    _link.setText(text);
  }
  
  public Hyperlink getLink() {
    return _link;
  }
}