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
  
  private String _title, _publicationString, _pmid;
  
  public Publication(Node pubmedArticle) {
    
    _title = EntrezUtility.getNodeText(EntrezUtility.getFirstNodeOfTag(pubmedArticle, "MedlineTA"));
    _pmid = EntrezUtility.getNodeText(EntrezUtility.getFirstNodeOfTag(pubmedArticle, "PMID")); 
    
    Node handler = EntrezUtility.getFirstNodeOfTag(pubmedArticle, "Journal"); 
    if (handler != null) {
      _publicationString = "Journal: " + _title + ". ";
      
      handler = EntrezUtility.getFirstNodeOfTag(pubmedArticle, "Year");
      if (handler != null) _publicationString += EntrezUtility.getNodeText(handler);
      
      _publicationString += ";";
      
      addLabel(_publicationString); 
    }
  }
}