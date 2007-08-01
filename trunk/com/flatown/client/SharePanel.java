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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.StackPanel;

/**
 * The Panel for helping users share their gadget.
 */
public class SharePanel extends ScrollPanel {
  
  /** A Singleton instance of the panel. */
  public static final SharePanel Singleton = new SharePanel();
  public static int Counter = 0;
  
  private StackPanel _controlPanel;
    
  private SharePanel() {
    _controlPanel = new StackPanel();
    _controlPanel.add(LinkAndCodePanel.Singleton, makeHeader("Link to Gadget"), true);
    _controlPanel.add(ExportPanel.Singleton, makeHeader("Export"), true);
    _controlPanel.add(ImportPanel.Singleton, makeHeader("Import"), true);
    
    setWidget(_controlPanel);
    
    DOM.setStyleAttribute(getElement(), "width", "100%");
    DOM.setStyleAttribute(getElement(), "height", "100%");
    DOM.setStyleAttribute(getElement(), "backgroundColor", "#E5ECF9");
    DOM.setStyleAttribute(getElement(), "maxHeight", Window.getClientHeight() - 50 + "px");
    
    DOM.setStyleAttribute(_controlPanel.getElement(), "width", "100%");
    DOM.setStyleAttribute(_controlPanel.getElement(), "height", "100%");
  }
  
  private String makeHeader(String text) {
    String _id = "hdr" + Counter++;
    return "<div style='width:100%; border-top:1px solid #7AA5D6;'>"
      + "<div style='margin:5px 10px 5px 10px; font-weight:bold;'>"
      + text + "</div></div>";
  }
}