import java.util.*;

public class LoanPayment {
  
  
  public static void main(String[] args) { 

    Scanner sc             = new Scanner(System.in);
    
    // array variables to store user input
    double[] interestRates = {-1, -1, -1};
    int[] terms            = {-1, -1, -1}; // initialized this way to handle exceptions later
    
    // double variable to store loan amount
    double loanAmount      = 0;
    
    // Calls functions to get interest rates, terms, and loan amount
    interestRates          = getInterestRates(sc, interestRates);
    terms                  = getTerms(sc, terms);
    loanAmount             = getLoanAmount(sc, loanAmount);
  
    // prints the header and returns the array it uses for calculations in displayRates()
    int[] yearsOfTerm      = displayHeader(terms);
    displayRates(yearsOfTerm, interestRates, loanAmount);
    
    sc.close();
    
  } // End main()
  
  
  
  
  /**
   * This method collects and returns data for the interestRates array.
   * Negative and promotional interest rates happen, 
   * as well as interest rates that exceed 100%,
   * so there's no validation to check for those.
   * There is a try/catch to catch non-double values
   * */
  public static double[] getInterestRates(Scanner sc, double[] interestRates) {
    
    // These are initialized to hold interestRates[0], [1], and [2] for readability later
    double min           = 0; 
    double max           = 0;
    double iter          = 0;
    
    try {
  
      System.out.print("Enter the starting annual interest rate as a percent (n.nnn)   : ");
      interestRates[0]   = sc.nextDouble();
      min                = interestRates[0];
      
      // ending rate must be larger than starting rate for this to work
      while (interestRates[1] < min) {
      
        System.out.print("\nEnter the ending annual interest rate as a percent (n.nnn)     : ");
        interestRates[1] = sc.nextDouble();
        max              = interestRates[1];
      
        if (max < min) {
        
          System.out.print("\nPlease ensure that the ending interest rate is larger than the starting interest rate.");
        
        }
      }
      
      // increment must be positive and must not be greater than the difference between the ending and starting rate
      
      while (interestRates[2] <= 0 || interestRates[2] > max - min) {
        
        System.out.print("\nEnter the annual interest rate increment as a percent (n.nnn)  : ");
        interestRates[2] = sc.nextDouble();
        iter             = interestRates[2];
        
        if (iter <= 0 || iter > max - min) {
        
          System.out.printf("\nPlease use only positive increment values less than %.3f.\n", max - min);
          
        }
      }
      
    } catch (Exception e) {
      
      System.out.println("The number you entered was not recognized. Please restart the program.");
      System.exit(0);
      
    }
    
    return interestRates;
  }
  
  /**
   * This method collects and returns data for the terms array.
   * Terms and increments must be positive.
   * There's quite a bit of exception handling going on to make this work.
   * 
   * */
  public static int[] getTerms(Scanner sc, int[] terms) {
  
    // similar logic as the getInterestRates method
    int min       = 0;
    int max       = 0;
    int iter      = 0;
    try {
      
      // input validation for first term
      
      while (terms[0] <= 0) {
      
        System.out.print("\nEnter the first term in years for calculating payments         : ");
        terms[0] = sc.nextInt();
        min      = terms[0];
        
        if (min <= 0) {
        
          System.out.println("Please enter a valid term.");
        
        }
      
      }
      
      // input validation for last term. Last term must be greater than first term.
      
      while (terms[1] <= 0 || terms[1] <= min) {
      
        System.out.print("\nEnter the last term in years for calculating payments          : ");
        terms[1] = sc.nextInt();
        max      = terms[1];
        
        if (max <= 0 || max <= min) {
        
          System.out.println("Please enter a valid term. This term must be greater than the first term.");
        
        }
      
      }
      
      // input validation for increment. Loops if increment is negative or greater than difference between terms
      
      while (terms[2] <= 0 || terms[2] > max - min) {
      
        System.out.print("\nEnter the term increment in years                              : ");
        terms[2] = sc.nextInt();
        iter     = terms[2];
        
        if (iter <= 0 || iter > max - min) {
        
          System.out.println("Please enter a valid term increment. This value must be less than the difference between the first and second terms.");
        
        }
      }
      
    } catch (Exception e) {
    
        System.out.println("The number you entered was not recognized. Please restart the program.");
        System.exit(0);
    
    }
    
    return terms;
    
  }
    
    /**
     * 
     * This method collects and returns the total loan amount from the user.
     * Only positive values may be used.
     * Partial cents may be entered, but final results will only print to two decimal places.
     * 
     * */
    
    public static double getLoanAmount(Scanner sc, double loanAmount) {
    
      try {
      
        // Controls for loan amount. Only positive nonzero values are allowed.
        
        while (loanAmount <= 0) {
      
          System.out.print("\nEnter the loan amount                                          : $");
          loanAmount = sc.nextDouble();
          System.out.println(); // for consistency of formatting
          
          if (loanAmount <= 0) {
        
            System.out.println("Please enter a valid loan amount. This amount must be a positive number in the format nnnnn.nn");
        
        }
      
      }
      
      } catch (Exception e) {
      
        System.out.println("The number you entered was not recognized. Please restart the program.");
        System.exit(0);
      
      }
    
      return loanAmount;
      
    }
    
    /**
     * This method is a fairly straightforward method to print the header.
     * 
     * If the max value doesn't divide evenly by the increment,
     * it prints as the final value no matter the interval.
     * 
     * For reference:
     * 
     * terms[0] is the lowest term
     * terms[1] is the highest term
     * terms[2] is the term increment
     * */
    public static int[] displayHeader(int[] terms) {
      
      // a few variables for readability
      int min         = terms[0];
      int max         = terms[1];
      int iter        = terms[2];
      
      int currentTerm = min - iter; // used to calculate the next term in the loop. Starts one iteration back.
      int countTerms  = getTerms(min, max, iter); // gets number of iterations in loop 
      int[] allTerms  = new int[countTerms];
      
      // creates an array containing the interest rates, saved in allTerms[]
      
      for (int i = 1; i <= countTerms; i++) {
        
        int currentIndex = i - 1; // introducing a new variable for readability again
        
        currentTerm += iter;
 
          if (currentTerm <= max) {
        
            allTerms[currentIndex] = currentTerm;
        
          } else if (currentTerm > max) { 
          
            allTerms[currentIndex] = max;
        
        }
      
        
      }
      
      
      // prints header
      
      System.out.print("Interest \nRate         ");
      
      for (int j = 0; j < countTerms; j++) {
      
        System.out.printf("%d Years      ", allTerms[j]);
        
      }
      
      System.out.println(); //This will help format later
      
      return allTerms;
    
    }
    
    /**
     * 
     * Calculates and displays rates
     * 
     * 
     * This is the most complex method in this program
     * 
     * */
    
    public static void displayRates(int[] yearsOfTerm, double[] interestRates, double loanAmount) {
      
      // a few variables for readability
      double min            = interestRates[0];
      double max            = interestRates[1];
      double iter           = interestRates[2];
      
      double mir            = 0; // monthly interest rate
      int mtp               = 0; // months to pay
      double annuityFactor  = 0;
      
    
      int countRates        = getTerms(min, max, iter);
      double currentRate    = min; 
      
      // iterates through rates
      
      while (currentRate < max + iter) {
        
        if (currentRate <= max) {
         
          System.out.printf("%.3f ", currentRate);
        
        } else {
        
          System.out.printf("%.3f ", max);
          currentRate        = max;
        
        }
        
        // for each rate, iterates through years
        
        for (int i = 0; i < yearsOfTerm.length; i++) {
          
          mir               = (currentRate / 100) / 12; // converts to a decimal and divides by 12
          mtp               = yearsOfTerm[i] * 12;
          annuityFactor     = (mir * Math.pow((1 + mir), mtp)) / (Math.pow((1 + mir), mtp) - 1); 
          
          System.out.printf("       $%.2f", annuityFactor * loanAmount);
          
          
        }
        
        System.out.println(); // this will simplify formatting
        
        if (currentRate != max) {
        
          currentRate         += iter; // sets next current rate
        
        } else {
        
          break; // trying to avoid the potential for an infinite loop
        
        }
      } 
      
      
    
    }
    
    /**
     * This method calculates the number of iterations from the terms[] array.
     * 
     * It is overloaded below to do exactly the same thing for the interestRates array.
     * */
    
    public static int getTerms(int min, int max, int iter) {
    
      int newTerm  = min - iter; // setting a starting point
      int numTerms = 0;
      
      while (newTerm < max) {
      
        numTerms++;
        newTerm += iter;
      
      }
      
      return numTerms;
    
    }
    
    /**
     * Same as above, but accepts double input.
     * 
     * */
    
    public static int getTerms(double min, double max, double iter) {
    
      double newTerm = min - iter; // setting a starting point
      int numTerms   = 0;
      
      while (newTerm < max) {
      
        numTerms++;
        newTerm += iter;
      
      }
      
      return numTerms;
    
    }
    
    
    /**
     * 
     * This method divides doubles by 100 to convert them from a percentage
     * 
     * */
    
    public static double asPercent(double num) {
    
      return num / 100;
    
    }
  
}
