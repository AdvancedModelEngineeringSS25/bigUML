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
package com.eclipsesource.uml.modelserver.shared.codec.codecs;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.uml.modelserver.shared.codec.CCommandProvider;
import com.eclipsesource.uml.modelserver.shared.codec.ContextProvider;

public interface TypeCodec {
   String ELEMENT_TYPE = "element_type";

   interface Encoder<T> extends CCommandProvider {
      default T type(final String type) {
         ccommand().setType(type);
         return (T) this;
      }

      default T elementType(final Class<? extends EObject> type) {
         ccommand().getProperties().put(ELEMENT_TYPE, type.getName());
         return (T) this;
      }
   }

   interface Decoder extends CCommandProvider, ContextProvider {
      default Optional<Class<? extends EObject>> elementType() {
         var elementType = ccommand().getProperties().get(ELEMENT_TYPE);
         return Optional.ofNullable(elementType).map(t -> {
            try {
               return (Class<EObject>) Class.forName(t);
            } catch (ClassNotFoundException e) {
               e.printStackTrace();
               return null;
            }
         });
      }
   }

}
