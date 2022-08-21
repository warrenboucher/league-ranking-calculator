package earth.guardian.lrc.utils.simple;

import java.io.File;

public class SimpleFileMakerImpl implements SimpleFileMaker{

	@Override
	public File make(String fileName) {
		return new File(fileName);
	}

}
