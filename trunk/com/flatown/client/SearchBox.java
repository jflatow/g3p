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
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.DOM;

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
  
  /** Called whenever a button is clicked on this SearchBox */
  public void onClick(Widget sender) {
    if (sender == _layoutPanel.getFavLink()) {
      if (_layoutPanel.isEditable())
        FavoritesPanel.Singleton.addSearchBox(this.makeFavorite());
      else
        FavoritesPanel.Singleton.remove(this);
    } 
    
    else if (sender == _layoutPanel.getSearchLink()) {
      this.submit();
    }
  }

  /**
   * Returns a SearchBox identical to this one, only uneditable
   */
  private SearchBox makeFavorite() {
    SearchBox box = new SearchBox(_layoutPanel.getSearchQuery(), _layoutPanel.getDatabase(), _layoutPanel.getNumResults());
    box.getLayout().makeFavorite();
    return box;
  }

  /** Gets the SearchLayout associated with this SearchBox */
  public SearchLayout getLayout() {
    return _layoutPanel;
  }
  
  /** Specialized FormHandler for this Form */
  class SearchFormHandler implements FormHandler {
    
    /** Function called whenever the form is submitted */
    public void onSubmit(FormSubmitEvent event) {
      /* Perform form validation here */
      if (_layoutPanel.getSearchQuery().equals("")) event.setCancelled(true);
      /* Configure the action to take */
      setAction("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi");
    }
    
    /** Function called whenever the form actions returns */
    public void onSubmitComplete(FormSubmitCompleteEvent event) {
      System.out.println(event.getResults()+ "\n");
    }
  }
}