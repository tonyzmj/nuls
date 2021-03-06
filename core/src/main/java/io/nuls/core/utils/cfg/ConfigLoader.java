package io.nuls.core.utils.cfg;

import io.nuls.core.constant.ErrorCode;
import io.nuls.core.exception.NulsException;
import io.nuls.core.utils.str.StringUtils;
import org.ini4j.Config;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author Niels
 * @date 2017/9/26
 */
public class ConfigLoader {

    private static Ini config = null;

    private static Properties properties = new Properties();

    public static Properties loadProperties(String fileName) throws IOException {
        InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream(fileName);
        Properties prop = new Properties();
        prop.load(is);

        for (String name : prop.stringPropertyNames()) {
            properties.setProperty(name, prop.getProperty(name));
        }
        return prop;
    }

    public static <T> T getPropValue(String name, T defaultValue) {
        if (properties.getProperty(name) == null) {
            return defaultValue;
        }
        String value = properties.getProperty(name);
        return getValueByType(value, defaultValue);
    }

    public static void loadIni(String fileName) throws IOException {
        Config cfg = new Config();
        URL url = ConfigLoader.class.getClassLoader().getResource(fileName);
        cfg.setMultiSection(true);
        Ini ini = new Ini();
        ini.setConfig(cfg);
        ini.load(url);
        config = ini;
    }


    public static String getCfgValue(String section, String key) throws NulsException {
        Profile.Section ps = config.get(section);
        if (null == ps) {
            throw new NulsException(ErrorCode.CONFIGURATION_ITEM_DOES_NOT_EXIST);
        }
        String value = ps.get(key);
        if (StringUtils.isBlank(value)) {
            throw new NulsException(ErrorCode.CONFIGURATION_ITEM_DOES_NOT_EXIST);
        }
        return value;
    }

    public static <T> T getCfgValue(String section, String key, T defaultValue) {
        Profile.Section ps = config.get(section);
        if (null == ps) {
            return defaultValue;
        }
        String value = ps.get(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return getValueByType(value, defaultValue);
    }

    private static <T> T getValueByType(String value, T defaultValue) {
        if (defaultValue instanceof Integer) {
            return (T) ((Integer) Integer.parseInt(value));
        } else if (defaultValue instanceof Long) {
            return (T) ((Long) Long.parseLong(value));
        } else if (defaultValue instanceof Float) {
            return (T) ((Float) Float.parseFloat(value));
        } else if (defaultValue instanceof Double) {
            return (T) ((Double) Double.parseDouble(value));
        } else if (defaultValue instanceof Boolean) {
            return (T) ((Boolean) Boolean.parseBoolean(value));
        }
        return (T) value;
    }

    public static Profile.Section getSection(String section) throws NulsException {
        Profile.Section ps = config.get(section);
        if (null == ps) {
            throw new NulsException(ErrorCode.CONFIGURATION_ITEM_DOES_NOT_EXIST);
        }
        return ps;
    }
}
