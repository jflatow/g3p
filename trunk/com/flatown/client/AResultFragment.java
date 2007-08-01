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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * AResultFragment is an object meant to be displayed as a descendant of an {@link AResult}, though this is not enforced.
 */
public class AResultFragment extends FlowPanel {

  /**
   * Constructs a new AResultFragment, assigning it the corresponding style.
   */
  public AResultFragment() {
    setStyleName("aResultFragment");
  }
  
  /**
   * Convenience constructor for creating a new fragment which contains a label with the specified String.
   * 
   * @param text the text to be used in the label
   */
  public AResultFragment(String text) {
    this();
    addLabel(text);
  }
  
  /** 
   * Convenience method for adding Labels as new layers to the AResultFragment.
   * 
   * @param s the string to be used in the label
   */
  public void addLabel(String s) {
    Label label = new Label(s);
    label.setStyleName("aResultFragment");
    add(label);
  }
}