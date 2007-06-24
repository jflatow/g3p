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

package com.flatown.client.eutils.ui;

import com.flatown.client.AResult;
import com.flatown.client.AResultFragment;
import com.flatown.client.HoverLink;
import com.flatown.client.eutils.EntrezUtility;

import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Element;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.DOM;

import com.google.gwt.user.client.Window;

public class PubmedArticle extends AResult implements ClickListener {
   
  private Node _article;
  private AResultFragment _title, _abstract;
  private Publication _publication;
  private PubmedID _pmid;
  private AuthorList _authors;
  private HoverLink _endNoteLink;
  private int _normalHeight;
  
  public PubmedArticle(Node pubmedArticle) {
    _article = pubmedArticle;  
    
    _title = new AResultFragment(makeTitle());
    _authors = makeAuthors();
    _publication = makePublication();
    
    String abstractText = makeAbstract();
    if (abstractText.length() - 1 > 250) abstractText = abstractText.substring(0, 250) + "...";
    _abstract = new AResultFragment(abstractText);
    
    _pmid = makeId();
    _save.getLink().setTargetHistoryToken(_pmid.toString());
    
    _endNoteLink = new HoverLink("EndNote", "endnoteToken", this);

    add(_title);
    add(_publication);
    add(_authors);
    add(_abstract);
    add(_pmid);
    add(_endNoteLink);
    add(_save);
    collapse();
    hideHover();
  }
  
  public String fullCitation() {
    return "";
  }
  
  public String endNoteCitation() {
    return "";
  }
  
  private String makeAbstract() {
    Node handler = EntrezUtility.getFirstNodeOfTag(_article, "AbstractText");
    return (handler == null) ? "" : EntrezUtility.getNodeText(handler);
  }
  
  private AuthorList makeAuthors() {
    AuthorList authors = new AuthorList(EntrezUtility.getFirstNodeOfTag(_article, "AuthorList")); 
    DOM.setStyleAttribute(authors.getElement(), "fontStyle", "italic");
    return authors;
  }
  
  private String makeTitle() {
    return EntrezUtility.getNodeText(EntrezUtility.getFirstNodeOfTag(_article, "ArticleTitle"));
  }
  
  private Publication makePublication() {
    Publication publication = new Publication(_article);
    return publication;
  }
  
  private PubmedID makeId() {
    return new PubmedID(_article);
  }
 
  private String makeMesh() {
    return "";
  }
  
  public void showHover() {
    DOM.setStyleAttribute(_title.getElement(), "fontStyle", "normal");
    DOM.setStyleAttribute(_title.getElement(), "fontWeight", "bold");
    DOM.setStyleAttribute(_title.getElement(), "backgroundColor", "#E5ECF9");
    DOM.setStyleAttribute(_publication.getElement(), "backgroundColor", "#E5ECF9");
    DOM.setStyleAttribute(_authors.getElement(), "backgroundColor", "#E5ECF9"); 
    DOM.setStyleAttribute(getElement(), "borderLeft", "1px solid #E5ECF9");
  }
  
  public void hideHover() {
    DOM.setStyleAttribute(_title.getElement(), "fontStyle", "normal");
    DOM.setStyleAttribute(_title.getElement(), "fontWeight", "bold");
    DOM.setStyleAttribute(_title.getElement(), "backgroundColor", "white");
    DOM.setStyleAttribute(_publication.getElement(), "backgroundColor", "white");
    DOM.setStyleAttribute(_authors.getElement(), "backgroundColor", "white"); 
    DOM.setStyleAttribute(getElement(), "borderLeft", "1px solid white");
  }
  
  public void expand() {
    //_normalHeight = _displayPanel.getOffsetHeight();
    _authors.setVisible(true);
    _abstract.setVisible(true);
    _pmid.setVisible(true);
    _endNoteLink.setVisible(true);
    _save.setVisible(true);
    //slide(_normalHeight, _displayPanel.getOffsetHeight());
  }
  
  public void collapse() {
    _authors.setVisible(false);
    _abstract.setVisible(false);
    _pmid.setVisible(false);
    _endNoteLink.setVisible(false);
    _save.setVisible(false);
    //setHeight(_normalHeight + "px");
  }
  
  public void makeEndNote() {}
  
  public void onClick(Widget sender) {
    if (sender instanceof Hyperlink) {
      Hyperlink link = (Hyperlink)sender;
      if (link.getText().equals("EndNote")) {
        makeEndNote();
        link.setText("Standard");
      } else if (link.getText().equals("Standard")) {
        showHover();
        link.setText("EndNote");
      }
    }
    // if its text is Normal, display the normal citation
  }
}
