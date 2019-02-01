//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           (AllSortsOfStudents)
// Files:           (Main.java)
// Course:          (CS300 Fall 2017)
//
// Author:          (Dustin Li)
// Email:           (dli284@wisc.edu)
// Lecturer's Name: (Gary Dahl)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (Brennan Fife)
// Partner Email:   (bfife@wisc.edu)
// Lecturer's Name: (Gary Dahl)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _x_ Write-up states that pair programming is allowed for this assignment.
//   _x_ We have both read and understand the course Pair Programming Policy.
//   _x_ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.  If you received no outside help from either type of 
// source, then please explicitly indicate NONE.
//
// Persons:         (NONE)
// Online Sources:  (NONE)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** 
 * Runs the user interface of the program
 * 
 * @author Dustin Li
 * @version 1.0
 */
public class Main 
{
	public static Scanner scnr; //Scanner object that reads the user input
	public static FileReader rdr; //FileReader object that is used to read data from the file
	
	/** 
	 * Main method that creates objects of other classes and calls their methods to run the program.
	 */
	public static void main (String[] args)
	{
		System.out.println("Enter the name of your student data file: ");
	    rdr = new FileReader(); //initializes the FileReader object to read the file selected by the user
		scnr = new Scanner(System.in);
		String input = scnr.nextLine();
		try
		{
			rdr.fileReader(input); 
		}
		catch (IOException e) //Catches the IOException when the file path entered is not valid
		{
			System.out.println("WARNING: File not found.");
			main(args); //Reruns the main when the user enters an invalid file name
		}
		UserInput.usrInput(); //Calls the usrInput method which prompts the user to enter a command
		scnr.close();
	}
}

/**
 * Reads the command entered by the user and executes the code corresponding with the entered command. 
 * If the user enters and invalid command, the console will prompt the user to enter another command.
 * 
 * @author Dustin Li
 * @version 1.0
 */
class UserInput extends Main //extends Main to inherit the Scanner and FileReader variables
{
	/** 
	 * Reads the user's input and executes the corresponding code 
	 */
	public static void usrInput()
	{
		boolean quit = false; //boolean that determines whether or not to continue running the code
		String userResponse = "";
		do 
		{
			System.out.print("> ");
			String nline = scnr.nextLine(); //Stores the user input in a String variable
			if (nline.equals("") || (nline.trim().equals(""))) //Checks to see if user input is empty
			{
				System.out.println("WARNING: No input detected.");
			}
			//Checks to see if the user only entered a command but not a number
			else if (nline.length() <= 1 && (!nline.equals("Q") && !nline.equals("q")))
			{
				System.out.println("WARNING: Invalid input.");
			} 
			else 
			{
				userResponse = nline.substring(0,1).toUpperCase(); //command entered by user
				String command = nline.substring(1,nline.length()).trim(); //number entered by user
				if (!userResponse.equals("Q"))
				{
					char[] charArray = command.toCharArray(); //Checks to see if characters 
															 //following command are numbers
					if (charArray[0] < '0' || charArray[0] > '9')
					{
						System.out.println("WARNING: Invalid input.");
						continue;
					}
					//Checks to see if user input is a valid command and if number entered 
					//is within acceptable range
					else if (!(userResponse.equals("A") || userResponse.equals("O") || 
							userResponse.equals("F")) || Integer.parseInt(command) > 
							FileReader.studentData.get(0).scores.length || Integer.parseInt(command) < 0) 
					{
						System.out.println("WARNING: Invalid input.");
						continue;
					}
				}
				switch (userResponse) 
				{
					case "O" : HeapSorter.heapSort(Integer.parseInt(command)); //corresponding method calls
					   		   rdr.printArray(); //prints the data after each command
						break;
					case "A" : InsertionSorter.insertionSort(Integer.parseInt(command));
							   rdr.printArray();
						break;
					case "F" : SelectionSorter.selectionSort(Integer.parseInt(command));
							   rdr.printArray();
						break;
					case "Q" : quit = true;
						break;
						
					default :  break;	
				}
			}
		} while (!quit);
	}
}

/** 
 * Stores the name and score data of each student in the public variables
 * 
 * @author Dustin Li
 * @version 1.0
 */
class Student
{
	public String name; //name of each student
	public int[] scores; //integer array of student scores
	
	/** 
	 * Creates a new student object which stores the input variables as the object's public variables
	 * 
	 * @param name - name of the student
	 * @param scores - integer array of the student's scores
	 */
	public Student(String name, int[] scores)
	{
		this.name = name;
		this.scores = scores;
	}
}

/**
 * Reads the data from the file and creates new Student objects which store the 
 * corresponding data in its public variables. It then sorts the data alphabetically
 * and prints the data out to the console. Also contains a method which prints out the data
 * to the console;
 * 
 * @author Dustin Li
 * @version 1.0
 */
class FileReader
{
	public static List<Student> studentData; //List of Student objects, each containing the 
                                         	//student data from the file
	
	/**
	 * Reads the data from the file and creates new Student objects which stores the 
	 * corresponding data in its public variables. It then calls methods which sort 
	 * the data alphabetically and prints the data out to the console.
	 * 
	 * @param fileName - name of the file entered by the user
	 * @throws IOException - when file name is invalid
	 */
	public void fileReader(String fileName) throws IOException
	{
		studentData = new ArrayList<Student>();
		Scanner scanner = new Scanner(new File(fileName));
		while (scanner.hasNextLine()) //reads through the data in the file
		{
		    String line = scanner.nextLine();
		    String[] temp = line.split(":");
		    String[] scores = temp[1].split(","); //stores the student's scores in a string array
		    for (int i = 0; i < scores.length; i++)
		    {
		    		scores[i] = scores[i].trim();
		    }
		    int[] numScores = new int[scores.length]; //transfers the student's scores into an integer array
		    for (int i = 0; i < scores.length; i++)
		    {
		    		numScores[i] = Integer.parseInt(scores[i]);
		    }
		    studentData.add(new Student(temp[0], numScores)); //creates a new Student object with the 
		    													 //corresponding name and scores and adds
		    													 //it to the studentData Arraylist
		}
		scanner.close();
		InsertionSorter.insertionSort(0); //calls insertionSort method which sorts the data alphabetically
		printArray(); //prints out the data
	}
	
	/**
	 * Prints out the data of each student
	 */
	public void printArray()
	{
		for (int i = 0; i < studentData.size(); i++) //iterates through list of students prints out the data
		{
			String output = studentData.get(i).name + ": "; //formats output to replicate data in the file
			for (int j = 0; j < studentData.get(i).scores.length; j++)
			{
				if (j < studentData.get(i).scores.length - 1)
				{
					output += studentData.get(i).scores[j] + ", ";
				}
				else 
				{
					output += studentData.get(i).scores[j];
				}
			}
			System.out.println(output);
		}
	}
}

/**
 * Sorts the data based on the insertion sort algorithm
 * Adaptive Time Complexity
 * <p>
 * We could have used Generics, but the TA recommended not using Generics due to our use of the 
 * compareTo() method to sort alphabetically and a different way to sort numerically
 * 
 * @author Dustin Li
 * @version 1.0
 */
class InsertionSorter extends FileReader //extends FileReader class to inherit the studentData variable
{
	/**
	 * Sorts the data based on the insertion sort algorithm.
	 * 
	 * @param x - user input which determines whether to sort alphabetically or which set of scores
	 */
	public static void insertionSort(int x) 
	{
		if (x == 0) //Sorts the data alphabetically by name
		{
			int n = studentData.size();
	        for (int i = 1; i < n; i++)
	        {
	            Student key = studentData.get(i);
	            int j = i - 1;
	            while (j >= 0 && studentData.get(j).name.compareTo(key.name) > 0)
	            {
	                studentData.set(j + 1, studentData.get(j));
	                j = j - 1;
	            }
	            studentData.set(j + 1, key);
	        }
		}
		else	 //Sorts the data based on the set of scores
		{
			int n = studentData.size();
	        for (int i = 1; i < n; i++)
	        {
	            Student key = studentData.get(i);
	            int j = i - 1;
	            while (j >= 0 && studentData.get(j).scores[x-1] > key.scores[x-1])
	            {
	                studentData.set(j + 1, studentData.get(j));
	                j = j - 1;
	            }
	            studentData.set(j + 1, key);
	        }
		}
	}
}

/**
 * Sorts the data based on the heap sort algorithm
 * Optimal Time Complexity
 * 
 * @author Dustin Li
 * @version 1.0
 */
class HeapSorter extends FileReader //extends FileReader class to inherit the studentData variable
{
	/**
	 * Sorts the data based on the heap sort algorithm
	 * 
	 * @param x - user input which determines whether to sort alphabetically or by set of scores
	 */
	public static void heapSort(int x)
	{
		if (x == 0) //Sorts the data alphabetically by name
		{
			int n = studentData.size();
			
	        for (int i = n / 2 - 1; i >= 0; i--)
	            nameHelper(studentData, n, i); //calls the helper
	 
	        for (int i = n-1; i >= 0; i--)
	        {
	        	    Student temp = studentData.get(0);
	            studentData.set(0, studentData.get(i));
	            studentData.set(i, temp);
	            nameHelper(studentData, i, 0); //calls the helper
	        }
		}
		else //Sorts the data based on the set of scores
		{
			int n = studentData.size();
			 
	        for (int i = n / 2 - 1; i >= 0; i--)
	            intHelper(studentData, n, i, x); //calls the helper
	 
	        for (int i = n-1; i >= 0; i--)
	        {
	            Student temp = studentData.get(0);
	            studentData.set(0, studentData.get(i));
	            studentData.set(i, temp);
	            intHelper(studentData, i, 0, x); //calls the helper
	        }
		}
	}
	
	/**
	 * Helper method for the heap sort algorithm which sorts by scores
	 * 
	 * @param arr - list of student objects
	 * @param n - size of the heap
	 * @param i - index of the current student being sorted
	 * @param x - user input which determines whether to sort alphabetically or by set of scores
	 */
	static void intHelper(List<Student> arr, int n, int i, int x)
	{
		int largest = i;
	    int l = (2 * i) + 1;
	    int r = (2 * i) + 2;
	    
	    //checks to see if child node is larger than parent node
	    if (l < n && studentData.get(l).scores[x-1] > studentData.get(largest).scores[x-1])
	    {
	    		largest = l;
	    }
	 
	    if (r < n && studentData.get(r).scores[x-1] > studentData.get(largest).scores[x-1])
	    {
	    		largest = r;
	    }
	 
	    if (largest != i)
	    {
	    		Student swap = studentData.get(i);
	        studentData.set(i, studentData.get(largest));
	        studentData.set(largest, swap);
	        intHelper(arr, n, largest, x); //recursively calls the helper
	    }  
	}
	
	/**
	 * Helper method for the heap sort algorithm which sorts alphabetically by name
	 * 
	 * @param arr - list of student objects
	 * @param n - size of the heap
	 * @param i - index of the current student being sorted
	 */
	static void nameHelper(List<Student> arr, int n, int i)
	{
		int largest = i;  
	    int l = 2 * i + 1;  
	    int r = 2 * i + 2; 
	    
	    //checks to see if child node is larger than parent node
	    if (l < n && studentData.get(l).name.compareTo(studentData.get(largest).name) > 0)
	    {
	    		largest = l;
	    }
	 
	    if (r < n && studentData.get(r).name.compareTo(studentData.get(largest).name) > 0)
	    {
	    		largest = r;
	    }

	    if (largest != i)
	    {
	    		Student swap = studentData.get(i);
	        studentData.set(i, studentData.get(largest));
	        studentData.set(largest, swap);
	        nameHelper(arr, n, largest); //recursively calls the helper
	    }  
	}
}

/**
 * Sorts the data based on the selection sort algorithm
 * Fewest Swaps Time Complexity
 * 
 * @author Dustin Li
 * @version 1.0
 */
class SelectionSorter extends FileReader //extends FileReader class to inherit the studentData variable
{
	/**
	 * Sorts the data based on the selection sort algorithm
	 * 
	 * @param x - user input which determines whether to sort alphabetically or by set of scores
	 */
	public static void selectionSort(int x)
	{
		if (x == 0) //sorts data alphabetically by name
		{
			int n = studentData.size();
	        for (int i = 0; i < n-1; i++)
	        {
	            int min = i;
	            for (int j = i + 1; j < n; j++) 
	            {
	            		if (studentData.get(j).name.compareTo(studentData.get(min).name) < 0)
	            		{
	            			min = j;
	            		}
	            }
	            Student temp = studentData.get(min);
	            studentData.set(min, studentData.get(i));
	            studentData.set(i, temp);
	        }
		}
		else //sorts the data numerically by set of scores
		{
			int n = studentData.size();
	        for (int i = 0; i < n-1; i++)
	        {
	            int min = i;
	            for (int j = i+1; j < n; j++)
	            {
	            		if (studentData.get(j).scores[x-1] < studentData.get(min).scores[x-1])
	            		{
	            			min = j;
	            		}
	            }
	            Student temp = studentData.get(min);
	            studentData.set(min, studentData.get(i));
	            studentData.set(i, temp);
	        }
		}
	}
}

