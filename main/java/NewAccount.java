

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NewAccount")
public class NewAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		
		long account_num=Long.parseLong(request.getParameter("acn"));
		String name=request.getParameter("custname");
		String password=request.getParameter("psw");
		String cPassword=request.getParameter("psw2");
		double balance=Double.parseDouble(request.getParameter("amt"));
		String address=request.getParameter("caddr");
		long mobile_no=Long.parseLong(request.getParameter("mno"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sathyadb","sathyadb");
			if(password.equals(cPassword))
			{
			PreparedStatement ps=con.prepareStatement("insert into sdfc_bank values(?,?,?,?,?,?)");
			ps.setLong(1, account_num);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.setDouble(4, balance);
			ps.setString(5, address);
			ps.setLong(6, mobile_no);
			
			int i=ps.executeUpdate();
			if(i==1)
			{
				response.sendRedirect("welcome.html");
			}
			}
			else {
				out.print("Your password did not matched with confirm password:");
			}
		}
		catch (Exception e) {
			out.println(e);
		}
		
	}

}
