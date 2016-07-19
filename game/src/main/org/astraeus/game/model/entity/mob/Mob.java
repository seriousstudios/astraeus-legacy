package astraeus.game.model.entity.mob;

import astraeus.game.model.*;
import astraeus.game.model.entity.Entity;
import astraeus.game.model.entity.item.Item;
import astraeus.game.model.entity.mob.npc.Npc;
import astraeus.game.model.entity.mob.player.Player;
import astraeus.game.model.entity.mob.player.attribute.AttributeMap;
import astraeus.game.model.entity.mob.update.UpdateFlag;
import astraeus.game.model.entity.object.GameObject;

import java.util.*;

/**
 * The class that represents an entity that is either an NPC or Player.
 * 
 * @author SeVen
 */
public abstract class Mob extends Entity {

      private final List<Player> localPlayers = new LinkedList<Player>();

      private final List<Npc> localNpcs = new LinkedList<Npc>();

      private final Queue<Animation> animations = new PriorityQueue<>();

      private final Queue<Graphic> graphics = new PriorityQueue<>();

      private AttributeMap attributes = new AttributeMap();

      /**
       * The specialized set for enumerated types that will store all of the updating flags.
       */
      private final EnumSet<UpdateFlag> FLAGS = EnumSet.noneOf(UpdateFlag.class);

      private Location lastLocation = new Location(0, 0, 0);

      private Location facingLocation;

      private final Movement movement = new Movement(this);

      private final MovementQueueListener movementListener = new MovementQueueListener(this);

      private ForceMovement forceMovement;
      private MobAnimation mobAnimation = new MobAnimation();

      private transient Mob interactingEntity;

      private int antipoisonTimer = 0;
      private int id;
      protected int size = 1;

      /**
       * The direction the entity is walking.
       */
      private int walkingDirection = -1;

      /**
       * The direction the entity is running.
       */
      private int runningDirection = -1;

      private boolean registered;
      private boolean poisoned;
      private boolean isDead;
      private boolean regionChange;
      private boolean teleporting;
      private boolean visible;
      private boolean following;

      private String forcedChat;

      public Mob(Location location) {
            super(location);
      }

      public abstract void clearUpdateFlags();
      public abstract void decrementHealth(int damage);
      public abstract int getCurrentHealth();
      public abstract void tick();
      public abstract int getHashCode();

      /**
       * Gets the maximum number of hitpoints an entity has.
       */
      public abstract int getMaximumHealth();

      /**
       * Prepares entity movement.
       */
      public abstract void prepare();

      /**
       * The method called when an entity is being added to the game world.
       */
      @Override
	public abstract void onRegister();

      /**
       * The method called when an entity is being removed from the game world.
       */
      @Override
	public abstract void onDeregister();

      /**
       * The method called when an entity dies.
       */
      public abstract void onDeath();

      /**
       * The method called when an entity walks or runs.
       */
      public abstract void onMovement();

      /**
       * The method called when a {@link Player} logs in.
       */
      public void onLogin() {

      }

      /**
       * The method called when a {@link Player} logs out.
       */
      public void onLogout() {

      }

      /**
       * Determines if this {@link Player} can logout.
       * 
       * @return {@code true} If this player can logout. {@code false} Otherwise.
       */
      public boolean canLogout() {
            return true;
      }

      /**
       * The method called when a {@link Player} gets initialized.
       */
      public void onStartup() {

      }

      public boolean canTeleport() {
            return true;
      }

      public boolean canClickButton() {
            return true;
      }

      public boolean canTrade() {
            return true;
      }

      public boolean canDuel() {
            return true;
      }

      public boolean canAttackMob(Npc mob) {
            return true;
      }

      public boolean canAttackPlayer(Player player) {
            return true;
      }

      public boolean canClickMob(Npc mob) {
            return true;
      }

      public boolean canClickObject(GameObject object) {
            return true;
      }

      public boolean canDrink() {
            return true;
      }

      public boolean canEat() {
            return true;
      }

      public boolean canDrop() {
            return true;
      }

      public boolean canMove() {
            return true;
      }

      public boolean canPickup(Item item) {
            return true;
      }

      public boolean canSave() {
            return true;
      }

      public boolean canPray() {
            return true;
      }

      public boolean canTalk() {
            return true;
      }

      public boolean canUnequip(Item item) {
            return true;
      }

      public boolean canUseSpecial() {
            return true;
      }

      public Npc getNpc() {
            return World.WORLD.getMobs()[slot];
      }

      public Player getPlayer() {
            return (Player) World.WORLD.getPlayers().get(slot);
      }

      public int getRunningDirection() {
            return runningDirection;
      }

      public int getSize() {
            return size;
      }

      public EnumSet<UpdateFlag> getUpdateFlags() {
            return FLAGS;
      }

      public boolean isUpdateRequired() {
            return !FLAGS.isEmpty();
      }

      public int getWalkingDirection() {
            return walkingDirection;
      }

      public boolean isDead() {
            return isDead;
      }

      public boolean isNpc() {
            return getClass() == Npc.class;
      }

      public boolean isPlayer() {
            return getClass() == Player.class;
      }

      @Override
	public boolean isMob() {
            return getClass() == Mob.class;
      }

      public boolean isRegionChange() {
            return regionChange;
      }

      public boolean isRegistered() {
            return registered;
      }

      public boolean isTeleporting() {
            return teleporting;
      }

      public boolean isVisible() {
            return visible;
      }

      public void resetEntityInteraction() {    
        if (this.getInteractingEntity() != null) {
          this.getInteractingEntity().setInteractingEntity(null);
          this.interactingEntity = null;
        }
        FLAGS.add(UpdateFlag.ENTITY_INTERACTION);
      }

      public void setDead(boolean isDead) {
            this.isDead = isDead;
      }

      public void setForcedChat(String forcedChat) {
            this.forcedChat = forcedChat;
            FLAGS.add(UpdateFlag.FORCED_CHAT);
      }

      public void setId(int id) {
            this.id = id;
      }

      public void setInteractingEntity(Mob entity) {
        this.interactingEntity = entity;
            FLAGS.add(UpdateFlag.ENTITY_INTERACTION);
      }

      public void setLastLocation(Location lastLocation) {
            this.lastLocation = lastLocation;
      }

      public void setRegionChange(boolean regionChange) {
            this.regionChange = regionChange;
      }

      public void setRegistered(boolean registered) {
            this.registered = registered;
      }

      public void setRunningDirection(int runningDirection) {
            this.runningDirection = runningDirection;
      }

      public void setTeleporting(boolean teleporting) {
            this.teleporting = teleporting;
      }

      public void setVisible(boolean visible) {
            this.visible = visible;
      }

      public void setWalkingDirection(int walkingDirection) {
            this.walkingDirection = walkingDirection;
      }

      public void startAnimation(final Animation animation) {
            if (animation != null) {
                  animations.add(animation);
                  FLAGS.add(UpdateFlag.ANIMATION);
            }
      }

      public void startGraphic(Graphic graphic) {
            if (graphic != null) {
                  graphics.add(graphic);
                  FLAGS.add(UpdateFlag.GRAPHICS);
            }
      }

      public ForceMovement getForceMovement() {
            return forceMovement;
      }

      public void setForceMovement(ForceMovement forceMovement) {
            this.forceMovement = forceMovement;
            FLAGS.add(UpdateFlag.FORCE_MOVEMENT);
      }

      public Animation getAnimation() {
            return animations.peek() == null ? new Animation(65535) : animations.peek();
      }

      public Queue<Animation> getAnimations() {
            return animations;
      }

      public AttributeMap attr() {
            return attributes;
      }

      public String getForcedChat() {
            return forcedChat;
      }

      public Graphic getGraphic() {
            return graphics.peek() == null ? new Graphic(65535) : graphics.peek();
      }

      public Queue<Graphic> getGraphics() {
            return graphics;
      }

      public int getId() {
            return id;
      }

      public Mob getInteractingEntity() {
            return interactingEntity;
      }

      public Location getLastLocation() {
            return lastLocation;
      }

      public List<Npc> getLocalNpcs() {
            return localNpcs;
      }

      public List<Player> getLocalPlayers() {
            return localPlayers;
      }

      public boolean isPoisoned() {
            return poisoned;
      }

      public void setPoisoned(boolean poisoned) {
            this.poisoned = poisoned;
      }

      public int getAntipoisonTimer() {
            return antipoisonTimer;
      }

      public void setAntipoisonTimer(int antipoisonTimer) {
            this.antipoisonTimer = antipoisonTimer;
      }

      public Location getFacingLocation() {
            return facingLocation;
      }

      public void faceLocation(Location facingLocation) {
            this.facingLocation = facingLocation;
            FLAGS.add(UpdateFlag.FACE_COORDINATE);
      }

      public MobAnimation getMobAnimation() {
            return mobAnimation;
      }

      public Movement getMovement() {
            return movement;
      }

      public boolean isFollowing() {
            return following;
      }

      public void setFollowing(boolean following) {
            this.following = following;
      }

      public MovementQueueListener getMovementListener() {
            return movementListener;
      }

      @Override
      public boolean equals(Object o) {
            if (o == null) {
                  return false;
            }

            if (o instanceof Mob) {
                  Mob entity = (Mob) o;
                  return getHashCode() == entity.getHashCode();
            }
            return false;
      }
}