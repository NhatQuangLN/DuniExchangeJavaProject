/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.User;

import Entity.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author truon
 */
public class LoginDAO {
    private Connection con = null;
    private PreparedStatement stm = null;
    private ResultSet rs = null;
    
    //Trả về true nếu có tồn tại username
    public boolean isHaveUsename(String username) throws SQLException{
        String query = "select userUserName from UserAccount where userUserName = ?";
        String user = null;
        try {
            con = DBConnection.DBConnection.makeConnection();

            if (con != null) {
                stm = con.prepareStatement(query);
                stm.setString(1, username);

                rs = stm.executeQuery();
                if (rs.next()) {
                    user = rs.getString(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null)con.close();
            return user!=null;
        }
    }
    
    //Ktra xem password người dùng nhập vào có giống password trong DB không
    public boolean checkPassword(String username,String password) throws SQLException{
        String query = "select userPassword from UserAccount where userUserName = ?";
        String pass = null;
        try {
            con = DBConnection.DBConnection.makeConnection();

            if (con != null) {
                stm = con.prepareStatement(query);
                stm.setString(1, username);

                rs = stm.executeQuery();
                if (rs.next()) {
                    pass = rs.getString(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null)con.close();
            return BCrypt.checkpw(password, pass);
        }
    }

    
    public Account getAccountByUserName(String username) throws SQLException{
        try {
            con = DBConnection.DBConnection.makeConnection();

            if (con != null) {
                String sql = "select * from UserAccount where userUsername = ?";

                stm = con.prepareStatement(sql);
                stm.setString(1, username);

                rs = stm.executeQuery();
                while (rs.next()) {
                    return new Account(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getDate(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getFloat(9),
                            rs.getBoolean(10),
                            rs.getBoolean(11));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return null;
    }
        
    public static void main(String[] args) throws Exception {
        LoginDAO dao = new LoginDAO();
        String username = "trang";
        String password = "banana";
        if (dao.isHaveUsename(username)) {
            if (dao.checkPassword(username,password)) {
                System.out.println("Dang nhap thanh cong");
                Account temp = dao.getAccountByUserName(username);
                System.out.println(temp.toString());
            }else{
                System.out.println("Sai mk");
            }
        }else{
            System.out.println("Khong ton tai nguoi dung");
        }
    }
}
