<?php
	// Reporte toutes les erreurs PHP (Voir l'historique des modifications)
error_reporting(E_ALL);
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="iso-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Je te vois quand tu te masturbes :3</title>

		<!-- Bootstrap -->
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
	<body>
	
		<form class="form-inline" role="form" action="index.php" method="post">
			<div class="form-group">
				<label class="sr-only" for="player">Joueur(s)</label>
				<input type="text" class="form-control" id="players" name="players" placeholder="Joueur">
			</div>
			
			<select class="form-control" name="server">
				<option value="">Tous</option>
				
				<optgroup label="Serveurs">
					<option value="nexus">nexus</option>
					<option value="semirp">semirp</option>
					<option value="origines">origines</option>
					<option value="creafun">creafun</option>
					<option value="factions">factions</option>
					<option value="ultrahard">ultrahard</option>
					<option value="musee">musee</option>
				</optgroup>
				
				<optgroup label="Jeux">
					<option value="infected">infected</option>
					<option value="mariokart">mariokart</option>
					<option value="tntrun">tntrun</option>
					<option value="hungergames">hungergames</option>
					<option value="paintball">paintball</option>
				</optgroup>
			</select>
			
			<select class="form-control" name="action">
				<option value="">Tous</option>
				
				<option value="0">LOGIN</option>
				<option value="1">CHAT</option>
				<option value="2">COMMAND</option>
				<option value="3">MOD_HISTORY</option>
				<option value="4">LOGOUT</option>
			</select>
			
			<div class="form-group">
				<label class="sr-only" for="data">Données</label>
				<input type="text" class="form-control" id="data" name="data" placeholder="Données">
			</div>
		
			<button type="submit" class="btn btn-primary">NSA</button>
		</form>
		<table class="table table-striped">
			<tr>
				<th>Horodatage</th>
				<th>Serveur</th>
				<th>Joueur</th>
				<th>Action</th>
				<th>Données</th>
			</tr>
<?php

	$mysqli = new mysqli("localhost", "mc-sql", "MfGJQMBzmAS5xYhH", "proxy");

	if ($mysqli->connect_errno)
	{
		echo "Echec lors de la connexion à MySQL : " . $mysqli->connect_error;
	}

	$query = "SELECT * FROM logs l WHERE 1 = 1 ";
	
	if (isset($_POST['players']) && $_POST['players'] != "")
	{	
		foreach (explode(',', $_POST['players']) as $player)
			$playersCondition .= (isset($playersCondition) ? "OR " : "") . "l.player LIKE '%" . $mysqli->real_escape_string($player) . "%' ";
			
		$query .= "AND (" . $playersCondition . ") ";
	}
	
	if (isset($_POST['action']) && $_POST['action'] != "")
	{
		$query .= "AND l.action = '" . $mysqli->real_escape_string($_POST['action']) . "' ";
	}
	
	if (isset($_POST['server']) && $_POST['server'] != "")
	{
		$query .= "AND l.server = '" . $mysqli->real_escape_string($_POST['server']) . "' ";
	}
	
	if (isset($_POST['data']) && $_POST['data'] != "")
	{
		$query .= "AND l.data LIKE '%" . $mysqli->real_escape_string($_POST['data']) . "%' ";
	}
	
	$query .= "ORDER BY l.id DESC LIMIT 2000";


	$res = $mysqli->query($query);

	while ($row = $res->fetch_assoc())
	{
		switch ($row['action'])
		{
			case 0: $action = "LOGIN"; break;
			case 1: $action = "CHAT"; break;
			case 2: $action = "COMMAND"; break;
			case 3: $action = "MOD_HISTORY"; break;
			default: $action = "???"; break;
		}
				
		echo '<tr><td>' . $row['timestamp'] . '</td><td>' . $row['server'] . '</td><td>' . $row['player'] . '</td><td>' . $action . '</td><td>' . $row['data'] . '</td></tr>';
	}

	$mysqli->close();
?>

		</table>

		<!-- jQuery -->
		<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
		<!-- Bootstrap -->
		<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    </body>
</html>