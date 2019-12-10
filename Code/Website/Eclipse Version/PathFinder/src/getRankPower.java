import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class getRankPower 
{
	
	public getRankPower()
	{
		
	}
	public void getRank()
	{
		
	}
	public String getNameRights() {
		// TODO Auto-generated method stub
		return null;
	}
	public static String getCookie(HttpServletRequest request) {
		String powerUsername = "";
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (powerUsername.equals(cookie.getValue())) {
	                return cookie.getValue();
	            }
	        }
	    }
	    return null;
	}
}
