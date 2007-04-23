package com.flatown.client;

import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * The GBox is a modified TabPanel to control our layout.
 */
public class GBox extends TabPanel {
  
  public static final GBox Singleton = new GBox();
  
  private GBox() {
    this.add(FavoritesPanel.Singleton, "Favorites");
    this.add(SearchPanel.Singleton, "Search");
    this.add(HelpPanel.Singleton, "Help");
    
    insertSpacer(5, 2);
    insertSpacer(5, 4);
    this.selectTab(0);
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
    DOM.setStyleAttribute(div, "whiteSpace", "no-wrap");
    DOM.setStyleAttribute(div, "width", width + "px");
    DOM.setStyleAttribute(div, "borderBottom", "1px solid #7AA5D6");
  
    DOM.setAttribute(td, "align", "left");
    DOM.setStyleAttribute(td, "verticalAlign", "bottom");  
    
    // add them to the TabBar 
    DOM.appendChild(td, div);
    DOM.insertChild(tr, td, position);
    
    System.out.println(td);
  }
}
