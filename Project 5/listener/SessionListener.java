package listener;

import java.util.Date;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 *
 * @author AD
 */


public class SessionListener implements HttpSessionListener {

  private static int sessionCount = 0;
  private static int sessionTotal = 0;

  public static int SessionListener() {
    return sessionCount;
  }
  
  public static int TotalSessionListener() {
    return sessionTotal;
  }

  public void sessionCreated(HttpSessionEvent se) {
    HttpSession session = se.getSession();
    session.setMaxInactiveInterval(60);
    synchronized (this) {
      sessionCount++;
      sessionTotal++;
    }
    
    System.out.println(sessionCount);
    System.out.println(sessionTotal);
  }

  public void sessionDestroyed(HttpSessionEvent se) {

    HttpSession session = se.getSession();
    String id = session.getId();
    synchronized (this) {
      --sessionCount;
    }
    System.out.println(sessionCount);
  }
}