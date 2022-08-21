package earth.guardian.lrc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IntegrationTest {

	/**
	 * 
	 */
	@Test(groups = TestConstants.INTEGRATION)
	public void testIntegrationWithSampleData() {
		final String inputFileName = String.format("%sinput.txt", UUID.randomUUID().toString());
		final String outputFileName = String.format("%soutput.txt", UUID.randomUUID().toString());
		final File inputFile = new File(inputFileName);
		final File outputFile = new File(outputFileName);

		try {		

			writeSampleData(inputFile);
			
			// Exercise fixture.
			Main.main(getCmdLineParameters(inputFile, outputFile));	

			// Assert output.
			Assert.assertEquals(readOutputFileContent(outputFile), getExpectedOutput());
			
		} catch (IOException e) {
			Assert.fail("Failed to read integration test output file.");
		} finally {
			// Cleanup integration test result.
			outputFile.delete();
			inputFile.delete();
		}
	}

	/**
	 * 
	 * @param outputFile
	 * @return
	 * @throws IOException
	 */
	private String readOutputFileContent(File outputFile) throws IOException {
		StringBuilder outputChecker = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(outputFile.toPath(), StandardCharsets.UTF_8)) {
			String matchLine;
			while ((matchLine = reader.readLine()) != null) {
				outputChecker.append(String.format("%s %n", matchLine.trim()));
			}	
		}		
		return outputChecker.toString();
	}
	
	/**
	 * 
	 * @param outputFile
	 * @return
	 */
	private String[] getCmdLineParameters(File inputFile, File outputFile) {
		return new String[] {
				"-i", inputFile.getAbsolutePath(), 
				"-o", outputFile.getAbsolutePath()};
	}
	
	/**
	 * 
	 * @return
	 */
	private String getExpectedOutput() {
		final StringBuilder expectedOutput = new StringBuilder();
		expectedOutput.append(String.format("%s %n", "1. Tarantulas, 6 pts")); 
		expectedOutput.append(String.format("%s %n", "2. Lions, 5 pts"));
		expectedOutput.append(String.format("%s %n", "3. FC Awesome, 1 pts")); 
		expectedOutput.append(String.format("%s %n", "4. Snakes, 1 pts")); 
		expectedOutput.append(String.format("%s %n", "5. Grouches, 0 pts"));
		return expectedOutput.toString();
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	private void writeSampleData(File inputFile) throws IOException {
		final StringBuilder expectedOutput = new StringBuilder();		
		expectedOutput.append(String.format("%s %n", "Lions 3, Snakes 3"));
		expectedOutput.append(String.format("%s %n", "Tarantulas 1, FC Awesome 0"));
		expectedOutput.append(String.format("%s %n", "Lions 1, FC Awesome 1"));
		expectedOutput.append(String.format("%s %n", "Tarantulas 3, Snakes 1"));
		expectedOutput.append(String.format("%s %n", "Lions 4, Grouches 0"));

		try (BufferedWriter writer = Files.newBufferedWriter(inputFile.toPath(), StandardCharsets.UTF_8)) {
			writer.write(expectedOutput.toString());
		}
		
		
	}
}
