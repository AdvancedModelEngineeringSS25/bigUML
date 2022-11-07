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
package com.eclipsesource.uml.modelserver.uml.diagram.communication_diagram.commands.interaction;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;

import com.eclipsesource.uml.modelserver.shared.notation.commands.UmlRemoveNotationElementCommand;
import com.eclipsesource.uml.modelserver.uml.diagram.communication_diagram.commands.lifeline.RemoveLifelineCompoundCommand;

public class RemoveInteractionCompoundCommand extends CompoundCommand {

   public RemoveInteractionCompoundCommand(final EditingDomain domain, final URI modelUri,
      final Interaction interaction) {

      removeLifelines(interaction, domain, modelUri);

      this.append(new RemoveInteractionSemanticCommand(domain, modelUri, interaction));
      this.append(new UmlRemoveNotationElementCommand(domain, modelUri, interaction));
   }

   protected void removeLifelines(final Interaction interactionToRemove, final EditingDomain domain,
      final URI modelUri) {
      for (Lifeline lifeline : interactionToRemove.getLifelines()) {
         this.append(new RemoveLifelineCompoundCommand(domain, modelUri, lifeline));
      }
   }
}
