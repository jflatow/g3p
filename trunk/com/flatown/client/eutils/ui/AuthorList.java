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
public class AuthorList extends AResultFragment {
  
  private String[] _authors;
  
  /**
   * Construct an AuthorList from an AuthorList node in a DOM.
   * 
   * @param authorListNode the node which will be assumed to be an AuthorList node in a DOM
   */
  public AuthorList(Node authorListNode) {
    if (authorListNode != null) {
      NodeList authorNodes = ((Element)authorListNode).getElementsByTagName("Author");
      _authors = new String[authorNodes.getLength()];
      for (int j = 0; j < authorNodes.getLength(); j++) {
        Node authorHandler = authorNodes.item(j);
        Node firstName = EntrezUtility.getFirstNodeOfTag(authorHandler, "ForeName");
        if (firstName == null) firstName = EntrezUtility.getFirstNodeOfTag(authorHandler, "FirstName");
        Node lastName = EntrezUtility.getFirstNodeOfTag(authorHandler, "LastName");
        Node suffix = EntrezUtility.getFirstNodeOfTag(authorHandler, "Suffix");
        _authors[j] = (lastName != null ? EntrezUtility.getNodeText(firstName) : "") + ", " 
          + (firstName != null ? EntrezUtility.getNodeText(lastName) : "")
          + (suffix != null ? ", " + EntrezUtility.getNodeText(suffix) : "");
      }
      addLabel(authorString()); 
    } 
  }
  
  /**
   * How to represent this object as a String. We don't override toString(), because the default
   * GWT toString() is sometimes useful.
   * 
   * @return this object as a String
   */
  public String authorString() {
    String s = "";
    if (_authors != null) {
      for (int i = 0; i < _authors.length; i++) {
        if (i == 3) return s + "et al.";
        s += _authors[i] + (i == _authors.length - 1 ? "." : ", ");
      }
    }
    return s;
  }
  
  /**
   * How to represent this object as part of an EndNote citation. Perhaps this should be moved to an interface.
   * 
   * @return the object as part of an EndNote citation String
   */
  public String endNoteCitation() {
    if (_authors == null) return "";
    String s = "";
    for (int i = 0; i < _authors.length; i++) {
      s += "%A " + _authors[i] + "\n";
    }
    return s;
  }
}