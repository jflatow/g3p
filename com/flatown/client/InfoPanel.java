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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;

public class InfoPanel extends FlowPanel {
  public InfoPanel() {
    DOM.setStyleAttribute(getElement(), "margin", "5px 10px 5px 10px");
    DOM.setStyleAttribute(getElement(), "overflowX", "hidden");
  }
  
  public void addText(String text) {
    Label l = new Label(text);
    DOM.setStyleAttribute(l.getElement(), "display", "inline");
    add(l);
    
  }
  
  public void addLink(String text, String targetURL) {
    Hyperlink link = new Hyperlink(text, targetURL);
    link.addClickListener(HoverLink.Listener);
    DOM.setStyleAttribute(link.getElement(), "display", "inline");
    add(link);
  }
  
  public void addBreak() {
    add(new HTML("<br>"));
  }
  
  public void addExample(String prefix, String text) {
    HTML example = new HTML(prefix + ": <code>" + text + "</code>");
    DOM.setStyleAttribute(example.getElement(), "margin", "0px 5px 0px 5px");
    add(example);
  }
}