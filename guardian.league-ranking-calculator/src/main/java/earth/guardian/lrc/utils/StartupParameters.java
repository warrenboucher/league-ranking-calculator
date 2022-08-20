package earth.guardian.lrc.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import earth.guardian.lrc.constants.CommandLineConstants;
import earth.guardian.lrc.utils.simple.SystemExit;

/**
 * Parse startup arguments.
 */
@Singleton
public class StartupParameters {

	@Inject
	private SystemExit systemExit;
	
	@Inject
	public StartupParameters () {
		// Inject constructor.
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	public CommandLine interpret(String[] args) {
		final Options options = new Options();

		final Option input = new Option("i", CommandLineConstants.getArgumentInput(), true, "input file path");
		input.setRequired(true);
		options.addOption(input);

		final Option output = new Option("o", CommandLineConstants.getArgumentOutput(), true, "output file");
		output.setRequired(true);
		options.addOption(output);

		final CommandLineParser parser = new DefaultParser();
		final HelpFormatter formatter = new HelpFormatter(); 

		try {
			return parser.parse(options, args);
		} catch (ParseException e) {
			formatter.printHelp(CommandLineConstants.getApplicationName(), options);
			systemExit.exit(1);
			return null;
		}
	}

}
