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
package com.eclipsesource.uml.modelserver.uml.diagram.sequence_diagram.commands.lifeline;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.uml2.uml.Interaction;

import com.eclipsesource.uml.modelserver.shared.model.ModelContext;
import com.eclipsesource.uml.modelserver.shared.notation.commands.AddShapeNotationCommand;

public final class CreateLifelineCompoundCommand extends CompoundCommand {
   public CreateLifelineCompoundCommand(final ModelContext context, final Interaction parent, final GPoint position) {
      var command = new CreateLifelineSemanticCommand(context, parent);
      var fixedSpawnHeight = 30;
      this.append(command);
      this.append(
         new AddShapeNotationCommand(context, command::getSemanticElement,
            GraphUtil.point(position.getX(), fixedSpawnHeight), GraphUtil.dimension(160, 500)));
   }
}
