import java.util.ArrayList;
import java.util.Scanner;

// Student Grade Tracker Program
// this program lets you add students and their grades
// and then shows a summary with avg, highest, lowest etc.

public class StudentGradeTracker {

    // arraylists to store student names and their grades
    static ArrayList<String> studentNames = new ArrayList<>();
    static ArrayList<Double> studentGrades = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("========================================");
        System.out.println("    Welcome to Student Grade Tracker");
        System.out.println("========================================");

        // main menu loop
        do {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add a Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Summary Report");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Remove a Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            // handling if user types something thats not a number
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // consume the newline
            } else {
                System.out.println("Please enter a valid number!");
                sc.nextLine();
                choice = 0; // just reset so the loop continues
            }

            // checking what the user picked
            if (choice == 1) {
                addStudent(sc);
            } else if (choice == 2) {
                viewAllStudents();
            } else if (choice == 3) {
                showSummary();
            } else if (choice == 4) {
                searchStudent(sc);
            } else if (choice == 5) {
                removeStudent(sc);
            } else if (choice == 6) {
                System.out.println("\nThanks for using Student Grade Tracker!");
                System.out.println("Goodbye :)");
            } else {
                if (choice != 0) {
                    System.out.println("Invalid choice, try again.");
                }
            }

        } while (choice != 6);

        sc.close();
    }

    // method to add a new student
    public static void addStudent(Scanner sc) {
        System.out.print("\nEnter student name: ");
        String name = sc.nextLine();

        // make sure name is not empty
        if (name.trim().isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }

        double grade = -1;
        System.out.print("Enter grade (0-100): ");

        if (sc.hasNextDouble()) {
            grade = sc.nextDouble();
            sc.nextLine(); // consume newline
        } else {
            System.out.println("Thats not a valid grade!");
            sc.nextLine();
            return;
        }

        // check if grade is in valid range
        if (grade < 0 || grade > 100) {
            System.out.println("Grade must be between 0 and 100!");
            return;
        }

        // add to our arraylists
        studentNames.add(name);
        studentGrades.add(grade);
        System.out.println(name + " added successfully with grade " + grade);
    }

    // method to display all students and grades
    public static void viewAllStudents() {
        if (studentNames.size() == 0) {
            System.out.println("\nNo students added yet!");
            return;
        }

        System.out.println("\n-----------------------------------------");
        System.out.println(String.format("%-5s %-20s %-10s %-10s", "No.", "Name", "Grade", "Letter"));
        System.out.println("-----------------------------------------");

        for (int i = 0; i < studentNames.size(); i++) {
            String letterGrade = getLetterGrade(studentGrades.get(i));
            System.out.println(String.format("%-5d %-20s %-10.1f %-10s",
                    (i + 1), studentNames.get(i), studentGrades.get(i), letterGrade));
        }
        System.out.println("-----------------------------------------");
    }

    // method to show the summary report
    public static void showSummary() {
        if (studentNames.size() == 0) {
            System.out.println("\nNo students to show summary for!");
            return;
        }

        // calculate average
        double total = 0;
        double highest = studentGrades.get(0);
        double lowest = studentGrades.get(0);
        String highestStudent = studentNames.get(0);
        String lowestStudent = studentNames.get(0);

        for (int i = 0; i < studentGrades.size(); i++) {
            double g = studentGrades.get(i);
            total = total + g;

            if (g > highest) {
                highest = g;
                highestStudent = studentNames.get(i);
            }
            if (g < lowest) {
                lowest = g;
                lowestStudent = studentNames.get(i);
            }
        }

        double average = total / studentGrades.size();

        // count how many passed and failed (passing = 40)
        int passCount = 0;
        int failCount = 0;
        for (int i = 0; i < studentGrades.size(); i++) {
            if (studentGrades.get(i) >= 40) {
                passCount++;
            } else {
                failCount++;
            }
        }

        // print the report
        System.out.println("\n=========================================");
        System.out.println("          SUMMARY REPORT");
        System.out.println("=========================================");
        System.out.println("Total Students  : " + studentNames.size());
        System.out.printf("Class Average   : %.2f\n", average);
        System.out.println("Highest Score   : " + highest + " (" + highestStudent + ")");
        System.out.println("Lowest Score    : " + lowest + " (" + lowestStudent + ")");
        System.out.println("Students Passed : " + passCount);
        System.out.println("Students Failed : " + failCount);
        System.out.println("=========================================");

        // grade distribution
        System.out.println("\n--- Grade Distribution ---");
        int aCount = 0, bCount = 0, cCount = 0, dCount = 0, fCount = 0;
        for (int i = 0; i < studentGrades.size(); i++) {
            double g = studentGrades.get(i);
            if (g >= 90) aCount++;
            else if (g >= 80) bCount++;
            else if (g >= 70) cCount++;
            else if (g >= 60) dCount++;
            else fCount++;
        }
        System.out.println("A (90-100) : " + aCount + " students");
        System.out.println("B (80-89)  : " + bCount + " students");
        System.out.println("C (70-79)  : " + cCount + " students");
        System.out.println("D (60-69)  : " + dCount + " students");
        System.out.println("F (Below 60): " + fCount + " students");
    }

    // method to search for a student by name
    public static void searchStudent(Scanner sc) {
        if (studentNames.size() == 0) {
            System.out.println("\nNo students added yet!");
            return;
        }

        System.out.print("\nEnter name to search: ");
        String searchName = sc.nextLine();
        boolean found = false;

        for (int i = 0; i < studentNames.size(); i++) {
            // using equalsIgnoreCase so it doesnt matter if caps or not
            if (studentNames.get(i).equalsIgnoreCase(searchName)) {
                System.out.println("\nStudent Found!");
                System.out.println("Name  : " + studentNames.get(i));
                System.out.println("Grade : " + studentGrades.get(i));
                System.out.println("Letter: " + getLetterGrade(studentGrades.get(i)));
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student \"" + searchName + "\" not found.");
        }
    }

    // method to remove a student
    public static void removeStudent(Scanner sc) {
        if (studentNames.size() == 0) {
            System.out.println("\nNo students to remove!");
            return;
        }

        viewAllStudents(); // show list first so they know the names

        System.out.print("Enter the name of student to remove: ");
        String removeName = sc.nextLine();
        boolean removed = false;

        for (int i = 0; i < studentNames.size(); i++) {
            if (studentNames.get(i).equalsIgnoreCase(removeName)) {
                System.out.println(studentNames.get(i) + " has been removed.");
                studentNames.remove(i);
                studentGrades.remove(i);
                removed = true;
                break;
            }
        }

        if (!removed) {
            System.out.println("Couldnt find student \"" + removeName + "\"");
        }
    }

    // helper method to convert number grade to letter grade
    public static String getLetterGrade(double grade) {
        if (grade >= 90) {
            return "A";
        } else if (grade >= 80) {
            return "B";
        } else if (grade >= 70) {
            return "C";
        } else if (grade >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}
