
package com.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 用來獲取.properties裡面屬性用的類別
 * <p>
 * .properties檔應放置於resources 第一層底下
 * 
 * @author David on 2021/01/18
 */
public class PropertyUtils {

    private static PropertyUtils INSTANCE = null;
    private final Properties props = new Properties();

    private PropertyUtils() {
        this.loadProperties("configuration.properties");
        this.props.putAll(System.getProperties());
    }

    private static PropertyUtils getInstance() {
        if (PropertyUtils.INSTANCE == null) {
            PropertyUtils.INSTANCE = new PropertyUtils();
        }
        return PropertyUtils.INSTANCE;
    }

    /**
     * 從key得到value
     * @param key
     * @return
     */
    public static String getProperty(final String key) {
        return PropertyUtils.getInstance().props.getProperty(key);
    }

    /**
     * 從key得到value，如果找不到就返回defalutValue
     *	<p>
     * 使用在.properties裡面定義的是整數 ex: app.version = 1
     * @param key
     * @param defaultValue
     * @return int
     */
    public static int getIntegerProperty(final String key, final int defaultValue) {
        int integerValue = 0;
        final String value = PropertyUtils.getInstance().props.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        integerValue = Integer.parseInt(value);
        return integerValue;
    }

    /**
     * 從key得到value，如果找不到就返回defalutValue
     *	<p>
     * 使用在.properties裡面定義的是字串 ex: app.version = apple
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperty(final String key, final String defaultValue) {
        return PropertyUtils.getInstance().props.getProperty(key, defaultValue);
    }

    /**
     * 這個方法用於讀取.properties檔
     *
     * @param path
     */
    public void loadProperties(final String path) {
        InputStream inputStream = null;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream(path);
            System.out.println(inputStream);
            if (inputStream != null) {
                this.props.load(inputStream);
            } else {
                throw new UnableToLoadPropertiesException("property file '" + path + "' not found in the classpath");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        return;
    }

    /**
     * @return Properties
     */
    public static Properties getProps() {
        return PropertyUtils.getInstance().props;
    }

}

class UnableToLoadPropertiesException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UnableToLoadPropertiesException(final String s) {
        super(s);
    }

    public UnableToLoadPropertiesException(final String string, final Exception ex) {
        super(string, ex);
    }
}