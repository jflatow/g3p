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

import com.flatown.client.xml.XmlParser;
import com.flatown.client.AResult;
import com.flatown.client.eutils.params.URLParams;

import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.Element;

import com.google.gwt.user.client.Window;

/** This interface encompasses all the EUtils, which need to be able to run
 * and should create results for a ResultsBox when they are finished running. In order to extend
 * this class a utility should implement a parseResults method and define the Name.
 */
public abstract class EntrezUtility implements XmlParser {
  
  protected String _name, _doctype;
  protected SinksEntrezUtil _formattedSink;
  protected URLParams _defaultParams;
  
  protected EntrezUtility() {
    initialize();
  }
  
  public EntrezUtility(SinksEntrezUtil sink) {
    this();
    _formattedSink = sink;
  }
  
  public void run(URLParams params) {
    params.syncWith(_defaultParams);
    EntrezEngine.Singleton.request(_name, params, this);
  }
  
  protected void initialize() {}
  
  public void parse(String xml) {
    try {
      if (xml == null) throw new EntrezException("Entrez server timed out or did not respond");
      Document eResult = XMLParser.parse(xml);
      AResult[] results = formatResults(eResult);
      checkForDTDErrors(eResult);
      _formattedSink.setResults(results);
    } catch (Exception e) {
      onEntrezError(new EntrezException("Could not parse document: " + e));
    }
  }
  
  protected abstract AResult[] formatResults(Document eResult);
  // format the results
  
  protected void onEntrezError(EntrezException e) {
    _formattedSink.onEntrezError(e);
  }
  
  protected void logError(String logEntry) {
    _formattedSink.logError(logEntry);
  }
  
  protected void checkForDTDErrors(Document eResult) throws EntrezException {
    if (!hasAtLeastOne(eResult, _doctype)) 
      throw new EntrezException("Incorrect document type returned by server");
    if (hasAtLeastOne(eResult, "ERROR")) throw new EntrezException("DTDError: " + getNodeText(getFirstNodeOfTag(eResult, "ERROR")));
  }
  
  public static String makeString(Node node) {
    String result = node.getNodeName() + ": " + node.getNodeValue();
    NodeList children = node.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      result += makeString(children.item(i));
    }
    return result; 
  }
  
  public static boolean hasAtLeastOne(Document doc, String tagName) {
    return (doc.getElementsByTagName(tagName).getLength() == 0) ? false : true;
  }
   
  public static boolean hasAtLeastOne(Node e, String tagName) {
    return (((Element)e).getElementsByTagName(tagName).getLength() == 0) ? false : true;
  }
  
  public static Node getFirstNodeOfTag(Document doc, String tagName) {
    return (hasAtLeastOne(doc, tagName)) ? doc.getElementsByTagName(tagName).item(0) : null;
  }

  public static Node getFirstNodeOfTag(Node e, String tagName) {
    return (hasAtLeastOne(e, tagName)) ? ((Element)e).getElementsByTagName(tagName).item(0) : null;
  }
  
  public static String getNodeText(Node node) {
    if (node.getFirstChild() == null) return "";
    return ((Text)node.getFirstChild()).getData();
  }
}