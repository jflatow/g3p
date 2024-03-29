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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class HelpPanel extends ScrollPanel {
  
  public static final HelpPanel Singleton = new HelpPanel();
  
  private StackPanel _controlPanel;
  
  private HelpPanel() {
    _controlPanel = new StackPanel();
    _controlPanel.add(makeSearchHelp(), makeHeader("Searching PubMed"), true);
    _controlPanel.add(makeSaveFavHelp(), makeHeader("Saving Favorites"), true);    
    _controlPanel.add(makeSaveBookmarkHelp(), makeHeader("Saving Bookmarks"), true);
    _controlPanel.add(makeExportHelp(), makeHeader("Exporting Results"), true);
    _controlPanel.add(makeShareLinkHelp(), makeHeader("Sharing Gadget Links"), true);
    // go to search to begin: search from there and add searches to favorites
    // if you save favorites it will load automatically instead of the help panel
    // you can save multiple favorites and reorder them by dragging and dropping
    // you can change the number of results or use the checkbox
    // click on results -> open the pubmed page
    // save as a bookmark
    // tag it for export
    // perform export from sharepanel
    
    setWidget(_controlPanel);
    
    DOM.setStyleAttribute(getElement(), "width", "100%");
    DOM.setStyleAttribute(getElement(), "height", "100%");
    DOM.setStyleAttribute(getElement(), "backgroundColor", "#E5ECF9");
    DOM.setStyleAttribute(getElement(), "maxHeight", Window.getClientHeight() - 40 + "px");
    
    DOM.setStyleAttribute(_controlPanel.getElement(), "width", "100%");
    DOM.setStyleAttribute(_controlPanel.getElement(), "height", "100%");
  }
  
  private String makeHeader(String text) {
    return "<div style='width:100%; border-top:1px solid #7AA5D6;'>"
      + "<div style='margin:5px 10px 5px 10px; font-weight:bold;'><a href='#'>"
      + text + "</a></div></div>";
  }
  
  private InfoPanel makeSearchHelp() {
    InfoPanel section = new InfoPanel();
    section.addText("To search ");
    section.addLink("PubMed", "http://www.pubmed.gov");
    section.addText(" go to the 'Search' panel, enter the ");
    section.addLink("search terms", "http://www.ncbi.nlm.nih.gov/books/bv.fcgi?rid=helppubmed.section.pubmedhelp.Searching_PubMed");
    section.addText(" into the textbox, select the desired number of results, and click the 'Search' link. "
                      + "In a moment, the results (if any exist) should appear below the search box.");
    section.addBreak();
    section.addExample("Example search terms (with fields)", "cancer[mesh] simon lin[au]");
    section.addExample("Example search terms (without fields)", "oligonucleotides nuID");
    section.addBreak();
    section.addText("Click on any of the results for more information and options. Click the PMID link to open the corresponding PubMed page in a new window. ");
    return section;
  }
  
  private FlowPanel makeSaveFavHelp() {
    InfoPanel section = new InfoPanel();
    section.addText("To save a search for later, click the 'Add to Favorites' link . A corresponding search box will then appear under the 'Favorites' panel every time you log in. "
                      + "Since the most recent results are always displayed, saving favorites is a good way to keep track of new results.");
    section.addBreak();
    section.addText("If you have multiple favorites, you can rearrange them by clicking and dragging.");
    section.addBreak();
    section.addText("Clicking the checkbox will toggle the display of the results. You can change the number of results you see by using the pulldown menu and clicking the 'Search' link.");
    section.addBreak();
    section.addText("Delete favorites at any time by clicking the 'Remove' link.");
    return section;
  }
  
  private FlowPanel makeSaveBookmarkHelp() {
    InfoPanel section = new InfoPanel();
    section.addText("You can save individual results to the Bookmarks panel, which will be remembered whenever you log in.");
    section.addBreak();
    section.addText("To save a bookmark, click on the result you wish to save, then click the 'Save Bookmark' link. "
                      + "When you go to the Bookmarks panel you will have the option to remove bookmarks instead of saving them.");
    return section;
  }
  
  private FlowPanel makeExportHelp() {
    InfoPanel section = new InfoPanel();
    section.addText("You can export results to a text format (currently the only format supported is EndNote). Exporting is a two step process. "
                      + "First you must tag all of the results you want to export. Once tagged, results will appear in the 'Export' section of the 'Share' panel. "
                      + "Select the format you want to export to, then click the 'Export' link. A new window should appear with the results in the desired format.");
    section.addBreak();
    section.addText("To tag an individual result, click on it, and then click the 'Tag for Export' link");
    return section;
  }
  
  private FlowPanel makeShareLinkHelp() {
    InfoPanel section = new InfoPanel();
    section.addText("If you want to share your current favorites and bookmarks with others, use the 'Link to Gadget' section of the 'Share' panel. "
                      + "From there you can create a URL that others can visit, or an iframe to include in another webpage. "
                      + "When accessing the gadget through this URL however, favorites and bookmarks will not be saved.");
    return section;
  }
}