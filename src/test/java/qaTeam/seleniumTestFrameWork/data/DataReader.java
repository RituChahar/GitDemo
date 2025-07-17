package qaTeam.seleniumTestFrameWork.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class to read test data from a JSON file and convert it into a list
 * of HashMaps (for use in data-driven testing).
 */
public class DataReader {

	/**
	 * Reads a JSON file and converts its content into a List of HashMaps. Each
	 * HashMap represents a single test case's key-value data.
	 *
	 * @return List of HashMaps containing test data
	 * @throws IOException if file reading or parsing fails
	 */
	public List<HashMap<String, String>> getJsonDataToMap() throws IOException {

		// Step 1: Read JSON content from file into a String
		String jsonContent = FileUtils.readFileToString(
				new File(System.getProperty("user.dir")
						+ "\\src\\test\\java\\qaTeam\\seleniumTestFrameWork\\data\\PurchaseOrder.json"),
				StandardCharsets.UTF_8);

		// Step 2: Convert JSON string into List of HashMaps using Jackson
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data; // return parsed test data
	}
}
