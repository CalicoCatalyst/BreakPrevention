/**
 * @(#)YamlDataFile.java        v0.1.0-SPIGOT-BETA 16/8/20
 * 
 * GNU General Public License v3
 * 
 * This class may be extremely useful to some users. As stated in the
 * License Agreement, the author is not responsible for any damages,
 * including but not limited to data that could be lost by misuse 
 * of this source code and/or compiled application.
 * See ReverseCore.java and/or the LICENSE.TXT for more information
 * 
 * @author Insxnity (Ben Morris)
 */

package net.insxnity.api.data.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.google.common.io.ByteStreams;


/**
 * This class aims to serve a dual purpose; Creating config files 
 * Which can be edited (Such as the default config.yml), and 
 * Creating ones to store information. In the future, storage
 * will most likely be moved over to json/mysql.
 * 
 * @version 
 *        1.00 20 Aug, 2016
 * @author
 *        Insxnity (Ben Morris) */
public class YamlDataFile {
    
    /**
     * The Main Class for the Plugin <br>
     * We need this to get the default file path
     */
    private Plugin plugin = null;
    
    /**
     * The FileConfiguration interface which can be used to set and retrieve
     * the values loaded into the memory. The YamlDataFile class acts as
     * an extension of this field
     */
    private FileConfiguration fileData = null;
    
    /**
     * The file to be loaded as the <code>fileData</code> interface. It is
     * usually set according to the file name, but may be passed directly to
     * one of the Constructors
     */
    private File pluginDataFile = null;
    
    private String fileName = null;
    private String filePath = plugin.getDataFolder().toString();
    
    /**
     * Creates all the necessary variables based on the <code>fileName</code>
     * Parameter. Assumes the file path is the plugin's resource folder.
     * 
     * @param fileName
     */
    public YamlDataFile(Plugin plugin, String fileName) {
        
        this.plugin = plugin;
        
        this.fileName = fileName;
        
        try {
            fileData = getDataFile();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Creates all the necessary variables based on the <code>fileName</code>
     * and <code>filePath</code> parameters. 
     * 
     * @param filePath
     *         String path to file
     * @param fileName
     *         String File name
     */
    public YamlDataFile(Plugin plugin, String filePath, String fileName) {
        
        this.filePath = filePath;
        this.fileName = fileName;
        
        genFile();
        
        try {
            fileData = getDataFile();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates all the necessary variables based on the <code>File</code>
     * Parameter. 
     */
    public YamlDataFile(Plugin plugin, File dataFile) {
        this.pluginDataFile = dataFile;
        this.fileName = dataFile.getName();
    }
    
    /**
     * Returns the file name passed to the Constructor or retrieved
     * from the File
     * 
     * @return The File Name, in String form
     */
    public String getFileName() {
        return this.fileName;
    }
    
    /**
     * Returns the file path, which is by default the plugin's folder. The
     * default value can be found with
     * ReverseCore.getPlugin().getResourceFolder()
     * 
     * @return The File Path, in String form
     */
    public String getFilePath() {
        return this.filePath;
    }
    
    /**
     * Retrieves the File field used by the YamlDataFile for retrieving values
     * into the memory. 
     * 
     * @return The file field which the class uses
     */
    public File getFile() {
        return this.pluginDataFile;
    }
    
    /**
     * Retrieves the FileConfiguration, but cleanly, and properly handles
     * any errors that arise.
     * 
     * @return The FileConfiguration interface
     */
    public FileConfiguration getData() {
        try {
            return getDataFile();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void genFile() {
        if (!new File(filePath).exists()) new File(filePath).mkdir();
        if (!new File(filePath, fileName).exists())
        {
            try {
                (new File(filePath, fileName)).createNewFile();
                try (InputStream is = plugin.getResource(fileName)) {
                    OutputStream os =  new FileOutputStream(new File(filePath, fileName));
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create config file", e);
            }
            
        }
    }
    
    /**
     * Loads the data from the disk into the field, which can be retrieved
     * using <code>getData()</code> or <code>getDataFile()</code>. The latter
     * should only be used by <code>getData()</code>, as this method cleanly
     * handles the exceptions that can be generated.
     * 
     * @throws UnsupportedEncodingException If there is an error in the 
     *                                      encoding of the file. This issue
     *                                      could arise in the event that it
     *                                      was edited wrongly, and/or it was
     *                                      an alien file.
     */
    public void reloadDataFile() throws UnsupportedEncodingException {
        if (pluginDataFile == null) {
            pluginDataFile = new File(filePath, fileName);
        }
        
        fileData = YamlConfiguration.loadConfiguration(pluginDataFile);

        // See if there are defaults compiled within the plugin
        Reader defConfigStream = new InputStreamReader(plugin
                .getResource(fileName), "UTF8");
        
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration
                    .loadConfiguration(defConfigStream);
            fileData.setDefaults(defConfig);
        }
    }
    
    /**
     * Gets the FileConfiguration interface from the loaded values,
     * and if the values are unloaded, loads those first.
     * 
     * @return The FileConfiguration that has been loaded into the memory.
     * @throws UnsupportedEncodingException
     */
    public FileConfiguration getDataFile() throws UnsupportedEncodingException {
        if (fileData == null) {
            reloadDataFile();
        }
        return fileData;
    }
    
    /**
     * Saves the data file to the disk, using the save method in 
     * FileConfiguration, but properly handles the correct errors.
     * In most instances this will only be used in the STORAGE
     * type files, but for the sake of compatibility it has been included
     * here
     */
    public void saveDataFile() {
        if (fileData == null || pluginDataFile == null) {
            return;
        }
        try {
            getDataFile().save(pluginDataFile);
        } catch (IOException ex) {
            plugin.getLogger()
                    .log(Level.SEVERE, 
                            "Could not save config to " + pluginDataFile,
                            ex); 
        }
    }
}
