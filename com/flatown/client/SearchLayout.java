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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.DOM;

public class SearchLayout extends FlowPanel {
  
  /** The SearchBox holding this SearchLayout */
  private SearchBox _searchbox;
  
  /** Form fields */
  private ChameleonBox _chamBox;
  private ListBox _numResults;
  private HoverLink _searchLink, _favLink;
  
  /** The associated ResultsBox */
  private ResultsBox _resultsbox;
  
  
   /** Constructs the layout panel that contains the top and bottom Panels
   * 
   */
  public SearchLayout(SearchBox searchbox) {
    _searchbox = searchbox;
    _resultsbox = new ResultsBox(_searchbox);
    
    this.add(createTopPanel());
    this.add(createBottomPanel());
    this.add(_resultsbox);
    this.setStyleName("searchbox-LayoutPanel");
  }
  
  /** Creates the top layer panel of the SearchBox
   * 
   */
  private FlowPanel createTopPanel() {
    /* Instantiate all the items that we will actually save handles to */
    _chamBox = new ChameleonBox();
    _searchLink = new HoverLink("Search", "searchToken", _searchbox);
    
    /* Put the panel together and return it */
    FlowPanel topPanel = new FlowPanel();
    topPanel.add(_chamBox);
    topPanel.add(_searchLink);
    topPanel.setStyleName("searchbox-Layer");
    return topPanel;
  }
  
  /** Creates the bottom layer panel of the SearchBox
   * 
   */
  private FlowPanel createBottomPanel() {
    /* Instantiate all the items that we will actually save handles to */
    createNResultsMenu();
    _favLink = new HoverLink("Add to Favorites", "favToken", _searchbox);
    
    /* Wrap everything inside DIVs appropriately */
    Label show = new Label("Show");
    DOM.setStyleAttribute(show.getElement(), "margin", "0px 5px 0px 15px");
    
    FlowPanel fieldPanel = new FlowPanel();
    fieldPanel.add(_numResults);
    DOM.setStyleAttribute(fieldPanel.getElement(), "cssFloat", "left");
    
    Label results = new Label("Results");
    
    /* Put the panel together and return it */
    FlowPanel bottomPanel = new FlowPanel();
    bottomPanel.add(show);
    bottomPanel.add(fieldPanel);
    bottomPanel.add(results);
    bottomPanel.add(_favLink);
    bottomPanel.setStyleName("searchbox-Layer");
    return bottomPanel;
  }
  
  /** Instantiates the list for the number of results */
  private void createNResultsMenu() {
    _numResults = new ListBox();
    _numResults.setName("numResults");
    for (int i = 1; i <= 10; i++) {
      _numResults.addItem("" + i);
    }
  }
  
  /* Field Accessor Methods */
  
  /** Sets the text entered in the SearchBox TextBox */
  public void setSearchQuery(String query) {
    _chamBox.setSearchQuery(query);
  }
  
  /** Gets the text entered in the SearchBox TextBox */
  public String getSearchQuery() {
    return _chamBox.getSearchQuery();
  }
  
  /** Sets the database selected for the SearchBox */
  public void setDatabase(String db) {
    _chamBox.setDatabase(db);
  }
  
  /** Gets the database selected for the SearchBox */
  public String getDatabase() {
    return _chamBox.getDatabase();
  }
  
  /** Sets the number of results selected for the SearchBox */
  public void setNumResults(int num) {
    Utilities.selectListItem(_numResults, String.valueOf(num));
  }
  
  /** Gets the number of results selected for the SearchBox */
  public int getNumResults() {
    return Integer.parseInt(_numResults.getItemText(_numResults.getSelectedIndex()));
  }
  
  /** Whether or not to display the results for this SearchBox */
  public boolean areResultsDisplayed() {
    return _chamBox.areResultsDisplayed();
  }
  
  /** Whether or not the ChameleonBox is editable */
  public boolean isEditable() {
    return _chamBox.isEditable();
  }
  
  /** Changes the favorites link and makes the ChameleonBox uneditable */
  public void makeFavorite() {
    _chamBox.setEditable(false);
    _favLink.setText("Remove");
  }
  
  /** Returns the favorites link for this layout panel */
  public HoverLink getFavLink() {
    return _favLink;
  }
  
  /** Returns the search link for this layout panel */
  public HoverLink getSearchLink() {
    return _searchLink;
  }
  
  /** Returns the ResultsBox located on this layout panel */
  public ResultsBox getResultsBox() {
    return _resultsbox;
  }
}