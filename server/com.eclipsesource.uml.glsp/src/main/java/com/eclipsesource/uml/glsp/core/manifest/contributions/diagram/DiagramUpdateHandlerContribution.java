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
package com.eclipsesource.uml.glsp.core.manifest.contributions.diagram;

import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.uml.glsp.core.handler.operation.update.DiagramUpdateHandler;
import com.eclipsesource.uml.modelserver.shared.manifest.supplier.ContributionBinderSupplier;
import com.eclipsesource.uml.modelserver.shared.manifest.supplier.ContributionIdSupplier;
import com.eclipsesource.uml.modelserver.shared.manifest.supplier.ContributionRepresentationSupplier;
import com.eclipsesource.uml.modelserver.unotation.Representation;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;

public interface DiagramUpdateHandlerContribution
   extends ContributionBinderSupplier, ContributionIdSupplier, ContributionRepresentationSupplier {

   default void contributeDiagramUpdateHandlers(
      final Consumer<Multibinder<DiagramUpdateHandler<? extends EObject>>> consumer) {
      var binder = contributionBinder();

      var multibinder = Multibinder.newSetBinder(binder,
         new TypeLiteral<DiagramUpdateHandler<? extends EObject>>() {},
         idNamed());

      consumer.accept(multibinder);

      MapBinder.newMapBinder(binder, new TypeLiteral<Representation>() {},
         new TypeLiteral<Set<DiagramUpdateHandler<? extends EObject>>>() {})
         .addBinding(representation())
         .to(Key.get(new TypeLiteral<Set<DiagramUpdateHandler<? extends EObject>>>() {}, idNamed()));
   }
}
