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

import com.flatown.client.io.*;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.Window;

import com.google.gwt.xml.client.Element;

/** 
 * Base class for an object containing a single search result. It isn't abstract so it can be used for generic things
 * like logging errors in a {@link ResultsBox}, but for most results it is a good idea to extend this class. 
 * 
 * @see com.flatown.client.eutils.ui.PubmedArticle For an example of extending AResult.
 */
public class AResult extends DragBar implements PopupWidget, Portable {
 
  protected FlowPanel _displayPanel;
  protected HoverLink _save;
  
  /**
   * Creates an empty result. An empty result instantiates a {@link HoverLink} that
   * can be added to the result in order to save the result to the {@link BookmarksPanel#Singleton}.
   */
  public AResult() {
    _displayPanel = new FlowPanel();
    setWidget(_displayPanel);
    setStyleName("aresult");
    _save = new HoverLink("Save Bookmark", "saveToken", BookmarksPanel.Singleton);
  }
  
  /**
   * Sets the text of the save {@link HoverLink}.
   * 
   * @param text the text the save link should display
   */
  public void setSaveText(String text) {
    _save.setText(text);
  }
  
  /**
   * Adds a Widget to this result.
   * 
   * @param w the Widget to add to the result.
   */
  public void add(Widget w) {
    _displayPanel.add(w);
  }
  
  /**
   * Removes a Widget from this result if it exists, and returns whether or not the Widget was removed.
   * 
   * @param w the Widget to add to this result.
   * @return whether or not the Widget was removed from this result.
   */
  public boolean remove(Widget w) {
    return _displayPanel.remove(w);
  }
  
  /**
   * Empties this result.
   */
  public void clear() {
    _displayPanel.clear();
  }
  
  /**
   * Copies the result --- things like this are the reason this should be abstract (TODO)
   */
  public AResult copy() {
    return new AResult();
  }
  
  /**
   * Changes this result in a fitting way for onMouseEnter events. Default is to do nothing, but override this method
   * to modify this result if you want it to have a mouseover.
   */
  public void showHover() {}
  
  /**
   * Changes this result in a fitting way for onMouseLeave events. Default is to do nothing, but override this method
   * to modify this result if you want it to have a mouseover. Usually a good idea to define the "normal" state of this result
   * here.
   */
  public void hideHover() {}
  
  /**
   * Changes this result in some way. Default is to do nothing. Override this to change views for expanded results.
   */
  public void expand() {}
  
  /**
   * Changes this result to a collapsed view. Default is to do nothing. Usually a good idea to define the "normal" state of
   * this result here.
   */
  public void collapse() {}
  
  /**
   * Slowly changes the size of this result from <code>start</code> to <code>end</code> pixels.
   * 
   * @param start the starting size of this result in pixels
   * @param end the ending size of this result in pixels
   */
  public void slide(int start, int end) {
    SlideTimer.Singleton.slide(this, start, end);
  }
  
  /** 
   * Convenience method for adding Labels as new layers to the AResult.
   * 
   * @param s the String to add as a Label to this result
   */
  public void addLabel(String s) {
    Label label = new Label(s);
    label.setStyleName("aResultFragment");
    add(label);
  }
  
  public void attachToPopupHost(PopupHost host) {
    addMouseListener(host.getPopupListener());
  }
   
  public void tagForExport() {
    ExportPanel.Singleton.addExport(this);  // ExportPanel will determine what to do based on the exportId 
  }
  
  public void untagForExport() {
    ExportPanel.Singleton.removeExport(this);
  }
  
  public void performImport(Element portableObject) throws ImportException {
    throw new ImportException("Cannot import object: " + portableObject);
  }
  
  public String performExport(String format) throws ExportException {
    throw new ExportException("Cannot export to format: " + format);
  }
  
  public Widget getWidget() {
    return this;
  }
}
