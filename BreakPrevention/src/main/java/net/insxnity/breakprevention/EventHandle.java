/**
 * @(#)EventHandle.java        v0.1.0-BUKKIT-BETA 16/9/4
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This source has been distributed in hopes of helping
 * smaller developers. Although we hope that
 * this program is useful, it is distributed WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Insxnity (Ben Morris) - Insxnity.net
 */

package net.insxnity.breakprevention;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * BreakPrevention EventHandler<br>
 * Handles the Events for the Plugin
 * 
 * @version 
 *        1.01 4 Sep, 2016
 * @author
 *        Insxnity (Ben Morris) */
public class EventHandle implements Listener {
    
    public BreakPrevention plugin = null;
    
    public Configuration config = null;
    
    public EventHandle(BreakPrevention plugin) {
        this.plugin = plugin;
        config = BreakPrevention.getMainConfiguration();
    }
    
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        
        if (!Events.BLOCK_BREAK.getToHandle()) return;
        Player player = event.getPlayer();
        
        event.setCancelled(config.cancelPlayerEvent(player, Events.BLOCK_BREAK));
        config.canceled(player, Events.BLOCK_BREAK);
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!Events.BLOCK_PLACE.getToHandle()) return;
        Player player = event.getPlayer();
        
        event.setCancelled(config.cancelPlayerEvent(player, Events.BLOCK_PLACE));
        config.canceled(player, Events.BLOCK_PLACE);
    }
    
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (!Events.ITEM_FRAME_INTERACT.getToHandle()) return;
        Player player = event.getPlayer();
        
        if (event.getRightClicked().getType()==EntityType.ITEM_FRAME 
                && config.cancelPlayerEvent(player,
                        Events.ITEM_FRAME_INTERACT)) {
                event.setCancelled(true);
                config.canceled(player, Events.ITEM_FRAME_INTERACT);
        }
        
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType()==EntityType.ITEM_FRAME
                && Events.ITEM_FRAME_INTERACT.getToHandle()) {
            event.setCancelled(true);
            if (event.getDamager() instanceof Player) {
                config.canceled((Player) event.getDamager(), Events.ITEM_FRAME_INTERACT);
            }
        }
    }
    
    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        if (event.getBlock().getType().equals(Material.FIRE)
                && Events.FIRE_SPREAD.getToHandle()) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        if (event.getBlock().getType().equals(Material.ICE)
                && Events.BLOCK_MELT.getToHandle()) {
            event.setCancelled(true);
        }
        else if (event.getBlock().getType().equals(Material.FIRE)
                && Events.FIRE_BURNOUT.getToHandle()) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(Events.BLOCK_BURN.getToHandle());
    }
}
