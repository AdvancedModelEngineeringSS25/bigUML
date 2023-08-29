/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.eclipsesource.uml.glsp.uml.elements.package_;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.graph.GraphPackage;
import org.eclipse.glsp.server.types.ShapeTypeHint;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.PrimitiveType;

import com.eclipsesource.uml.glsp.uml.configuration.RepresentationNodeConfiguration;
import com.eclipsesource.uml.glsp.uml.elements.class_.ClassConfiguration;
import com.eclipsesource.uml.modelserver.unotation.Representation;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class PackageConfiguration extends RepresentationNodeConfiguration<org.eclipse.uml2.uml.Package> {

   @Inject
   public PackageConfiguration(@Assisted final Representation representation) {
      super(representation);
   }

   public enum Property {
      NAME,
      URI,
      VISIBILITY_KIND,
      PACKAGE_IMPORTS,
      PACKAGE_MERGES;
   }

   @Override
   public Map<String, EClass> getTypeMappings() { return Map.of(
      typeId(), GraphPackage.Literals.GNODE); }

   @Override
   public Set<String> getGraphContainableElements() { return Set.of(typeId()); }

   @Override
   public Set<ShapeTypeHint> getShapeTypeHints() {
      return Set.of(
         new ShapeTypeHint(typeId(), true, true, true, false,
            List.of(
               configurationFor(org.eclipse.uml2.uml.Class.class).typeId(),
               configurationFor(org.eclipse.uml2.uml.Class.class, ClassConfiguration.class).abstractTypeId(),
               configurationFor(Enumeration.class).typeId(),
               configurationFor(Interface.class).typeId(),
               configurationFor(DataType.class).typeId(),
               configurationFor(PrimitiveType.class).typeId(),
               configurationFor(org.eclipse.uml2.uml.Package.class).typeId())));

   }
}
