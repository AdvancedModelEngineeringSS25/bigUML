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
package com.eclipsesource.uml.glsp.operations.usecasediagram;

import com.eclipsesource.uml.glsp.model.UmlModelIndex;
import com.eclipsesource.uml.glsp.model.UmlModelState;
import com.eclipsesource.uml.glsp.modelserver.UmlModelServerAccess;
import com.eclipsesource.uml.glsp.util.UmlConfig.Types;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSBasicCreateOperationHandler;
import org.eclipse.glsp.graph.*;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.GeometryUtil;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.*;

import java.util.List;
import java.util.Optional;

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

public class CreateUseCaseDiagramNodeOperationHandler
      extends EMSBasicCreateOperationHandler<CreateNodeOperation, UmlModelServerAccess> {

   private static Logger LOGGER = Logger.getLogger(CreateUseCaseDiagramNodeOperationHandler.class);

   public CreateUseCaseDiagramNodeOperationHandler() {
      super(handledElementTypeIds);
   }

   private static List<String> handledElementTypeIds = Lists.newArrayList(Types.COMPONENT, Types.PACKAGE, Types.ACTOR,
         Types.USECASE, Types.COMMENT);

   @Override
   public boolean handles(final Operation execAction) {
      if (execAction instanceof CreateNodeOperation) {
         CreateNodeOperation action = (CreateNodeOperation) execAction;
         return handledElementTypeIds.contains(action.getElementTypeId());
      }
      return false;
   }

   protected UmlModelState getUmlModelState() {
      return (UmlModelState) getEMSModelState();
   }

   @Override
   public void executeOperation(final CreateNodeOperation operation, final UmlModelServerAccess modelAccess) {

      UmlModelState modelState = getUmlModelState();
      UmlModelIndex modelIndex = modelState.getIndex();

      switch (operation.getElementTypeId()) {
         case Types.PACKAGE: {
            /*modelAccess.addPackage(UmlModelState.getModelState(modelState), operation.getLocation())
               .thenAccept(response -> {
                  if (!response.body()) {
                     throw new GLSPServerException("Could not execute create operation on new Package node");
                  }
               });
            break;*/
            NamedElement parentContainer = getOrThrow(
                  modelIndex.getSemantic(operation.getContainerId(), NamedElement.class),
                  "No semantic container object found for source element with id " + operation.getContainerId());

            if (parentContainer instanceof Model) {
               modelAccess.addPackage(getUmlModelState(), operation.getLocation(), Model.class.cast(parentContainer))
                     .thenAccept(response -> {
                        if (!response.body()) {
                           throw new GLSPServerException("Could not execute create operation on new Package node");
                        }
                     });
            } else if (parentContainer instanceof Package) {
               // If the container is a Package node, find its structure compartment to compute the relative position
               Optional<GModelElement> container = modelIndex.get(operation.getContainerId());
               Optional<GModelElement> structCompartment = container.filter(GNode.class::isInstance)
                     .map(GNode.class::cast)
                     .flatMap(this::getStructureCompartment);
               Optional<GPoint> relativeLocation = getRelativeLocation(operation, operation.getLocation(),
                     structCompartment);
               modelAccess.addPackage(getUmlModelState(), relativeLocation, Package.class.cast(parentContainer))
                     .thenAccept(response -> {
                        if (!response.body()) {
                           throw new GLSPServerException("Could not execute create operation on new Package node");
                        }
                     });
            }
            break;
         }
         case Types.COMPONENT: {
            PackageableElement container = null;
            try {
               container = getOrThrow(
                     UmlModelState.getModelState(modelState).getIndex().getSemantic(operation.getContainerId()),
                     PackageableElement.class, "No valid container with id " + operation.getContainerId() + " found");
            } catch (GLSPServerException ex) {
               LOGGER.error("Could not find container", ex);
            }
            if (container instanceof Model) {
               modelAccess.addComponent(UmlModelState.getModelState(modelState), operation.getLocation())
                     .thenAccept(response -> {
                        if (!response.body()) {
                           throw new GLSPServerException("Could not execute create operation on new Component node");
                        }
                     });
            } else if (container instanceof Package) {
               modelAccess
                     .addComponent(UmlModelState.getModelState(modelState), (Package) container,
                           operation.getLocation())
                     .thenAccept(response -> {
                        if (!response.body()) {
                           throw new GLSPServerException("Could not execute create operation on new Component node");
                        }
                     });
            }
            break;
         }
         case Types.ACTOR: {
            PackageableElement container = null;
            try {
               container = getOrThrow(
                     UmlModelState.getModelState(modelState).getIndex().getSemantic(operation.getContainerId()),
                     PackageableElement.class, "No valid container with id " + operation.getContainerId() + " found");
            } catch (GLSPServerException ex) {
               LOGGER.error("Could not find container", ex);
            }
            if (container instanceof Model) {
               modelAccess.addActor(UmlModelState.getModelState(modelState), operation.getLocation())
                     .thenAccept(response -> {
                        if (!response.body()) {
                           throw new GLSPServerException("Could not execute create operation on new Actor node");
                        }
                     });
            } else if (container instanceof Package) {
               // If the container is a Package node, find its structure compartment to compute the relative position
               Optional<GModelElement> containerNew = modelIndex.get(operation.getContainerId());
               Optional<GModelElement> structCompartment = containerNew.filter(GNode.class::isInstance)
                     .map(GNode.class::cast)
                     .flatMap(this::getStructureCompartment);
               Optional<GPoint> relativeLocation = getRelativeLocation(operation, operation.getLocation(),
                     structCompartment);
               modelAccess.addActor(getUmlModelState(), Package.class.cast(container), relativeLocation)
                     .thenAccept(response -> {
                        if (!response.body()) {
                           throw new GLSPServerException("Could not execute create operation on new Package node");
                        }
                     });
            } else {
               modelAccess
                     .addActor(UmlModelState.getModelState(modelState), (Package) container,
                           operation.getLocation())
                     .thenAccept(response -> {
                        if (!response.body()) {
                           throw new GLSPServerException("Could not execute create operation on new nested Actor node");
                        }
                     });
            }
            break;
         }
         case Types.USECASE: {
            PackageableElement container = null;
            try {
               container = getOrThrow(
                     UmlModelState.getModelState(modelState).getIndex().getSemantic(operation.getContainerId()),
                     PackageableElement.class, "No valid container with id " + operation.getContainerId() + " found");
            } catch (GLSPServerException ex) {
               LOGGER.error("Could not find container", ex);
            }
            if (container instanceof Model) {
               modelAccess.addUseCase(UmlModelState.getModelState(modelState), operation.getLocation())
                     .thenAccept(response -> {
                        if (!response.body()) {
                           throw new GLSPServerException("Could not execute create operation on new Usecase node");
                        }
                     });
            } else if (container instanceof Package || container instanceof Component) {
               modelAccess
                     .addUseCase(UmlModelState.getModelState(modelState), container,
                           operation.getLocation())
                     .thenAccept(response -> {
                        if (!response.body()) {
                           throw new GLSPServerException("Could not execute create operation on new nested Usecase node");
                        }
                     });
            }
            break;
         }
      }

   }


   protected Optional<GCompartment> getStructureCompartment(final GNode packageable) {
      return packageable.getChildren().stream().filter(GCompartment.class::isInstance).map(GCompartment.class::cast)
            .filter(comp -> Types.STRUCTURE.equals(comp.getType())).findFirst();
   }

   protected Optional<GPoint> getRelativeLocation(final CreateNodeOperation operation,
                                                  final Optional<GPoint> absoluteLocation, final Optional<GModelElement> container) {
      if (absoluteLocation.isPresent() && container.isPresent()) {
         // When creating elements on a parent node (other than the root Graph),
         // prevent the node from using negative coordinates
         boolean allowNegativeCoordinates = container.get() instanceof GGraph;
         GModelElement modelElement = container.get();
         if (modelElement instanceof GBoundsAware) {
            try {
               GPoint relativePosition = GeometryUtil.absoluteToRelative(absoluteLocation.get(),
                     (GBoundsAware) modelElement);
               GPoint relativeLocation = allowNegativeCoordinates
                     ? relativePosition
                     : GraphUtil.point(Math.max(0, relativePosition.getX()), Math.max(0, relativePosition.getY()));
               return Optional.of(relativeLocation);
            } catch (IllegalArgumentException ex) {
               return absoluteLocation;
            }
         }
      }
      return Optional.empty();
   }

   @Override
   public String getLabel() {
      return "Create uml classifier";
   }

}
