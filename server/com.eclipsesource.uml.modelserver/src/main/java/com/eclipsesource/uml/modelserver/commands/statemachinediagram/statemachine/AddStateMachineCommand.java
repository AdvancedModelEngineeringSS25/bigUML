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
package com.eclipsesource.uml.modelserver.commands.statemachinediagram.statemachine;

import com.eclipsesource.uml.modelserver.commands.commons.semantic.UmlSemanticElementCommand;
import com.eclipsesource.uml.modelserver.commands.util.UmlSemanticCommandUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLFactory;

public class AddStateMachineCommand extends UmlSemanticElementCommand {

   protected final StateMachine newStateMachine;

   public AddStateMachineCommand(final EditingDomain domain, final URI modelUri) {
      super(domain, modelUri);
      this.newStateMachine = UMLFactory.eINSTANCE.createStateMachine();
   }

   @Override
   protected void doExecute() {
      newStateMachine.setName(UmlSemanticCommandUtil.getNewStateMachineName(umlModel));
      // TODO:
      // region is created automatically, maybe this should be done user-driven
      /*Region region = UMLFactory.eINSTANCE.createRegion();
      region.setName(UmlSemanticCommandUtil.getNewRegionName(newStateMachine));
      newStateMachine.getRegions().add(region);*/
      umlModel.getPackagedElements().add(newStateMachine);
   }

   public StateMachine getNewStateMachine() {
      return newStateMachine;
   }

}
