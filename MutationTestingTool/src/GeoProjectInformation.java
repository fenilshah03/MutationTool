import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

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
		Collection<File> files = FileUtils.listFiles(new File(projectFolderPrefix + "\\src\\main"), new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
		for (File file : files) {
			String[] absPath = file.getAbsolutePath().split(projectFolderPrefix);
			filePaths.add(absPath[1]);
		}
		return filePaths;
	}
}
