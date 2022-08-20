package earth.guardian.lrc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import earth.guardian.lrc.constants.CommandLineConstants;
import earth.guardian.lrc.utils.L;
import earth.guardian.lrc.utils.StartupParameters;
import earth.guardian.lrc.utils.simple.SystemExit;

/**
 * The league ranking calculator application.
 */
@Singleton
public class LeagueRankingCalculator {

	@Inject
	private SystemExit systemExit;
	
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
		final CommandLine commandLine = LrcRuntime.getInjector().getInstance(StartupParameters.class).interpret(args);
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

		final File inputFile = new File(inputFileName);
		final File outputFile = new File(outputFileName);		
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
			try (final BufferedReader objReader = new BufferedReader(new FileReader(inputFile))) {
				while ((matchLine = objReader.readLine()) != null) {
					recorder.recordMatchResult(matchLine);
				}	
			}
		} catch (FileNotFoundException e) {
			L.getLogger().error("{} Input file {} does not exist.", LrcRuntime.getCorrelationId(), inputFile.getAbsolutePath(), e);
			systemExit.exit(1);
		} catch (IOException e) {
			L.getLogger().error("{} Failed to read line from {}.", LrcRuntime.getCorrelationId(), inputFile.getAbsolutePath(), e);
			systemExit.exit(1);
		}	
	}
	
	/**
	 * 
	 * @param recorder
	 * @param outputFile
	 */
	private void writeOutputFile(LeagueMatchRecorder recorder, File outputFile) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
			writer.append(recorder.getLeagueResults());
		} catch (IOException e) {
			L.getLogger().error("{} Failed to write league results to  {}.", LrcRuntime.getCorrelationId(), outputFile.getAbsolutePath(), e);
			systemExit.exit(1);
		}
	}

	/**
	 * 
	 * @param inputFile
	 */
	private void validateInputFile(File inputFile) {
		if (!inputFile.exists()) {
			L.getLogger().error("{} Input file {} does not exist.", LrcRuntime.getCorrelationId(), inputFile.getAbsolutePath());
			systemExit.exit(1);
		} else if (!inputFile.canRead() || inputFile.isDirectory()) {
			L.getLogger().error("{} Input file {} cannot be read.", LrcRuntime.getCorrelationId(), inputFile.getAbsolutePath());
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
			L.getLogger().error("{} Output file {} already exist.", LrcRuntime.getCorrelationId(), outputFile.getAbsolutePath());
			systemExit.exit(1);
		}		

		try {
			final boolean created = outputFile.createNewFile();
			if (!created) {
				L.getLogger().error("{} Could not create output file {}.", LrcRuntime.getCorrelationId(), outputFile.getAbsolutePath());
				systemExit.exit(1);
			}
		} catch (IOException e) {
			L.getLogger().error("{} Could not create output file {}.  {}", LrcRuntime.getCorrelationId(), outputFile.getAbsolutePath(), e.getMessage(), e);
			systemExit.exit(1);
		}
	}
	
}
