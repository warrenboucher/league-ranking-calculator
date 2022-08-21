package earth.guardian.lrc.utils.simple;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Writes UTF-8 character encoding.
 */
public class SimpleBufferedWriterMakerImpl implements SimpleBufferedWriterMaker {

	@Override
	public BufferedWriter make(File file) throws IOException {
		return Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);
	}

}
