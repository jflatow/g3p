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

import com.flatown.client.eutils.params.EnvAndURLParams;

import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Element;

/**
 * An {@link EntrezUtility} that can produce an IdList or WebEnv & QueryKey should extend this
 * class so it can flow into other EntrezUtilities.
 */
public abstract class YieldsEnvironment extends EntrezUtility {
    
  protected EntrezUtility _utilSink;
  protected EnvAndURLParams _utilParams;
  protected boolean _isPipe;
  
  protected YieldsEnvironment() {}
  
  public YieldsEnvironment(SinksEntrezUtil sink) {
    super(sink);
    _isPipe = false;
  }
  
  public YieldsEnvironment(EntrezUtility sink, EnvAndURLParams params) {
    _utilSink = sink;
    _utilParams = params;
    _isPipe = true;
  }
  
  public void parse(String xml) {
    try {
      if (_isPipe) {
        Document eResults = XMLParser.parse(xml);
        checkForDTDErrors(eResults);
        extractParams(eResults);
        _utilSink.run(_utilParams);
      } else {
        super.parse(xml);
      }
    } catch (Exception e) {
      onEntrezError(new EntrezException("Could not parse document: " + e));
    }
  }
  
    /** What to do to the utilParams if we are running another utility (yields takes care of environments)
     * Override this to add extra parsing for the piped utility, but be sure to call the super method in order
     * to pass the environment.
     */
  protected void extractParams(Document eResults) throws EntrezException {
    // attempt to extract the webenv and querykey
    Node envNode, queryKeyNode;
    Element idListNode;
    if (((envNode = getFirstNodeOfTag(eResults, "WebEnv")) != null) 
          && ((queryKeyNode = getFirstNodeOfTag(eResults, "QueryKey")) != null)) {
      _utilParams.setEnv(getNodeText(envNode), Integer.parseInt(getNodeText(queryKeyNode)));
    } 
    // otherwise try for an IdList
    else if ((idListNode = (Element)getFirstNodeOfTag(eResults, "IdList")) != null) {
      NodeList idList = idListNode.getElementsByTagName("Id");
      String ids[] = new String[idList.getLength()];
      for (int i = 0; i < idList.getLength(); i++) {
        ids[i] = getNodeText(idList.item(i));
      }
      _utilParams.setEnv(ids);
    }
  }
  
  protected void onEntrezError(EntrezException e) {
    if (_isPipe) _utilSink.onEntrezError(e);
    else super.onEntrezError(e);
  }
  
  protected void logError(String logEntry) {
    if (_isPipe) _utilSink.logError(logEntry);
    else super.logError(logEntry);
  }
  
}