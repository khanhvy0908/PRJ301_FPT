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

    public static UserDTO getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            return (UserDTO)session.getAttribute("user");
        }
        return null;
    }
    
    public static boolean isLoggedIn(HttpServletRequest request){
        return getCurrentUser(request) != null;
    }
    
    public static boolean hasRole(HttpServletRequest request, String role){
        UserDTO user = getCurrentUser(request);
        if(user != null){
            String userRole = user.getRole();
            return userRole.endsWith(role);
        }
        return false;
    }
    
    public static boolean isInstructor(HttpServletRequest request){
        return hasRole(request, "Instructor");
    }
    public static boolean isStudent(HttpServletRequest request){
        return hasRole(request, "Student");
    }
}
