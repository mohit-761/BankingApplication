

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/BalanceCode")
public class BalanceCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("text/html");
	PrintWriter out=response.getWriter();
	long account_num=Long.parseLong(request.getParameter("acn"));
	String name=request.getParameter("cname");
	String password=request.getParameter("psw");
	
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sathyadb","sathyadb");
		
		out.print("<body bgcolor='pink'>");
		out.print("<table border=1 align='center' cellspacing='4px' bgcolor='orange' font-size='20px'>");
		PreparedStatement ps=con.prepareStatement("select * from sdfc_bank where account_num=? and name=? and password=?");
		ps.setLong(1, account_num);
		ps.setString(2, name);
		ps.setString(3, password);
		ResultSet rs=ps.executeQuery();
		ResultSetMetaData rsmd= rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		

		for(int i=1; i<=columnCount; i++)
		{
			out.print("<th><font color=white font size=6>"+rsmd.getColumnName(i));
		}
			out.print("</th>");
		
			
		while(rs.next())
		{
		out.print("<tr>");
			for(int i=1; i<=columnCount; i++)
			{
				out.print("<td><font color=white font size=4>"+rs.getString(i));
			}
			out.print("</td>");
			out.print("</tr>");
		}
		
		out.print("</table>");
		con.close();
	}
	catch(Exception e)
	{
		out.print(e);
	}
	
		
	}

}
