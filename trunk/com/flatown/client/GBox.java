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

import com.flatown.client.prefs.*;

import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.Element;

/**
 * The GBox is a modified TabPanel to control our layout.
 */
public class GBox extends TabPanel {
  
  public static final GBox Singleton = new GBox();
  public static Preferences Prefs;
  
  private GBox() {
    this.add(FavoritesPanel.Singleton, "Favorites");
    this.add(SearchPanel.Singleton, "Search");
    this.add(BookmarksPanel.Singleton, "Bookmarks");
    this.add(SharePanel.Singleton, "Share");
    this.add(HelpPanel.Singleton, "Help");
    
    insertSpacer(5, 2);
    insertSpacer(5, 4);
    insertSpacer(5, 6);
    insertSpacer(5, 8);
    
    Prefs = new GadgetPrefs();
    Prefs.updateLastLogin();
    this.selectTab(Prefs.loadFavorites() ? 0 : 4);
    Prefs.loadBookmarks();
    
    Window.addWindowResizeListener(new WindowResizeListener() {
      public void onWindowResized(int width, int height) {
        DOM.setStyleAttribute(FavoritesPanel.Singleton.getElement(), "maxHeight", height - 40 + "px");
        DOM.setStyleAttribute(SearchPanel.Singleton.getElement(), "maxHeight", height - 40 + "px");
        DOM.setStyleAttribute(BookmarksPanel.Singleton.getElement(), "maxHeight", height - 40 + "px");
        DOM.setStyleAttribute(HelpPanel.Singleton.getElement(), "maxHeight", height - 40 + "px");
      }
    });
  }
  
  /**
   * Unfortunately, because of the way the TabBar is set up, in order to have better control of the formatting
   * we have to directly modify the DOM, particularly to allow for spacers in between tab elements. This is quite messy
   * and should be changed as soon as future version of GWT allow for arbitrary widgets. I've made a comment on a related
   * issue on the GWT issue tracker about getting this fixed. -jf
   * 
   * The worst part is that we can't even add a CSS class for the spacer - notice the hardwiring of the border,
   * Ideally this will be controlled with the rest of the tab style rules.
   */
  private void insertSpacer(int width, int position) {
    // create DOM elements
    Element div = DOM.createDiv();
    Element td = DOM.createTD();
    Element tr = DOM.getChild(DOM.getChild(getTabBar().getElement(), 0), 0);
    
    // configure the elements
    DOM.setAttribute(div, "class", "gbox-Spacer");
    DOM.setStyleAttribute(div, "whiteSpace", "nowrap");
    DOM.setStyleAttribute(div, "width", width + "px");
    DOM.setStyleAttribute(div, "borderBottom", "1px solid #7AA5D6");
  
    DOM.setAttribute(td, "align", "left");
    DOM.setStyleAttribute(td, "verticalAlign", "bottom");  
    
    // add them to the TabBar 
    DOM.appendChild(td, div);
    DOM.insertChild(tr, td, position);
  }
}
