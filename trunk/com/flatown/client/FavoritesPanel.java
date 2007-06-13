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
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONArray;

public class FavoritesPanel extends ScrollPanel {
  
  public static final FavoritesPanel Singleton = new FavoritesPanel();
  
  private VerticalPanel _favorites;
  
  private FavoritesPanel() {
    _favorites = new VerticalPanel();
    setWidget(_favorites);
    _favorites.setStyleName("favorites");
    setStyleName("favoritesPanel");
    DOM.setStyleAttribute(getElement(), "maxHeight", Window.getClientHeight() - 50 + "px");
  }
  
  /** Adds a SearchBox to the FavoritesPanel, but only if it doesn't already exist (and is non-empty) */
  public void addSearchBox(SearchBox searchBox) {
    if (!searchBox.getSearchQuery().equals("") 
          && !containsSearch(searchBox.getSearchQuery(), searchBox.getDatabase())) {
      _favorites.add(searchBox);
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
}