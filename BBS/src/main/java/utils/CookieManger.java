package utils;

import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManger {
	
	public static void makeCookie(HttpServletResponse res, String cName, String cValue, int cTime) {
		Cookie cookie = new Cookie(cName, cValue);
		cookie.setPath("/");
		cookie.setMaxAge(cTime);
		res.addCookie(cookie);
	}
	
	public static String readCookie(HttpServletRequest req, String cName) {
		String cookieValue = "";
		
		Cookie[] cookie = req.getCookies();
		if (cookie != null) {
			for (Cookie c : cookie) {
				String cookieName = c.getName();
				if (cookieName.equals(cName)) {
					cookieValue = c.getValue();
				}
			}
		}
		
		return cookieValue;
	}
	
	public static void deleteCookie(HttpServletResponse res, String cName) {
		makeCookie(res, cName, "", 0);
		
	}

}
