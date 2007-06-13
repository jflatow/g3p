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

import com.flatown.client.AResult;

import com.google.gwt.xml.client.Document;

public class EGQuery extends EntrezUtility {
  
  public EGQuery(SinksEntrezUtil sink){
    super(sink);
    _name = "egquery.fcgi";
    _doctype = "eGQueryResult";
  }
 
  protected AResult[] formatResults(Document eResult) {
    return null;
  }
}