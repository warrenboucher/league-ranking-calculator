package earth.guardian.lrc.utils.simple;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Reads UTF-8
 */
public class SimpleBufferedReaderMakerImpl implements SimpleBufferedReaderMaker {

	@Override
	public BufferedReader make(File file) throws IOException  {
		return Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);
	}

}
