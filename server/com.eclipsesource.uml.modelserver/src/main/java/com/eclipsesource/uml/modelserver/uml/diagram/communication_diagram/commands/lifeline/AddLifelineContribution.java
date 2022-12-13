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
package com.eclipsesource.uml.modelserver.uml.diagram.communication_diagram.commands.lifeline;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.emfcloud.modelserver.edit.command.BasicCommandContribution;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.uml2.uml.Interaction;

import com.eclipsesource.uml.modelserver.core.commands.noop.NoopCommand;
import com.eclipsesource.uml.modelserver.shared.constants.NotationKeys;
import com.eclipsesource.uml.modelserver.shared.constants.SemanticKeys;
import com.eclipsesource.uml.modelserver.shared.extension.SemanticElementAccessor;
import com.eclipsesource.uml.modelserver.shared.utils.UmlGraphUtil;

public class AddLifelineContribution extends BasicCommandContribution<Command> {

   public static final String TYPE = "uml:add_lifeline";

   public static CCompoundCommand create(final Interaction interaction, final GPoint position) {
      var command = CCommandFactory.eINSTANCE.createCompoundCommand();

      command.setType(TYPE);
      command.getProperties().put(NotationKeys.POSITION_X,
         String.valueOf(position.getX()));
      command.getProperties().put(NotationKeys.POSITION_Y,
         String.valueOf(position.getY()));
      command.getProperties().put(SemanticKeys.PARENT_SEMANTIC_ELEMENT_ID,
         SemanticElementAccessor.getId(interaction));

      return command;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {
      var elementAccessor = new SemanticElementAccessor(modelUri, domain);

      var position = UmlGraphUtil.getGPoint(
         command.getProperties().get(NotationKeys.POSITION_X),
         command.getProperties().get(NotationKeys.POSITION_Y));

      var parentInteractionsemanticElementId = command.getProperties().get(SemanticKeys.PARENT_SEMANTIC_ELEMENT_ID);

      var parentInteraction = elementAccessor.getElement(parentInteractionsemanticElementId, Interaction.class);

      return parentInteraction
         .<Command> map(p -> new AddLifelineCompoundCommand(domain, modelUri, position, p))
         .orElse(new NoopCommand());
   }

}
