import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

import com.google.gson.Gson;

public class Main {
    static boolean isValid;
    static Tasks newTask;
    static int counter;
    static Scanner input = new Scanner(System.in);
    static List<Tasks> tasksArrayList = new ArrayList<>();
    static int parsedNumber;

    static Gson gson = new Gson();
    static File saveFile = new File("data.json");
    static FileWriter fileWriter;

    static {
        try (FileReader reader = new FileReader(saveFile)) {
            int content;
            while ((content = reader.read()) != -1) {
                System.out.print((char) content);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            fileWriter = new FileWriter("data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void menuSelection() {
        try {
            System.out.println("      Menu      " +
                    "\n1) Add a task" +
                    "\n2) Remove a task" +
                    "\n3) Update a task" +
                    "\n4) List of tasks" +
                    "\n5) List task by Priority" +
                    "\n0) Exit the program");

            //User input for which menu number they want to select
            System.out.print("Enter a number here: ");
            String usrNumber = input.nextLine();
            isValid = false;
            while (!isValid) {
                try {
                    parsedNumber = parseInt(usrNumber);
                    if (parsedNumber > 5 || parsedNumber < 0) {
                        System.out.println("Enter a number 0-5");
                        usrNumber = input.nextLine();
                    } else {
                        isValid = true;
                    }
                } catch (Exception e) {
                    System.out.println("Enter a number 0-5");
                    usrNumber = input.nextLine();
                }
            }
            switch (parsedNumber) {
                case 1:
                    addTask();
                    counter++;
                    System.out.println();
                    menuSelection();
                    break;
                case 2:
                    if (tasksArrayList.size() == 0) {
                        System.out.println("Please add a task first");
                    } else {
                        removeTask();
                    }
                    System.out.println();
                    menuSelection();
                    break;
                case 3:
                    if (tasksArrayList.size() == 0) {
                        System.out.println("Please add a task first");
                    } else {
                        updateTask();
                    }
                    System.out.println();
                    menuSelection();
                    break;
                case 4:
                    if (tasksArrayList.size() == 0) {
                        System.out.println("Please add a task first");
                    } else {
                        printTasks();
                    }
                    System.out.println();
                    menuSelection();
                    break;
                case 5:
                    if (tasksArrayList.size() == 0) {
                        System.out.println("Please add a task first");
                    } else {
                        printByPriority();
                    }
                    System.out.println();
                    menuSelection();
                    break;
                case 0:
                    //Closes the program
                    System.out.println("Thanks!");

                    String json = gson.toJson(tasksArrayList);
                    try {
                        gson.toJson(tasksArrayList, fileWriter);
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;

            }
        }
        //Catch anything errors that might randomly occur
        catch (Exception e) {
            System.out.println("Something went wrong, please try again.  Error: " + e);
            menuSelection();
        }
    }

    static void addTask() throws IOException {
        System.out.println("What is the title of the task you want to add?: ");
        String titleOfTask = input.nextLine();
        isValid = false;
        while (!isValid) {
            try {
                Double.parseDouble(titleOfTask);
                System.out.println("Please enter a string");
                titleOfTask = input.nextLine();
            } catch (Exception e) {
                isValid = true;
                continue;
            }
        }
        System.out.println("What is the description of the task you want to add?: ");
        String descriptionOfTask = input.nextLine();
        isValid = false;
        while (!isValid) {
            try {
                Double.parseDouble(descriptionOfTask);
                System.out.println("Please enter a string");
                descriptionOfTask = input.nextLine();
            } catch (Exception e) {
                isValid = true;
                continue;
            }
        }
        System.out.println("What is the priority of the task?: ");
        String priorityOfTask = input.nextLine();
        isValid = false;
        while (!isValid) {
            try {
                int a = parseInt(priorityOfTask);
                if (a > 5 || a < 0) {
                    System.out.println("Enter a number 0-5");
                    priorityOfTask = input.nextLine();
                } else {
                    newTask = new Tasks(titleOfTask, descriptionOfTask, a);
                    isValid = true;
                }
            } catch (Exception e) {
                System.out.println("Not a valid input, please enter an Integer");
                priorityOfTask = input.nextLine();
            }
        }
        tasksArrayList.add(newTask);

    }


    static void removeTask() {
        System.out.println("What is the index of the task you would like to remove?: ");
        String removeIndex = input.nextLine();
        boolean isRemoved = false;
        while (!isRemoved) {
            try {
                int parsedInt = parseInt(removeIndex);
                tasksArrayList.remove(parsedInt);
                isRemoved = true;
            } catch (Exception e) {
                System.out.println("Not a valid input");
                System.out.println("What is the index of the task you would like to remove?: ");
                removeIndex = input.nextLine();
            }
        }
    }

    static void updateTask() {
        try {
            System.out.println("what is the index of the task you would like to update?: ");
            String updateIndex = input.nextLine();
            int parseUpdateIndex = parseInt(updateIndex);
            System.out.println("Task title: " + tasksArrayList.get(parseUpdateIndex).getTaskTitle()
                    + " | Task Description: " + tasksArrayList.get(parseUpdateIndex).getTaskDescription()
                    + " | Task Priority: " + tasksArrayList.get(parseUpdateIndex).getTaskPriority());
            System.out.println("What would you like to update?: " +
                    "\n1) Task Title" +
                    "\n2) Task Description" +
                    "\n3) Task Priority");
            String updatePicker = input.nextLine();
            isValid = false;
            while (!isValid) {
                try {
                    parsedNumber = parseInt(updatePicker);
                    if (parsedNumber > 3 || parsedNumber < 1) {
                        System.out.println("not a valid input");
                        System.out.println("Please enter a number 1-3");
                        updatePicker = input.nextLine();
                    } else {
                        isValid = true;
                    }
                } catch (Exception e) {
                    System.out.println("not a valid input");
                    System.out.println("What would you like to update?: " +
                            "\n1) Task Title" +
                            "\n2) Task Description" +
                            "\n3) Task Priority");
                    updatePicker = input.nextLine();
                }
            }
            switch (parsedNumber) {
                case 1:
                    System.out.println("What is the new title of the task?: ");
                    String updateTitle = input.nextLine();
                    isValid = false;
                    while (!isValid) {
                        try {
                            Double.parseDouble(updateTitle);
                            System.out.println("Please enter a string");
                            updateTitle = input.nextLine();
                        } catch (Exception e) {
                            tasksArrayList.get(parseUpdateIndex).setTaskTitle(updateTitle);
                            isValid = true;
                            continue;
                        }
                    }
                    break;
                case 2:
                    System.out.println("What is the new description of the task?: ");
                    String updateDescription = input.nextLine();
                    isValid = false;
                    while (!isValid) {
                        try {
                            Double.parseDouble(updateDescription);
                            System.out.println("Please enter a string");
                            updateDescription = input.nextLine();
                        } catch (Exception e) {
                            tasksArrayList.get(parseUpdateIndex).setTaskDescription(updateDescription);
                            isValid = true;
                            continue;
                        }
                    }
                    break;
                case 3:
                    System.out.println("What is the priority of the task?: ");
                    String updatePriority = input.nextLine();
                    isValid = false;
                    while (!isValid) {
                        try {
                            int a = parseInt(updatePriority);
                            if (a > 5 || a < 0) {
                                System.out.println("Enter a number 0-5");
                                updatePriority = input.nextLine();
                            } else {
                                tasksArrayList.get(parseUpdateIndex).setTaskPriority(a);
                                isValid = true;
                            }
                        } catch (Exception e) {
                            System.out.println("Not a valid input, please enter an Integer");
                            updatePriority = input.nextLine();
                        }
                    }
            }
        } catch (Exception e) {
            System.out.println("Not a valid input, please enter an index number");
            updateTask();
        }
    }

    static void printTasks() {
        Collections.sort(tasksArrayList);
        for (int i = 0; i < tasksArrayList.size(); i++) {
            System.out.println("Index: " + i
                    + " | Task title: " + tasksArrayList.get(i).getTaskTitle()
                    + " | Task Description: " + tasksArrayList.get(i).getTaskDescription()
                    + " | Task Priority: " + tasksArrayList.get(i).getTaskPriority());
        }
    }

    static void printByPriority() {
        System.out.println("What is the priority you want to look up?: ");
        String priorityNum = input.nextLine();
        isValid = false;
        while (!isValid) {
            try {
                parsedNumber = parseInt(priorityNum);
                if (parsedNumber > 5 || parsedNumber < 0) {
                    System.out.println("not a valid input");
                    System.out.println("Please enter a priority 0-5");
                    priorityNum = input.nextLine();
                } else {
                    isValid = true;
                }
            } catch (Exception e) {
                System.out.println("not a valid input");
                System.out.println("What is the priority you want to look up?: ");
                priorityNum = input.nextLine();
            }
        }
        for (int i = 0; i < tasksArrayList.size(); i++) {
            int priorityToMatch = tasksArrayList.get(i).getTaskPriority();
            if (priorityToMatch == parsedNumber) {
                System.out.println("Index: " + i
                        + " | Task title: " + tasksArrayList.get(i).getTaskTitle()
                        + " | Task Description: " + tasksArrayList.get(i).getTaskDescription()
                        + " | Task Priority: " + tasksArrayList.get(i).getTaskPriority());
            }
        }
    }


    public static void main(String[] args) {
        menuSelection();
    }


}