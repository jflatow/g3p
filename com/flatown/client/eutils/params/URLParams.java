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

package com.flatown.client.eutils.params;

import java.util.HashMap;
import java.util.Iterator;

/** Holds the parameters for an EntrezUtility */
public class URLParams {
  
  private static final String Tool = "g3p";
  protected HashMap _params;
  
  public URLParams() {
    _params = new HashMap();
    setParam("tool", new StringParam(Tool));
    // explicate all the parameter keys
  }
  
  public void setParam(String key, Parameter value) {
    _params.put(key, value);
  }
  
  public void syncWith(URLParams params) {
    if (params == null) return;
    String key;
    for (Iterator it = params.getKeyIterator(); it.hasNext() && ((key = (String)it.next()) != null); ) {
      if (!_params.containsKey(key)) setParam(key, params.get(key));
    }
  }
  
  public Iterator getKeyIterator() {
    return _params.keySet().iterator();
  }
  
  public Parameter get(String s) {
    return (Parameter)_params.get(s);
  }
  
  public String toString() {
    // for every parameter in the hashmap, if it has a value, print the key and the parameter toString
    String connector = "?", retValue = "", key;
    for (Iterator it = getKeyIterator(); it.hasNext() && ((key = (String)it.next()) != null); ) {
      retValue += connector + key + "=" + get(key).toString();
      connector = "&";
    }
    return retValue;
  }
}