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

import java.util.ArrayList;

import com.flatown.client.io.*;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.FormPanel;

/**
 * The Panel for helping users share their gadget.
 */
public class ExportPanel extends SharedPanel {
  
  public static final ExportPanel Singleton = new ExportPanel();
  
  private FlowPanel _exportControls;
  private VerticalPanel _exportDisplay;
  private ResultsBox _resultsDisplay;
  private ArrayList _exportBin;
  private ListBox _formatMenu;
  private HoverLink _exportLink;
  
  private ExportPanel() {
    createExportControls();
    _exportBin = new ArrayList();
    _exportDisplay = new VerticalPanel();
    _resultsDisplay = new ResultsBox(null);
    
    _exportDisplay.add(_resultsDisplay);
    
    add(_exportControls);
    add(_exportDisplay);

    _exportDisplay.setStyleName("leftFloat");
    DOM.setStyleAttribute(_exportControls.getElement(), "width", "100%");
    DOM.setStyleAttribute(_exportDisplay.getElement(), "width", "100%");
    DOM.setStyleAttribute(_exportDisplay.getElement(), "height", "100%");
  }
  
  public void addExport(Portable e) {
    // check to see if anyone has same exportId
    if (e instanceof AResult) {
      if (!_exportBin.contains(e)) {
        _exportBin.add(e);
        _resultsDisplay.addResult((AResult)e.getWidget());
      }
    }
  }
  
  public void removeExport(Portable e) {
    if (e instanceof AResult) {
      try {
        _exportBin.remove(_exportBin.indexOf(e));
      } catch (Exception exc) {}
      _resultsDisplay.remove((AResult)e.getWidget());
    }
  }
  
  private void createExportControls() {
    _exportControls = new FlowPanel();
    _exportControls.setStyleName("exportControls");

    _exportControls.add(makeLowLabel("Format: "));
    
    createFormatMenu();
    _exportControls.add(_formatMenu);

    createExportLink();
    _exportControls.add(_exportLink);
  }
  
  private Label makeLowLabel(String text) {
    Label l = new Label(text);
    l.setStyleName("leftFloat");
    DOM.setStyleAttribute(l.getElement(), "lineHeight", "2em");
    DOM.setStyleAttribute(l.getElement(), "marginLeft", "15px");
    return l;
  }
  
  private void createFormatMenu() {
    _formatMenu = new ListBox();
    _formatMenu.setName("formatMenu");
    _formatMenu.addItem("EndNote");
    _formatMenu.setStyleName("leftFloat");
  }
  
  private void createExportLink() {
    _exportLink = new HoverLink("Export...", "exportToken", new ClickListener() {
      public void onClick(Widget sender) {
        if (sender instanceof Hyperlink) {
          Hyperlink link = (Hyperlink)sender;
          if (link.getTargetHistoryToken().equals("exportToken")) {
            openExportWindow(exportAllApplicable());
          }
        }
      }
    }, "shareLink");
    DOM.setStyleAttribute(_exportLink.getElement(), "marginLeft", "15px");
  }
  
  public String exportAllApplicable() {
    String formatString = _formatMenu.getItemText(_formatMenu.getSelectedIndex()),
      exportString = "<textarea rows='30' cols='70' name='" + formatString + "'>";
    for (int i = 0; i < _exportBin.size(); i++) {
      try {
        exportString += ((Portable) _exportBin.get(i)).performExport(formatString) + "\n";
      } catch (ExportException e) {
        // add to the could not export list
      }
    }
    return exportString + "</textarea>";
  }
  
  public static native void openExportWindow(String exportString) /*-{
    baby = window.open("about:blank", "exportWindow", "");
    baby.document.write(exportString);
  }-*/;
}