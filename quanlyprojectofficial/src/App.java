import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App {

    // show tutorial for user
    public static void showTutorial() {
        System.out.println(
                "MAIN MENU-Lua chon 1: Nhap vao duong dan den mot project java tren may cua ban de xem cac file va thu muc ben trong do.");
        System.out.println("Vi du: E:\\Code\\java\\ctppmhw");
        System.out.println("** Thu muc `bin` cua project la noi luu tru cac file .class, ");
        System.out.println("di chuyen den do de chay cac chuong trinh trong project cua ban.");
        System.out.println("LUU Y: Cac doi so duoc nhap cach nhau boi mot dau cach.");
        System.out.println("** Thu muc `src` cua project la noi luu tru cac file .java, ");
        System.out.println("di chuyen den do de xem code cua ban.");
        System.out.println();
        System.out.println("Nhan phim Enter de tiep tuc...");
    }

    // exit program method
    public static void exitProgram() {
        System.out.println("Thoat chuong trinh.....");
        System.exit(0); // Exit program with status = 0(NORMAL EXIT)
    }

    // Di den mot folder duoc chi dinh (use Recursive)
    public static void toFolder(String path, int status) throws IOException {
        clrscr(); // clear console

        // Print the menu
        System.out.println("Current path:" + path);// Print path of project
        System.out.println("----------------------------------------------------------------------------");

        //
        Scanner sc = new Scanner(System.in);// scanner used to input from keyboard

        // If-else used to handle the path which is user input
        if (path == null)
            System.out.println("Vui long nhap dung thu muc!"); // Print notification for an exception
        else if (!(new File(path).isDirectory())) // check path is Directory or not
            System.out.println("Vui long nhap dung thu muc!"); // Print notification for an exception
        else {
            String previousPath = FileManagerment.getPreviousPath(path);// get previous path

            // Handle status, default is 0
            if (status == 0)// status = 0 => show all files and directories
                FileManagerment.listingAll(path); // Listing all files and dirs in path
            else if (status == 1) // status = 1 => show all directories
                FileManagerment.listingDirs(path);// Listing all dirs in path
            else if (status == 2) // status = 2 => show all files
                FileManagerment.listingFiles(path); // Listing all files in path
            else // default is 0
                FileManagerment.listingAll(path); // If invalid status typed, status back to 0

            // Show menu
            System.out.println("-THAO TAC VOI FILE VA THU MUC-----------------------------------------------");
            System.out.println("1.Xem noi dung file");
            System.out.println("2.Chay file (.class)");
            System.out.println("-HIEN THI--------------------------------------------------------------------");
            System.out.println("3.Hien thi tat ca thu muc trong duong dan hien tai");
            System.out.println("4.Hien thi tat ca cac file trong duong dan hien tai");
            System.out.println("5.Hien thi tat ca tep tin va thu muc co trong duong dan hien tai");
            System.out.println("-LUA CHON KHAC---------------------------------------------------------------");
            System.out.println("6.Quay ve thu muc truoc");
            System.out.println("7.Di den thu muc con");
            System.out.println("8.Ve main menu");
            System.out.println("0.Thoat chuong trinh");

            // User enter the choose
            System.out.print("Moi ban nhap lua chon:\n");
            String c2 = sc.nextLine(); // Enter your selection

            // if-else if - else to handle user's choose
            if (c2.equals("1")) {// SELECTION 1 : read file
                System.out.print("Nhap stt file muon xem:");

                //
                int number;
                String newPath;

                //
                while (true) {
                    try {
                        number = Integer.parseInt(sc.nextLine());
                        newPath = FileManagerment.getPathByIndex(path, number, status);

                        break;
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("Invalid input! Moi ban nhap lai!");
                        System.out.print("Nhap stt file muon xem:");
                    }
                }

                clrscr();// clear screen

                FileManagerment.readFile(newPath); // read the file

                sc.nextLine(); // pause program

                toFolder(path, 0);// back to current folder
            } else if (c2.equals("2")) { // SELECTION 2 : Run file .class
                // Anotate user input index file want to run
                System.out.print("Nhap stt file muon chay (.class file):");

                //
                int number;
                String newPath;
                while (true) {
                    try {
                        number = Integer.parseInt(sc.nextLine());
                        newPath = FileManagerment.getPathByIndex(path, number, status);

                        break;
                    } catch (Exception e) {
                        System.out.println("Moi ban nhap lai!!");
                        System.out.print("Nhap stt file muon chay (.class file):");
                    }
                }

                clrscr(); // clear screen

                // User enter args
                System.out.println(
                        "Nhap doi so muon truyen vao cho file(neu co, cac doi so cach nhau boi khoang trang [space]): \n"
                                + newPath + "\n");
                String argsStr = sc.nextLine(); //

                String[] args = null; // String array used to save agurment

                if (argsStr != null) {
                    args = argsStr.split("\\s+"); // get args from user's input
                    FileManagerment.runFile(newPath, args);
                } else {// no args input
                    FileManagerment.runFile(newPath);
                }

                sc.nextLine();// Pause program
                toFolder(path, status); // Back to current folder
            } else if (c2.equals("3")) { // SELECTION 3: Show all dirs, hide all files
                toFolder(path, 1);
            } else if (c2.equals("4")) { // SELECTION 4: Show all files, hide all dirs
                toFolder(path, 2);
            } else if (c2.equals("5")) { // SELECTION 5:
                toFolder(path, 0);
            } else if (c2.equals("6")) { // SELECTION 6: return previous folder
                if (previousPath != null) {
                    clrscr(); // clear console
                    toFolder(previousPath, 0); // go to previous path
                } else {
                    clrscr(); // clear console
                    System.out.println("Khong tim thay thu muc truoc do!"); // print notification
                }
            } else if (c2.equals("7")) { // SELECTION 7: Go to subdirectory

                int number;
                String newPath;

                //
                while (true) {
                    try {
                        System.out.print("Nhap stt thu muc muon di den:");
                        number = Integer.parseInt(sc.nextLine());
                        newPath = FileManagerment.getPathByIndex(path, number, status);

                        break;
                    } catch (Exception e) {
                        // To do handle Exception
                        System.out.println("Invalid input!");
                    }
                }

                clrscr(); // clear console

                // if-else
                if (newPath == null) {
                    System.out.println("Vui long nhap dung thu muc!");

                    sc.nextLine();

                    toFolder(path, 0);
                } else if (!(new File(newPath).isDirectory())) {
                    System.out.println("Vui long nhap dung thu muc!");

                    sc.nextLine();

                    toFolder(path, 0);
                } else {
                    toFolder(newPath, 0);
                }
            } else if (c2.equals("8")) {// SELECTION 8: return main menu
                mainMenu();
            } else if (c2.equals("0")) { // SELECTION 0: invoke exitProgram()
                sc.close();
                exitProgram();
            } else { // Enter again if invalid input
                System.out.println("Invalid Input!! Moi ban nhap lai!!");
                toFolder(path, 0);
            }
        }
    }

    // clear screen method
    public static void clrscr() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // run main menu
    public static void mainMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        // while-loop xu ly main menu
        while (true) {
            clrscr(); // clear console

            // Print menu
            System.out.println("-----------CHUONG TRINH QUAN LY PROJECT JAVA--------------");
            System.out.println("1.Nhap duong dan den mot Project");
            System.out.println("2.Huong dan su dung");
            System.out.println("0.Thoat chuong trinh");

            // User enter a choose
            System.out.println("Nhap lua chon cua ban: ");
            String c1 = sc.nextLine();

            // if-else used to handle chooses
            if (c1.equals("0")) { // CHOOSE 0: Exit program
                sc.close(); // close the scanner

                exitProgram(); // invoke exitProgram method
            } else if (c1.equals("1")) { // CHOOSE 1: Go to the project's path
                clrscr(); // clear console

                // user enter project's path
                System.out.println("Nhap duong dan project cua ban: ");
                String path = sc.nextLine();

                // if-else to handle path which user enter
                if (FileManagerment.isJavaProject(path)) { // check if path is the way to java project
                    clrscr(); // clear console

                    FileManagerment.projectPath = path;// save the path

                    toFolder(path, 0); // go to the project folder.
                } else {
                    System.out.println("Vui long nhap vao duong dan mot project!!"); // Show anounce to user

                    sc.nextLine(); // used to pause program
                }
            } else if (c1.equals("2")) { // show tutorial
                clrscr(); // clear screen

                showTutorial(); // invoke showTutorial() method

                sc.nextLine(); // used to pause program
            } else { // Handle Invalid input
                clrscr(); // clear console's screen
                System.out.println("Moi ban nhap lai! ");
            }
        }
    }

    // Main METHOD
    public static void main(String[] args) throws Exception {
        mainMenu(); // run mainMenu method
    }
}
