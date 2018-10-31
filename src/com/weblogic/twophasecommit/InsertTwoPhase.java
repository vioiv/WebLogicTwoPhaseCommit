package com.weblogic.twophasecommit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Servlet implementation class InsertTwoPhase
 */
@WebServlet("/InsertTwoPhase")
public class InsertTwoPhase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertTwoPhase() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InitialContext initialContext;
		UserTransaction userTransaction = null;
		try {
			initialContext = new InitialContext();
			userTransaction = (UserTransaction) initialContext.lookup("java:comp/UserTransaction");
			userTransaction.begin();

			
			DataSource dataSource1 = (DataSource)initialContext.lookup("MYSQL");
			Connection connection1 = dataSource1.getConnection();
			
			Statement statement1 = connection1.createStatement();
			statement1.executeUpdate("insert into test values('testtwo','col77')");
			
//			ResultSet resultSet1 = statement1.executeQuery("select * from test");
//			while(resultSet1.next()) {
//				String memberName1 = resultSet1.getString("test1");
//				System.out.println(memberName1);
//			}
			
			DataSource dataSource = (DataSource)initialContext.lookup("DB1");
			Connection connection = dataSource.getConnection();
			
			Statement statement = connection.createStatement();
			statement.executeUpdate("insert into tyx_member values(41,'김정훈','1980-11-30','FW',null,'99','한국')");
			
//			ResultSet resultSet = statement.executeQuery("select * from tyx_member");
//			while(resultSet.next()) {
//				String memberName = resultSet.getString("V_MEMBER_NM");
//				System.out.println(memberName);
//			}
			userTransaction.commit();
		} catch (Exception e) {
			try {
				userTransaction.rollback();
				System.out.println("The Job was failed, Data Transaction was rollback!");
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				System.out.println("The Rollback was failed!");
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().append("<p>").append("REQUESTED BY GET PROTOCOL.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
