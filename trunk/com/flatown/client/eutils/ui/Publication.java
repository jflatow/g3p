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

import com.flatown.client.AResultFragment;
import com.flatown.client.eutils.EntrezUtility;

import com.google.gwt.xml.client.Node;

import com.google.gwt.user.client.ui.Label;

public class Publication extends AResultFragment {
 
  private Node _article;
  
  public Publication(Node pubmedArticle) {
    _article = pubmedArticle;
    addLabel(toString()); 
  }
  
  public String getTitle() {
    return EntrezUtility.getNodeText(EntrezUtility.getFirstNodeOfTag(_article, "MedlineTA")) + ".";
  }
  
  public String getVolume() {
    Node handler = EntrezUtility.getFirstNodeOfTag(_article, "Volume");
    return (handler == null) ? "" : EntrezUtility.getNodeText(handler);
  }
  
  public String getIssue() {
    Node handler = EntrezUtility.getFirstNodeOfTag(_article, "Issue");
    return (handler == null) ? "" : "(" + EntrezUtility.getNodeText(handler) + "):";
  }
  
  public String getPages() {
    String pageString = "";
    Node handler = EntrezUtility.getFirstNodeOfTag(_article, "StartPage");
    pageString += (handler == null) ? "" : EntrezUtility.getNodeText(handler);
    handler = EntrezUtility.getFirstNodeOfTag(_article, "EndPage");
    pageString += (handler == null) ? "" : "-" + EntrezUtility.getNodeText(handler) + " ";
    handler = EntrezUtility.getFirstNodeOfTag(_article, "MedlinePgn");
    pageString += (handler == null) ? "" : EntrezUtility.getNodeText(handler);
    return pageString + (pageString.equals("") ? "" : ".");
  }
  
  public String getDate() {
    Node handler = EntrezUtility.getFirstNodeOfTag(_article, "PubDate");
    if (handler == null) return "";
    String pubDate = "";
    Node dateElem = EntrezUtility.getFirstNodeOfTag(handler, "Year");
    pubDate += (dateElem == null) ? "" : " " + EntrezUtility.getNodeText(dateElem);
    dateElem = EntrezUtility.getFirstNodeOfTag(handler, "Month");
    pubDate += (dateElem == null) ? "" : " " + EntrezUtility.getNodeText(dateElem);
    dateElem = EntrezUtility.getFirstNodeOfTag(handler, "Day");
    pubDate += (dateElem == null) ? "" : " " + EntrezUtility.getNodeText(dateElem);
    dateElem = EntrezUtility.getFirstNodeOfTag(handler, "Season");
    pubDate += (dateElem == null) ? "" : " " + EntrezUtility.getNodeText(dateElem);
    dateElem = EntrezUtility.getFirstNodeOfTag(handler, "MedlineDate");
    pubDate += (dateElem == null) ? "" : " " + EntrezUtility.getNodeText(dateElem);
    return pubDate + ";";
  }
  
  public String endNoteCitation() {
    return "%J " + getTitle() + "\n";
/*      + "%D " + getDate() + "\n"
      + "%V " + getVolume() + "\n"
      + "%P " + getPages() + "\n";
      + "%0 " +  also get the publication type + "\n"
      */ // and make all these objects
  }
  
  public String toString() {
    return getTitle() + getDate() + getVolume() + getIssue() + getPages();
  }
}