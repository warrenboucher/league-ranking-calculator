package earth.guardian.lrc.utils;

import org.apache.logging.log4j.Logger;

import earth.guardian.lrc.LrcRuntime;
import earth.guardian.lrc.Main;

/**
 * Using short class name because we will be using logging everywhere.  L is also a convention in some languages.
 */
public class L {

	private static final int INDEX_CALLING_CLASS = 2; // 0 = Thread.  1 = This method.  2 = Who called this method.
	
	/**
	 * 
	 */
	private L() {
		// Do not allow instantiation.
	}
	
	/**
	 * Calling this method is the same as calling</br>
	 * <code>
	 * L.getLogger(getClass())
	 * </code>
	 * @return
	 */
	public static Logger getLogger() {		
		final StackTraceElement element = Thread.currentThread().getStackTrace()[INDEX_CALLING_CLASS];
		try {
			Class<?> clazz = Class.forName(element.getClassName());
			return getLogger(clazz);
		} catch (ClassNotFoundException e) {
			return getLogger(Main.class);
		}		
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		return LrcRuntime.getInjector().getInstance(SimpleLoggerMaker.class).make(clazz);
	}
	
}
