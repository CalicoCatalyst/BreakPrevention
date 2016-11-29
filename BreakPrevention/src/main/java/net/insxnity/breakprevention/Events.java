/**
 * @(#)Events.java        v0.1.0-BUKKIT-BETA 16/9/4
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

/**
 * BreakPrevention Events<br>
 * A collection of events, and methods for retrieving information on
 * them
 * 
 * @version 
 *        1.00 4 Sep, 2016
 * @author
 *        Insxnity (Ben Morris) */
public class Events {
    public static final Events BLOCK_BREAK = new Events("block-break", true);
    public static final Events BLOCK_PLACE = new Events("block-place", true);
    public static final Events ITEM_FRAME_INTERACT = new Events("item-frame-interact", true);
    public static final Events ARMOR_STAND_INTERACT = new Events("armor-stand-interact", true);
    public static final Events FISH = new Events("fish", true);
    public static final Events PLAYER_FIRE_EXTINGUISH = new Events("player-fire-extinguish", true);
    public static final Events FIRE_SPREAD = new Events("fire-spread", false);
    public static final Events BLOCK_MELT = new Events("block-melt", false);
    public static final Events BLOCK_BURN = new Events("block-burn", false);
    public static final Events FIRE_BURNOUT = new Events("fire-burnout", false);
    
    public Configuration config = null;
    
    private String permission = null;
    private Boolean handle = null;
    private String message = null;
    
    public Boolean playerEvent = null;
    
    public Events(String name, boolean isPlayerEvent) {
        config = BreakPrevention.getMainConfiguration();

        setToHandle(config.getData().getBoolean("prevent." + name));
        
        playerEvent = isPlayerEvent;
        
        if (playerEvent) {
            setPermission(config.getData().getString("permissions." + name));
            setMessage(config.getData().getString("messages." + name));
        }
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getToHandle() {
        return handle;
    }

    public void setToHandle(Boolean handle) {
        this.handle = handle;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
    
    
}