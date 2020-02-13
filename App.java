import java.util.*;

class App {

  public static void display(Collection c, String name) {

    System.out.println("\n\n");
    System.out.println(name);
    System.out.println("\n\n");
    c.forEach(System.out::println);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    ArrayList<Integer> totalArrayList = new ArrayList<Integer>();
    ArrayList<Integer> availableArrayList = new ArrayList<Integer>();
    ArrayList<ArrayList<Integer>> allocationArrayList = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> maximumNeedArrayList = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> needArrayList = new ArrayList<ArrayList<Integer>>();

    System.out.println("\nEnter the number of resources : \n");
    int numberOfResource = sc.nextInt();
    System.out.println("\nEnter the total amount of resources available : \n");
    System.out.println("\nEnter the value one by one : \n");
    for (int i = 0; i < numberOfResource; i++) {
      System.out.println("\nEnter the value for Resource " + (i + 1));
      System.out.println();
      totalArrayList.add(sc.nextInt());
    }
    System.out.println("\nEnter the number of processes: \n");
    int numberOfProcess = sc.nextInt();
    System.out.println("\nEnter the allocated resources for each process : \n");
    for (int i = 0; i < numberOfProcess; i++) {
      System.out.println("\n\n\nProcess " + (i + 1));
      System.out.println();
      ArrayList<Integer> resources = new ArrayList<Integer>();
      for (int j = 0; j < numberOfResource; j++) {
        System.out.println("\nEnter the value for Resource " + (j + 1));
        System.out.println();
        resources.add(sc.nextInt());
      }
      allocationArrayList.add(resources);
    }

    System.out.println("\nEnter the maximum need of resources for each process : \n");
    for (int i = 0; i < numberOfProcess; i++) {
      System.out.println("\n\nProcess " + (i + 1));
      System.out.println();
      ArrayList<Integer> resources = new ArrayList<Integer>();

      for (int j = 0; j < numberOfResource; j++) {
        System.out.println("\nEnter the value for Resource " + (j + 1));
        System.out.println();
        resources.add(sc.nextInt());
      }
      maximumNeedArrayList.add(resources);
    }

    // initialising the need

    for (int i = 0; i < numberOfProcess; i++) {
      ArrayList<Integer> resources = new ArrayList<Integer>();
      for (int j = 0; j < numberOfResource; j++) {
        int mNeed = maximumNeedArrayList.get(i).get(j);
        int allo = allocationArrayList.get(i).get(j);
        int tot = totalArrayList.get(j);
        resources.add(mNeed - allo);
      }
      needArrayList.add(resources);
    }

    // initialising the available

    for (int j = 0; j < numberOfResource; j++) {
      availableArrayList.add(0);
    }

    for (int i = 0; i < numberOfProcess; i++) {
      for (int j = 0; j < numberOfResource; j++) {
        int allo = allocationArrayList.get(i).get(j);
        int get = availableArrayList.get(j);
        availableArrayList.set(j, get + allo);
      }
    }

    for (int j = 0; j < numberOfResource; j++) {
      int get = availableArrayList.get(j);
      availableArrayList.set(j, totalArrayList.get(j) - get);
    }

    display(totalArrayList, "Total");
    display(allocationArrayList, "Allocation");
    display(maximumNeedArrayList, "Maximum Need");
    display(needArrayList, "Need");
    display(availableArrayList, "Available");

    // checking for possibilities
    System.out.println("\nProcess execution: \n");
    int i = 0;
    int flag = 0;
    while (numberOfProcess != 0) {
      if (flag == numberOfProcess) {
        System.out.println("\nSafe state has been reached !\n\n");
        return;
      }

      if (i == numberOfProcess)
        i = 0;

      
      int count = 0;
      for (int j = 0; j < numberOfResource; j++) {

        if (needArrayList.get(i).get(j) == -1) {
          break;
        }

        if (availableArrayList.get(j) < needArrayList.get(i).get(j)) {
          break;
        }

        if (availableArrayList.get(j) >= needArrayList.get(i).get(j)) {
          count++;
        }

      }

      if (count == numberOfResource) {
        System.out.println("\nProcess " + (i + 1) + " gets executed! ");
        for (int j = 0; j < numberOfResource; j++) {
          int get = availableArrayList.get(j);
          availableArrayList.set(j, get + allocationArrayList.get(i).get(j));
          needArrayList.get(i).set(j, -1);
        }
        display(availableArrayList, "New Available After Process " + (i + 1));
        flag++;
      }

      i++;
    }

  }
}