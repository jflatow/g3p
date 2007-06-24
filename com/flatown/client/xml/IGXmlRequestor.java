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

package com.flatown.client.xml;

/** XmlRequestor specific to the implementation as a gadget */
public class IGXmlRequestor implements XmlRequestor {
  
  public void fetchXmlContent(String url, XmlParser parser) {
    if (hasIGFetch()) igFetchXmlContent(url, parser);
  }
  
  private static native boolean hasIGFetch() /*-{
   return ($wnd.parent._IG_FetchXmlContent) ? true : false;
  }-*/;
  
  // perhaps should use FetchXml, but this returns a DOM
  private static native void igFetchXmlContent(String url, XmlParser parser) /*-{
   $wnd.parent._IG_FetchContent(url, function (response) {
     parser.@com.flatown.client.xml.XmlParser::parse(Ljava/lang/String;)(response);
   }, { refreshInterval: (60 * 30) });
  }-*/;
}