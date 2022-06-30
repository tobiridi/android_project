<?php
	try {
		// Connection to DB
		$dbh = new PDO("pgsql:host=localhost; port=5433; dbname=js2118; user=js2118; password=brofi48fraicro");

		// User inscription
		if(isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['postaladdress']) &&isset($_POST['postalcode']) && isset($_POST['city'])){

			$passwordHash = hash("sha256", $_POST['password']);

			$stm = $dbh -> prepare("INSERT INTO public.user (username,email,password,postal_address,postal_code,city) VALUES (?,?,?,?,?,?)");
            $stm -> execute(array($_POST['username'], $_POST['email'],$passwordHash,$_POST['postaladdress'],$_POST['postalcode'],$_POST['city']));

			exit();
			//FINISH
		}

		// User connection
		if(isset($_POST['email']) && isset($_POST['password'])){
			$passwordHash = hash("sha256", $_POST['password']);

			$stm = $dbh -> prepare("SELECT id,username,email,password,postal_address,postal_code,city FROM public.user WHERE email = ? and password = ?");
	        $stm -> execute(array($_POST['email'], $passwordHash));
	        if($value = $stm -> fetch(PDO:: FETCH_ASSOC)){
	            echo (json_encode($value));
	        }
	        else{
	        	echo ('{"errorMessage" : "User not found!"}');
	        }
	        exit();
	        //FINISH
		}

		// Create an object
		// Select user objects
		// Add object photos
		// Add following object
		// Select following object
		// Update following object

	} catch (Exception $e) {
		echo ('Erreur: '.$e -> getMessage());
		exit();
	}

?>
