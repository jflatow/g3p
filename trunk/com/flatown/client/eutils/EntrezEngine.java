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
import com.flatown.client.SearchBox;
import com.flatown.client.xml.*;

/** The EntrezEngine are used by the SearchBox to search the databases.
 * All the search logic is taken care of by this class. A comprehensive API is provided to the
 * eUtils.
 */
public class EntrezEngine {
  
  public static final EntrezEngine Singleton = new EntrezEngine();

  private static final String BaseURL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
  
  private static XmlRequestor _requestor;
  
  private EntrezEngine() { 
    _requestor = new IGXmlRequestor();
  }
  
  public void search(SearchBox searchbox) {
    // create the pipeline
    EntrezUtility fetch = new EFetch(searchbox.getResultsBox());
    EnvAndURLParams fetchParams = new EnvAndURLParams();
    fetchParams.setParam("dispmax", new IntParam(searchbox.getNumResults()));
    
    EntrezUtility search = new ESearch(fetch, fetchParams);
    URLParams searchParams = new URLParams();
    searchParams.setParam("term", new TermParam(searchbox.getSearchQuery()));
    searchParams.setParam("db", new StringParam(searchbox.getDatabase()));
    searchParams.setParam("retmax", new IntParam(searchbox.getNumResults()));

    // set RetMax, RetStart, etc.
    // call run with the pipeline and the searchbox's resultsbox as the final sink
    search.run(searchParams);
  }
  
  /** Give the utilities access to the fetchXml functions */
  public void request(String utility, URLParams params, XmlParser parser) {
    _requestor.fetchXmlContent(BaseURL + utility + params, parser);
  }
  
  /** Calls the underlying javascript escape function */
  public static native String escape(String s) /*-{
    return escape(s);
  }-*/;
  
  /** Replaces segments of white space with a plus sign */
  public static String replaceSpaces(String s) {
    return s.replaceAll("[ \t\n\f\r]+", "+");
  }
}