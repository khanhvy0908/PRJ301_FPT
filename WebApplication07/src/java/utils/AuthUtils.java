/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.UserDTO;

/**
 *
 * @author Admin
 */
public class AuthUtils {

    // get current Users
    public static UserDTO getCurrentUsers(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            return (UserDTO) session.getAttribute("user");
        }
        return null;
    }

    // check is logged or not
    public static boolean isLogged(HttpServletRequest request) {
        return AuthUtils.getCurrentUsers(request) != null;
    }

    // check if user has the input role?
    public static boolean hasRole(HttpServletRequest request, String role) {
        UserDTO user = getCurrentUsers(request);
        if (user != null) {
            String userRole = user.getRoleID();
            return userRole.equals(role);
        }
        return false;
    }
    
    public static boolean isAdmin(HttpServletRequest request){
        return hasRole(request, "AD");
    }

    public static boolean isManager(HttpServletRequest request){
        return hasRole(request, "MA");
    }
    
    public static boolean isUser(HttpServletRequest request){
        return hasRole(request, "MB");
    }
    
    public static String getLoginURL(){
        return "MainController?action=login";
    }
    public static String getAccessDeniedMessage(String action){
        return "You don't have permission to " + action +". Please contact administrator.";
    }
}
