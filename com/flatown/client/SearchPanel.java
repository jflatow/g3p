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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchPanel extends ScrollPanel {
  
  public static final SearchPanel Singleton = new SearchPanel();
  
  private SearchPanel() {
    VerticalPanel displayPanel = new VerticalPanel();
    displayPanel.add(new SearchBox());
    setWidget(displayPanel);
    DOM.setStyleAttribute(displayPanel.getElement(), "width", "100%");
    DOM.setStyleAttribute(displayPanel.getElement(), "height", "100%");
    DOM.setStyleAttribute(getElement(), "maxHeight", Window.getClientHeight() - 40 + "px");
  }
}