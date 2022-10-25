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
package com.eclipsesource.uml.modelserver.old.diagram.statemachinediagram.statemachine;

public class AddStateMachineCommandContribution { /*-{

   public static final String TYPE = "addStateMachineContributuion";

   public static CCompoundCommand create(final GPoint position) {
      CCompoundCommand addStateMachineCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      addStateMachineCommand.setType(TYPE);
      addStateMachineCommand.getProperties().put(NotationKeys.POSITION_X,
         String.valueOf(position.getX()));
      addStateMachineCommand.getProperties().put(NotationKeys.POSITION_Y,
         String.valueOf(position.getY()));
      return addStateMachineCommand;
   }

   @Override
   protected CompoundCommand toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {
      GPoint classPosition = UmlNotationCommandUtil.getGPoint(
         command.getProperties().get(NotationKeys.POSITION_X),
         command.getProperties().get(NotationKeys.POSITION_Y));

      return new AddStateMachineCompoundCommand(domain, modelUri, classPosition);
   }
   */
}
