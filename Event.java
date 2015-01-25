package icsfilecreator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.TimeZone;

/**
 * Class Event
 *
 * Creates a new Event object
 * 
 * Has a whole bunch of methods, some called upon by Main.java
 *
 */

public class Event {
	// EVENT VALUES
	
    //Start date and time of the event
    //Form of "yyyymmddThhmmss" 
    //The set methods rely on this exact format
    private StringBuilder start_date_time;
    
    //End date and time of the event
    //In the form of "yyyymmddThhmmss"
    private StringBuilder end_date_time;
    
    //Description of the event
    private String summary;
    
    //Location of event
    private String location;
    
    //BufferedWriter to write a event file
    BufferedWriter eventWriter;
    
    //To hold recurrences;
    private String recurrence;
    
    //A way of users designatinge vents as public (default), private or confidential.
    private String classification;
    
    //This property value specifies latitude and longitude
    private String geoPosition;
    
    //Priority of event (0-9, 0 being unspecified, 1 being highest priority and 9 being lowest
    private int priority;
   
    //Value to show if the event is recurring or not
    private boolean recurring;

    //Longitude and latitude values
    private float longitude;
    private float latitude;
    
    //Tracks if lat and long are set
    private boolean latlongisset;
    private String timeZoneIdentifier;

    /**
     * Default constructor
     */
    
    public Event() {
        start_date_time = new StringBuilder();
        start_date_time.append("00000000T000000");
        
        end_date_time = new StringBuilder();
        end_date_time.append("00000000T000000");
            
        priority = 0;
        summary = "";
        location = "";
        latlongisset = false;
        eventWriter = null;
		recurring = false;
    }

    //Save file to default name
    public boolean writeFile() {
            try {
            	return writeFile("Event.ics");
            } 
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
    }

    /**
     * Writefile()
     * Writes the event to a .ics file
     * @param  filename - The filename to write to
     * @return TRUE on successful write, false otherwise
     * @throws IOException 
     */
    
    public boolean writeFile(String filename) throws IOException { 
        //Create Writer
    	
        try {
        	eventWriter = new BufferedWriter(new FileWriter(new File(filename)));
           
            //Write file contents
            write(eventWriter, "BEGIN:VCALENDAR");
            write(eventWriter, "VERSION:2.0");
            write(eventWriter, "PRODID:-//MEKONG//MEKONGCALENDAR v1.0//EN");
            write(eventWriter, "BEGIN:VEVENT");
            write(eventWriter, "DTSTART:" + start_date_time.toString());
            write(eventWriter, "DTEND:" + end_date_time.toString());
		    write(eventWriter, "SUMMARY:" + summary);
		    write(eventWriter, "LOCATION:" + location);
		    
		    if (latlongisset) {
		        write(eventWriter, "GEO:" + Float.toString(latitude) + ";" + Float.toString(longitude));
		    }
		    
	        write(eventWriter, "CLASS:" + classification);
	        write(eventWriter, "TZID:" + timeZoneIdentifier);
	        write(eventWriter, "PRIORITY:" + priority);
	        
		    if(recurring){
		    	write(eventWriter, "RRULE:" + recurrence);
		    }
		    
            write(eventWriter, "END:VEVENT");
            write(eventWriter, "END:VCALENDAR");
                
            //Close writer
            eventWriter.close();
            return true;
        }
        
        catch (IOException e) {
        	System.out.println("Error writing file");
            return false;
        }
    }
    

    /**
    *Method that prints start_date_time
    */
    
    public void printStartDateTime() {
        System.out.println("Start date and time: " + start_date_time);
    }

   /**
   *Method that prints end_date_time
   */
    
    public void printEndDateTime() {
    	System.out.println("End date and time: " + end_date_time);
    }

   /**
   * Method that returns a string representation of the event
   */
    
    public String toString() {
        String returnString = "Start time: " + start_date_time + "\n" +
        "End time: " + end_date_time + "\n" +
        "Summary: " + summary + "\n" +
        "Location: " + location + "\n" +
	    "Latitude: " + latitude + "\n" +
	    "Priority: " + priority + "\n" +
	    "Classification: " + classification + "\n" +
	    "Longitude: " + longitude + "\n" +
		"Recurrence setting: " + recurrence;
        
        return returnString;
    }

    /**
    * @returns true if latitude is between -90 and 90
    */
    
    public boolean setLatitude(float latitude) {
        if (latitude < -90 || latitude > 90) {
            return false;
        }
        this.latitude = latitude;
        return true;
    }
    
    /**Sets the recurrence for the file
     * @param choice Integer corresponding to option chosen by the user
     * @param occurences The number of occurences the user chose
     */
    
    public void setRecurrence(int choice, int occurences){
    	this.recurring = true;
        switch(choice){
            case 1: this.recurrence = "FREQ=DAILY;COUNT=" + Integer.toString(occurences);
            	break;
            case 2: this.recurrence = "FREQ=WEEKLY;COUNT=" + Integer.toString(occurences);
                break;
            case 3: this.recurrence = "FREQ=MONTHLY;COUNT=" + Integer.toString(occurences);
                break;
          default: System.out.println("Invalid input!");
                break;
        }
    }

    /**
     * @returns true if longitude is between -180 and 180
     */

    public boolean setLongitude (float longitude) {
        if (longitude < -180 || longitude > 180) {
            return false;
        }
        this.longitude = longitude;
        return true;
    }

    /**
     *Method that tells writefile to write lat and long to the file
     */
    public void setlatlong() {
        latlongisset = true;
    }

   /**
   *Method that sets the summary for the event
   */
    
    public void setSummary(String summary) {
        this.summary = summary;
    }

   /**
   *Method that sets the location for the event
   */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the hour 
     * all other time/date set functions follow
     * the same logic as this method.
     *
     * @param  hour The hour to set
     * @return      TRUE on good input
     *              FALSE on bad input
     */

    public boolean setStartHour(int hour) {
	    String hourString = "";
	    
	    //Quit on bad input
	    if (hour < 0 || hour > 23) {
	            return false;
	    }
	    
	    hourString = Integer.toString(hour);
	    
	    //turn 1 into "01", etc 
	    if (hour < 10) {
	            hourString = "0" + Integer.toString(hour);
	    }
	    
	    //modify the stringbuilder with the correct values
	    start_date_time.setCharAt(9, hourString.charAt(0));
	    start_date_time.setCharAt(10, hourString.charAt(1));
	    
	    return true;
    }

    /**
     * Sets the minute for the starting date
     * @param minute Minute specified by user for starting date
     */
    
    public boolean setStartMinute(int minute) {
        String minuteString = "";
        
        if (minute < 0 || minute > 59) {
        	return false;
        }
        
        minuteString = Integer.toString(minute);
        
        if (minute < 10) {
            minuteString = "0" + Integer.toString(minute);
        }
        
        start_date_time.setCharAt(11, minuteString.charAt(0));
        start_date_time.setCharAt(12, minuteString.charAt(1));
        return true;
    }

    /**
     * Sets the starting day for the date.
     * @param day User specified day.
     */
    
    public boolean setStartDay(int day) {
            String dayString = "";
            
            if (day < 1 || day > 31) {
                return false;
            }
            
            dayString = Integer.toString(day);
            
            if (day < 10) {
                dayString = "0" + Integer.toString(day);
            }
            
            start_date_time.setCharAt(6, dayString.charAt(0));
            start_date_time.setCharAt(7, dayString.charAt(1));
            
            return true;
    }

    /**
     * Sets the starting month for the date.
     * @param month User specified month.
     */
    
    public boolean setStartMonth(int month) {
        String monthString = "";
        
        if (month < 1 || month > 12) {
            return false;
        }
        
        monthString = Integer.toString(month);
        
        if (month < 10) {
            monthString = "0" + Integer.toString(month);
        }
        
        start_date_time.setCharAt(4, monthString.charAt(0));
        start_date_time.setCharAt(5, monthString.charAt(1));
        return true;
    }

    /**
     * Method for setting starting year.
     * @param year User specified starting year.
     */
    
    public boolean setStartYear(int year) {
        String yearString = "";
        
        if (year < 2000 || year > 3000) {
                return false;
        }
        
        yearString = Integer.toString(year);
        start_date_time.setCharAt(0, yearString.charAt(0));
        start_date_time.setCharAt(1, yearString.charAt(1));
        start_date_time.setCharAt(2, yearString.charAt(2));
        start_date_time.setCharAt(3, yearString.charAt(3));
        
        return true;
    }

    //END TIME AND DATE

    /**
     * Method for setting end minute.
     * @param minute User specified ending minute.
     */
    
    public boolean setEndMinute(int minute) {
        String minuteString = "";
        
        if (minute < 0 || minute > 59) {
            return false;
        }
        
        minuteString = Integer.toString(minute);
        
        if (minute < 10) {
            minuteString = "0" + Integer.toString(minute);
        }
        
        end_date_time.setCharAt(11, minuteString.charAt(0));
        end_date_time.setCharAt(12, minuteString.charAt(1));
        
        return true;
    }

    /**
     * Method for setting end hour.
     * @param hour User specified ending hour.
     */
    
    public boolean setEndHour(int hour) {
        String hourString = "";
        
        if (hour < 0 || hour > 23) {
            return false;
        }
        
        hourString = Integer.toString(hour);
        
        if (hour < 10) {
            hourString = "0" + Integer.toString(hour);
        }
        
        end_date_time.setCharAt(9, hourString.charAt(0));
        end_date_time.setCharAt(10, hourString.charAt(1));
        
        return true;
    }

    /**
     * Method to set end day.
     * @param day User specified end day.
     */
    
    public boolean setEndDay(int day) {
        String dayString = "";
        
        if (day < 1 || day > 31) {
            return false;
        }
        
        dayString = Integer.toString(day);
        
        if (day < 10) {
            dayString = "0" + Integer.toString(day);
        }
        
        end_date_time.setCharAt(6, dayString.charAt(0));
        end_date_time.setCharAt(7, dayString.charAt(1));
        
        return true;
    }

    /**
     * Method to set end month.
     * @param month User specified end month.
     */
    public boolean setEndMonth(int month) {
            String monthString = "";
            
            if (month < 1 || month > 12) {
                return false;
            }
            
            monthString = Integer.toString(month);
            
            if (month < 10) {
                monthString = "0" + Integer.toString(month);
            }
            
            end_date_time.setCharAt(4, monthString.charAt(0));
            end_date_time.setCharAt(5, monthString.charAt(1));
            
            return true;
    }

    /**
     * Method to set end year.
     * @param year User specified end year.
     */
    
    public boolean setEndYear(int year) {
        String yearString = "";
        
        if (year < 2000 || year > 3000) {
            return false;
        }
        
        yearString = Integer.toString(year);
        end_date_time.setCharAt(0, yearString.charAt(0));
        end_date_time.setCharAt(1, yearString.charAt(1));
        end_date_time.setCharAt(2, yearString.charAt(2));
        end_date_time.setCharAt(3, yearString.charAt(3));
        
        return true;
    }
        
  /**
   * Method to set classification of event (PUBLIC, PRIVATE, CONFIDENTIAL)
   * @param userClassification user specified classification.
   * @return Returns false if the user input does not match one of the three options.  If valid returns true.
   */
    
    public boolean setClassification(String userClassification) {
        String input = userClassification.toUpperCase();
        
        //quit on bad input
        if (!input.equals("PUBLIC")
            && !input.equals("PRIVATE")
            && !input.equals("CONFIDENTIAL")) {
            return false;
        }
        
	    else this.classification = input;
	            return true;
    }
        
  /**
   * Method to set geographic position.
   * @param latitude User specified latitude.
   * @param longitude User specified longitude.
   */
    
    public void setGeoPosition(float latitude, float longitude ) {
        String input = String.valueOf(latitude)+";"+String.valueOf(longitude);
        this.geoPosition = input;
    }
        
  /**
   * Method to set priority of event.
   * @param priorityNum User specified priority.
   * @return Returns true if the priority is valid.  False otherwise.
   */
    public boolean setPriority(int priorityNum) {
        if(priorityNum < 0 || priorityNum > 9){
                return false;
        }
        priority = priorityNum;
        return true;
    }
        
  /**
   * Method to print the Time Zone.
   */
    public void printTimeZoneID() {
        System.out.println(TimeZone.getAvailableIDs());
    }
        
   /**
    * Method used to set the time zone ID
    * @param ID ID of the time zone.
    * @return Returns true if the time zone is valid.
    */
    
    public boolean setTimeZoneID(String ID){
        String[] validIDs = TimeZone.getAvailableIDs();
        
        for (String str : validIDs) {
            if (str != null && str.equals(ID)) {
                timeZoneIdentifier = ID;
                return true;
            }
        }
        
        return false;
    }
        
  /**
   *Method to write a string to a file
   *@param eventWriter The BufferedWriter that is used to write to the file
   *@param string String to write to the file.
   */
    
    public static void write(BufferedWriter eventWriter, String string){
	    try{
	        eventWriter.write(string, 0, string.length());
	        eventWriter.newLine();
	    }
	    
        catch (IOException e){
        	System.err.println("Caught IOException: " + e.getMessage());
        }
    }
        
  /**
   *Method to verify that the start date is a valid date.
   *@return Returns true if the current date is a valid date.
   */
    
  public boolean verifyStartDate() {
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
      try {
          df.parse(start_date_time.toString().substring(0, 8));
          return true;
      } 
      catch (ParseException e) {
          return false;
      }
  }
  
	/**
	 *Method to verify that the end date is a valid date.
	 *@Return Returns true of the current date is a valid date.
	 */
  
    public boolean verifyEndDate() {
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	    
	    try {
	        df.parse(end_date_time.toString().substring(0, 8));
	        return true;
	    }
	    catch (ParseException e) {
	        return false;
	    }
    }
}
