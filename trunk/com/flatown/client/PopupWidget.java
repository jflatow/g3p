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

import com.google.gwt.user.client.ui.Widget;

/** 
 * Interface for an object that wants to be able to attach to a {@link PopupHost}. Implementors
 * of this interface need only define what to do when something tries to attach this object
 * to a PopupHost.
 */
public interface PopupWidget {
  /**
   * Attaches this object to a host.
   * 
   * @param host the PopupHost that we are told to attach to
   */
  public void attachToPopupHost(PopupHost host);
}