/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.eclipsesource.uml.modelserver.uml.diagram.sequence_diagram.commands.interaction;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Interaction;

import com.eclipsesource.uml.modelserver.shared.model.ModelContext;
import com.eclipsesource.uml.modelserver.shared.notation.commands.DeleteNotationElementCommand;
import com.eclipsesource.uml.modelserver.uml.diagram.sequence_diagram.commands.combined_fragment.DeleteCombinedFragmentCompoundCommand;
import com.eclipsesource.uml.modelserver.uml.diagram.sequence_diagram.commands.lifeline.DeleteLifelineCompoundCommand;

public final class DeleteInteractionCompoundCommand extends CompoundCommand {

   public DeleteInteractionCompoundCommand(final ModelContext context, final Interaction semanticElement) {

      var test = semanticElement.eContents();
      var t2 = semanticElement.getFragments();

      semanticElement.getLifelines().stream()
         .map(f -> new DeleteLifelineCompoundCommand(context, f))
         .forEach(c -> this.append(c));

      semanticElement.eContents().stream()
         .filter(f -> f instanceof CombinedFragment)
         .map(f -> new DeleteCombinedFragmentCompoundCommand(context, (CombinedFragment) f))
         .forEach(c -> this.append(c));

      this.append(new DeleteInteractionSemanticCommand(context, semanticElement));
      this.append(new DeleteNotationElementCommand(context, semanticElement));

      // new SequenceDiagramCrossReferenceRemover(context).deleteCommandsFor(semanticElement)
      // .forEach(this::append);
   }
}
