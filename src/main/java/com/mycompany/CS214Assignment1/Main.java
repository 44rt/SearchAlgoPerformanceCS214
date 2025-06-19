/*
CS214 ASSIGNMENT 1

Group Members:
Samuela Robin - S11199961
Ranjan Naidu  - S11201181
Roska Takayawa - S11187423

 */

package com.mycompany.CS214Assignment1;

//importing packages
import java.awt.Dimension;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {
    public static void main(String[] args){
        //path of the csv file
        //make sure put the right pathfile name in the code below if not you will get an error
        String csvFile = "/Users/roskat/Desktop/A1_S11199961_S11201181_S11187423/src/main/java/com/mycompany/CS214Assignment1/Article.csv";

        //creating of the lists
        List<Article> csvDataArrayList = new ArrayList<>();
        List<Article> csvDataLinkedList = new LinkedList<>();

        //Reading csv file 
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
            CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT.withFirstRecordAsHeader())){

            //parsing data from csv file
            for (CSVRecord csvRecord : csvParser){
                int id = Integer.parseInt(csvRecord.get("ID"));
                String title = csvRecord.get("TITLE");
                String abstractText = csvRecord.get("ABSTRACT");

                boolean[] subjects = new boolean[]{
                    csvRecord.get("Computer Science").equals("1"),
                    csvRecord.get("Physics").equals("1"),
                    csvRecord.get("Mathematics").equals("1"),
                    csvRecord.get("Statistics").equals("1"),
                    csvRecord.get("Quantitative Biology").equals("1"),
                    csvRecord.get("Quantitative Finance").equals("1")
                };

                Article article = new Article(id, title, abstractText, subjects);

                // Add the parsed data to both ArrayList and LinkedList
                csvDataArrayList.add(article);
                csvDataLinkedList.add(article);

            }
            
           // Sort the ArrayList and Linkedlist 
            Collections.sort(csvDataArrayList, new ArticleComparator());
            Collections.sort(csvDataLinkedList, new ArticleComparator());
        } 
        catch (IOException e){
            e.printStackTrace();
        }
        
        Scanner scanner = new Scanner(System.in);

        boolean exitProgram = false;

        while (!exitProgram){
            //Menu for the program
            System.out.println("Welcome to the Article Search and Performance Analysis!");
            System.out.println("\n1. Search for an Article");
            System.out.println("2. Analyze Performance Analysis");
            System.out.println("3. Print Article Sorted by Title");
            System.out.println("4. Show performance of each search algorithm with data structure graphically");
            System.out.println("5. To exit program");
            System.out.print("------------------------------------------------------");
            System.out.print("\n\nEnter your choice: ");


            String choiceStr = scanner.next();
            scanner.nextLine();

            if (choiceStr.equals("1")){
                // Code for searching for an article
                System.out.print("\nPlease Enter Title of Article: ");
                String searchKeyword = scanner.nextLine();

                //Using threads to run each search algorithms simultaneously
                //Binary Search for Arraylist
                Thread t1 = new Thread(() -> {
                    long startTime = System.nanoTime();
                    int arrayListIndex = binarySearch(csvDataArrayList, new Article(0, searchKeyword, "", new boolean[0]), new ArticleComparator());
                    long endTime = System.nanoTime();
                    long timeTaken = endTime - startTime;

                    if (arrayListIndex >= 0){
                        Article foundArticle = csvDataArrayList.get(arrayListIndex);
                        synchronized (System.out){
                            System.out.print("\n------------------------------------------------------");
                            System.out.println("\nBinary Search for ArrayList: ");
                            System.out.println("ID: " + foundArticle.getId());
                            System.out.println("Title: " + foundArticle.getTitle());
                            displayRelatedSubjects(foundArticle.getSubjects());
                            System.out.println("\nBinary Search for ArrayList took: " + timeTaken + " nanoseconds to find article");
                            System.out.print("------------------------------------------------------");
                        }
                    } 
                    else{
                        synchronized (System.out){
                            System.out.println("\nBinary Search for ArrayList: Article not found.");
                            System.out.println("Binary Search for ArrayList took: " + timeTaken + " nanoseconds to find article");
                        }
                    }
                });

                //Binary search for Linkedlist
                Thread t2 = new Thread(() -> {
                    long startTime = System.nanoTime();
                    int linkedListIndex = binarySearch(csvDataLinkedList, new Article(0, searchKeyword, "", new boolean[0]), new ArticleComparator());
                    long endTime = System.nanoTime();
                    long timeTaken = endTime - startTime;

                    if (linkedListIndex >= 0){
                        Article foundArticle = csvDataLinkedList.get(linkedListIndex);
                        synchronized (System.out){
                            System.out.println("\nBinary Search for Linkedlist: ");
                            System.out.println("ID: " + foundArticle.getId());
                            System.out.println("Title: " + foundArticle.getTitle());
                            displayRelatedSubjects(foundArticle.getSubjects());
                            System.out.println("\nBinary Search for LinkedList took: " + timeTaken + " nanoseconds to find article");
                            System.out.print("------------------------------------------------------");
                        }
                    }
                    else{
                        synchronized (System.out){
                            System.out.println("\nBinary Search for LinkedList: Article not found.");
                            System.out.println("Binary Search for Linkedlist took: " + (endTime - startTime) + " nanoseconds to find article");
                        }
                    }
                });

                //Fibonacci Search for Arraylist
                Thread t3 = new Thread(() ->{
                    long startTime = System.nanoTime();
                    int arrayListIndex = fibonacciSearch(csvDataArrayList, new Article(0, searchKeyword, "", new boolean[0]), new ArticleComparator());
                    long endTime = System.nanoTime();

                    if (arrayListIndex >= 0) {
                        Article foundArticle = csvDataArrayList.get(arrayListIndex);
                        synchronized (System.out) {
                            System.out.println("\nFibonacci Search for Arraylist: ");
                            System.out.println("ID: " + foundArticle.getId());
                            System.out.println("Title: " + foundArticle.getTitle());
                            displayRelatedSubjects(foundArticle.getSubjects());
                            System.out.println("\nFibonacci Search for Arraylist took: " + (endTime - startTime) + " nanoseconds to find article");
                            System.out.print("------------------------------------------------------");
                        }
                    } 
                    else {
                        synchronized (System.out)
                        {
                            System.out.println("\nFibonacci Search for ArrayList: Article not found.");
                            System.out.println("Fibonnaci Search for ArrayList took: " + (endTime - startTime) + " nanoseconds to find article");
                        }
                    }
                });

                //Fibonnaci Search for Linkedlist
                Thread t4 = new Thread(() -> {
                    long startTime = System.nanoTime();
                    int linkedListIndex = fibonacciSearch(csvDataLinkedList, new Article(0, searchKeyword, "", new boolean[0]), new ArticleComparator());
                    long endTime = System.nanoTime();

                    if (linkedListIndex >= 0) {
                        Article foundArticle = csvDataLinkedList.get(linkedListIndex);
                        synchronized (System.out)
                        {
                            System.out.println("\nFibonacci Search for Linkedlist: ");
                            System.out.println("ID: " + foundArticle.getId());
                            System.out.println("Title: " + foundArticle.getTitle());
                            displayRelatedSubjects(foundArticle.getSubjects());
                            System.out.println("\nFibonnaci Search for Linkedlist took: " + (endTime - startTime) + " nanoseconds to find article");
                            System.out.print("------------------------------------------------------");
                        }
                    } 
                    else {
                        synchronized (System.out) {
                            System.out.println("\nFibonacci Search for Linkedlist: Article not found.");
                            System.out.println("Fibonnaci Search for Linkedlist took: " + (endTime - startTime) + " nanoseconds to find article");
                        }
                    }
                });

                //Linear Search for Arraylist 
                Thread t5 = new Thread(() -> {
                    long startTime = System.nanoTime();
                    int arrayListIndex = linearSearch(csvDataArrayList, new Article(0, searchKeyword, "", new boolean[0]), new ArticleComparator());
                    long endTime = System.nanoTime();

                    if (arrayListIndex >= 0) {
                        Article foundArticle = csvDataArrayList.get(arrayListIndex);
                        synchronized (System.out) {
                            System.out.println("\nLinear Search for Arraylist: ");
                            System.out.println("ID: " + foundArticle.getId());
                            System.out.println("Title: " + foundArticle.getTitle());
                            displayRelatedSubjects(foundArticle.getSubjects());
                            System.out.println("\nLinear Search for Arraylist took: " + (endTime - startTime) + " nanoseconds to find article");
                            System.out.print("------------------------------------------------------");
                        }
                    } 
                    else {
                        synchronized (System.out) {
                            System.out.println("\nLinear Search for Arraylist: Article not found.");
                            System.out.println("Linear Search for Arraylist took: " + (endTime - startTime) + " nanoseconds to find article");
                        }
                    }
                });

                //Linear Search for Linkedlist
                Thread t6 = new Thread(() -> {
                    long startTime = System.nanoTime();
                    int linkedListIndex = linearSearch(csvDataLinkedList, new Article(0, searchKeyword, "", new boolean[0]), new ArticleComparator());
                    long endTime = System.nanoTime();

                    if (linkedListIndex >= 0) {
                        Article foundArticle = csvDataLinkedList.get(linkedListIndex);
                        synchronized (System.out) {
                            System.out.println("\nLinear Search for Linkedlist: ");
                            System.out.println("ID: " + foundArticle.getId());
                            System.out.println("Title: " + foundArticle.getTitle());
                            displayRelatedSubjects(foundArticle.getSubjects());
                            System.out.println("\nLinear Search for Linkedlist took: " + (endTime - startTime) + " nanoseconds to find article");
                            System.out.print("------------------------------------------------------");
                        }
                    } 
                    else {
                        synchronized (System.out) {
                            System.out.println("\nLinear Search for Linkedlist: Article not found.");
                            System.out.println("Linear Search for Linkedlist took: " + (endTime - startTime) + " nanoseconds to find article");
                        }
                    }
                });

                //Ternary Search for Arraylist
                Thread t7 = new Thread(() -> {
                    long startTime = System.nanoTime();
                    int arrayListIndex = ternarySearch(csvDataArrayList, new Article(0, searchKeyword, "", new boolean[0]), new ArticleComparator());
                    long endTime = System.nanoTime();

                    if (arrayListIndex >= 0) {
                        Article foundArticle = csvDataArrayList.get(arrayListIndex);
                        synchronized (System.out) {
                            System.out.println("\nTernary Search for Arraylist: ");
                            System.out.println("ID: " + foundArticle.getId());
                            System.out.println("Title: " + foundArticle.getTitle());
                            displayRelatedSubjects(foundArticle.getSubjects());
                            System.out.println("\nTernary Search for Arraylist took: " + (endTime - startTime) + " nanoseconds to find article");
                            System.out.print("------------------------------------------------------");
                        }
                    } 
                    else {
                        synchronized (System.out) {
                            System.out.println("\nTernary Search for Arraylist: Article not found.");
                            System.out.println("Ternary Search for Arraylist took: " + (endTime - startTime) + " nanoseconds to find article");
                        }
                    }
                });

                //Ternary Search for Linkedlist
                Thread t8 = new Thread(() -> {
                    long startTime = System.nanoTime();
                    int linkedListIndex = ternarySearch(csvDataLinkedList, new Article(0, searchKeyword, "", new boolean[0]), new ArticleComparator());
                    long endTime = System.nanoTime();

                    if (linkedListIndex >= 0) {
                        Article foundArticle = csvDataLinkedList.get(linkedListIndex);
                        synchronized (System.out) {
                            System.out.println("\nTernary Search for Linkedlist: ");
                            System.out.println("ID: " + foundArticle.getId());
                            System.out.println("Title: " + foundArticle.getTitle());
                            displayRelatedSubjects(foundArticle.getSubjects());
                            System.out.println("\nTernary Search for Linkedlist took: " + (endTime - startTime) + " nanoseconds to find article");
                            System.out.print("------------------------------------------------------");
                        }
                    } 
                    else {
                        synchronized (System.out) {
                            System.out.println("\nTernary Search for Linkedlist: Article not found.");
                            System.out.println("Ternary Search for Linkedlist took: " + (endTime - startTime) + " nanoseconds to find article");
                        }
                    }
                });

                // Start all threads
                t1.start();
                t2.start();
                t3.start();
                t4.start();
                t5.start();
                t6.start();
                t7.start();
                t8.start();

                // Wait for all threads to finish running
                try {
                    t1.join();
                    t2.join();
                    t3.join();
                    t4.join();
                    t5.join();
                    t6.join();
                    t7.join();
                    t8.join();
                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } 
            else if (choiceStr.equals("2")) {
                // Perform Performance Analysis
                System.out.println("\nPerforming Performance Analysis...\n");

                String searchTitle = getUserInput("Enter the title of the article to analyze: ");
                Article targetArticle = new Article(0, searchTitle, "", new boolean[0]);

                PerformanceAnalysis(targetArticle);
            }
            else if (choiceStr.equals("3")) {
                System.out.println("\nArticles Sorted by Title:");

                // Sorting and printing the ArrayList
                csvDataArrayList.sort(Comparator.comparing(Article::getTitle, String.CASE_INSENSITIVE_ORDER));
                for (int i = 0; i < csvDataArrayList.size(); i++) {
                    Article article = csvDataArrayList.get(i);
                    System.out.println((i + 1) + ". ID: " + article.getId() + ", Title: " + article.getTitle());
                }
            }
            else if (choiceStr.equals("4")) {
                createPerformanceGraph(csvDataArrayList, csvDataLinkedList);
            }
    
             else if (choiceStr.equals("5")) {
                exitProgram = true;
                System.out.println("Exiting program. Goodbye!");
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        scanner.close();
    }
    
    private static void createPerformanceGraph(List<Article> arrayList, List<Article> linkedList) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        String[] algorithms = {
            "Binary Search", "Fibonacci Search",
            "Linear Search", "Ternary Search"
            // Add more algorithms here if needed
        };

        for (String algorithm : algorithms) {
            XYSeries seriesArrayList = new XYSeries(algorithm + " (ArrayList)");
            XYSeries seriesLinkedList = new XYSeries(algorithm + " (LinkedList)");

            // Collect performance data and add to seriesArrayList and seriesLinkedList
            collectPerformanceData(arrayList, algorithm, seriesArrayList);
            collectPerformanceData(linkedList, algorithm, seriesLinkedList);

            dataset.addSeries(seriesArrayList);
            dataset.addSeries(seriesLinkedList);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
            "Search Algorithm Performance",
            "Run Number",
            "Time (nanoseconds)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);

        // Customize renderer settings if needed

        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        JFrame frame = new JFrame("Search Algorithm Performance Graph");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
    
    private static void collectPerformanceData(List<Article> list, String algorithm, XYSeries series) {
        Comparator<Article> comparator = new ArticleComparator();
        int numRuns = 30;

        for (int i = 0; i < numRuns; i++) {
            long searchTime = runSearchAlgorithm(list, new Article(0, "Random Article", "", new boolean[0]), comparator, algorithm);
            series.add(i + 1, searchTime);
        }
    }
    
    private static void PerformanceAnalysis(Article targetArticle) {
        List<Article> csvDataArrayList = new ArrayList<>();
        List<Article> csvDataLinkedList = new LinkedList<>();


        System.out.println("\nResults for Binary Search (ArrayList):");
        printPerformanceResults("ArrayList", "Binary Search", csvDataArrayList, targetArticle, new ArticleComparator());

        System.out.println("\nResults for Binary Search (LinkedList):");
        printPerformanceResults("LinkedList", "Binary Search", csvDataLinkedList, targetArticle, new ArticleComparator());

        System.out.println("\nResults for Fibonacci Search (ArrayList):");
        printPerformanceResults("ArrayList", "Fibonacci Search", csvDataArrayList, targetArticle, new ArticleComparator());
        
        System.out.println("\nResults for Fibonacci Search (LinkedList):");
        printPerformanceResults("LinkedList", "Fibonacci Search", csvDataLinkedList, targetArticle, new ArticleComparator());
        
        System.out.println("\nResults for Linear Search (ArrayList):");
        printPerformanceResults("ArrayList", "Linear Search", csvDataLinkedList, targetArticle, new ArticleComparator());
        
        System.out.println("\nResults for Linear Search (LinkedList):");
        printPerformanceResults("LinkedList", "Linear Search", csvDataLinkedList, targetArticle, new ArticleComparator());
        
        System.out.println("\nResults for Ternary Search (ArrayList):");
        printPerformanceResults("ArrayList", "Ternary Search", csvDataLinkedList, targetArticle, new ArticleComparator());
        
        System.out.println("\nResults for Ternary Search (LinkedList):");
        printPerformanceResults("LinkedList", "Ternary Search", csvDataLinkedList, targetArticle, new ArticleComparator());
        // Repeat the above process for other search algorithms (Fibonacci, Linear, Ternary) and data structures
    }
    
    private static void printPerformanceResults(String dataStructure, String searchAlgorithm,
                                                List<Article> list, Article target, Comparator<Article> comparator) {
        int numRuns = 30;
        long minTime = Long.MAX_VALUE;
        long maxTime = Long.MIN_VALUE;
        long totalTime = 0;

        for (int i = 0; i < numRuns; i++) {
            long searchTime = runSearchAlgorithm(list, target, comparator, searchAlgorithm);
            minTime = Math.min(minTime, searchTime);
            maxTime = Math.max(maxTime, searchTime);
            totalTime += searchTime;
        }

        long avgTime = totalTime / numRuns; //calculate the average search time

        System.out.println("Number of Runs: " + numRuns);
        System.out.println("Minimum Search Time: " + minTime + " nanoseconds");
        System.out.println("Maximum Search Time: " + maxTime + " nanoseconds");
        System.out.println("Average Search Time: " + avgTime + " nanoseconds");
    }
    
    private static long runSearchAlgorithm(List<Article> list, Article target, Comparator<Article> comparator,
                                           String searchAlgorithm) {
        switch (searchAlgorithm) {
            case "Binary Search":
                return runBinarySearch(list, target, comparator);
            case "Fibonacci Search":
                return runFibonacciSearch(list, target, comparator);
            case "Linear Search":
                return runLinearSearch(list, target, comparator);
            case "Ternary Search":
                return runTernarySearch(list, target, comparator);
            default:
                return 0; // Invalid search algorithm
        }
    }
    
    // Method to run Fibonacci search and measure time
    private static long runFibonacciSearch(List<Article> list, Article target, Comparator<Article> comparator) {
        long startTime = System.nanoTime();
        fibonacciSearch(list, target, comparator); // Existing Fibonacci search method
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    // Method to run linear search and measure time
    private static long runLinearSearch(List<Article> list, Article target, Comparator<Article> comparator) {
        long startTime = System.nanoTime();
        linearSearch(list, target, comparator); // Existing linear search method
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    // Method to run ternary search and measure time
    private static long runTernarySearch(List<Article> list, Article target, Comparator<Article> comparator) {
        long startTime = System.nanoTime();
        ternarySearch(list, target, comparator); // Existing ternary search method
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private static long runBinarySearch(List<Article> list, Article target, Comparator<Article> comparator) {
        long startTime = System.nanoTime();
        binarySearch(list, target, comparator); // Existing binary search method
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    
    private static String getUserInput(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    // Sorting articles in data structures for search algorithms to work
    // Custom comparator to sort articles in alphabetical order
    static class ArticleComparator implements Comparator<Article> {
        @Override
        public int compare(Article a1, Article a2) {
            String title1 = a1.getTitle();
            String title2 = a2.getTitle();

            // Handle special characters coming first
            if (!Character.isLetterOrDigit(title1.charAt(0)) && Character.isLetterOrDigit(title2.charAt(0))) {
                return -1;
            }
            if (!Character.isLetterOrDigit(title2.charAt(0)) && Character.isLetterOrDigit(title1.charAt(0))) {
                return 1;
            }
            // Both titles start with special characters or alphabets/numbers
            return title1.compareToIgnoreCase(title2);
        }
    }

    // Correctly splitting the Article title and Abstract
    public static String[] parseCSVLine(String line) {
        // Split the line using comma as delimiter
        String[] rowData = line.split(",", -1);

        // Remove leading and trailing whitespace from each element
        for (int i = 0; i < rowData.length; i++) {
            rowData[i] = rowData[i].trim();
        }

        return rowData;
    }
    
    
    public static boolean[] parseSubjects(String[] rowData) {
        boolean[] subjects = new boolean[rowData.length - 3];
        for (int i = 3; i < rowData.length; i++) {
            subjects[i - 3] = rowData[i].equals("1");
        }
        return subjects; // Add this return statement
    }
    
    // General Binary Search Algorithm for both ArrayList and LinkedList
    public static <T> int binarySearch(List<T> list, T target, Comparator<T> comparator){
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            T midElement = list.get(mid);

            int compareResult = comparator.compare(midElement, target);

            if (compareResult == 0) {
                return mid;
            } 
            else if (compareResult < 0) {
                left = mid + 1;
            } 
            else {
                right = mid - 1;
            }
        }

        return -1; // Element not found
    }
    
     // General Fibonacci search algorithm for ArrayList and LinkeList
    public static <T> int fibonacciSearch(List<T> list, T target, Comparator<T> comparator) {
        int n = list.size();
        int fibMMinus2 = 0; // (m-2)th Fibonacci number
        int fibMMinus1 = 1; // (m-1)th Fibonacci number
        int fibM = fibMMinus1 + fibMMinus2; // mth Fibonacci number

        while (fibM < n) {
            fibMMinus2 = fibMMinus1;
            fibMMinus1 = fibM;
            fibM = fibMMinus1 + fibMMinus2;
        }

        int offset = -1; // Offset of the first element in the range
        while (fibM > 1) {
            int i = Math.min(offset + fibMMinus2, n - 1);

            T midElement = list.get(i);
            int compareResult = comparator.compare(midElement, target);

            if (compareResult == 0) {
                return i;
            } 
            else if (compareResult < 0) {
                fibM = fibMMinus1;
                fibMMinus1 = fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
                offset = i;
            } 
            else {
                fibM = fibMMinus2;
                fibMMinus1 = fibMMinus1 - fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
            }
        }

        if (fibMMinus1 == 1 && offset + 1 < n && comparator.compare(list.get(offset + 1), target) == 0) {
            return offset + 1;
        }

        return -1; // Element not found
    }
    
    //Generic Linear Search Algorithm for Arraylist and LinkedList
    public static <T> int linearSearch(List<T> list, T target, Comparator<T> comparator) {
        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            if (comparator.compare(element, target) == 0) {
                return i;
            }
        }
        return -1; // Element not found
    }

    // Generic Ternary Search Algorithm for Arraylist and Linkedlist
    public static <T> int ternarySearch(List<T> list, T target, Comparator<T> comparator) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int partitionSize = (right - left) / 3;
            int mid1 = left + partitionSize;
            int mid2 = right - partitionSize;

            T mid1Element = list.get(mid1);
            T mid2Element = list.get(mid2);

            int compareMid1 = comparator.compare(mid1Element, target);
            int compareMid2 = comparator.compare(mid2Element, target);

            if (compareMid1 == 0) {
                return mid1;
            } 
            if (compareMid2 == 0) {
                return mid2;
            }

            if (compareMid1 < 0) {
                left = mid1 + 1;
            } 
            else if (compareMid2 > 0) {
                right = mid2 - 1;
            } 
            else {
                left = mid1 + 1;
                right = mid2 - 1;
            }
        }

        return -1; // Element not found
    }

    //class for showing which article is related to which subject
    private static void displayRelatedSubjects(boolean[] subjects) {
        System.out.print("Related Subjects: ");

        List<String> relatedSubjects = new ArrayList<>();
        if (subjects[0]) {
            relatedSubjects.add("Computer Science");
        }
        if (subjects[1]) {
            relatedSubjects.add("Physics");
        }
        if (subjects[2]) {
            relatedSubjects.add("Mathematics");
        }
        if (subjects[3]) {
            relatedSubjects.add("Statistics");
        }
        if (subjects[4]) {
            relatedSubjects.add("Quantitative Biology");
        }
        if (subjects[5]) {
            relatedSubjects.add("Quantitative Finance");
        }

        System.out.println(String.join(", ", relatedSubjects));
    }
}