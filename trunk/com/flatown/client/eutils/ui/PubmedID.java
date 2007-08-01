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
import com.flatown.client.HoverLink;
import com.flatown.client.eutils.EntrezUtility;
import com.flatown.client.eutils.EntrezEngine;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ClickListener;

import com.google.gwt.xml.client.Node;
// now that we can pass data with the hoverlink we can move the clicklistener into the host: resultsbox?
// might be slightly better memory usage but this is much clearer, and the listener is pretty lightweight
public class PubmedID extends AResultFragment implements ClickListener {
  
  private String _pmid; 
   
  public PubmedID(Node article) {
    _pmid = EntrezUtility.getNodeText(EntrezUtility.getFirstNodeOfTag(article, "PMID")); 
    add(new HoverLink("PMID: " + _pmid, "pmidToken", this, "aResultFragment"));
  }
  
  public String toString() {
    return _pmid;
  }
  
  /** Implement the ClickListener interface */
  public void onClick(Widget sender) {
    Window.open(EntrezEngine.PubmedURL + _pmid, "pubmed", "");
  }
}