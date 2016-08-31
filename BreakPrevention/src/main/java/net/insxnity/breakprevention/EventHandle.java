/**
 * @(#)EventHandle.java        v0.1.0-BUKKIT-BETA 16/8/30
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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockBurnEvent;

/**
 * BreakPrevention EventHandler<br>
 * Handles the Events for the Plugin
 * 
 * @version 
 *        1.00 30 Aug, 2016
 * @author
 *        Insxnity (Ben Morris) */
public class EventHandle implements Listener {
    
    public BreakPrevention plugin = null;
    
    public EventHandle(BreakPrevention plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!handleEvent("Prevent.BlockBreak")) return;
        Player player = event.getPlayer();
        
        event.setCancelled(cancelEvent(player, "breakprevention.break"));
        canceled(player, "Break Blocks");
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!handleEvent("Prevent.BlockPlace")) return;
        Player player = event.getPlayer();
        
        event.setCancelled(cancelEvent(player, "breakprevention.place"));
        canceled(player, "Place Blocks");
    }
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (!handleEvent("Prevent.ItemFrameInteract")) return;
        Player player = event.getPlayer();
        
        if (event.getRightClicked().getType()==EntityType.ITEM_FRAME) {
            if (cancelEvent(player, "breakprevention.itemframe")) {
                event.setCancelled(true);
                canceled(player, "Interact With Item Frames");
            }
        }
        
    }
    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        if (event.getBlock().getType().equals(Material.ICE)) {
            if (handleEvent("Prevent.BlockMelt")) {
                event.setCancelled(true);
                return;
            }
        }
        else if (event.getBlock().getType().equals(Material.FIRE)) {
            if (handleEvent("Prevent.FireExtinguish")) {
                event.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        boolean cancel = handleEvent("Prevent.BlockBurn");
        event.setCancelled(cancel);
    }
    public Boolean handleEvent(String configKey) {
        boolean handle = false;
        FileConfiguration config = plugin.getMainConfiguration().getData();
        handle = config.getBoolean(configKey);
        return handle;
    }
    public Boolean cancelEvent(Player player, String permission) {
        boolean cancel = true;
        if (player.isOp() || player.hasPermission(permission)) {
            cancel = false;
        }
        return cancel;
    }
    public void canceled(Player player, String canceledText) {
        if (!handleEvent("Prevent.SendPlayerAlert")) return;
        player.sendMessage(ChatColor.RED.toString() + "You are not allowed to "
                + ChatColor.YELLOW.toString() + canceledText 
                + ChatColor.RED.toString() + "!");
    }
}
