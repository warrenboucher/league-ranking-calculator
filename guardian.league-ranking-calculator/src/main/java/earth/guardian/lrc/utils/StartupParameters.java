package earth.guardian.lrc.utils;

import org.apache.commons.cli.CommandLine;

/**
 * 
 */
public interface StartupParameters {
	CommandLine interpret(String[] args);
}
