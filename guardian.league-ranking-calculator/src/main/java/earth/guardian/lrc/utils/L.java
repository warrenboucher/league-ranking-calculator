package earth.guardian.lrc.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import earth.guardian.lrc.LrcRuntime;
import earth.guardian.lrc.Main;
import earth.guardian.lrc.utils.simple.SimpleLoggerMaker;

/**
 * Using short class name because we will be using logging everywhere.  L is also a convention in some languages.
 */
@Singleton
public class L {

	/**
	 * 0 = Thread.  
	 * 1 = getCallingClass
	 * 2 = This method.  
	 * 3 = Who called this method.
	 */
	private static final int INDEX_CALLING_CLASS_BASIC = 3;
	
	/**
	 * 0 = Thread.  
	 * 1 = getCallingClass
	 * 2 = logging method  
	 * 3 = Who called logging method.
	 *  */
	private static final int INDEX_CALLING_CLASS_LOG_METHOD = 4; 
	
	/**
	 * 
	 */
	@Inject
	public L() {
		// Inject constructor. 
	}
	
	/**
	 * Calling this method is the same as calling</br>
	 * <code>
	 * L.getLogger(getClass())
	 * </code>
	 * @return
	 */
	public static Logger getLogger() {		
		return getLogger(getCallingClass(INDEX_CALLING_CLASS_BASIC));	
	}
	
	/**
	 * 
	 * @return
	 */
	private static Class<?> getCallingClass(int callingClassIndex) {
		try {
			final StackTraceElement element = Thread.currentThread().getStackTrace()[callingClassIndex];
			return Class.forName(element.getClassName());
		} catch (ClassNotFoundException e) {
			return Main.class; // Log against main class instead of having nothing logged.
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
	
	/**
	 * 
	 * @param teamName
	 * @param matchScore
	 * @param seasonScore
	 */
	public void logMatchResult(String teamName, int matchScore, long seasonScore) {
		final Logger logger = getLogger(getCallingClass(INDEX_CALLING_CLASS_LOG_METHOD));
		logger.debug("{} {} recorded match score {}.  Season total is {}.", LrcRuntime.getCorrelationId(), teamName, matchScore, seasonScore);
	}

	/**
	 * 
	 * @param inputFile
	 * @param e
	 */
	public void logInputFileDoesNotExist(File inputFile, FileNotFoundException e) {
		final Logger logger = getLogger(getCallingClass(INDEX_CALLING_CLASS_LOG_METHOD));
		logger.error("{} Input file {} does not exist.", LrcRuntime.getCorrelationId(), inputFile.getAbsolutePath(), e);
	}

	/**
	 * 
	 * @param inputFile
	 * @param e
	 */
	public void logFailedToReadLineFromInputFile(File inputFile, IOException e) {
		final Logger logger = getLogger(getCallingClass(INDEX_CALLING_CLASS_LOG_METHOD));
		logger.error("{} Failed to read line from input file {}.", LrcRuntime.getCorrelationId(), inputFile.getAbsolutePath(), e);	
	}

	/**
	 * 
	 * @param outputFile
	 * @param e
	 */
	public void logFailedToWriteLeageResultsToOutputFile(File outputFile, IOException e) {
		final Logger logger = getLogger(getCallingClass(INDEX_CALLING_CLASS_LOG_METHOD));
		logger.error("{} Failed to write league results to  {}.", LrcRuntime.getCorrelationId(), outputFile.getAbsolutePath(), e);		
	}

	/**
	 * 
	 * @param inputFile
	 */
	public void logInputFileDoesNotExist(File inputFile) {
		final Logger logger = getLogger(getCallingClass(INDEX_CALLING_CLASS_LOG_METHOD));
		logger.error("{} Input file {} does not exist.", LrcRuntime.getCorrelationId(), inputFile.getAbsolutePath());		
	}

	/**
	 * 
	 * @param inputFile
	 */
	public void logInputFileCannotBeReadFrom(File inputFile) {
		final Logger logger = getLogger(getCallingClass(INDEX_CALLING_CLASS_LOG_METHOD));
		logger.error("{} Input file {} cannot be read.", LrcRuntime.getCorrelationId(), inputFile.getAbsolutePath());
	}

	/**
	 * 
	 * @param outputFile
	 */
	public void logOutputFileAlreadyExists(File outputFile) {
		final Logger logger = getLogger(getCallingClass(INDEX_CALLING_CLASS_LOG_METHOD));
		logger.error("{} Output file {} already exist.", LrcRuntime.getCorrelationId(), outputFile.getAbsolutePath());		
	}

	/**
	 * 
	 * @param outputFile
	 */
	public void logCouldNotCreateOutputFile(File outputFile) {
		final Logger logger = getLogger(getCallingClass(INDEX_CALLING_CLASS_LOG_METHOD));
		logger.error("{} Could not create output file {}.", LrcRuntime.getCorrelationId(), outputFile.getAbsolutePath());		
	}

	/**
	 * 
	 * @param outputFile
	 * @param e
	 */
	public void logCouldNotCreateOutputFile(File outputFile, IOException e) {
		final Logger logger = getLogger(getCallingClass(INDEX_CALLING_CLASS_LOG_METHOD));
		logger.error("{} Could not create output file {}.  {}", LrcRuntime.getCorrelationId(), outputFile.getAbsolutePath(), e.getMessage(), e);		
	}
	
}
