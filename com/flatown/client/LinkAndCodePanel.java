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

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextArea;

/**
 * The code/link sharing panel.
 */
public class LinkAndCodePanel extends SharedPanel {
  
  public static final LinkAndCodePanel Singleton = new LinkAndCodePanel();
  
  private FlowPanel _codeAreaPanel;
  private TextArea _codeArea;
  private HoverLink _instanceURLLink, _iframeCodeLink;
  
  private LinkAndCodePanel() {
    
    createCodeArea();
    createInstanceURLLink();
    createIFrameCodeLink();
    
    add(new Label("Here you can create a URL that you can use to share this gadget " +
                                "with its current preferences. " +
                                "Users who visit the gadget at this URL will not be able to permanently " + 
                                "change any preferences for the gadget. " +
                                "You can share this gadget with your current preferences in a webpage by using an iframe whose " +
                                "'src' attribute points to the URL."));

    add(_instanceURLLink);
    add(_iframeCodeLink);
    add(_codeAreaPanel); 
    DOM.setStyleAttribute(getElement(), "marginLeft", "10px");
  }
  
  private void createCodeArea() {
    _codeArea = new TextArea();
    _codeArea.setCharacterWidth(40);
    _codeArea.setVisibleLines(4);
    
    _codeAreaPanel = new FlowPanel();
    _codeAreaPanel.setStyleName("leftFloat");
    DOM.setStyleAttribute(_codeAreaPanel.getElement(), "clear", "both");
    _codeAreaPanel.add(_codeArea);
  }
  
  private void createInstanceURLLink() {
    _instanceURLLink = new HoverLink("Create URL", "instanceURLToken", new ClickListener() {
      public void onClick(Widget sender) {
        _codeArea.setText(GBox.Prefs.instanceURL());
        _codeArea.selectAll();
      }
    });
    _instanceURLLink.setStyleName("shareLink");
  }
  
  private void createIFrameCodeLink() {
    _iframeCodeLink = new HoverLink("Generate IFrame Code", "iframeCodeToken", new ClickListener() {
      public void onClick(Widget sender) {
        _codeArea.setText("<iframe src='" + GBox.Prefs.instanceURL() + "' style='border:0;width:100%;height:100%;' scrolling='no' frameborder='no'></iframe>");
        _codeArea.selectAll();
      }
    });
    _iframeCodeLink.setStyleName("shareLink");
  }
}