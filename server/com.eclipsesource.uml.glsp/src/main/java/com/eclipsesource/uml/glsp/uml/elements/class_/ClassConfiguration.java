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
package com.eclipsesource.uml.glsp.uml.elements.class_;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GraphPackage;
import org.eclipse.glsp.server.types.ShapeTypeHint;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;

import com.eclipsesource.uml.glsp.uml.configuration.RepresentationNodeConfiguration;
import com.eclipsesource.uml.glsp.uml.utils.QualifiedUtil;
import com.eclipsesource.uml.modelserver.unotation.Representation;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public final class ClassConfiguration extends RepresentationNodeConfiguration<Class> {

   @Inject
   public ClassConfiguration(@Assisted final Representation representation) {
      super(representation);
   }

   public String abstractTypeId() {
      return QualifiedUtil.templateTypeId(representation, DefaultTypes.NODE, "abstract",
         Class.class.getSimpleName());
   }

   public enum Property {
      NAME,
      IS_ABSTRACT,
      IS_ACTIVE,
      VISIBILITY_KIND,
      OWNED_ATTRIBUTES,
      OWNED_OPERATIONS;
   }

   @Override
   public Map<String, EClass> getTypeMappings() {
      return Map.of(
         typeId(), GraphPackage.Literals.GNODE,
         abstractTypeId(), GraphPackage.Literals.GNODE);
   }

   @Override
   public Set<String> getGraphContainableElements() { return Set.of(typeId(), abstractTypeId()); }

   @Override
   public Set<ShapeTypeHint> getShapeTypeHints() {
      return Set.of(
         new ShapeTypeHint(typeId(), true, true, true, false,
            existingConfigurationTypeIds(Set.of(org.eclipse.uml2.uml.Property.class,
               Operation.class))));
   }
}
