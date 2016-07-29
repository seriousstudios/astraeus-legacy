package plugin.buttons;

import astraeus.game.event.SubscribesTo;
import astraeus.game.event.impl.ButtonActionEvent;
import astraeus.game.model.entity.mob.combat.def.AttackType;
import astraeus.game.model.entity.mob.player.Player;

@SubscribesTo(ButtonActionEvent.class)
public final class WarHammerAttackTypeButtons extends ButtonClick {

	@Override
	public void execute(Player player, ButtonActionEvent event) {
		switch (event.getButton()) {
		
		case 431:
			player.setAttackType(AttackType.WARHAMMER_BLOCK);
			break;
			
		case 432:
			player.setAttackType(AttackType.WARHAMMER_PUMMEL);
			break;
			
		case 433:
			player.setAttackType(AttackType.WARHAMMER_POUND);
			break;
		
		}
	}
	
	@Override
	public boolean test(ButtonActionEvent event) {
		return event.getButton() == 431 || event.getButton() == 432 || event.getButton() == 433;
	}

}