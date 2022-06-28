<?php
	try {
		$dbh = new PDO("pgsql:host=localhost; port=5433; dbname=js2118; user=js2118; password=brofi48fraicro");

		// User connection
		if(isset($_POST['email']) && isset($_POST['password'])){

			exit();
		}

		// User inscription
		if(isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['postalAddress']) &&isset($_POST['postalcode']) && isset($_POST['city'])){

			exit();
		}

	} catch (Exception $e) {
		echo ('{"Erreur": '."\"".$e -> getMessage()."\"}");
	}

?>
