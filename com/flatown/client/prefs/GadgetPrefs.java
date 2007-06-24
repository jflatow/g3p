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

package com.flatown.client.prefs;

import java.util.Date;

import com.flatown.client.FavoritesPanel;
import com.flatown.client.BookmarksPanel;
import com.flatown.client.SearchBox;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

/** Preferences specific to a gadget 
 * Don't use a Singleton because we don't want GadgetPrefs taking up memory if it's not being used 
 */
public class GadgetPrefs implements Preferences {
  
  public Date LastLogin;
  
  public boolean loadFavorites() {
    return igLoadFavorites();
  }
  
  public void saveFavorites() {
    igSaveFavorites();
  }
  
  public boolean loadBookmarks() {
    return igLoadBookmarks();
  }
  
  public void saveBookmarks() {
    igSaveBookmarks();
  }
  
  public void updateLastLogin() {
    if (!hasPrefs()) return;
    LastLogin = new Date(Long.parseLong(getPref("lastLog")));
    setPref("lastLog", String.valueOf(new Date().getTime()));
  }
  
  public static boolean igLoadFavorites() {
    if (!hasPrefs()) return false;
    String favs = getPref("favs");
    if (favs.equals("")) return false;
    boolean hasFavs = false;
    JSONArray favorites = JSONParser.parse(favs).isArray();
    for (int i = 0; i < favorites.size(); i++) {
      SearchBox current = new SearchBox(favorites.get(i).isObject());
      current.getLayout().makeFavorite();
      FavoritesPanel.Singleton.addSearchBox(current);
      hasFavs = true;
    }
    return hasFavs;
  }
  
  public static void igSaveFavorites() {
    if (!hasPrefs()) return;
    setPref("favs", FavoritesPanel.Singleton.toJSON().toString());
  }

  public static boolean igLoadBookmarks() {
    if (!hasPrefs()) return false;
    String bookmarkString = getPref("bookmarks");
    if (bookmarkString.equals("")) return false;
    JSONArray bookmarks = JSONParser.parse(bookmarkString).isArray();
    BookmarksPanel.Singleton.loadBookmarks(bookmarks);
    return bookmarks.size() > 0;
  }
  
  public static void igSaveBookmarks() {
    if (!hasPrefs()) return;
    setPref("bookmarks", BookmarksPanel.Singleton.toJSON().toString());
  }
  
  private static native boolean hasPrefs() /*-{  
   return ($wnd.parent.prefs) ? true : false;
  }-*/;
    
  private static native String getPref(String name) /*-{
   return $wnd.parent.prefs.getString(name);
  }-*/;
    
  private static native void setPref(String name, String value) /*-{
   $wnd.parent.prefs.set(name, value);
  }-*/;
}