package com.astraeus.net.packet.out;

import java.util.Optional;

import com.astraeus.game.world.entity.mob.player.Player;
import com.astraeus.game.world.entity.mob.player.PlayerRelation;
import com.astraeus.net.codec.game.GamePacketBuilder;
import com.astraeus.net.packet.OutgoingPacket;
import com.astraeus.net.packet.Sendable;

/**
 * The packet that sends the first private messaging list load status.
 *
 * The status for the friends lists are as follows:
 * <p>
 * <p>
 * Loading: 0
 * <p>
 * Connecting: 1
 * <p>
 * Loaded: 2
 * <p>
 * <p>
 * 
 * @return an instance of this encoder.
 */
public final class SetFriendListStatusPacket implements Sendable {

  private final PlayerRelation.PrivateMessageListStatus status;

  public SetFriendListStatusPacket(PlayerRelation.PrivateMessageListStatus status) {
    this.status = status;
  }

  @Override
  public Optional<OutgoingPacket> writePacket(Player player) {
    GamePacketBuilder builder = new GamePacketBuilder(221);
    builder.write(status.getCode());
    return builder.toOutgoingPacket();
  }

}
