/**
 * @(#)Configuration.java        v0.1.0-BUKKIT-BETA 16/9/4
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.insxnity.api.data.storage.YamlConfigFile;

/**
 * BreakPrevention Configuration Handler<br>
 * Handles the Configuration values
 * 
 * @version 
 *        1.01 4 Sep, 2016
 * @author
 *        Insxnity (Ben Morris) */
public class Configuration extends YamlConfigFile {
    
    private FileConfiguration config = null;
    
    public Configuration(Plugin plugin, String fileName) {
        super(plugin, fileName);
        config = getData();
    }
    
    public Boolean cancelPlayerEvent(Player player, Events event) {
        boolean cancel = true;
        String permission = event.getPermission();
        
        boolean opOverride = config.getBoolean("op-override");
        
        boolean isNull = permission == null;
        boolean isEmpty = permission.length() == 0;
        boolean hasPermission = player.hasPermission(permission);
        
        if ((player.isOp() && opOverride)
                || (!isNull && !isEmpty && hasPermission)) {
            cancel = false;
        }
        
        return cancel;
    }
    public void canceled(Player player, Events event) {
        String message = event.getMessage();
        message = ChatColor.translateAlternateColorCodes('&', message);
        
        if (!(message.trim()=="")) {
            player.sendMessage(message);
        }
        
    }
}
