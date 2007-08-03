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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ClickListener;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONArray; 
import com.google.gwt.json.client.JSONString;


/**
 * The Panel used to hold the saved {@link AResult results}.
 */
public class BookmarksPanel extends ScrollPanel implements ClickListener {
  
  /** A Singleton instance of the panel. */
  public static final BookmarksPanel Singleton = new BookmarksPanel();
  /** The maximum number of results that can be contained in the panel */
  public static final int MaxBookmarks = 20;
  
  private BookmarkResultsBox _bookmarks;
  private String[] _savedIds;
  private int _count;
  
  private BookmarksPanel() {
    _bookmarks = new BookmarkResultsBox();
    _savedIds = new String[MaxBookmarks];
    _count = 0;
    
    VerticalPanel displayPanel = new VerticalPanel();
    displayPanel.add(createControls());
    displayPanel.add(_bookmarks);
    setWidget(displayPanel);

    DOM.setStyleAttribute(displayPanel.getElement(), "width", "100%");
    DOM.setStyleAttribute(getElement(), "width", "100%");
    DOM.setStyleAttribute(getElement(), "height", "100%");
    DOM.setStyleAttribute(getElement(), "backgroundColor", "#E5ECF9");
    DOM.setStyleAttribute(getElement(), "maxHeight", Window.getClientHeight() - 50 + "px");
  }
  
  public FlowPanel createControls() {
    FlowPanel controlPanel = new FlowPanel();
    controlPanel.add(new HoverLink("Clear All", "clearBookmarksToken", this, "shareLink"));
    controlPanel.add(new HoverLink("Tag All for Export", "exportAllToken", this, "shareLink"));
    DOM.setStyleAttribute(controlPanel.getElement(), "margin", "0px 0px 5px 20px");
    return controlPanel;
  }
  
  /**
   * Saves a String, which we assume to be a Pubmed ID, to the BookmarksPanel.
   * 
   * @param pmid the pubmed id of the result to be saved
   * @return whether or not the id was saved
   */
  public boolean saveId(String pmid) {
    // true only if the id gets added
    if (_count == MaxBookmarks) return false;
    for (int i = 0; i < _count + 1; i++) {
      if (_savedIds[i] == null) _savedIds[i] = pmid;
      else if (_savedIds[i].equals(pmid)) return false;
    }
    _count++;
    saveBookmarks();
    return true;
  }
  
  /**
   * Removes a String, which we assume to be a Pubmed ID, from the BookmarksPanel
   * 
   * @param pmid the pubmed id of the result to be removed
   */
  public void removeId(String pmid) {
    for (int i = 0; i < _count; i++) {
      if (_savedIds[i].equals(pmid)) {
        // adjust the id stack
        for (int j = i; j < _count - 1; j++) {
          _savedIds[j] = _savedIds[j+1];
        }
        _savedIds[_count] = null;
        _count--;
        saveBookmarks();
        _bookmarks.remove(i);
        return;
      }
    }
  }
  
  public void removeAll() {
    for (int i = _count - 1; i >= 0; i--) {
      _savedIds[i] = null;
      _bookmarks.remove(i);
    }
    _count = 0;
    saveBookmarks();
  }
  
  /**
   * Takes an array of bookmarks and loads them into memory, fetching and displaying the results from Pubmed.
   * 
   * @param bookmarks the bookmarks to be loaded
   */
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
  
  /** 
   * Implement the ClickListener interface -- only listens for save links.
   */
  public void onClick(Widget sender) {
    if (sender instanceof Hyperlink) {
      Hyperlink link = (Hyperlink)sender;
      if (link.getText().equals("Save Bookmark")) {
        String id = link.getTargetHistoryToken();
        if (saveId(id)) EntrezEngine.fetchBookmarks(new String[] {id});
      }
      else if (link.getText().equals("Remove Bookmark")) {
        removeId(link.getTargetHistoryToken());
      } else if (link.getTargetHistoryToken().equals("clearBookmarksToken")) {
        removeAll();
      } else if (link.getTargetHistoryToken().equals("exportAllToken")) {
        _bookmarks.tagAllForExport();
      }
    }
  }
  
  /** 
   * Returns this BookmarksPanel as a JSONArray.
   * 
   * @return the JSONValue representation of this BookmarksPanel.
   */
  public JSONValue toJSON() {
    JSONArray json = new JSONArray();
    for (int i = 0; i < _count; i++) {
      json.set(i, new JSONString(_savedIds[i]));
    }
    return json;
  }
  
  /** 
   * Get the BookmarkResultsBox so the EntrezEngine can push results to us.
   */
  public BookmarkResultsBox getSink() {
    return _bookmarks;
  }
  
  class BookmarkResultsBox extends ResultsBox {
    private BookmarkResultsBox() {
      super(null);
    }
    
    public void setResults(AResult[] results) {
      for (int i = 0; i < results.length; i++) {
        addResult(results[i]);
        results[i].setSaveText("Remove Bookmark");
      }
    }
  }
}