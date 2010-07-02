<%@ page import="backgammonator.impl.webinterface.Util"%>
<% Util.printHeader(request, out, "Tutorial", Util.HOME); %>
<i>Protocol Tutorial Description</i>
<ol>
	<li>
		Every move is realized with game info, which is given to the player on the standard input and the player&#39;s move should be returned on the standard output.</li>
	<li>
		The game info, needed to player to make its move will be on separate row for each move. It will be in the following format:<br />
		<tt>&lt;count_1&gt; &lt;possession_1&gt; &lt;count_2&gt; &lt;possession_2&gt; ... &lt;count_24&gt; &lt;possession_24&gt; &lt;hits_current&gt; &lt;hits_opponent&gt; &lt;bornoff_current&gt; &lt;bornoff_opponent&gt; &lt;die_1&gt; &lt;die_2&gt; &lt;game_status&gt;</tt><br />
		Where<br />
		<tt>&lt;count_n&gt;</tt> is the number of checkers in point n - integer number between 0 and 15.<br />
		<tt>&lt;possession_n&gt;</tt> is the player whose are the checkers in point n - integer number between 0 and 1 (0 for current player, 1 for opponent player).<br />
		<tt>&lt;hits_current&gt;</tt> and <tt>&lt;hits_opponent&gt;</tt> are the numbers of hit checkers for the current and opponent players respectively - integer number between 0 and 15.<br />
		<tt>&lt;bornoff_current&gt;</tt> and <tt>&lt;bornoff_opponent&gt;</tt> are the numbers of born off checkers for the current and opponent players respectively - integer number between 0 and 15.<br />
		<tt>&lt;die_1&gt;</tt> and <tt>&lt;die_2&gt;</tt> are the dices&#39; values - integer numbers between 1 and 6.<br />
		<tt>&lt;game_status&gt;</tt> gives information for the current game status:<br />
		0: Game is not over yet<br />
		1: Standard win for the current player<br />
		2: Double win for the current player<br />
		3: Triple win for the current player<br />
		4: Exception game end and the current player wins<br />
		5: Invalid move and the current player wins<br />
		6: Game end due timeout and the current player wins<br />
		7: Standard win for the opposite player<br />
		8: Double win for the opposite player<br />
		9: Triple win for the opposite player<br />
		10: Exception game end and the opposite player wins<br />
		11: Invalid move and the opposite player wins<br />
		12: Game end due timeout and the opposite player wins</li>
	<li>
		The returned move info from the player should be on separate row for each move. It should be in the following format:<br />
		<tt>&lt;start_point_1&gt; &lt;move_length_1&gt; &lt;start_point_2&gt; &lt;move_length_2&gt; [ &lt;start_point_3&gt; &lt;move_length_3&gt; &lt;start_point_4&gt; &lt;move_length_4&gt; ]</tt><br />
		Where<br />
		<tt>&lt;start_point_n&gt;</tt> is the start point of the n-th checker move - integer number between 0 and 25. If the AI player reenters a hit checker he should use 25 for start point. If the AI player can&#39;t move, he should use 0 for start point.<br />
		<tt>&lt;move_length_n&gt;</tt> is the length of the n-th checker move - integer number between 1 and 6.<br />
		The number of checker moves might be 4 when the dice are double.</li>
</ol>

<% Util.printFooter(out); %>