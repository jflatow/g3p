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

import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.DOM;

public class SearchBox extends FormPanel {

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
  private TextBox _search;
  private ListBox _dbMenu, _numResults;
  private Hyperlink _favLink;
  
  /** Constructor for a SearchBox
   * 
   */
  public SearchBox() {
    _editable = true;
    
    _layoutPanel = new VerticalPanel();
    _topPanel = createTopPanel();
    _bottomPanel = createBottomPanel();
    
    /* Finish putting the panels together */
    _layoutPanel.add(_topPanel);
    _layoutPanel.add(_bottomPanel);
    _layoutPanel.setStyleName("searchbox-LayoutPanel");
    this.setStyleName("searchbox");
    this.setWidget(_layoutPanel);
  }
  
  /** Creates the top layer of the SearchBox
   * 
   */
  private FlowPanel createTopPanel() {
    /* Instantiate all the items that we will actually save handles to */
    FlowPanel panel = new FlowPanel();
    _search = new TextBox();
    _search.setVisibleLength(30);
    _dbMenu = new ListBox();
    _dbMenu.addItem("PubMed");
    
    /* Wrap everything insides DIVs for proper formatting */
    FlowPanel fieldPanel = new FlowPanel();
    fieldPanel.add(_search);
    fieldPanel.add(_dbMenu);
    DOM.setStyleAttribute(fieldPanel.getElement(), "cssFloat", "left");
    DOM.setStyleAttribute(fieldPanel.getElement(), "width", "auto");
    DOM.setStyleAttribute(fieldPanel.getElement(), "padding", "0px 0px 5px 5px");
    
    Hyperlink test = new Hyperlink("Test", "token");
    
    /* Put the panel together and return it */
    panel.add(fieldPanel);
    panel.add(test);
    panel.setStyleName("searchbox-Layer");
    return panel;
  }
        
  /** Creates the bottom layer of the SearchBox
   * 
   */
  private FlowPanel createBottomPanel() {
    /* Instantiate all the items that we will actually save handles to */
    FlowPanel panel = new FlowPanel();
    _numResults = new ListBox();
    _numResults.addItem("1");
    _numResults.addItem("2");
    _numResults.addItem("3");
    _numResults.addItem("5");
    _numResults.addItem("10");
    _favLink = new Hyperlink("Add to Favorites", "token");
    
    /* Wrap everything inside DIVs appropriately */
    FlowPanel fieldPanel = new FlowPanel();
    fieldPanel.add(_numResults);
    DOM.setStyleAttribute(fieldPanel.getElement(), "cssFloat", "left");
    
    Label show = new Label("Show");
    DOM.setStyleAttribute(show.getElement(), "margin", "0px 5px 0px 15px");
    
    Label results = new Label("Results");
    
    /* Put the panel together and return it */
    panel.add(show);
    panel.add(fieldPanel);
    panel.add(results);
    panel.add(_favLink);
    panel.setStyleName("searchbox-Layer");
    return panel;
  }
}