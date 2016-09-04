/**
 * @(#)BreakPrevention.java        v0.1.0-BUKKIT-BETA 16/9/4
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

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * BreakPrevention Main Class<br>
 * The main class for the plugin.
 * 
 * @see 
 *        <a href="http://Insxnity.Net">Insxnity.Net</a>
 * @version 
 *        1.00 04 Sep, 2016
 * @author
 *        Insxnity (Ben Morris) */
public class BreakPrevention extends JavaPlugin {
    
    private static BreakPrevention plugin = null;
    
    private static Logger logger = null;
    
    private static Configuration mainConfiguration = null;
    
    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        logger = plugin.getLogger();
        
        logger.info("Enabling...");
        
        mainConfiguration = new Configuration(plugin, "config.yml");
        
        if (!getMainConfiguration().getData().getBoolean("Enable", true)) {
            logger.warning("Enable set to false! Disabling plugin...");
            plugin.getPluginLoader().disablePlugin(this);
        }

        plugin.getServer().getPluginManager().registerEvents(new EventHandle(this), plugin);
        
        logger.info("Enabled!");
        
    }
    
    @Override
    public void onDisable() {
        plugin = null;
        logger = null;
        mainConfiguration = null;
    }

    public static Configuration getMainConfiguration() {
        return mainConfiguration;
    }

    public static BreakPrevention getPlugin() {
        return plugin;
    }
    
}
