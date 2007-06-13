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
import com.flatown.client.AResult;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Element;
import com.google.gwt.user.client.ui.Label;

public class ESearch extends YieldsEnvironment {
  
  public ESearch(SinksEntrezUtil sink) {
    super(sink);
  }
  
  public ESearch(EntrezUtility sink, EnvAndURLParams params) {
    super(sink, params);
  }
  
  protected void initialize() {
    _name = "esearch.fcgi";
    _doctype = "eSearchResult";
    _defaultParams = new URLParams();
    _defaultParams.setParam("usehistory", new StringParam("y"));
    _defaultParams.setParam("retmax", new IntParam(10));
  }
    
  /** What to do if we are going to a browser */
  protected AResult[] formatResults(Document eResult) {
    AResult[] results = new AResult[1];

    // set a result for every Id
    Element idListNode;
    if ((idListNode = (Element)getFirstNodeOfTag(eResult, "IdList")) != null) {
      NodeList idList = idListNode.getElementsByTagName("Id");
      results = new AResult[idList.getLength() + 1];
      for (int i = 0; i < idList.getLength(); i++) {
        results[i] = new AResult();
        results[i].addLabel(makeString(idList.item(i)));
      }
    }
    
    // set the result for all the other info
    results[results.length-1] = new AResult();
    String[] otherNodes = {"Count", "RetMax", "RestStart", "TranslationSet", "QueryTranslation", "QueryKey", "WebEnv", "TranslationStack"};
    Node currentNode;
    for (int i = 0; i < otherNodes.length; i++) {
      if ((currentNode = getFirstNodeOfTag(eResult, otherNodes[i])) != null) {
              results[results.length-1].addLabel(makeString(currentNode));
      }
    }

    return results;
  }
  
  /** All the possible errors we can throw from the get-go */
  protected void checkForDTDErrors(Document eResult) throws EntrezException {
    super.checkForDTDErrors(eResult);
    Node warningList, errorList;
    
    if ((warningList = getFirstNodeOfTag(eResult, "WarningList")) != null) {
      // phrase ignored or quoted phrase not found or ouput message
      // log warnings and ignore
      NodeList warnings = warningList.getChildNodes();
      for (int i = 0; i < warnings.getLength(); i++) {
        logError(makeString(warnings.item(i)));
      }
    }
    
    if ((errorList = getFirstNodeOfTag(eResult, "ErrorList")) != null) {
      // phrase not found or field not found error
      // log error and flag utility execution to stop
      NodeList errors = errorList.getChildNodes();
      for (int i = 0; i < errors.getLength(); i++) {
        logError(makeString(errors.item(i)));
      }
      throw new EntrezException("ESearch: Entrez server returned an error. See log for details");
    }

  }
}