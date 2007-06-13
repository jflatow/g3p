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
import com.flatown.client.eutils.EntrezUtility;

import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Element;

import com.google.gwt.user.client.ui.Label;

public class PubmedArticle extends AResult {
  
  private String[] _authors;
  private String _title, _abstract, _journal, _book;
  
  public PubmedArticle(Node pubmedArticle) {
    // parse the node into an object
    Node handler = EntrezUtility.getFirstNodeOfTag(pubmedArticle, "ArticleTitle");
    if (handler != null) {
      _title = EntrezUtility.getNodeText(handler);
      addLabel("Title: " + _title);
    }
    
    handler = EntrezUtility.getFirstNodeOfTag(pubmedArticle, "AuthorList");
    add(new AuthorList(handler));                

    add(new Publication(pubmedArticle));   
  }
}
