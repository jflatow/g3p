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

import com.google.gwt.user.client.ui.UIObject;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Timer;

/** Slider to make elements slowly change size
 */
public class SlideTimer extends Timer {
  
  public static final SlideTimer Singleton = new SlideTimer();
  public static boolean BUSY;
  private UIObject _obj;
  private int _curHeight, _endHeight, _inc, _dir;
  private static final int _timing = 10, _divisions = 20;
   
  private SlideTimer() {
    BUSY = false;
    _curHeight = 0;
    _endHeight = 100;
    _inc = 10;
  }
  
  public void run() {
    BUSY = true;
    if (Math.abs(_endHeight - _curHeight) > _inc) {
      if (_endHeight > _curHeight) _curHeight += _inc;
      else _curHeight -= _inc;
      _obj.setHeight(_curHeight + "px");
      schedule(_timing);
    } else {
      _obj.setHeight(_endHeight + "px");
      DOM.scrollIntoView(_obj.getElement());
      BUSY = false;
    }
  }
  
  public void slide(UIObject obj, int start, int end) {
    _obj = obj;
    _curHeight = start;
    _obj.setHeight(_curHeight + "px");
    _endHeight = end;
    _inc = Math.abs(_endHeight - _curHeight)/_divisions;
    run();
  }
}