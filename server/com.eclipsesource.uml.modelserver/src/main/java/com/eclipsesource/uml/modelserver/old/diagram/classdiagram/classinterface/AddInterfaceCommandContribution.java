package com.eclipsesource.uml.modelserver.old.diagram.classdiagram.classinterface;

public class AddInterfaceCommandContribution { /*-{

   public static final String TYPE = "addInterfaceContribution";

   public static CCompoundCommand create(final GPoint position) {
      CCompoundCommand addInterfaceCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      addInterfaceCommand.setType(TYPE);
      addInterfaceCommand.getProperties().put(NotationKeys.POSITION_X, String.valueOf(position.getX()));
      addInterfaceCommand.getProperties().put(NotationKeys.POSITION_Y, String.valueOf(position.getY()));
      return addInterfaceCommand;
   }

   @Override
   protected CompoundCommand toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
         throws DecodingException {
      GPoint interfacePosition = UmlNotationCommandUtil.getGPoint(
            command.getProperties().get(NotationKeys.POSITION_X),
            command.getProperties().get(NotationKeys.POSITION_Y)
      );
      return new AddInterfaceCompoundCommand(domain, modelUri, interfacePosition);
   }
      */
}
