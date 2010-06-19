<%@ page import="backgammonator.impl.db.DBManager"%>
<% DBManager.createDB(); %>
<% out.println("Successfully created database!"); %>