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

import com.google.gwt.user.client.ui.ListBox;

/**
 * Miscellaneous useful functions that have no home.
 */
public class Utilities {
  
  /** Selects an item in a ListBox with text the same as the given string, if it exists
   */
  public static void selectListItem(ListBox list, String text) {
    for (int i = 0; i < list.getItemCount(); i++) {
      if (list.getItemText(i).equals(text)) {
        list.setSelectedIndex(i);
        return;
      }
    }
  }
  
  /** Calls the underlying javascript escape function */
  public static native String escape(String s) /*-{
    return escape(s);
  }-*/;
}