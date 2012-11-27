package listener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author AD
 */
public class RequestListener implements ServletRequestListener {
    
    private static long totalEdits = 0;
    private static int TotalMonthEdits[] = new int[32];
    private static int Month = -1;
    private static int lastDay = -1;
    @Override
  public void requestInitialized(ServletRequestEvent sre) {

    ServletContext context = sre.getServletContext();
    ServletRequest request = sre.getServletRequest();


    synchronized (context){
        
       int tempDay, tempMonth;
        
        /* Get current date */
        Date date = new Date();
        
        /* Create a calendar */
        Calendar calendar = GregorianCalendar.getInstance();
        
        /* assign calendar to given date */
        calendar.setTime(date); 
        
        /* Get current Day */
        tempDay = calendar.get(Calendar.DAY_OF_MONTH);
        
        /* Get current Month */
        tempMonth = calendar.get(Calendar.MONTH);
        
        /* Initialize variables */
        if (Month == -1 ){
            
            lastDay = tempDay;
            Month = tempMonth; 
            for (int i = 1; i < 32 ; i++){
                TotalMonthEdits[i] = 0;
            }
        }
        
        if ( (((HttpServletRequest)request).getRequestURI()).equals("/imagestuck/EditImage.do") ) {
            
        totalEdits++;
 
            /* If one month has passed, empty array */
            if ( tempMonth - Month  > 1 ){

                for (int i = 1; i < 32 ; i++){
                    TotalMonthEdits[i] = 0;
                }

            }
            /* It's the same month */
            if ( tempMonth - Month == 0){

                TotalMonthEdits[tempDay]++;

            }
          }

            

        
    Month = tempMonth;
    lastDay = tempDay;
        
    }//synchronized

  }
    
    @Override
  public void requestDestroyed(ServletRequestEvent sre) {

  }
  
    public static long getEditCount() {
        
        return totalEdits;
        
    }
      
    synchronized public void WriteRequestsData( ){

        try{
 
            /* Write Total Edits data */
            FileWriter fstream = new FileWriter("/usr/local/tomcat/webapps/imagestuck/data.json");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("{\n   \"label\": \"Monthly Image Edits \",");
            String data = "";
            if(Month == 0 || Month == 2 || Month == 4 || Month == 6 || Month == 7 || Month == 9 || Month == 11){
                for (int i = 1; i < 32 ; i++){
                    if ( i == 31){

                        data += "[" + i + "," + TotalMonthEdits[i] + "]";
                    } 
                    else {
                        data += "[" + i + "," + TotalMonthEdits[i] + "],";
                    }

                }
            }
            else if(Month == 1){
                for (int i = 1; i < 29 ; i++){
                    if ( i == 28){

                        data += "[" + i + "," + TotalMonthEdits[i] + "]";
                    } 
                    else {
                        data += "[" + i + "," + TotalMonthEdits[i] + "],";
                    }

                }
            }
            else if(Month == 3 || Month == 5 || Month == 8 || Month == 10){
                for (int i = 1; i < 31 ; i++){
                    if ( i == 30){

                        data += "[" + i + "," + TotalMonthEdits[i] + "]";
                    } 
                    else {
                        data += "[" + i + "," + TotalMonthEdits[i] + "],";
                    }

                }
            }
            out.write("\n\"data\":[" + data + "]\n}");
            out.close();
        }
        catch (Exception e){
            
            System.err.println("Error: " + e.getMessage());
            
        }
    }

}