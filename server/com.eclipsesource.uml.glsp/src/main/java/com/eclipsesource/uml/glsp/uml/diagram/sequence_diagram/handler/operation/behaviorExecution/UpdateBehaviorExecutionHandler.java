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
package com.eclipsesource.uml.glsp.uml.diagram.sequence_diagram.handler.operation.behaviorExecution;

import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;

import com.eclipsesource.uml.glsp.core.handler.operation.update.UpdateOperation;
import com.eclipsesource.uml.glsp.uml.diagram.sequence_diagram.diagram.UmlSequence_BehaviorExecution;
import com.eclipsesource.uml.glsp.uml.handler.operations.update.BaseUpdateElementHandler;
import com.eclipsesource.uml.modelserver.uml.diagram.sequence_diagram.commands.behaviorExecution.UpdateBehaviorExecutionArgument;
import com.eclipsesource.uml.modelserver.uml.diagram.sequence_diagram.commands.behaviorExecution.UpdateBehaviorExecutionContribution;

public final class UpdateBehaviorExecutionHandler
   extends BaseUpdateElementHandler<BehaviorExecutionSpecification, UpdateBehaviorExecutionArgument> {

   public UpdateBehaviorExecutionHandler() {
      super(UmlSequence_BehaviorExecution.typeId());
   }

   @Override
   protected CCommand createCommand(final UpdateOperation operation, final BehaviorExecutionSpecification element,
      final UpdateBehaviorExecutionArgument updateArgument) {
      return UpdateBehaviorExecutionContribution.create(element, updateArgument);
   }
}
