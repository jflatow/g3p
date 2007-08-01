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
import com.flatown.client.io.ExportException;
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

/**
 * The main type of AResult used by the engine, since most pipelines end with an EFetch.
 */
public class PubmedArticle extends AResult implements ClickListener {
   
  private Node _article;
  private ArticleTitle _title;
  private AResultFragment _abstract;
  private Publication _publication;
  private PubmedID _pmid;
  private AuthorList _authors;
  private HoverLink _exportLink;
  private int _normalHeight;
  
  public PubmedArticle(Node pubmedArticle) {
    _article = pubmedArticle;  
    
    _title = new ArticleTitle(EntrezUtility.getFirstNodeOfTag(_article, "ArticleTitle"));
    
    _authors = new AuthorList(EntrezUtility.getFirstNodeOfTag(_article, "AuthorList")); 
    DOM.setStyleAttribute(_authors.getElement(), "fontStyle", "italic");
    
    _publication = new Publication(_article);
    
    String abstractText = makeAbstract();
    if (abstractText.length() - 1 > 250) abstractText = abstractText.substring(0, 250) + "...";
    _abstract = new AResultFragment(abstractText);
    
    _pmid = new PubmedID(_article);
    _save.getLink().setTargetHistoryToken(_pmid.toString());
    
    _exportLink = new HoverLink("Tag for Export", "exportToken", this);

    add(_title);
    add(_publication);
    add(_authors);
    add(_abstract);
    add(_pmid);
    add(_exportLink);
    add(_save);
    collapse();
    hideHover();
  }
  
  public String fullCitation() {
    return "";
  }
  
  public String endNoteCitation() {
    return _authors.endNoteCitation() + _title.endNoteCitation() + _publication.endNoteCitation(); // "%X " + _abstract?
  }
  
  private String makeAbstract() {
    Node handler = EntrezUtility.getFirstNodeOfTag(_article, "AbstractText");
    return (handler == null) ? "" : EntrezUtility.getNodeText(handler);
  }
  
  public AuthorList getAuthors() {
    return _authors;
  }
  
  public ArticleTitle getArticleTitle() {
    return _title;
  }
  
  public Publication getPublication() {
    return _publication;
  }
  
  public PubmedID getId() {
    return _pmid;
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
    _exportLink.setVisible(true);
    _save.setVisible(true);
    //slide(_normalHeight, _displayPanel.getOffsetHeight());
  }
  
  public void collapse() {
    _authors.setVisible(false);
    _abstract.setVisible(false);
    _pmid.setVisible(false);
    _exportLink.setVisible(false);
    _save.setVisible(false);
    //setHeight(_normalHeight + "px");
  }
  
  public void tagForExport() {
    super.tagForExport();
    _exportLink.setText("Untag for Export");
  }
  
  public String performExport(String format) throws ExportException {
    if (format.equals("EndNote")) {
      return endNoteCitation();
    } else {
      return super.performExport(format);
    }
  }
  
  public boolean equals(Object obj) {
    if (obj instanceof PubmedArticle)
      return ((PubmedArticle)obj).getId().toString().equals(_pmid.toString());
    return false;
  }
  
  public void onClick(Widget sender) {
    if (sender instanceof Hyperlink) {
      Hyperlink link = (Hyperlink)sender;
      if (link.getText().equals("Tag for Export")) {
        PubmedArticle copy = new PubmedArticle(_article);
        copy.tagForExport();
      } else if (link.getText().equals("Untag for Export")) {
        untagForExport();
      }
    }
  }
}
