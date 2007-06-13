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

package com.flatown.client.eutils;

import com.flatown.client.eutils.params.*;
import com.flatown.client.eutils.ui.PubmedArticle;
import com.flatown.client.AResult;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Element;

public class EFetch extends EntrezUtility {

  public static final int MAXRESULTS = 10;
  
  public EFetch(SinksEntrezUtil sink) {
    super(sink);
  }
  
  protected void initialize() {
    _name = "efetch.fcgi";
    _doctype = "PubmedArticleSet";
    _defaultParams = new URLParams();
    _defaultParams.setParam("report", new StringParam("sgml"));
    _defaultParams.setParam("mode", new StringParam("xml"));
    _defaultParams.setParam("db", new StringParam("pubmed"));
    _defaultParams.setParam("dispmax", new IntParam(MAXRESULTS));
  }
  
  protected AResult[] formatResults(Document eResult) {
    NodeList articleNodes = eResult.getElementsByTagName("PubmedArticle");
    AResult[] results = null;
    
    if (articleNodes.getLength() > 0) {
      results = new AResult[articleNodes.getLength()];
      
      Node handler;
      for (int i = 0; i < results.length; i++) {
        results[i] = new PubmedArticle(articleNodes.item(i));
      }
    }
    
    return results;
  }
}