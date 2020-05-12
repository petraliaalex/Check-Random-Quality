/*

Alex Petralia
CSCI 241
07/20/2017

Create a tree given a file of numbers
by parsing through it and keeping track 
of duplicates and order of insertion.
Tree also does not accept duplicates,
only keeps track.

Source(s):
"Building JAVA Programs (4th edition)" by Stuart Reges & Marty Stepp


*/

import java.util.*;
import java.io.*;
import java.util.Scanner;


public class CheckRandQuality {
   
   //the main tree node
   static IntTreeNode treeRoot;
   //counts the index of the file for parsing
   public static int orderOfIteration = 1;
   //counter used for adding the first node
   static int iterationCount = 0;
   
   //printSideways for this class
   public void printSideways() {
      printSideways(treeRoot, 0);
   }
   //used for printing out the array
   static void printSideways(IntTreeNode root, int level) {
      if (root != null) {
         printSideways(root.right, level + 1);
         for (int i = 0; i <level; i++) {
         
            System.out.print("    ");
         }
         System.out.println(root.data);
         printSideways(root.left, level + 1);
      }
   }
  
     //print with inorder traversal
   public static void print() {
      printInorder(treeRoot);
      System.out.println();
   }
   
   //print with inorder trav with given root
   static void printInorder(IntTreeNode root) {
      if (root != null) {
         printInorder(root.left);
         System.out.print(root.data);
         printInorder(root.right);
      }
   }
  
  //checks if the given value from the given root is in the tree
   static boolean contains(IntTreeNode root, int value) {
      return root != null && (root.data == value || (value < root.data && 
      contains(root.left, value)) || (value >= root.data && contains(root.left, value)));
   }
   
   //searches for the desired value and prints out the total .counter of the node
   //boolean will ask if a print statement is needed (true = yes)
   static boolean searchForMatch(IntTreeNode root, int value){
      if(root == null) {
         return false;  
      }else if (value == root.data) {
         return true;
      }else if (value < root.data){
         return searchForMatch(root.left, value);
      }else{ //if value > root.data
         return searchForMatch(root.right, value);
      }
   }
   
   static int searchForCounter(IntTreeNode root, int value){
      if(root == null) {
         return 0;  
      }else if (value == root.data) {
         //here are all the print statements for the output
         //they are placed here as this function gives access to the current
         //node we need to point to.
         System.out.print(root.data + " " + "appears" + " "); 
         System.out.print(root.counter);
         System.out.print(" " + "time(s), " + "order of insertion: " + root.order );
         System.out.println();
         return root.counter;
      }else if (value < root.data){
         return searchForCounter(root.left, value);
      }else{ //if value > root.data
         return searchForCounter(root.right, value);
      }
   }
   
   //calls main add function
   static void add(int value){
      treeRoot = add(treeRoot, value);
   }
   
   //value added to given tree and BST property retained
   static IntTreeNode add(IntTreeNode root, int value) {
      if(root == null){ 
         root = new IntTreeNode(value);
         root.setOrder(orderOfIteration);
      }else if (value < root.data) {
         root.left = add(root.left, value);
      }else if(value > root.data){
         root.right = add(root.right, value);
      }else if (value == root.data){
         //it is a duplicate so add to the counter
         root.counter = root.counter + 1;
         //adds an index to the order of insertion list in the node
         root.setOrder(orderOfIteration);
      }
      return root;
   }


   //"main" contains:
   //----user input reader
   //----adds all the nodes to the tree
   //----necessary print statements
   public static void main(String[] args){
      Scanner file = null; 
      Scanner fileIn = new Scanner(args[0]);
      String fileInput1 = fileIn.nextLine();
      String fileInput = fileInput1.toString();
      try{
         file = new Scanner(new File(fileInput));   
      }catch(Exception e){
      //print statements if the args[0] file is not found in the immediate directory of this program
         System.out.println("-------------------");
         System.out.println("Could not find file");
         System.out.println("-------------------");
      }
      int number = 0;
      
      //adding node logic
      while(file.hasNext() == true){
         //loads first value
         while(iterationCount == 0){
            number = file.nextInt();
            add(number);
            //after each add we must add to the 
            //orderOfIteration to see what index we are at in the file
            orderOfIteration++;
            iterationCount++;
            number = file.nextInt();
            
         }
         //adds the node and ++ on the iteration for the file
         add(number);
         orderOfIteration++;
         number = file.nextInt();
         //edge case solution: last iteration when file.hasNext == false
         if(file.hasNext() == false){
            if (contains(treeRoot, number)){
               number = file.nextInt();
            }else{
               add(number);
               orderOfIteration++;            
            }
         }
         
      }   
      //logic for the print statements/duplicate counting/order of insertion adding
      //most of the logic for the print statements is found in searchForCounter()
      int initiateSearch = 0;
      if (args.length > 1) {
         for(int i = 1; i < args.length; i++){
            int num = Integer.parseInt(args[i]);
            if(searchForMatch(treeRoot, num) == false){
               System.out.println(num + " " + "not found in BST"); 
            }else{
               initiateSearch = searchForCounter(treeRoot, num);
            }
         }
      }

   }
}

//-----------------------------Node Class-----------------------------------------------------------------------
class IntTreeNode {
   public int data;
   //left node
   public IntTreeNode left;
   //right node
   public IntTreeNode right;
   //counts number of duplicates, stored in each unique node
   public int counter;
   //holds the order of insertion
   public ArrayList<Integer> order;
   //constructor
   public IntTreeNode(int data){
      this(data, null, null, 1, null);
   }
   //instantiates the data types
   public IntTreeNode(int data, IntTreeNode left, IntTreeNode right, int counter, ArrayList<Integer> order) {
      this.data = data;
      this.left = left;
      this.right = right;
      this.counter = counter;
      this.order = new ArrayList<Integer>();
   }
   //initiator for setting a new order to the order of insertion ArrayList
   public void setOrder(int intOrder){
      this.order.add(intOrder);
   }
}