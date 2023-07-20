

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

@WebServlet("/TransferCode")
public class TransferCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	   response.setContentType("text/html");
    	   PrintWriter out=response.getWriter();
    	   
    	
    	    long account_num= Long.parseLong(request.getParameter("acn"));
   			String name=request.getParameter("cname");
   			String password=request.getParameter("psw");
   			long taccount=Long.parseLong(request.getParameter("tacn"));
   			double amount=Double.parseDouble(request.getParameter("amt"));
   			double tbalance=0.0,balance=0.0;
   			try {
   				Class.forName("oracle.jdbc.driver.OracleDriver");
   				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sathyadb","sathyadb");
   				
   				//Your account balance:
   				PreparedStatement ps=con.prepareStatement("select balance from sdfc_bank where account_num=? and name=? and password=?");
   				ps.setLong(1, account_num);
   				ps.setString(2, name);
   				ps.setString(3, password);
   				
   				ResultSet rs=ps.executeQuery();
   				out.print("<table  align=center cellpadding=12px cellspacing=12px  >");
   				
   				out.print("<tr><td><font size=6>Before Transaction details: </td></tr>"); 
   				out.print("<tr><td><font size=6>-------------------------------------------</td></tr>");
   				
   				if(rs.next())
   				{
   					balance=rs.getDouble(1);
   					out.print("<td><font size=6> Your account balance is :</td><td><font size=6>"+balance+"</td>");
   					out.print("<tr>");
   					out.print("<td><font size=6> Your account balance is deducted by :</td><td><font size=6>"+amount+"</td>");
   				}
   				out.print("<tr>");
   				
   				
   			   //Target account balance:
   				PreparedStatement ps2=con.prepareStatement("select balance from sdfc_bank where account_num=?");
   				ps2.setLong(1, taccount);
   				ResultSet rs2=ps2.executeQuery();
   				if(rs2.next())
   				{
   					tbalance=rs2.getDouble(1);
   					out.print("<td><font size=6> Target account balance is :</td><td><font size=6>"+tbalance+"</td>");
   					out.print("<tr>");
   					out.print("<td><font size=6> Target amount is :</td><td><font size=6>"+amount+"</td>");
   				}
   				
   				out.print("<tr><td><font size=6>After Transaction details: </td></tr>"); 
   				out.print("<tr><td><font size=6>-------------------------------------------</td></tr>");
   				out.print("<tr>");
   				
   				if(balance>=amount)
   				{
   				//Updating Your account balance:
   				 balance=balance-amount;
   				 ps=con.prepareStatement("update sdfc_bank set balance=?  where account_num=?");
   				 ps.setDouble(1, balance);
   				 ps.setLong(2, account_num);
   				 rs=ps.executeQuery();
   				 
   				 //Updating Target account balance:
   				 tbalance=tbalance+amount;
   				 ps2=con.prepareStatement("update sdfc_bank set balance=? where account_num=?");
   				 ps2.setDouble(1, tbalance);
   				 ps2.setLong(2, taccount);
   				 rs2=ps2.executeQuery();
   				 out.print("<td><font size=6> After Transcation Your account balance is :</td><td><font size=6>"+balance+"</td>");
   				 out.print("<tr>");
   				 out.print("<td><font size=6> After Transaction Target Account Balance is :</td><td><font size=6>"+tbalance+"</td>");
   				out.print("<tr><td><font size=6>----------THANK YOU----------</td></tr>"); 
   				}
   				else {
   				 out.print("You don't have sufficient amount to Transfer");
   				}
 	}
   			catch (Exception e) {
				out.print(e);
			}
    	   
	}

}
