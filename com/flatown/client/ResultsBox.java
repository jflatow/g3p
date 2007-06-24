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

import com.google.gwt.user.client.DOM;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;

/** ResultsBoxes contain results for an associated SearchBox
 * 
 */
public class ResultsBox extends FlowPanel implements SinksEntrezUtil, PopupHost {
  
  private SearchBox _searchbox;
  
  private boolean _displayLog;
  private AResult _log;
  
  private static AResult _hoverOwner, _downOwner, _clickOwner;
  private static MouseListener _PopupListener;
  
  public ResultsBox(SearchBox searchbox) {
    _searchbox = searchbox;
    _displayLog = true;
    _log = new AResult();
    this.setStyleName("resultsbox");
    
    _PopupListener = new MouseListenerAdapter() {
      public void onMouseEnter(Widget sender) {
        if (sender instanceof AResult) {
          if (sender != _hoverOwner) {
            hideHover();
            _hoverOwner = (AResult)sender;
            _hoverOwner.showHover();
          }
        }
      }
      
      public void onMouseLeave(Widget sender) {
        if (sender instanceof AResult) {
          _hoverOwner = (AResult)sender;
          _hoverOwner.hideHover();
        }
      }
      
      public void onMouseDown(Widget sender, int x, int y) {
        if (sender instanceof AResult && !SlideTimer.BUSY) {
            _downOwner = (AResult)sender;
        }
      }
      
      public void onMouseUp(Widget sender, int x, int y) {
        if (sender instanceof AResult && !SlideTimer.BUSY) {
          if (_clickOwner != sender) collapse();
          if (_downOwner == sender) {
            _clickOwner = _downOwner;
            _clickOwner.expand();
          }
        }
      }
    };
  }
   
  public SearchBox getSearchBox() {
    return _searchbox;
  }
  
  public static void hideHover() {
    if (_hoverOwner != null) {
      _hoverOwner.hideHover();
      _hoverOwner = null;
    }
  }
  
  public static void collapse() {
    if (_clickOwner == null) return;
    _clickOwner.collapse();
  }
  
  /** Allows the ResultsBox to implement SinkEntrezUtil */
  public void setResults(AResult[] results) {
    clearResults();
    for (int i = 0; i < results.length; i++) {
      add(results[i]);
      results[i].attachToPopupHost(this);
    }
  }
  
  /** Allows the ResultsBox to implement SinkEntrezUtil */
  public void onEntrezError(EntrezException e) {
    // handle/display an entrez exception
    if (_displayLog) {
      clearResults();
      AResultFragment header = new AResultFragment(e.toString());
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
  
  public MouseListener getPopupListener() {
    return _PopupListener;
  }
}