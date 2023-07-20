

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DepositeCode")
public class DepositeCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		long account_num= Long.parseLong(request.getParameter("acn"));
		String name=request.getParameter("cname");
		String password=request.getParameter("psw");
		double amount=Double.parseDouble(request.getParameter("amt"));
		double balance=0.0;
		
				
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sathyadb","sathyadb"); 
			
			PreparedStatement ps= con.prepareStatement("select balance from sdfc_bank where account_num=? and name=? and password=?");
			ps.setLong(1, account_num);
			ps.setString(2, name);
			ps.setString(3, password);
			ResultSet rs=ps.executeQuery();
			
			out.print("<body bgColor='royalblue'>");
			out.print("<table border='1' align='center' cellpadding='10px'>");
			if(rs.next()) {
			balance = rs.getDouble(1);
			}
			
			
		
		out.print("<td><font color=white font size=6>Before Deposite Account Balance: "+balance+"</td>");
		out.print("<tr>");
		out.print("<td><font color=white font size=6>Deposite Amount is: "+amount+"</td>");
	
		
		
		 balance = balance+amount;
		
		PreparedStatement ps1= con.prepareStatement("update sdfc_bank set balance=? where account_num=?");
		 ps1.setDouble(1, balance);
		 ps1.setLong(2, account_num);
		
		 ps1.executeUpdate();   //you were using old object:
		
		out.print("<tr>");
		out.print("<td><font color=white font size=6>After Deposite Account Balance:"+balance+"</td>");
		con.close();
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}

}
