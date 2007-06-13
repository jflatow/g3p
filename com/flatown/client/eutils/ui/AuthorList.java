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

public class AuthorList extends AResultFragment {
  
  private String[] _authors;
  
  public AuthorList(Node authorListNode) {
    if (authorListNode != null) {
      NodeList authorNodes = ((Element)authorListNode).getElementsByTagName("Author");
      _authors = new String[authorNodes.getLength()];
      for (int j = 0; j < authorNodes.getLength(); j++) {
        Node authorHandler = authorNodes.item(j);
        _authors[j] = 
          EntrezUtility.getNodeText(EntrezUtility.getFirstNodeOfTag(authorHandler, "LastName")) + " " +
          EntrezUtility.getNodeText(EntrezUtility.getFirstNodeOfTag(authorHandler, "Initials"));
      }
      addLabel("Authors: " + authorString()); 
    } 
  }
  
  public String authorString() {
    String s = "";
    if (_authors != null) {
      for (int i = 0; i < _authors.length; i++) {
        s += _authors[i] + (i == _authors.length - 1 ? "." : ", ");
      }
    }
    return s;
  }
}