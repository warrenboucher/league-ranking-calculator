package earth.guardian.lrc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import earth.guardian.lrc.constants.CommandLineConstants;
import earth.guardian.lrc.utils.L;
import earth.guardian.lrc.utils.StartupParameters;
import earth.guardian.lrc.utils.simple.SimpleBufferedReaderMaker;
import earth.guardian.lrc.utils.simple.SimpleBufferedWriterMaker;
import earth.guardian.lrc.utils.simple.SimpleFileMaker;
import earth.guardian.lrc.utils.simple.SimpleSystemExit;

/**
 * The league ranking calculator application.
 */
@Singleton
public class LeagueRankingCalculator {

	private final L log = LrcRuntime.getInjector().getInstance(L.class);
	
	@Inject private SimpleSystemExit systemExit;
	@Inject private SimpleBufferedReaderMaker bufferedReaderMaker;
	@Inject private SimpleBufferedWriterMaker bufferedWriterMaker;
	@Inject private StartupParameters startupParameters;
	@Inject private SimpleFileMaker simpleFileMaker;
		
	/**
	 * 
	 */
	@Inject
	public LeagueRankingCalculator() {
		// Inject constructor.
	}
	
	/**
	 * 
	 * @param args pass in startup parameters from the command line.
	 */
	public void run(String[] args) {
		initArguments(args);
		process();
	}
	
	/**
	 * Validates arguments.  If required arguments are not present, output options to console before shutting down.
	 * Stores instance of CommandLine so that any part of the application has access to it.
	 * @param args
	 */
	private void initArguments(String[] args) {
		final CommandLine commandLine = startupParameters.interpret(args);
		LrcRuntime.setCommandLine(commandLine);
	}

	/**
	 * DHandles everything that is needed to be done.
	 */
	private void process() {
		final CommandLine commandLine = LrcRuntime.getCommandLine();
		final String inputFileName = commandLine.getOptionValue(CommandLineConstants.getArgumentInput());
		final String outputFileName = commandLine.getOptionValue(CommandLineConstants.getArgumentOutput());
		final LeagueMatchRecorder recorder = LrcRuntime.getInjector().getInstance(LeagueMatchRecorder.class);
		final File inputFile = simpleFileMaker.make(inputFileName);
		final File outputFile = simpleFileMaker.make(outputFileName);
		validateInputFile(inputFile);
		validateOutputFile(outputFile);
		processInputFile(recorder, inputFile);
		writeOutputFile(recorder, outputFile);
	}
	
	/**
	 * 
	 * @param recorder
	 * @param inputFile
	 */
	private void processInputFile(LeagueMatchRecorder recorder, File inputFile) {
		try {
			String matchLine;
			try (final BufferedReader objReader =  bufferedReaderMaker.make(inputFile)) {
				while ((matchLine = objReader.readLine()) != null) {
					recorder.recordMatchResult(matchLine);
				}	
			}
		} catch (FileNotFoundException e) {
			log.logInputFileDoesNotExist(inputFile, e);
			systemExit.exit(1);
		} catch (IOException e) {
			log.logFailedToReadLineFromInputFile(inputFile, e);			
			systemExit.exit(1);
		}	
	}
	
	/**
	 * 
	 * @param recorder
	 * @param outputFile
	 */
	private void writeOutputFile(LeagueMatchRecorder recorder, File outputFile) {
		try (BufferedWriter writer = bufferedWriterMaker.make(outputFile)) {
			writer.write(recorder.getLeagueResults());
		} catch (IOException e) {			
			log.logFailedToWriteLeageResultsToOutputFile(outputFile, e);
			systemExit.exit(1);
		}
	}

	/**
	 * 
	 * @param inputFile
	 */
	private void validateInputFile(File inputFile) {
		if (!inputFile.exists()) {			
			log.logInputFileDoesNotExist(inputFile);
			systemExit.exit(1);
		} else if (!inputFile.canRead() || inputFile.isDirectory()) {			
			log.logInputFileCannotBeReadFrom(inputFile);
			systemExit.exit(1);
		}
	}

	/**
	 * 
	 * @param outputFile
	 * @param outputFileName
	 */
	private void validateOutputFile(File outputFile) {
		if (outputFile.exists()) {			
			log.logOutputFileAlreadyExists(outputFile);
			systemExit.exit(1);
		}		

		try {
			final boolean created = outputFile.createNewFile();
			if (!created) {				
				log.logCouldNotCreateOutputFile(outputFile);
				systemExit.exit(1);
			}
		} catch (IOException e) {
			log.logCouldNotCreateOutputFile(outputFile, e);
			systemExit.exit(1);
		}
	}
	
}
