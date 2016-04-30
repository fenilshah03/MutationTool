import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class GeoProjectInformation {
	// The project path - The project should exist here.
	// Please create a mutants directory and put Geo project in that.
	static String projectFolderPrefix = "C:\\Users\\Fenil\\Documents\\Eclipse projects\\mutants\\geo-master";

	static void createProjectCopy(int copyNumber) throws IOException {
		FileUtils.copyDirectory(new File(projectFolderPrefix), new File(projectFolderPrefix + "-" + copyNumber));
	}

	static void deleteProjectCopy(int copyNumber) throws IOException {
		FileUtils.deleteDirectory(new File(projectFolderPrefix + "-" + copyNumber));
	}

	static List<String> getFileList() {
		List<String> filePaths = new ArrayList<String>();

		// All project files with code EXCLUDING test files.
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\GeoHash.java");
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\Base32.java");
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\Coverage.java");
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\CoverageLongs.java");
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\Direction.java");
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\LatLong.java");
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\Parity.java");
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\mem\\Geomem.java");
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\package-info.java");
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\mem\\Info.java");
		filePaths.add("\\src\\main\\java\\com\\github\\davidmoten\\geo\\util\\Preconditions.java");

		return filePaths;
	}
}
