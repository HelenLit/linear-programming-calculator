Linear Programming Calculator - ReadMe

      linear-programming-calculatorThe Linear Programming Calculator is a program that calculates the optimal solution 
      in linear programming using the Simplex method. It also provides the option to calculate the dual form of the problem. 
      This ReadMe file provides an overview of the program and instructions on how to use it effectively.

      Main Window

      Upon launching the program, the main window will appear with the following components:

      Top Menu: The top menu contains the following options:

      "File" -> "Exit": Allows you to exit the program.
      "Settings" -> "Matrix size": Opens a new window to customize the size of the matrix of constraints by selecting the number of equations and variables.
      "Help" -> "ReadMe": Opens a window with information on how to use the program.
      "Help" -> "About": Displays information about the author of the program.
      Matrix of Constraints: This section displays the matrix of constraints. The size of the matrix can be adjusted using the "Settings" -> "Matrix size" button. 
      The matrix contains the following columns:

      Variables coefficients: Columns labeled as "x1", "x2", "x3", and so on, represent the coefficients of the variables in the constraints.
      Sign: The "sign" column indicates the sign of each equation. Possible options are ">=", "=", and "<=".
      Right-hand side: The "b" column represents the right-hand side of the constraint equation.
      Calculate Primal and Calculate Dual Buttons: These buttons allow you to calculate the optimal solution for the primal or dual form of the problem, respectively.

      Objective Function: This section includes the following components:

      Goal Type ComboBox: The goal type combobox allows you to select the objective of the problem. It has two options: "Min" (Minimize) and "Max" (Maximize).
      Function Coefficients: The matrix with one row displays the coefficients of the variables in the objective function. 
      The columns are labeled as "x1", "x2", "x3", and so on.
      Constant Term: The column labeled "c" represents the constant term in the objective function.
      Negative Solution Checkbox: The checkbox allows you to specify the possibility of a negative solution.

      Calculation Window

      After clicking on the "Calculate primal" or "Calculate dual" button, a new window will appear showing the steps of the calculation process. 
      It provides a detailed overview of each iteration and the resulting tableau.
      Table looks like: 
                          W |  Z |  x1 |  x2 |  x- | s1 |  s2 |  a1 |  RHS
                           ---------------------------------------------------
                           -1    0    0     0     0     0     0     1     0   <= phase 1 objective
                            0    1   -15   -10    0     0     0     0     0   <= phase 2 objective
                            0    0    1     0     0     1     0     0     2   <= constraint 1
                            0    0    0     1     0     0     1     0     3   <= constraint 2
                            0    0    1     1     0     0     0     1     4   <= constraint 3

                          W: Phase 1 objective function
                          Z: Phase 2 objective function
                          x1 & x2: Decision variables
                          x-: Extra decision variable to allow for negative valuess
                          s1 & s2: Slack/Surplus variables
                          a1: Artificial variable
                          RHS: Right hand side
      Solution Window

      If the calculation is successful, a new window will display the optimal solution. 
      It presents the solution in a user-friendly format. For the primal form, it shows the values of the variables (X*) and the objective function (F(X)). For the dual form, it presents the dual variables and the dual objective function.

      Exception Windows

      In case of unsuccessful calculations, the program will display an exception window with a problem explanation. 
      There are two possible exception situations:

      "No feasible solution": This exception occurs when there is no feasible solution to the linear programming problem.

      "Unbounded solution": This exception occurs when the linear programming problem has an unbounded solution.

      "Incorrect input": This exception occurs when user enteres incorrect values.

      These exception windows provide information about the exceptional situations encountered during the calculation process.

      I hope you find the Linear Programming Calculator useful for solving linear programming problems. 
      Should you have any further questions or need assistance, please refer to the "Help" menu or reach out to the author.
      
      
      КОНТРОЛЬНІ ПРИКЛАДИ

Приклад №1, пряма задача, існуючий і додатній розв'язок:

![Screenshot](images/img.png)

![Screenshot](images/img_1.png)

![Screenshot](images/img_2.png)

![Screenshot](images/img_3.png)
 
Приклад №2, двоїста задача, існуючий і додатній розв'язок:

![Screenshot](images/img_4.png)

![Screenshot](images/img_5.png)

![Screenshot](images/img_6.png)


Результат у SimplexWin:

![Screenshot](images/img_7.png)


Приклад №3, пряма задача, існуючий і від'ємний розв'язок:
 
![Screenshot](images/img_8.png)

![Screenshot](images/img_9.png)

![Screenshot](images/img_10.png)

![Screenshot](images/img_11.png)
 

Приклад №4, пряма задача, неіснючий розв'язок, виняток "Необмежений розв'язок":
 
![Screenshot](images/img_12.png)

![Screenshot](images/img_13.png)

![Screenshot](images/img_14.png)


Приклад №5, пряма задача, неіснючий розв'язок, виняток "Неможливий розв'язок":
 
![Screenshot](images/img_15.png)

![Screenshot](images/img_16.png)


Приклад №6, пряма задача, неіснючий розв'язок, виняток "Неправильно введені дані":

![Screenshot](images/img_17.png)

![Screenshot](images/img_18.png)
 

Кнопка “Open ReadMe” у верхньому меню:

![Screenshot](images/img_19.png)


Кнопка “About” у верхньому меню:

![Screenshot](images/img_20.png)

![Screenshot](images/img_21.png)


