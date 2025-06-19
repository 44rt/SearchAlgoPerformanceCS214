
# Article Search and Analysis Tool

This Java application allows you to search for articles within a CSV dataset using various search algorithms. It
reads data from a CSV file containing information about articles and then performs searches using binary
search, Fibonacci search, linear search, and ternary search algorithms. The application utilizes threads to
simultaneously execute these search algorithms on both ArrayList and LinkedList data structures. It provides
information about the search results, execution times, and related subjects for each article found.

### Prerequisites
Java Development Kit (JDK) installed on your system.
Apache Commons CSV library (org.apache.commons.csv) added to your project.

### Getting Started
Clone or download the project to your local machine.
Open the project in your preferred Java IDE or compile and run it from the command line.
Steps to follow before running the project.
Place the correct path to your CSV file containing article data at the specified location:

//path of the csv file

String csvFile = "C:/donny/Brave/Article.csv";

Ensure that the path to your CSV file is correct.
Run the Main class's main method to execute the application.
You will be given options on what a user would like to do.
Upon running the application, you'll be prompted to enter the title of the article you want to search for .
The application will then perform searches using multiple search algorithms and display the search results,
execution times, and related subjects.

## NOTE: Analysis report is in analysis.pdf :D