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

import com.flatown.client.eutils.EntrezEngine;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.ClickListener;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONArray; 
import com.google.gwt.json.client.JSONString;


// give this a specialized resultsbox
public class BookmarksPanel extends FocusPanel implements ClickListener {
  
  public static final BookmarksPanel Singleton = new BookmarksPanel();
  public static final int MaxBookmarks = 20;
  
  private BookmarkResultsBox _bookmarks;
  private String[] _savedIds;
  private int _count;
  
  private BookmarksPanel() {
    _bookmarks = new BookmarkResultsBox();
    _savedIds = new String[MaxBookmarks];
    _count = 1;
    
    FlowPanel displayPanel = new FlowPanel();
    displayPanel.add(_bookmarks);
    setWidget(displayPanel);

    DOM.setStyleAttribute(getElement(), "width", "100%");
    DOM.setStyleAttribute(getElement(), "height", "100%");
    DOM.setStyleAttribute(getElement(), "maxHeight", Window.getClientHeight() - 50 + "px");
  }
  
  public boolean saveId(String pmid) {
    // true only if the id gets added
    if (_count == MaxBookmarks) return false;
    for (int i = 0; i < _count; i++) {
      if (_savedIds[i] == null) _savedIds[i] = pmid;
      else if (_savedIds[i].equals(pmid)) return false;
    }
    _count++;
    saveBookmarks();
    return true;
  }
  
  public void removeId(String pmid) {
    for (int i = 0; i < _count; i++) {
      if (_savedIds[i].equals(pmid)) {
        // adjust the id stack
        for (int j = 0; j < _count - i - 1; j++) {
          _savedIds[i+j] = _savedIds[i+j+1];
        }
        _savedIds[_count] = null;
        _count--;
        saveBookmarks();
        _bookmarks.remove(i);
        return;
      }
    }
  }
  
  public void loadBookmarks(JSONArray bookmarks) {
    _count = bookmarks.size();
    for (int i = 0; i < _count; i++) {
      _savedIds[i] = bookmarks.get(i).isString().stringValue();
    }
    EntrezEngine.fetchBookmarks(_savedIds);
  } 
  
  public void saveBookmarks() {
    GBox.Prefs.saveBookmarks();
  }
  
  /** Implement the ClickListener interface -- only listens for save links*/
  public void onClick(Widget sender) {
    if (sender instanceof Hyperlink) {
      Hyperlink link = (Hyperlink)sender;
      if (link.getText().equals("Save Bookmark")) {
        String id = link.getTargetHistoryToken();
        if (saveId(id)) EntrezEngine.fetchBookmarks(new String[] {id});
      }
      else if (link.getText().equals("Remove Bookmark")) {
        removeId(link.getTargetHistoryToken());
      }
    }
  }
  
  /** Returns this BookmarksPanel as a JSONArray */
  public JSONValue toJSON() {
    JSONArray json = new JSONArray();
    for (int i = 0; i < _count - 1; i++) {
      json.set(i, new JSONString(_savedIds[i]));
    }
    return json;
  }
  
  /** Get the BookmarkResultsBox for the Singleton */
  public BookmarkResultsBox getSink() {
    return _bookmarks;
  }
  
  class BookmarkResultsBox extends ResultsBox {
    private BookmarkResultsBox() {
      super(null);
    }
    
    public void setResults(AResult[] results) {
      for (int i = 0; i < results.length; i++) {
        add(results[i]);
        results[i].setSaveText("Remove Bookmark");
        results[i].attachToPopupHost(this);
      }
    }
  }
}