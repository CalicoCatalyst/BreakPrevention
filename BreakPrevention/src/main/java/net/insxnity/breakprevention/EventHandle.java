package net.insxnity.breakprevention;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class EventHandle
  implements Listener
{
  public BreakPrevention plugin = null;
  public Configuration config = null;
  
  public EventHandle(BreakPrevention plugin)
  {
    this.plugin = plugin;
    this.config = BreakPrevention.getMainConfiguration();
  }
  
  @EventHandler
  public void onBlockBreak(BlockBreakEvent event)
  {
    if (!Events.BLOCK_BREAK.getToHandle().booleanValue()) {
      return;
    }
    Player player = event.getPlayer();
    
    event.setCancelled(this.config.cancelPlayerEvent(player, Events.BLOCK_BREAK).booleanValue());
    this.config.canceled(player, Events.BLOCK_BREAK);
  }
  
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event)
  {
    if (!Events.BLOCK_PLACE.getToHandle().booleanValue()) {
      return;
    }
    Player player = event.getPlayer();
    
    event.setCancelled(this.config.cancelPlayerEvent(player, Events.BLOCK_PLACE).booleanValue());
    this.config.canceled(player, Events.BLOCK_PLACE);
  }
  
  @EventHandler
  public void onEntityInteract(PlayerInteractEntityEvent event)
  {
    if (!Events.ITEM_FRAME_INTERACT.getToHandle().booleanValue()) {
      return;
    }
    Player player = event.getPlayer();
    if (event.getRightClicked().getType() == EntityType.ITEM_FRAME) {
      if (this.config.cancelPlayerEvent(player, Events.ITEM_FRAME_INTERACT).booleanValue())
      {
        event.setCancelled(true);
        this.config.canceled(player, Events.ITEM_FRAME_INTERACT);
      }
    }
  }
  
  @EventHandler
  public void onEntityDamage(EntityDamageByEntityEvent event)
  {
    if ((event.getEntityType() == EntityType.ITEM_FRAME) && 
      (Events.ITEM_FRAME_INTERACT.getToHandle().booleanValue()))
    {
      event.setCancelled(true);
      if ((event.getDamager() instanceof Player)) {
        this.config.canceled((Player)event.getDamager(), Events.ITEM_FRAME_INTERACT);
      }
    }
  }
  
  @EventHandler
  public void onBlockSpread(BlockSpreadEvent event)
  {
    if ((event.getBlock().getType().equals(Material.FIRE)) && 
      (Events.FIRE_SPREAD.getToHandle().booleanValue())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onBlockFade(BlockFadeEvent event)
  {
    if ((event.getBlock().getType().equals(Material.ICE)) && 
      (Events.BLOCK_MELT.getToHandle().booleanValue())) {
      event.setCancelled(true);
    } else if ((event.getBlock().getType().equals(Material.FIRE)) && 
      (Events.FIRE_BURNOUT.getToHandle().booleanValue())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onBlockBurn(BlockBurnEvent event)
  {
    event.setCancelled(Events.BLOCK_BURN.getToHandle().booleanValue());
  }
}
