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
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Element;

/**
 * A type of {@link AResultFragment} that knows how to construct itself from the Entrez definition of 
 * an AuthorList XML element.
 */
public class ArticleTitle extends AResultFragment {
  
  private String _title;
  
  /**
   * Construct an ArticleTitle from an ArticleTitle node in a DOM.
   * 
   * @param authorListNode the node which will be assumed to be an AuthorList node in a DOM
   */
  public ArticleTitle(Node articleTitleNode) {
    addLabel(_title = EntrezUtility.getNodeText(articleTitleNode));
  }
  
  /**
   * How to represent this object as a String. We don't override toString(), because the default
   * GWT toString() is sometimes useful.
   * 
   * @return this object as a String
   */
  public String titleString() {
    return _title;
  }
  
  /**
   * How to represent this object as part of an EndNote citation. Perhaps this should be moved to an interface.
   * 
   * @return the object as part of an EndNote citation String
   */
  public String endNoteCitation() {
    return "%T " + titleString() + "\n";
  }
}