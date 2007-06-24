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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.DOM;

/** A ChameleonBox displays different elements depending on whether or not the parent SearchBox is editable.
 * If it is editable, the ChameleonBox shows a TextBox and a ListBox, otherwise it shows a CheckBox and a Label
 */
public class ChameleonBox extends FlowPanel {
  
  /** Stores the state of this ChameleonBox, i.e. whether or not we can change what it is searching for.
   * This is so we can reuse SearchBoxes in the FavoritesPanel.
   */
  private boolean _editable;
  
  /* Fields contained inside the ChameleonBox */
  private TextBox _search;
  private ListBox _dbMenu;
  private CheckBox _displayResults;
  private Label _searchQuery;
  private ResultsBox _resultsbox;
  
  /** Constructor for a ChameleonBox
   * 
   */
  public ChameleonBox(ResultsBox resultsbox) {
    _resultsbox = resultsbox;
    /* When a ChameleonBox is first created, it is editable. It only becomes uneditable
     * when the SearchBox gets moved to the FavoritesPanel.
     */
    _editable = true;

    
    /* Create the fields maintained by the ChameleonBox */
    createSearchField();
    createDatabaseMenu();
    createDisplayResultsCheckbox();
    createSearchQueryLabel();
    
    /* Add the widgets to the DIV (FlowPanel) */
    this.add(_search);
    this.add(_dbMenu);
    this.add(_displayResults);
    this.add(_searchQuery);
    
    /* Format the element properly */
    DOM.setStyleAttribute(this.getElement(), "cssFloat", "left");
    DOM.setStyleAttribute(this.getElement(), "width", "auto");
    DOM.setStyleAttribute(this.getElement(), "padding", "0px 0px 5px 5px");
  }
  
  /** Toggles whether or not the search query can change */ 
  public void toggleEditable() {
    setEditable(_editable ? false : true);
  }
  
  /** Sets whether or not the search query is editable and displays the 
   * appropriate fields.
   */
  public void setEditable(boolean editable) {
    updateSearchQueryLabel();
    
    _editable = editable;
    _search.setVisible(_editable);
    _dbMenu.setVisible(_editable);
    _displayResults.setVisible(!_editable);
    _searchQuery.setVisible(!_editable);
  }
  
  /** Returns true if the ChameleonBox is editable
   * 
   */
  public boolean isEditable() {
    return _editable;
  }
  
  /** Instantiates the textbox object in which the search is stored */
  private void createSearchField() {
    _search = new TextBox();
    _search.setName("search");
    _search.setVisibleLength(25);
  }
  
  /** Instantiates the list of databases to search from */
  private void createDatabaseMenu() {
    _dbMenu = new ListBox();
    _dbMenu.setName("dbMenu");
    _dbMenu.addItem("PubMed");
  }
  
  /** Instantiates the checkbox that controls whether or not results are displayed */
  private void createDisplayResultsCheckbox() {
    _displayResults = new CheckBox();
    _displayResults.setChecked(true);
    _displayResults.setVisible(false);
    DOM.setStyleAttribute(_displayResults.getElement(), "cssFloat", "left");
    _displayResults.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        _resultsbox.setVisible(areResultsDisplayed());
        GBox.Prefs.saveFavorites();
      }
    });
  }
  
  /** Instantiates a label that displays the search query */
  private void createSearchQueryLabel() {
    _searchQuery = new Label();
    _searchQuery.setVisible(false);
  }
  
  /** Refreshes the text in the search query label so that it matches that of the text box */
  private void updateSearchQueryLabel() {
    _searchQuery.setText(getSearchQuery() + " in " + getDatabase());
  }
  
  /* Field Accessors */
  
  /** Sets the search text for the TextBox */
  public void setSearchQuery(String query) {
    _search.setText(query);
  }
  
  /** Gets the search text from the TextBox */
  public String getSearchQuery() {
    return _search.getText();
  }
  
  /** Sets the database that is selected in the ListBox */
  public void setDatabase(String db) {
    Utilities.selectListItem(_dbMenu, db);
  }
  
  /** Gets the database that is selected from the listbox */
  public String getDatabase() {
    return _dbMenu.getItemText(_dbMenu.getSelectedIndex());
  }
  
  /** Tells whether or not the CheckBox is checked, which determines
   * if results should be displayed for the SearchBox
   */
  public boolean areResultsDisplayed() {
    return _displayResults.isChecked();
  }
  
  /** Set whether or not to display the results for this ChamBox */
  public void setResultsDisplayed(boolean b) {
    _displayResults.setChecked(b);
  }
}