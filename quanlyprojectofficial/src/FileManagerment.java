import java.io.DataOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.FileNotFoundException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Scanner;

/**
 * FileManagerment class is created to handle Java Project through project's
 * path (String) in your computer (local) and paths direct to files
 * 
 * You can check a path direct to java project or not by isJavaProject(String
 * path) method, return true if it is.
 * 
 * 
 * 
 * @author Phan Vu Nguyen Hoang - K65A4
 * @version 1.0
 * @since 2021-12-7
 */
public class FileManagerment {

    public static String projectPath; // path of project in local filesystem

    // return String[] is parts of path
    public static String[] getPartsOfPath(String path) {
        // Slit path input
        String[] parts = path.split("\\\\");

        return parts;
    }

    // Tra ve String[] la ten toan bo THU MUC trong Path
    public static String[] getListDir(String path) {
        // Check path is null or not
        if (!(new File(path).isDirectory())) {
            return null;
        }

        // declare variables
        File file = new File(path);

        // Get array contains names of directories, use Interface FilenameFilter to
        // check is directory or not
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        //
        return directories;
    }

    // Tra ve String[] la ten toan bo File trong Path
    public static String[] getListFile(String path) {
        // Check path is null or not
        if (!(new File(path).isDirectory())) {
            return null;
        }

        // declare variables
        File file = new File(path);

        // Get array contains names of files, use Interface FilenameFilter to check is
        // File or not
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return !(new File(current, name).isDirectory());
            }
        });

        //
        return directories;
    }

    // Tra ve String[] la ten toan bo THU MUC va FILE trong Path
    public static String[] getAll(String path) {
        // Check path is null or not
        if (!(new File(path).isDirectory())) {
            return null;
        }

        // Declare variables
        File file = new File(path);

        // listing all files and directories in path
        String[] directories = file.list();

        //
        return directories;
    }

    // return String is previous path
    public static String getPreviousPath(String path) {
        if (path.contains("\\")) { // Check is Path or not
            String[] parts = path.split("\\\\"); // add 4 \\\\
            String previousPath = ""; // the result is previous path

            // use for-loop to add parts into the result path, except last path
            for (int i = 0; i < parts.length - 1; i++) {
                if (i == parts.length - 2)
                    previousPath = previousPath + parts[i];
                else
                    previousPath = previousPath + parts[i] + "\\";
            }

            return previousPath; // return previous path
        }
        return null; // if is not path return null
    }

    // return String is path by index of file(dir) in current path
    public static String getPathByIndex(String path, int index, int status) {
        String newPath = ""; // result

        if (status == 0) { // status = 0 <=> all files and dirs
            if (index > getAll(path).length || index < 0) { // Handle invalid input
                return null;
            }
            newPath = path + "\\" + getAll(path)[index - 1]; // get newpath by add File or Folder name after current
                                                             // path with "//"
        } else if (status == 1) { // status = 1 <=> all dirs
            if (index > getListDir(path).length || index < 0) { // Handle invalid input
                return null;
            }
            newPath = path + "\\" + getListDir(path)[index - 1]; // get newpath by add File or Folder name after current
                                                                 // path with "//"
        } else if (status == 2) { // status = 2 <=> all files
            if (index > getListFile(path).length || index < 0) { // Handle invalid input
                return null;
            }
            newPath = path + "\\" + getListFile(path)[index - 1]; // get newpath by add File or Folder name after
                                                                  // current path with "//"
        } else { // Exception
            return null;
        }

        return newPath; // return new path

    }

    // get Package path of file .class
    public static String getPackagePathOfFileDocClass(String path) {
        if (!isDocClassFile(path))
            return null;

        // Decalre variables
        File file = new File(path);
        String[] nameAndEx = file.getName().split("\\.");

        String packagePath = "";
        String[] parts = getPartsOfPath(path);

        // Get package
        int binIdx = -1;
        for (int i = 0; i < parts.length - 1; i++) {
            if (parts[i].equals("bin"))
                binIdx = i;
            if (binIdx != -1 && i > binIdx)
                packagePath += parts[i] + ".";
        }

        packagePath += nameAndEx[0];

        //
        return packagePath;
    }

    // return String is last modification time
    public static String getLastModifiedTime(String path) {
        // Declare variables
        Path PATH = Paths.get(path);
        FileTime fileTime;

        try {
            //
            fileTime = Files.getLastModifiedTime(PATH);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");

            //
            return dateFormat.format(fileTime.toMillis());
        } catch (IOException e) {
            // To do handle Exception
            System.out.println("Cannot get the last modified time - " + e);

            //
            return null;
        }
    }

    // return String is permissions of file and folder
    public static String getPermissions(String path) {
        // Declare variables
        String permissions = "";
        File myFile = new File(path);

        //
        if (!myFile.exists())
            return null;

        //
        if (myFile.canRead())
            permissions += "r";
        else
            permissions += "-";

        if (myFile.canWrite())
            permissions += "w";
        else
            permissions += "-";

        if (myFile.canExecute())
            permissions += "x";
        else
            permissions += "-";

        //
        return permissions;
    }

    // In ra danh sach cac file va dir co trong path
    public static void listingAll(String path) {
        // Declare variables
        String[] items = getAll(path);

        //
        if (items == null) // Check If items or path is null
            System.out.println("Invalid path!!");
        else { // Else then print files in path
            System.out.println(" | \t" + "Permission" + "\t" + "Last modified time" + "\t" + "Name");

            // for-loop used to print list
            for (int i = 0; i < items.length; i++) {
                // Declare variables
                String part = path + "\\" + items[i];
                String lastModifiedTime = getLastModifiedTime(part);
                String permissions = getPermissions(part);

                // print
                System.out.println((i + 1) + "|\t" + permissions + "\t\t" + lastModifiedTime + "\t" + items[i]);
            }
        }
    }

    // In ra danh sach cac file co trong path
    public static void listingFiles(String path) {
        // Declare variables
        String[] items = getListFile(path); // array contains paths

        //
        if (items == null || path == null) // Check If items or path is null
            System.out.println("Invalid path!!");
        else { // Else then print files in path
            System.out.println(" | \t" + "Permission" + "\t" + "Last modified time" + "\t" + "Name");

            // for-loop used to print list
            for (int i = 0; i < items.length; i++) {
                // Declare variables
                String part = path + "\\" + items[i];
                String lastModifiedTime = getLastModifiedTime(part);
                String permissions = getPermissions(part);

                // print
                System.out.println((i + 1) + "|\t" + permissions + "\t\t" + lastModifiedTime + "\t" + items[i]);
            }
        }
    }

    // In ra danh sach cac thu muc co trong path
    public static void listingDirs(String path) {
        // Declare variables
        String[] items = getListDir(path);

        //
        if (items == null) // Check If items or path is null
            System.out.println("Invalid path!!");
        else { // Else then print dirs in path
            System.out.println(" | \t" + "Permission" + "\t" + "Last modified time" + "\t" + "Name");

            // for-loop used to print
            for (int i = 0; i < items.length; i++) {
                // Declare variables
                String part = path + "\\" + items[i];
                String lastModifiedTime = getLastModifiedTime(part);
                String permissions = getPermissions(part);

                // print
                System.out.println((i + 1) + "|\t" + permissions + "\t\t" + lastModifiedTime + "\t" + items[i]);
            }
        }
    }

    // Print content of file
    public static void readFile(String path) {
        try {
            // Declare variables
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            // while loop to read file contents
            while (scanner.hasNextLine())
                System.out.println(scanner.nextLine()); // Print line if hasNextLine

            scanner.close(); // close scanner after read file
        } catch (FileNotFoundException e) {
            // To do handle Exception
            System.out.println("Invalid path of file!");
        } catch (NullPointerException e) {
            // To do handle Exception
            System.out.println("Path not found!");
        }
    }

    // Check is java project or not, return true if path has the bin folder, false
    // if not
    public static boolean isJavaProject(String path) {
        // Declare variables
        String[] allItems = getAll(path); // Get all file and dirs name of the path's project
        boolean hasBin = false; // check has bin folder or not
        boolean hasSrc = false; // check has src folder or not

        // if used to check null
        if (allItems == null) { // Check null
            return false;
        }

        // use for loop to check has bin folder or not
        for (String item : allItems) {
            if (item.equals("bin"))
                hasBin = true;
            if (item.equals("src"))
                hasSrc = true;
        }

        //
        return hasBin && hasSrc;
    }

    // Check is file .class or not
    public static boolean isDocClassFile(String path) {
        if (path == null)
            return false; //

        File docClassFile = new File(path); // get File

        // Check Exception
        if (!docClassFile.exists()) // Check exists or not
            return false;
        if (docClassFile.isDirectory()) // Check is file or not
            return false;

        String fileName = docClassFile.getName();// get file name
        String[] sepByDoc = fileName.split("\\."); // seperates file by doc, check exception like abc.class.txt

        if (sepByDoc.length == 0 | sepByDoc == null)
            return false;

        if (sepByDoc[sepByDoc.length - 1].equals("class")) // if has last item is class, return true
            return true;

        //
        return false;
    }

    // (OVERLOAD method) run file .class without args
    public static void runFile(String path) throws IOException {
        if (path == null) {
            System.out.println("Path is invalid!");
        } else if (isDocClassFile(path)) { // check is .class or not
            // Declare variables
            String command = "java -classpath " + projectPath + "\\bin\\ " + getPackagePathOfFileDocClass(path); // command
                                                                                                                 // run
                                                                                                                 // file
                                                                                                                 // .class
            Runtime runTime = Runtime.getRuntime(); // get Runtime object
            Process process = runTime.exec(command); // Executes the specified string command in a separate process with
                                                     // the specified environment and working directory.

            InputStream inputStream = process.getInputStream();// Read binary
            InputStreamReader isr = new InputStreamReader(inputStream); // Read char, create pipline from InputStream =>
                                                                        // InputStreamReader, we use InputStreamReader
                                                                        // read char

            // handle String buffer
            int n1;
            char[] c1 = new char[1024];
            StringBuffer standardOutput = new StringBuffer();

            while ((n1 = isr.read(c1)) > 0) {
                standardOutput.append(c1, 0, n1); // (char[], offset, len) Appends the string representation of a
                                                  // subarray of the char array argument to this sequence.
            }

            System.out.println(standardOutput.toString()); // standardOutput.toString() Returns a string representing
                                                           // the data in this sequence
        } else { // announce if not
            System.out.println("Vui long chay file co extension la .class");
        }
    }

    // (OVERLOAD method) run file .class with args pass through file .class
    // (subprocess)
    public static void runFile(String path, String[] args) throws IOException {
        if (isDocClassFile(path)) { // check is .class or not
            // Declare variables
            String command = "java -classpath " + projectPath + "\\bin\\ " + getPackagePathOfFileDocClass(path); // command
                                                                                                                 // run
                                                                                                                 // file
                                                                                                                 // .class
            Runtime runTime = Runtime.getRuntime(); // get Runtime object
            Process process = runTime.exec(command); // Executes the specified string command in a separate process with
                                                     // the specified environment and working directory.

            InputStream inputStream = process.getInputStream();// Read binary
            InputStreamReader isr = new InputStreamReader(inputStream); // Read char, create pipline from InputStream =>
                                                                        // InputStreamReader, we use InputStreamReader
                                                                        // read char
            // OutputStream for write
            OutputStream fos = process.getOutputStream();
            DataOutputStream dos = new DataOutputStream(fos);

            //
            for (int i = 0; i < args.length; i++) {
                dos.writeBytes(args[i]);
                dos.writeBytes(System.lineSeparator());
            }

            // flush and close the stream, end pass value process
            dos.flush();
            dos.close();

            // handle String buffer
            int n1;
            char[] c1 = new char[1024];
            StringBuffer standardOutput = new StringBuffer();

            while ((n1 = isr.read(c1)) > 0) {
                standardOutput.append(c1, 0, n1); // (char[], offset, len) Appends the string representation of a
                                                  // subarray of the char array argument to this sequence.
            }

            System.out.println(standardOutput.toString()); // standardOutput.toString() Returns a string representing
                                                           // the data in this sequence
        } else { // announce if not
            System.out.println("Vui long chay file co extension la .class");
        }
    }
}
