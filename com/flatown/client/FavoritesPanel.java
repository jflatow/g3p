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

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONArray;

/**
 * The panel holding all of our favorite {@link SearchBox searchboxes}.
 */
public class FavoritesPanel extends ScrollPanel implements DragHost {
  
  public static final FavoritesPanel Singleton = new FavoritesPanel();
  
  private VerticalPanel _favorites;
  private FocusPanel _listenerPanel;
  private DragBar _clicked;
  private MouseListener _dragListener;
  
  private FavoritesPanel() {
    _favorites = new VerticalPanel();
    _favorites.setStyleName("favorites");
    
    _listenerPanel = new FocusPanel(_favorites);
    _listenerPanel.addMouseListener(new MouseListenerAdapter() {
      public void onMouseLeave(Widget sender) {
        _clicked = null;
      }
      
      public void onMouseUp(Widget sender, int x, int y) {
        _clicked = null;
      }
    });
    
    setWidget(_listenerPanel);
    setStyleName("favoritesPanel");
    DOM.setStyleAttribute(getElement(), "maxHeight", Window.getClientHeight() - 50 + "px");
    
    _dragListener = new MouseListenerAdapter() {
      public void onMouseEnter(Widget sender) {
        if (sender instanceof DragBar) {
          DragBar bar = (DragBar)sender;
          if (isClicked() && _clicked != bar) {
            int newPos = _favorites.getWidgetIndex(bar.getDragWidget());
            _favorites.remove(_clicked.getDragWidget());
            _favorites.insert(_clicked.getDragWidget(), newPos);
          }
        }
      }
      
      public void onMouseDown(Widget sender, int x, int y) {
        if (sender instanceof DragBar) _clicked = (DragBar)sender;
      }
      
      public void onMouseUp(Widget sender, int x, int y) {
        if (isClicked()) GBox.Prefs.saveFavorites();
      }
    };
  }
  
  /** Adds a SearchBox to the FavoritesPanel, but only if it doesn't already exist (and is non-empty) */
  public void addSearchBox(SearchBox searchBox) {
    if (!searchBox.getSearchQuery().equals("") 
          && !containsSearch(searchBox.getSearchQuery(), searchBox.getDatabase())) {
      _favorites.add(searchBox);
      // have the searchbox search
      searchBox.getResultsBox().setVisible(searchBox.areResultsDisplayed());
      searchBox.search();
      GBox.Prefs.saveFavorites();
    }
  }
  
  public boolean remove(Widget w) {
    if (_favorites.remove(w)) {
      GBox.Prefs.saveFavorites();
      return true;
    }
    return false;
  }
  
  /** Checks to see if the FavoritesPanel contains a given search request */
  private boolean containsSearch(String searchQuery, String database) {
    for (int i = _favorites.getWidgetCount() - 1; i >= 0; i--) {
      Widget current = _favorites.getWidget(i);
      if (current instanceof SearchBox) {
        if (((SearchBox)current).getSearchQuery().equals(searchQuery)
              && ((SearchBox)current).getDatabase().equals(database))
          return true;
      }
    }
    return false;
  }
  
  /** Returns this FavoritesPanel as a JSONArray */
  public JSONValue toJSON() {
    JSONArray json = new JSONArray();
    for (int i = _favorites.getWidgetCount() - 1; i >= 0; i--) {
      Widget current = _favorites.getWidget(i);
      if (current instanceof SearchBox) {
        json.set(i, ((SearchBox)current).toJSON());
      }
    }
    return json;
  }
  
  /** Implement the DragHost interface */
  public MouseListener getDragListener() {
    return _dragListener;
  }
  
  public boolean isClicked() {
    return _clicked == null ? false : true;
  }
}