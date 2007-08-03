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

import com.flatown.client.eutils.EntrezEngine;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONBoolean;

public class SearchBox extends FormPanel implements ClickListener {
  
  /** Panel to control layout and to hold our form fields 
   */
  private SearchLayout _layoutPanel;

  /** Constructor for a new SearchBox
   * 
   */
  public SearchBox() {
    /* Create the Layout */
    _layoutPanel = new SearchLayout(this);
 
    /* Configure the style and attach the layout */
    this.setStyleName("searchbox");
    this.setWidget(_layoutPanel);
    
    /* Setup the FormHandler */
    this.addFormHandler(new SearchFormHandler());
  }
  
  
  /** Convenience constructor for a pre-configured SearchBox */
  public SearchBox(String searchQuery, String database, int numResults) {
    this();
    _layoutPanel.setSearchQuery(searchQuery);
    _layoutPanel.setDatabase(database);
    _layoutPanel.setNumResults(numResults);
  }
  
  /** 
   * Convenience constructor for a SearchBox from a JSONObject. 
   */
  public SearchBox(JSONObject searchbox) {
    this();
    _layoutPanel.setSearchQuery(searchbox.get("query").isString().stringValue());
    _layoutPanel.setDatabase(searchbox.get("dbName").isString().stringValue());
    _layoutPanel.setNumResults((int)(searchbox.get("numResults").isNumber().getValue()));
    
    JSONValue vis;
    if ((vis = searchbox.get("vis")) != null) _layoutPanel.setResultsDisplayed(vis.isBoolean().booleanValue());
  }
  
  /** Called whenever a button is clicked on this SearchBox */
  public void onClick(Widget sender) {
    if (sender == _layoutPanel.getFavLink().getLink()) {
      if (_layoutPanel.isEditable())
        FavoritesPanel.Singleton.addSearchBox(this.makeFavorite());
      else
        FavoritesPanel.Singleton.remove(this);
    } 
    
    else if (sender == _layoutPanel.getSearchLink().getLink()) {
      this.submit();
    }
    // TODO: make the others like this
    else if (sender instanceof Hyperlink) {
      if (((Hyperlink)sender).getTargetHistoryToken().equals("viewPubmedSearchToken")) {
        Window.open(EntrezEngine.PubmedSearchURL + EntrezEngine.escape(EntrezEngine.replaceSpaces(getSearchQuery())), "", "");
      }
    }
  }

  /**
   * Returns a SearchBox identical to this one, only uneditable
   */
  private SearchBox makeFavorite() {
    SearchBox box = new SearchBox(getSearchQuery(), getDatabase(), getNumResults());
    box.getLayout().makeFavorite();
    return box;
  }

  /** Convenience methods to get the SearchBox parameters */
  public String getSearchQuery() {
    return _layoutPanel.getSearchQuery();
  }
  
  public String getDatabase() {
    return _layoutPanel.getDatabase();
  }
  
  public int getNumResults() {
    return _layoutPanel.getNumResults();
  }
  
  /** Gets the SearchLayout associated with this SearchBox */
  public SearchLayout getLayout() {
    return _layoutPanel;
  }
  
  /** Convenience method to get the ResultsBox for this SearchBox */
  public ResultsBox getResultsBox() {
    return _layoutPanel.getResultsBox();
  }
  
  /** Returns this SearchBox as a JSONValue */
  public JSONValue toJSON() {
    JSONObject json = new JSONObject();
    json.put("query", new JSONString(getSearchQuery()));
    json.put("dbName", new JSONString(getDatabase()));
    json.put("numResults", new JSONNumber(getNumResults()));
    json.put("vis", JSONBoolean.getInstance(areResultsDisplayed()));
    return json;
  }
  
  /** Tells whether or not the CheckBox is checked, which determines
   * if results should be displayed for the SearchBox
   */
  public boolean areResultsDisplayed() {
    return _layoutPanel.areResultsDisplayed();
  }
  
  /** Use this to force the searchbox to perform a new search on its values */
  public void search() {
    GBox.Prefs.saveFavorites();
    EntrezEngine.Singleton.search(this);
  }
  
  /** Specialized FormHandler for this Form. 
   * Since GWT doesn't actually use forms but iframes, we are still subject to the same-origin
   * security policy. Therefore, we won't ever actually submit the form but instead pass the submission
   * off to the Google Gadget API fetch utilities.
   */
  class SearchFormHandler implements FormHandler {
    
    /** Function called whenever the form is submitted */
    public void onSubmit(FormSubmitEvent event) {
      /* Perform form validation here */
      if (getSearchQuery().equals("")) return;
      /* Request the search be made */
      SearchBox.this.search();
      /* Always cancel the submission, since we aren't actually using the form to submit */
      event.setCancelled(true);
    }
    
    /** Function called whenever the submit returns (which doesn't really happen) */
    public void onSubmitComplete(FormSubmitCompleteEvent event) {}
  }
}