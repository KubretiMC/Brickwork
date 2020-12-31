package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //creates scanner for user input
        Scanner sc = new Scanner(System.in);

        //takes dimension input
        String dimension[] = sc.nextLine().split(" ");

        //inputs width into variable n and length into variable m
        int n = Integer.parseInt(dimension[0]);
        int m = Integer.parseInt(dimension[1]);

        //validation that defined area is less than 100 and is even
        if (n % 2 != 0 || m%2!=0 || n >= 100 || m >= 100 || n < 2 || m < 2) {
            System.out.println("Wrong input! It should be even number between 2 and 98");
            return;
        }

        //creates arrays representing the first and second layer of bricks (user input)
        int[][] firstLayerArr = new int[n][m];
        int[][] secondLayerArr = new int[n][m];


        //fills the first layer of bricks with the user input
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                //the user input
                firstLayerArr[i][j] = sc.nextInt();
            }
        }

        //list to store checked numbers
        ArrayList<Integer> bricksList = new ArrayList<>();

        //validates the brick is made by more than 1 variable by checking right and down neighbour
        //note:no need to check the left and upper neighbour because they will be previously checked in the previous validations
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                //if firstLayer[i][j]'s value is in the list that means its already checked
                if (bricksList.contains(firstLayerArr[i][j])) {
                    continue;
                } else if (searchSame(i, j + 1, firstLayerArr, firstLayerArr[i][j])) { // checks if the brick has other part on right
                    //System.out.println(firstLayerArr[i][j] + " exist");
                    bricksList.add(firstLayerArr[i][j]);
                } else if (searchSame(i + 1, j, firstLayerArr, firstLayerArr[i][j])) { // checks if the brick has other part on down
                    //System.out.println(firstLayerArr[i][j] + " exist");
                    bricksList.add(firstLayerArr[i][j]);
                } else {
                    System.out.println("Brick " + firstLayerArr[i][j] + " does not exist or is made from less than 2 variables");
                    return;
                }
            }
        }


        //validates there is no brick spanning 3 rows/columns
        //in this loop we check if we inputed all the numbers from 1 to n. If a number is not in the dimension that means we have wrong input
        //if all numbers are in the dimension they are surely to be there more than 1 time, because the previous loop validates it
        //there cant be brick made by 3 or more numbers, because that means another one is made by 1 which is proved to be impossible by searchSame


        //for loop for numbers from 1 to the last bricks number
        for (int number = 1; number <= (n * m) / 2; number++) {
            //flag that will signal when number is found
            boolean flag = false;
            for (int i = 0; i < n; i++) {
                //if number is found, we break the middle loop
                if (flag == true) {
                    break;
                }
                for (int j = 0; j < m; j++) {
                    //if number is found, we break the inner loop
                    if (number == firstLayerArr[i][j]) {
                        flag = true;
                        break;
                    }
                }
            }
            //if number in not in the dimension
            if (!flag) {
                System.out.println("Number " + number + " is not in the list twice!");
                return;
            }
        }

        int number = 0;
        int variable1;
        //try to fill second layer with the values
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                //if index is filled skip
                if (secondLayerArr[i][j] != 0) {
                    continue;
                } else {
                    //if the iteration is on the last column and is not filled yet we add brick on different rows
                    if (j == m - 1) {
                        number++;
                        secondLayerArr[i][j] = number;
                        secondLayerArr[i + 1][j] = number;

                        //if brick from first layer is with the same number on same index we will swap it with a neighbouring brick
                        if (firstLayerArr[i][j] == firstLayerArr[i + 1][j] && firstLayerArr[i][j] == secondLayerArr[i][j]) {
                            if (secondLayerArr[i][j - 1] != 0) //try to swap with the left brick
                            {
                                if (secondLayerArr[i][j - 1] == secondLayerArr[i][j - 2]) // see if left brick continues on left
                                {
                                    variable1 = secondLayerArr[i][j - 1];
                                    secondLayerArr[i][j - 1] = secondLayerArr[i][j];
                                    secondLayerArr[i][j - 2] = secondLayerArr[i][j];
                                    secondLayerArr[i][j] = variable1;
                                    secondLayerArr[i + 1][j] = variable1;
                                } else if (i > 0) {
                                    if (secondLayerArr[i][j - 1] == secondLayerArr[i - 1][j - 1]) // see if left brick continues on up
                                    {
                                        variable1 = secondLayerArr[i][j - 1];
                                        secondLayerArr[i][j - 1] = secondLayerArr[i][j];
                                        secondLayerArr[i - 1][j - 1] = secondLayerArr[i][j];
                                        secondLayerArr[i][j] = variable1;
                                        secondLayerArr[i + 1][j] = variable1;
                                    }
                                } else if (i < secondLayerArr.length - 1) {
                                    if (secondLayerArr[i][j - 1] == secondLayerArr[i + 1][j - 1]) // see if left brick continues on down
                                    {
                                        variable1 = secondLayerArr[i][j - 1];
                                        secondLayerArr[i][j - 1] = secondLayerArr[i][j];
                                        secondLayerArr[i + 1][j - 1] = secondLayerArr[i][j];
                                        secondLayerArr[i][j] = variable1;
                                        secondLayerArr[i + 1][j] = variable1;
                                    }
                                }
                            } else if (secondLayerArr[i - 1][j] != 0) //try to swap with the upper brick
                            {
                                if (i - 1 > 0) {
                                    if (secondLayerArr[i - 1][j] == secondLayerArr[i - 2][j]) // see if upper brick continues on up
                                    {
                                        variable1 = secondLayerArr[i - 1][j];
                                        secondLayerArr[i - 1][j] = secondLayerArr[i][j];
                                        secondLayerArr[i - 2][j] = secondLayerArr[i][j];
                                        secondLayerArr[i][j] = variable1;
                                        secondLayerArr[i + 1][j] = variable1;
                                    }
                                }
                            } else {
                                System.out.println("-1!");
                                return;
                            }
                        }
                    }
                    //else if iteration is not on the last column
                    else if (j < m - 1) {
                        number++;

                        //if brick from first layer is with the same number, on the same row and on the same indexes
                        if (firstLayerArr[i][j] == firstLayerArr[i][j + 1] && firstLayerArr[i][j] == number) {
                            //set brick on different rows on layer 2
                            secondLayerArr[i][j] = number;
                            secondLayerArr[i + 1][j] = number;
                        } else {  //if brick from first layer is with the same number, on different row and on the same indexes
                            //set brick on same row on layer 2
                            secondLayerArr[i][j] = number;
                            secondLayerArr[i][j + 1] = number;
                        }
                    }
                }
            }
            System.out.println();
        }


        //second layer output
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(secondLayerArr[i][j]+" ");
            }
            System.out.println();
        }


    }

    //checks for the other part of the brick
    private static boolean searchSame(int row, int col, int[][] brickArr, int number) {
        //validation that we are not going out of the brickwall
        if (col >= brickArr[0].length || (row >= brickArr.length)) {
            return false;
        }

        // Check if we have found the same number(the other part of the brick)
        if (brickArr[row][col] == number) {
            return true;
        }
        return false;
    }

}