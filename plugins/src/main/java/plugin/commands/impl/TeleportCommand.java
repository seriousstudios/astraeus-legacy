package plugin.commands.impl;

import astraeus.game.event.SubscribesTo;
import astraeus.game.event.impl.CommandEvent;
import astraeus.game.model.Position;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.PlayerRights;
import plugin.commands.Command;
import plugin.commands.CommandParser;

@SubscribesTo(CommandEvent.class)
public final class TeleportCommand extends Command {

	@Override
	protected boolean execute(Player player, CommandParser parser) {
		
		if (parser.hasNext(3)) {
			
			int x = parser.nextInt();
			
			int y = parser.nextInt();
			
			int z = parser.nextInt();
			
			player.move(new Position(x, y, z));			
		} else if (parser.hasNext(2)) {
			int x = parser.nextInt();
			
			int y = parser.nextInt();
			
			player.move(new Position(x, y));	
		}
		
		
		return true;
	}

	@Override
	public boolean test(CommandEvent event) {
		return event.getName().equalsIgnoreCase("tele");
	}

	@Override
	protected PlayerRights rights() {
		return PlayerRights.DEVELOPER;
	}

}