package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    static double balance = 0;
    static ArrayList<String> list = new ArrayList<>();
    static PurchaseType<String> food, clothes, entertainment, other, all;
    static File file = new File("C:\\Users\\madhu\\Desktop\\JavaPrograms\\JAVA\\Budget Manager1\\Budget Manager\\task\\purchases.txt");
    static double balanceBeforeSaving = 0;

    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        String menu = "Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit";
        String purchaseType = "\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back";
        String showPurhaseList = "\nChoose the type of purchases\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) All\n" +
                "6) Back";
        String sortOptions = "How do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back";
        boolean exit = false;
        while (!exit) {
            //System.out.println();
            System.out.println(menu);

            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    addIncome(scanner);
                    break;
                case 2:
                    boolean back = false;
                    while (!back) {
                        System.out.println(purchaseType);
                        int purchaseTypeNumber = scanner.nextInt();
                        System.out.println();

                        switch (purchaseTypeNumber) {
                            case 1:
                                if (food == null) {
                                    food = new PurchaseType<>();
                                }
                                food.readProductnPrice();
                                break;
                            case 2:
                                if (clothes == null) {
                                    clothes = new PurchaseType<>();
                                }

                                clothes.readProductnPrice();
                                break;
                            case 3:
                                if (entertainment == null) {
                                    entertainment = new PurchaseType<>();
                                }
                                entertainment.readProductnPrice();
                                break;
                            case 4:
                                if (other == null) {
                                    other = new PurchaseType<>();
                                }
                                other.readProductnPrice();
                                break;
                            case 5:
                                back = true;
                                break;
                        }
                    }

                    break;
                case 3:
                    boolean quit = false;
                    while (!quit) {

                        System.out.println(showPurhaseList);
                        int showPurchaseListNumber = scanner.nextInt();
                        System.out.println();
                        switch (showPurchaseListNumber) {
                            case 1:
                                showList("Food:", food);

                                break;
                            case 2:
                                showList("Clothes:", clothes);
                                break;
                            case 3:
                                showList("Entertainment:", entertainment);
                                break;
                            case 4:
                                showList("Other:", other);
                                break;
                            case 5:

                                System.out.println("All:");
                                PurchaseType.allList.forEach((name, price) -> {
                                    System.out.printf("%s $%.2f\n", name.trim(), price);
                                });
                                System.out.printf("Total sum: $%.2f\n", PurchaseType.allProductsTotal);
                                break;
                            case 6:
                                quit = true;
                                break;
                        }
                    }

                    break;
                case 4:
                    balance = balance - PurchaseType.allProductsTotal;
                    System.out.printf("balance: $%.2f\n\n", balance);
                    break;
                case 5:
                    System.out.println("nothing");
                    //File file = new File("purchases.txt");

                    //FileWriter printWriter = new FileWriter(file,true);
                    try (PrintWriter printWriter = new PrintWriter(file)) {

                        //printWriter.print("balance\n" + (balance - PurchaseType.allProductsTotal) + "\n");
                        printWriter.print("balance\n" + (balance) + "\n");
                        saveList("Food", food, printWriter);
                        saveList("Clothes", clothes, printWriter);
                        saveList("Entertainment", entertainment, printWriter);
                        saveList("Other", other, printWriter);
                        System.out.println("Purchases were saved!");
                        printWriter.write("");
                        printWriter.flush();
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }


                    break;
                //break;
                case 6:
                    File fileRead = new File("C:\\Users\\madhu\\Desktop\\JavaPrograms\\JAVA\\Budget Manager1\\Budget Manager\\task\\purchases.txt");
                    loadPurchases(fileRead);
                    System.out.println("Purchases were loaded!\n");
                    break;
                case 7:
                    boolean quitFromSort = false;
                    while (!quitFromSort) {
                        System.out.println(sortOptions);

                        int sortOption = scanner.nextInt();

                        switch (sortOption) {
                            case 1://sort all purchases
                                System.out.println();
                                if (isNull(PurchaseType.allList.isEmpty())) break;
                                SortedMap<String, Double> map = new TreeMap<>(PurchaseType.getAllList());
                                //System.out.println();
                                System.out.println("All:");
                                sort(map);
                                System.out.printf("Total: $%.2f\n", PurchaseType.allProductsTotal);
                                System.out.println();

                                break;
                            case 2:
                                // TODO: 15/09/2020 sort by type
                                System.out.println();
                                System.out.println("Types:");
                                SortedMap<String, Double> categoryMap = new TreeMap<>();
                                if (food != null) {
                                    categoryMap.put("Food", food.getTotalPrice());
                                }
                                categoryMap.putIfAbsent("Food", 0.0);
                                if (clothes != null) {
                                    categoryMap.put("Clothes", clothes.getTotalPrice());
                                }
                                categoryMap.putIfAbsent("Clothes", 0.0);
                                if (entertainment != null) {
                                    categoryMap.put("Entertainment", entertainment.getTotalPrice());
                                }
                                categoryMap.putIfAbsent("Entertainment", 0.0);
                                if (other != null) {
                                    categoryMap.put("Other", other.getTotalPrice());
                                }
                                categoryMap.putIfAbsent("Other", 0.0);
                                ValueComparator bvc = new ValueComparator(categoryMap);
                                TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
                                //PurchaseType.getAllList().forEach((key, value) -> sorted_map.put(key, value));
                                sorted_map.putAll(categoryMap);
                                sorted_map.forEach((key, value) -> System.out.printf("%s - $%.2f\n", key, value));

                                System.out.printf("Total sum: $%.2f\n\n", PurchaseType.allProductsTotal);
                                break;
                            case 3:
                                // TODO: 15/09/2020 sort certain type
                                System.out.println("\nChoose the type of purchase\n" +
                                        "1) Food\n" +
                                        "2) Clothes\n" +
                                        "3) Entertainment\n" +
                                        "4) Other");
                                int sortCatType = scanner.nextInt();
                                System.out.println();
                                switch (sortCatType) {
                                    case 1:
                                        if (isNull(food == null)) break;
                                        System.out.println("Food:");
                                        sort(new TreeMap<>(food.purchaseList));
                                        System.out.printf("Total: $%.2f\n\n", food.getTotalPrice());
                                        break;
                                    case 2:
                                        if (isNull(clothes == null)) break;
                                        System.out.println("Clothes:");
                                        sort(new TreeMap<>(clothes.purchaseList));
                                        System.out.printf("Total: $%.2f\n\n", clothes.getTotalPrice());
                                        break;
                                    case 3:
                                        if (isNull(entertainment == null)) break;
                                        System.out.println("Entertainment:");
                                        sort(new TreeMap<>(entertainment.purchaseList));
                                        System.out.printf("Total: $%.2f\n\n", entertainment.getTotalPrice());
                                        break;
                                    case 4:
                                        if (isNull(other == null)) break;
                                        System.out.println("Other:");
                                        sort(new TreeMap<>(other.purchaseList));
                                        System.out.printf("Total: $%.2f\n\n", other.getTotalPrice());
                                        break;
                                }

                                break;
                            case 4:
                                System.out.println();
                                quitFromSort = true;
                                break;
                        }
                    }
                    break;

                case 0:
                    exit = true;
                    System.out.println("Bye!");
                    break;


            }
        }

    }

    private static boolean isNull(boolean b) {
        if (b) {
            System.out.println("Purchase list is empty!\n");
            return true;
        }
        return false;
    }

    private static void sort(SortedMap<String, Double> map) {
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
        //PurchaseType.getAllList().forEach((key, value) -> sorted_map.put(key, value));
        sorted_map.putAll(map);

        //System.out.println("All:");
        sorted_map.forEach((key, value) -> System.out.printf("%s$%.2f\n", key, value));

    }

    private static void loadPurchases(File fileRead) {
        try {
            Scanner read = new Scanner(fileRead);

            while (read.hasNext()) {

                String firstWord = read.nextLine();
                //System.out.println(firstWord);
                if (firstWord.equals("balance")) {
                    //balanceBeforeSaving = (Double.parseDouble(read.nextLine()));
                    balance = (Double.parseDouble(read.nextLine()));
                    //System.out.println("balance agter loading " + balance);
                    continue;
                }
                String nextString = read.nextLine();
                int indexOf$ = nextString.lastIndexOf('$');
                // String[] keyValueSet = read.nextLine().split("\\$");

                String s = nextString.substring(0, indexOf$);
                double p = Double.parseDouble(nextString.substring(indexOf$ + 1));

                switch (firstWord) {
                    case "Food":
                        if (food == null) {
                            food = new PurchaseType<>();
                        }
                        readFromFiletoCategory(p, s, food);
                        break;
                    case "Clothes":
                        if (clothes == null) {
                            clothes = new PurchaseType<>();
                        }
                        readFromFiletoCategory(p, s, clothes);
                        break;
                    case "Entertainment":
                        if (entertainment == null) {
                            entertainment = new PurchaseType<>();
                        }
                        readFromFiletoCategory(p, s, entertainment);
                        break;
                    case "Other":
                        if (other == null) {
                            other = new PurchaseType<>();
                        }
                        readFromFiletoCategory(p, s, other);
                        break;

                }

            }
            //System.out.println("Purchases were loaded!\n");
            //file= new File("");
            read.close();

            //System.out.println("deleted succeesfully "+file.delete());


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    private static void readFromFiletoCategory(double p, String s, PurchaseType<String> food) {

        food.setTotalPrice(p);
        food.setAllList(s, p);
        food.setAllProductsTotal(p);
        food.purchaseList.put(s, p);
    }

    private static void saveList(String s2, PurchaseType<String> productType, PrintWriter printWriter) {
        //System.out.println(s2);


        if (!Objects.equals(productType, null)) {

            if (!productType.getPurchaseList().isEmpty()) {
                productType.getPurchaseList().forEach((name, price) -> {
                    printWriter.print(s2 + "\n");
                    printWriter.write(name + " $" + price + "\n");
                    System.out.println();

                });

            }
        }
    }

    private static void showList(String s2, PurchaseType<String> productType) {
        System.out.println(s2);
        if (!productType.getPurchaseList().isEmpty()) {
            productType.getPurchaseList().forEach((name, price) -> {
                System.out.printf("%s$%.2f\n", name, price);
            });
            System.out.println("Total sum: $" + productType.getTotalPrice());
        } else
            System.out.println("Purchase list is empty!");
    }

    private static void addIncome(Scanner scanner) {
        System.out.println("Enter income:");
        double income = scanner.nextDouble();
        scanner.nextLine();
        balance += income;
        System.out.println("Income was added\n");
    }
}
