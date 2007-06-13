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

package com.flatown.client;

import com.flatown.client.eutils.SinksEntrezUtil;
import com.flatown.client.eutils.EntrezException;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.DOM;

/** ResultsBoxes contain results for an associated SearchBox
 * 
 */
public class ResultsBox extends FlowPanel implements SinksEntrezUtil {
  
  private SearchBox _searchbox;
  
  private boolean _displayLog;
  private AResult _log;
  
  public ResultsBox(SearchBox searchbox) {
    _searchbox = searchbox;
    _displayLog = true;
    _log = new AResult();
    this.setStyleName("resultsbox");
  }
  
  public SearchBox getSearchBox() {
    return _searchbox;
  }
  
  /** Allows the ResultsBox to implement SinkEntrezUtil */
  public void setResults(AResult[] results) {
    clearResults();
    for (int i = 0; i < results.length; i++) {
      add(results[i]);
    }
  }
  
  /** Allows the ResultsBox to implement SinkEntrezUtil */
  public void onEntrezError(EntrezException e) {
    // handle/display an entrez exception
    if (_displayLog) {
      clearResults();
      AResult header = new AResult();
      header.add(new Label(e.toString()));
      add(header);
      add(_log); 
    }
  }
  
  public void logError(String logEntry) {
    // write the logEntry to the AResult log (provide mechanism for displaying log)
    _log.add(new Label(logEntry));
  }
  
  private void clearResults() {
    for (int i = getWidgetCount(); i > 0; i--) {
      remove(i-1);
    }
  }
}