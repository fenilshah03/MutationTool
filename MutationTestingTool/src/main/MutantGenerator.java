package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class MutantGenerator {
	// Specify the maximum number of mutants you want to create.
	static int maxNumberOfMutants = Integer.MAX_VALUE;

	static List<ASTVisitor> mutantCreators = ExpressionVisitor.getVisitors();
	static int numberOfAllowedMutantVisitor = mutantCreators.size();

	public static void main(String ar[]) {
		// take project path from argument
		try {
			if (ar[0] != null && ar[0].length() > 1)
				GeoProjectInformation.projectFolderPrefix = ar[0];
		} catch (ArrayIndexOutOfBoundsException e1) {
			System.out.println("Please enter the project directory path as first argument to the jar file.");
			System.out.println("For example: java -jar MutationInsertion.jar \"path\\to\\parent-dir\\geo-master\"");
			return;
		}

		try {
			// fileChanges - String value format:
			// filePath-className-methodName-statementNumber-mutationClassName
			HashSet<String> fileChanges = new HashSet<String>();

			// components of
			// filePath-className-methodName-statementNumber-mutationClassName format
			String className, methodName;
			int statementCounter;
			ASTVisitor currentVisitor;

			// get all file-paths for Geo project
			List<String> filePaths = GeoProjectInformation.getFileList();

			// used to track change in file
			boolean isChanged = false;

			// iterating mutant project directories
			for (int mutantNo = 1; mutantNo <= maxNumberOfMutants; mutantNo++) {
				// Reset isChanged to false while starting for new project
				isChanged = false;

				GeoProjectInformation.createProjectCopy(mutantNo);

				String currentProjectPath = GeoProjectInformation.projectFolderPrefix + "-" + mutantNo;
				GeoProjectInformation.buildReport("****************************************************************");
				GeoProjectInformation.buildReport("Mutant Project Path: " + currentProjectPath);

				// iterate through files
				for (String filePath : filePaths) {
					if (isChanged) {
						// Previous file changed somewhere, so mutant creation complete.
						// Start with the new project folder after this.
						break;
					}

					// get file content
					String currentFilePath = currentProjectPath + filePath;
					GeoProjectInformation.buildReport(currentFilePath);
					File currentFile = new File(currentFilePath);
					String fileContent = FileUtils.readFileToString(currentFile);

					// create AST
					ASTParser parser = ASTParser.newParser(AST.JLS3);
					parser.setSource(fileContent.toCharArray());
					parser.setKind(ASTParser.K_COMPILATION_UNIT);

					CompilationUnit cu = (CompilationUnit) parser.createAST(null);
					cu.recordModifications();

					// the original document content
					Document document = new Document(fileContent);

					// iterate over all types
					for (int t = 0; t < cu.types().size() & !isChanged; t++) {
						MethodDeclaration methodDeclaration;
						Block block;

						TypeDeclaration typeDeclaration = (TypeDeclaration) cu.types().get(0);
						className = typeDeclaration.getName().toString();
						// iterate over all the methods
						for (int i = 0; i < typeDeclaration.getMethods().length && !isChanged; i++) {
							// Get method declaration and its body
							methodDeclaration = typeDeclaration.getMethods()[i];
							methodName = methodDeclaration.getName().toString();
							block = methodDeclaration.getBody();

							@SuppressWarnings("unchecked")
							List<Statement> lst = block.statements();

							for (statementCounter = 0; statementCounter < lst.size() && !isChanged; statementCounter++) {
								for (int visitorCount = 0; visitorCount < numberOfAllowedMutantVisitor && !isChanged; visitorCount++) {
									currentVisitor = mutantCreators.get(visitorCount);
									if (fileChanges.contains(filePath + "-" + className + "-" + methodName + "-"
											+ statementCounter + "-" + currentVisitor.getClass().toString())) {
										// This statement was mutated in some way previously.
										// So skip this statement.
										continue;
									}

									Statement s = lst.get(statementCounter);

									String beforeToCompare, afterToCompare, beforeToReport, afterToReport;

									int position = s.getStartPosition();

									beforeToCompare = s + " -> at Position " + position;
									beforeToReport = s.toString();

									s.accept(currentVisitor);

									afterToCompare = s + " -> at Position " + position;
									afterToReport = s.toString();

									if (!(beforeToCompare.equals(afterToCompare))) {
										isChanged = true;
										GeoProjectInformation.buildReport();
										GeoProjectInformation.buildReport("Before: " + beforeToReport);
										GeoProjectInformation.buildReport("After: " + afterToReport);
										GeoProjectInformation.buildReport();
										fileChanges.add(filePath + "-" + className + "-" + methodName + "-"
												+ statementCounter + "-" + currentVisitor.getClass().toString());
									}
								} // mutant visitor iteration end
							} // statement iteration end
						} // method iteration end
					} // class iteration end

					// apply edit to document
					TextEdit edits = cu.rewrite(document, null);
					edits.apply(document);

					// replace file content
					// true to append & false to overwrite
					FileWriter fileOverwrite = new FileWriter(currentFile, false);
					fileOverwrite.write(document.get());
					fileOverwrite.close();
				} // end of file loop

				if (!isChanged) {
					// isChanged false means that there was no change in the previous mutant
					// It means that all possible mutants are created
					// There is nothing left to change as per the defined mutation strategy.
					// So delete current project folder as it is same as original project, not a mutant.
					GeoProjectInformation.buildReport();
					GeoProjectInformation
							.buildReport("*** No more changes possible as per defined mutation strategy. ***");
					GeoProjectInformation.buildReport("Deleting " + currentProjectPath + " ...");
					GeoProjectInformation.deleteProjectCopy(mutantNo);
					GeoProjectInformation.buildReport(currentProjectPath + " deleted.");
					GeoProjectInformation
							.buildReport("Mutant creation complete. Please run Powershell script to test.");
					// exit the mutant creation process.
					break;
				}
			} // end of project folder loop
				// save report
			GeoProjectInformation.commitReport();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (MalformedTreeException e) {
			System.out.println(e.getMessage());
		} catch (BadLocationException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception occurred. Please check details.");
			e.printStackTrace();
		}
	}

}