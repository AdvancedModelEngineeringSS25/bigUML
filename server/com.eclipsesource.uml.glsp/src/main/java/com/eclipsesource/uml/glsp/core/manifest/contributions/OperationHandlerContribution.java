/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.eclipsesource.uml.glsp.core.manifest.contributions;

import org.eclipse.glsp.server.operations.OperationHandler;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

/*
 * Contributes to GLSP directly
 */
public interface OperationHandlerContribution {

   default void contributeOperationHandler(final Binder binder) {
      var provider = Multibinder.newSetBinder(binder, OperationHandler.class);
      contributeOperationHandler(provider);
   }

   void contributeOperationHandler(Multibinder<OperationHandler> multibinder);
}
