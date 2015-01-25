package icsfilecreator;
import java.util.*;

/**
 * UI Driver for Event.java 
 */

public class Main {
    	//Create the actual event object used to create the ICS file
        private static Event event = new Event();
        
        private static Scanner input = new Scanner(System.in);
        
        //Event details
        private static String summary = "";
        private static String location = "";
        
        //All values initialized to -1 so that input validation loops work properly
        private static int startDay = -1;
        private static int startMonth = -1;
        private static int startYear = -1;
        private static int startHour = -1;
        private static int startMinute = -1;
        private static int endDay = -1;
        private static int endMonth = -1;
        private static int endYear = -1;
        private static int endHour = -1;
        private static int endMinute = -1;
        private static String menuSelection = "-1";

        public static void main(String[] args) {
        	
	        //Quiting the menu is the last option in the menu
	        final String QUIT = "12";
	        boolean displayMenu = true;
	        
	        //Display the interactive main menu
	        //While displayMenu is true.
            while (displayMenu) {
            	System.out.println("ICS EVENT MAKER");
                System.out.println("Main Menu");
                System.out.println("1. Set Event Summary");
                System.out.println("2. Set Event Location");
                System.out.println("3. Set Event Start Time and Date");
                System.out.println("4. Set Event End Time and Date");
	            System.out.println("5. Set Latitude and Longitude");
	            System.out.println("6. Set priority");
	            System.out.println("7. Set Classification");
	            System.out.println("8. Set Recurrence (off by default)");
	            System.out.println("9. Print Event");
	            System.out.println("10. Reset Event");
	            System.out.println("11. Write to File");
                System.out.println(QUIT + ". Quit");
                System.out.print("Enter menu selection: ");
                
                //Get User Input
                menuSelection = input.nextLine();
                
                //Actions depend on user inputs according to menu
                
                //Set summary
                if (menuSelection.equals("1")) {
                	summary = "";
                	
                    while (summary.equals("")) {
                    	System.out.print("Enter a summary of the event: ");
                    	summary = input.nextLine();
                    }
                    
                    event.setSummary(summary);
                }
                
                //Set location
                else if (menuSelection.equals("2")) {
                	location = "";
                	
                    while (location.equals("")) {
                    	System.out.print("Enter a location for the event: ");
                        location = input.nextLine();
                    }
                    
                    event.setLocation(location);
                }
                
                //Set start times
                else if (menuSelection.equals("3")) {
                	//set ALL the start values 
                    setStart();
                }
                
                //Set end times
                else if (menuSelection.equals("4")) {
                	//set ALL the end values
                	setEnd();
                
                }
                
                //Set lat and long
                else if (menuSelection.equals("5")) {
                	setLatLong();
                }
                
                //Set priority
                else if (menuSelection.equals("6")) {
	                int priority = -1;
	                
	                while (!event.setPriority(priority)) {
	                    System.out.print("Enter a priority (0-9): ");
	                    priority = input.nextInt();
	                }
	                
	                System.out.println("Priority Set");
                }
                
	            //Set classification
	            else if (menuSelection.equals("7")) {
	                String classification = "";
	                
	                while (!event.setClassification(classification)) {
	                    System.out.print("Enter classification (PUBLIC, PRIVATE, CONFIDENTIAL): ");
	                    classification = input.nextLine();
	                }
	                
	                System.out.println("Classification set");
	            }
                
                //Set recurrence
	            else if (menuSelection.equals("8")){
	                setRecurrence();
	            }
                
                //Print 
                else if (menuSelection.equals("9")) {
                	System.out.println(event.toString());
                }
                
	            //Reset event
	            else if (menuSelection.equals("10")){
	                if(decision("reset event")) event = new Event();
	            }
                
                //Write file
                else if (menuSelection.equals("11")) {
                	event.writeFile(); 
                }
                
                //Quit
                else if (menuSelection.equals(QUIT)) {
	                if(decision("quit")) { 
	                    displayMenu = false;
	                }
                }
                
	            else{
	            	System.out.println("Invalid input, please try again.");
	            }     
                
	            menuSelection = null;
            }
        }
        //END OF MAIN
        
    
    /**Method to ascertain that user wants to make a decision.
     *@param 'making' string containing the decision to be made
     *@return True if the user would like to continue, false if they want to return.
     */   
    public static boolean decision(String making){
        Scanner decision = new Scanner(System.in);
        String decided;
        
        while(true){
            System.out.print("Would you like to " + making + "? (Y/N): ");
            decided = decision.nextLine();
            decided.toLowerCase();
            
            if(decided.contains("y")||decided.contains("Y")){
            	decision.close();
                return true;
            }
            if(decided.contains("n")||decided.contains("N")){
                decision.close();
            	return false;
            }
        }
    }

    public static void setLatLong() {
        float lat = -10000;
        float longitude = -10000;
        
        while(!event.setLatitude(lat)) {
            System.out.print("Enter the latitude (-90 to 90): ");
            lat = input.nextFloat();
        }
        
        while(!event.setLongitude(longitude)) {
            System.out.print("Enter the longitude (-180 to 180): ");
            longitude = input.nextFloat();
        }
        
        event.setlatlong();
    }
    
   /**
   *Method to set the recurrence for the event.
   */
   public static void setRecurrence(){
	   
       Scanner option = new Scanner(System.in);
       String choice = new String();
       
       //Get Users Input
       while(!(choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4"))){
         System.out.printf("Would you like recurrence to be\n1.Daily\n2.Weekly\n3.Monthly\n4.Cancel Recurrence\nEnter Number: ");
         choice = option.nextLine().trim();
       }
       
       //Actions based on users input
       
       //Cancel
       if(choice.equals("4")){
    	   option.close();
    	   return;
       }
       
       //Daily
       else if(choice.equals("1")){
           choice = "a";
           
           while(!isInteger(choice)){
        	   System.out.print("How many days would you like to repeat this for?  Enter number: ");
        	   choice = option.nextLine().trim();
           }
           
           event.setRecurrence(1, Integer.parseInt(choice));
       }
       
       else if(choice.equals("2")){
           choice = "a";
           
           while(!isInteger(choice)){
        	   System.out.print("How many weeks would you like to repeat this for?  Enter number: ");
        	   choice = option.nextLine().trim();
           }
           
           event.setRecurrence(2, Integer.parseInt(choice));
       }
       
       else if(choice.equals("3")){
           choice = "a";
           
           while(!isInteger(choice)){
        	   System.out.print("How many months would you like to repeat this for?  Enter number: ");
        	   choice = option.nextLine().trim();
           }
           
           event.setRecurrence(3, Integer.parseInt(choice));
           option.close();
         }  
   }

   /**
    * This method will set all of the start times and dates by
    * calling the associated methods of Event.java
    */

    public static void setStart() {
    	//will always enter the loop because startYear is initialized to -1
        //same for the other values
        //will continue to loop while the method calls return false
        boolean valid = false;
        
        //Year, Month, Day
        while(!valid){
            while (!event.setStartYear(startYear)) {
               System.out.print("Enter a valid start year (2000 - 3000): ");
               startYear = input.nextInt();
            }
            
            while (!event.setStartMonth(startMonth)) {
               System.out.print("Enter a valid start month (1 - 12): ");
               startMonth = input.nextInt();
            }
            
            while (!event.setStartDay(startDay)) {
               System.out.print("Enter a valid start day (1 - 31): ");
               startDay = input.nextInt();
            }
            
            valid = event.verifyStartDate();
            if(!valid) System.out.print("Invalid date (check days in month for that year) please try again.");
        }
        
        //Hour, Minute
        while (!event.setStartHour(startHour)) {
            System.out.print("Enter a valid start hour (0 - 23): ");
            startHour = input.nextInt();
        }
        
        while (!event.setStartMinute(startMinute)) {
            System.out.print("Enter a valid start minute (0 - 59): ");
            startMinute = input.nextInt();
    	}
    }
  
    //Returns true if the string represents an integer.
    public static boolean isInteger(String str) {
    	//Check if null or empty
	    if (str == null) {
	            return false;
	    }
	    
	    int length = str.length();
	    if (length == 0) {
	            return false;
	    }
	    
	    //Check if just '-'
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	                return false;
	        }
	        i = 1;
	    }
	    
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c <= '/' || c >= ':') {
	                return false;
	        }
	    }
	    
	    return true;
	}

    /**
     * This method will set all of the end times and dates by
     * calling the associated methods of Event.java
     */
    
    public static void setEnd() {
        //will always enter the loop because endYear is initialized to -1
        //if startYear is not yet set, setEnd() exits!
	    boolean valid = false;
	    
	    while(!valid){
	        while (!event.setEndYear(endYear)) {
	           System.out.print("Enter a valid end year (2000 - 3000): ");
	           
	           if (startYear < 0) {
	        	   System.out.print("\nERROR: You must set a start time first!\n");
	        	   return;
	           }
	           
	           endYear = input.nextInt();
	           
	           //end values are checked to ensure that they're after start values
	           if (endYear < startYear) {
	        	   endYear = -1;
	        	   System.out.print("End can't be before start\n");
	           }
	        }
	        
	        while (!event.setEndMonth(endMonth)) {
	        	System.out.print("Enter a valid end month (1 - 12): ");
	        	endMonth = input.nextInt();
	        	if (endMonth < startMonth) {
	        		endMonth = -1;
	        		System.out.print("End can't be before start\n");
	        	}
	        }
	        
	        while (!event.setEndDay(endDay)) {
	        	System.out.print("Enter a valid end day (1 - 31): ");
	        	endDay = input.nextInt();
	        	if (endDay < startDay) {
	        		endDay = -1;
	        		System.out.print("End can't be before start\n");
	        	}
	        }
	        
	        valid = event.verifyEndDate();
        
	        if(!valid) System.out.print("Invalid date (check days in month for that year) please try again.");
	    	}
            	while (!event.setEndHour(endHour)) {
            		System.out.print("Enter a valid end hour (0 - 23): ");
                    endHour = input.nextInt();
                    
                    if (endHour < startHour && endDay == startDay) {
                            endHour = -1;
                            System.out.print("End can't be before start\n");
                    }
                }
                
                while (!event.setEndMinute(endMinute)) {
                    System.out.print("Enter a valid end minute (0 - 59): ");
                    endMinute = input.nextInt();
                    
                    if (endMinute < startMinute && endDay == startDay && endHour <= startHour) {
                            endMinute = -1;
                            System.out.print("End can't be before start\n");
                    }
                }
        }
    
}//END MAIN CLASS
