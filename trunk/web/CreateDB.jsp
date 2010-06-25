<%@ page import="backgammonator.impl.db.DB"%>
<% DB.getDBManager().createDB(); %>
<% out.println("Successfully created database!"); %>