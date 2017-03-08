package plugin.click.widget;

import io.battlerune.game.event.EventContext;
import io.battlerune.game.event.EventSubscriber;
import io.battlerune.game.event.SubscribesTo;
import io.battlerune.game.event.impl.WidgetContainerFourthOptionEvent;
import io.battlerune.game.model.entity.item.Item;
import io.battlerune.game.model.entity.item.ItemDefinition;
import io.battlerune.game.model.entity.mob.player.Player;
import io.battlerune.game.model.entity.mob.player.PlayerRights;
import io.battlerune.net.packet.out.ServerMessagePacket;
import plugin.shops.Shops;

@SubscribesTo(WidgetContainerFourthOptionEvent.class)
public final class WidgetContainerFourthClick implements EventSubscriber<WidgetContainerFourthOptionEvent> {

	@Override
	public void subscribe(EventContext context, Player player, WidgetContainerFourthOptionEvent event) {
		if (player.getRights().equal(PlayerRights.DEVELOPER) && player.attr().get(Player.DEBUG_KEY)) {
			player.queuePacket(new ServerMessagePacket("[WidgetContainerFourthOption] widgetId: " + event.getWidgetId()
					+ " id: " + event.getItemId() + " slot: " + event.getItemSlot()));
		}

		switch (event.getWidgetId()) {

		case 3823:
        	Shops.search(player).ifPresent(it -> it.sell(player, new Item(event.getItemId(), 10), event.getItemSlot()));
			break;

		case 3900:
			Shops.search(player).ifPresent(it -> it.purchase(player, new Item(event.getItemId(), 10)));
			break;

		case 5064:
			player.getBank().depositFromInventory(event.getItemSlot(), player.getInventory().amount(player.getInventory().getId(event.getItemSlot())));
			break;

		case 5382:
            int amount = 0;
            if (player.isWithdrawAsNote()) {
                amount = player.getBank().amount(event.getItemId());
            } else {
                Item itemWithdrew = new Item(event.getItemId(), 1);
                amount = ItemDefinition.lookup(itemWithdrew.getId()).isStackable() ? player.getBank().amount(event.getItemId()) : 28;
            }

            player.getBank().withdraw(event.getItemSlot(), amount, true);
			break;

		}
	}

}
