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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.DOM;

public class SearchBox extends FormPanel implements ABox {

  /** Stores the state of this SearchBox, i.e. whether or not we can change what it is searching for.
   * This is so we can reuse SearchBoxes in the FavoritesPanel.
   */
  private boolean _editable;
  
  /** Panels to control layout and to hold our form fields 
   *  We use FlowPanels to wrap our layers inside of DIVs
   */
  private VerticalPanel _layoutPanel;
  private FlowPanel _topPanel, _bottomPanel;
  
  /** Form fields */
  private ChameleonBox _chamBox;
  private ListBox _numResults;
  private Hyperlink _searchLink, _favLink;
  
  /** The associated ResultsBox */
  private ResultsBox _resultsBox;
  
  /** Constructor for a new SearchBox
   * 
   */
  public SearchBox() {
    /* When a searchbox is first created, it is editable. It only becomes uneditable
     * when it gets moved to the FavoritesPanel.
     */
    _editable = true;

    /* Create the top and bottom layers and place them into a container */
    createTopPanel();
    createBottomPanel();
    createLayoutPanel();
 
    /* Configure the style and attach the layout */
    this.setStyleName("searchbox");
    this.setWidget(_layoutPanel);
  }
  
  /** Convenience constructor for a pre-configured SearchBox */
  public SearchBox(String searchQuery, String database, int numResults) {
    this();
    this.setSearchQuery(searchQuery);
    this.setDatabase(database);
    this.setNumResults(numResults);
  }
  
  /** Creates the layout panel that contains the top and bottom Panels
   * 
   */
  private void createLayoutPanel() {
    _layoutPanel = new VerticalPanel();
    _layoutPanel.add(_topPanel);
    _layoutPanel.add(_bottomPanel);
    _layoutPanel.setStyleName("searchbox-LayoutPanel");
  }
  
  /** Creates the top layer panel of the SearchBox
   * 
   */
  private void createTopPanel() {
    /* Instantiate all the items that we will actually save handles to */
    _topPanel = new FlowPanel();
    _chamBox = new ChameleonBox();
    createSearchLink();
    
    /* Put the panel together and return it */
    _topPanel.add(_chamBox);
    _topPanel.add(_searchLink);
    _topPanel.setStyleName("searchbox-Layer");
  }
        
  /** Creates the bottom layer panel of the SearchBox
   * 
   */
  private void createBottomPanel() {
    /* Instantiate all the items that we will actually save handles to */
    _bottomPanel = new FlowPanel();
    createNResultsMenu();
    createFavLink();
    
    /* Wrap everything inside DIVs appropriately */
    Label show = new Label("Show");
    DOM.setStyleAttribute(show.getElement(), "margin", "0px 5px 0px 15px");
    
    FlowPanel fieldPanel = new FlowPanel();
    fieldPanel.add(_numResults);
    DOM.setStyleAttribute(fieldPanel.getElement(), "cssFloat", "left");
    
    Label results = new Label("Results");
    
    /* Put the panel together and return it */
    _bottomPanel.add(show);
    _bottomPanel.add(fieldPanel);
    _bottomPanel.add(results);
    _bottomPanel.add(_favLink);
    _bottomPanel.setStyleName("searchbox-Layer");
  }
  
  /** Instantiates a link to fire searches */
  private void createSearchLink() {
    _searchLink = new Hyperlink("Search", "searchToken"); 
  }
  
  /** Instantiates the list for the number of results */
  private void createNResultsMenu() {
    _numResults = new ListBox();
    for (int i = 1; i <= 10; i++) {
      _numResults.addItem("" + i);
    }
  }

  /** Instantiates a link that adds this SearchBox to the FavoritesPanel */
  private void createFavLink() {
    _favLink = new Hyperlink("Add to Favorites", "favToken");
    _favLink.addClickListener(this);
  }
  
  /** Called whenever a button was clicked on this SearchBox */
  public void onClick(Widget sender) {
    if (sender == _favLink) {
      if (_editable) {
        FavoritesPanel.Singleton.addSearchBox(this.makeFavorite());
      } else {
        FavoritesPanel.Singleton.remove(this);
      }
    }
  }
  
  /**
   * Returns a SearchBox identical to this one, only uneditable
   */
  private SearchBox makeFavorite() {
    SearchBox box = new SearchBox(this.getSearchQuery(), this.getDatabase(), this.getNumResults());
    box.transformFavLink();
    
    /* Make sure this is the last thing we do */
    box.makeUnEditable();
    return box;
  }
  
  /** Tells the ChameleonBox to hide the TextBox and ListBox and show the CheckBox and Label */
  public void makeUnEditable() {
    _chamBox.setEditable(false);
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
    selectListItem(_numResults, String.valueOf(num));
  }
  
  /** Gets the number of results selected for the SearchBox */
  public int getNumResults() {
    return Integer.parseInt(_numResults.getItemText(_numResults.getSelectedIndex()));
  }
  
  /** Whether or not to display the results for this SearchBox */
  public boolean areResultsDisplayed() {
    return _chamBox.areResultsDisplayed();
  }
  
  /* Utility methods */
  
  /** Turns the Add to Favorites link into a Remove link */
  public void transformFavLink() {
    _favLink.setText("Remove");
  }
  
  /** Selects an item in a ListBox with text the same as the given string, if it exists
   */
  public static void selectListItem(ListBox list, String text) {
    for (int i = 0; i < list.getItemCount(); i++) {
      if (list.getItemText(i).equals(text)) {
        list.setSelectedIndex(i);
        return;
      }
    }
  }
  
  /** A ChameleonBox displays different elements depending on whether or not the parent SearchBox is editable.
   * If it is editable, the ChameleonBox shows a TextBox and a ListBox, otherwise it shows a CheckBox and a Label
   */
  private class ChameleonBox extends FlowPanel {
    
    /* Fields contained inside the ChameleonBox */
    private TextBox _search;
    private ListBox _dbMenu;
    private CheckBox _displayResults;
    private Label _searchQuery;
    
    /** Constructor for a ChameleonBox
     * 
     */
    public ChameleonBox() {
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
    
    /** Instantiates the textbox object in which the search is stored */
    private void createSearchField() {
      _search = new TextBox();
      _search.setVisibleLength(25);
    }
    
    /** Instantiates the list of databases to search from */
    private void createDatabaseMenu() {
      _dbMenu = new ListBox();
      _dbMenu.addItem("PubMed");
    }
    
    /** Instantiates the checkbox that controls whether or not results are displayed */
    private void createDisplayResultsCheckbox() {
      _displayResults = new CheckBox();
      _displayResults.setChecked(true);
      _displayResults.setVisible(false);
      DOM.setStyleAttribute(_displayResults.getElement(), "cssFloat", "left");
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
      selectListItem(_dbMenu, db);
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
  }
}