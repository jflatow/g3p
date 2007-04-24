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

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FavoritesPanel extends VerticalPanel {
  
  public static final FavoritesPanel Singleton = new FavoritesPanel();
  
  private FavoritesPanel() {
    
  }
  
  /** Adds a SearchBox to the FavoritesPanel, but only if it doesn't already exist (and is non-empty) */
  public void addSearchBox(SearchBox searchBox) {
    if (!searchBox.getSearchQuery().equals("") 
          && !containsSearch(searchBox.getSearchQuery(), searchBox.getDatabase()))
      super.add(searchBox);
  }
  
  /** Checks to see if the FavoritesPanel contains a given search request */
  private boolean containsSearch(String searchQuery, String database) {
    for (int i = 0; i < this.getWidgetCount(); i++) {
      Widget current = this.getWidget(i);
      if (current instanceof SearchBox) {
        if (((SearchBox)current).getSearchQuery().equals(searchQuery)
              && ((SearchBox)current).getDatabase().equals(database))
          return true;
      }
    }
    return false;
  }
}