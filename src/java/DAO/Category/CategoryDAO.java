/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Category;

import DAO.User.UserDAO;
import DBConnection.DBConnection;
import Entity.Category;
import Entity.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Minky
 */
public class CategoryDAO {
    public static List<Category> getAllCategory() throws SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        String query = "select * from Category";
        List<Category> categories = new ArrayList();
        try {
            con = DBConnection.makeConnection();

            if (con != null) {
                stm = con.prepareStatement(query);  

                rs = stm.executeQuery();
                while (rs.next()) {
                    int categoryID = rs.getInt(1);
                    String categoryName = rs.getString(2);
                    String categoryIcon = rs.getString(3);
                    categories.add(new Category(categoryID, categoryName, categoryIcon));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return categories;
    }
}