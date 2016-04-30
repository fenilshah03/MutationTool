package main;

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

	static StringBuilder report = new StringBuilder();

	public static void createProjectCopy(int copyNumber) throws IOException {
		FileUtils.copyDirectory(new File(projectFolderPrefix), new File(projectFolderPrefix + "-" + copyNumber));
	}

	public static void deleteProjectCopy(int copyNumber) throws IOException {
		FileUtils.deleteDirectory(new File(projectFolderPrefix + "-" + copyNumber));
	}

	public static List<String> getFileList() {
		List<String> filePaths = new ArrayList<String>();

		// All project files with code EXCLUDING test files.
		Collection<File> files = FileUtils.listFiles(new File(projectFolderPrefix + "\\src\\main"),
				new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
		for (File file : files) {
			String[] absPath = file.getAbsolutePath().split(projectFolderPrefix);
			filePaths.add(absPath[1]);
		}

		return filePaths;
	}

	public static void buildReport(String str) {
		report.append("\n" + str);
	}

	public static void buildReport() {
		report.append("\n");
	}

	public static void commitReport() {
		String[] pathParts = projectFolderPrefix.split("geo-master");
		try {
			File f = new File(pathParts[0] + "\\MutationInsertionReport.txt");
			if (f.exists() && !f.isDirectory()) {
				// delete previously generated report
				f.delete();
			}
			FileUtils.writeStringToFile(f, report.toString(), true);
		} catch (IOException e) {
			System.out.println("There was an error in commiting the report.");
			e.printStackTrace();
		}
	}
}
