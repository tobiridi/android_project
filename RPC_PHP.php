<?php
	try {
		//Connection to DB
		$dbh = new PDO("pgsql:host=localhost; port=5433; dbname=js2118; user=js2118; password=brofi48fraicro");

		//Configuration HTTP response
		header('Content-Type: application/json; charset=utf-8');

		//User inscription
		if(isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['postal_address']) && 
		   isset($_POST['street_number']) && isset($_POST['postal_code']) && isset($_POST['city'])){

		   	if (is_numeric($_POST['street_number'])) {
		   		$passwordHash = hash("sha256", $_POST['password']);

				$stm = $dbh -> prepare("INSERT INTO public.user (username,email,password,postal_address,postal_code,city,street_number) VALUES (?,?,?,?,?,?,?)");
	            $stm -> execute(array($_POST['username'], $_POST['email'],$passwordHash,$_POST['postal_address'],
	            					  $_POST['postal_code'],$_POST['city'],$_POST['street_number']));

	            header('HTTP/1.1 201 Created');
				exit();
		   	}
		}

		//User connection
		if(isset($_POST['email']) && isset($_POST['password'])){

			$passwordHash = hash("sha256", $_POST['password']);

			$stm = $dbh -> prepare("SELECT id,username,email,password,postal_address,postal_code,city,street_number FROM public.user WHERE email = ? and password = ?");
	        $stm -> execute(array($_POST['email'], $passwordHash));
	        if($value = $stm -> fetch(PDO:: FETCH_ASSOC)){
	        	header('HTTP/1.1 200 OK');
	            echo (json_encode($value));
	        }
	        else{
	        	header('HTTP/1.1 404 Not Found');
	        	//$erroMessage = array('errorMessage' => 'User not found!');
	        	echo '{"errorMessage" : "User not found!"}';
	        }
	        
	        exit();
		}

		//Create a sale object
		if(isset($_POST['object_name']) && isset($_POST['object_type']) && isset($_POST['object_description']) && 
		   isset($_POST['price']) && isset($_POST['id_user']) && isset($_POST['longitude']) && isset($_POST['latitude'])){

		   	if (is_numeric($_POST['price']) && is_numeric($_POST['id_user']) && is_numeric($_POST['longitude']) && is_numeric($_POST['latitude'])) {
		   		$stm = $dbh -> prepare("INSERT INTO public.sale_object (object_name,object_type,price,object_description,id_user,longitude,latitude) VALUES (?,?,?,?,?,?,?)");
	            $stm -> execute(array($_POST['object_name'],$_POST['object_type'],$_POST['price'],$_POST['object_description'],$_POST['id_user'],$_POST['longitude'],$_POST['latitude']));

            	header('HTTP/1.1 201 Created');
            	exit();
		   	}
			
		}

		//Add tracking object
		if(isset($_POST['id_user']) && isset($_POST['id_sale_object'])){
			$stm = $dbh -> prepare("INSERT INTO public.object_user_tracking (id_user,id_sale_object) VALUES (?,?)");
			$stm -> execute(array($_POST['id_user'],$_POST['id_sale_object']));

	        header('HTTP/1.1 201 Created');
	        exit();
		}

		//Select user's sale objects
		if(isset($_POST['id_user'])){
			$stm = $dbh -> prepare("SELECT id,object_name,object_type,price,object_description FROM public.sale_object WHERE id_user = ?");
			$stm -> execute(array($_POST['id_user']));

			$tabjson = [];

			while($value = $stm -> fetch(PDO:: FETCH_ASSOC)){
	            array_push($tabjson, $value);
	        }

	        echo json_encode($tabjson);
	        header('HTTP/1.1 200 OK');

	        exit();
		}

		//Sale objects around the user
		//Add object photos
		//Select tracking object
		//Update tracking object


	} catch (Exception $e) {
		echo ('Erreur: '.$e -> getMessage());
		header('HTTP/1.1 500 Internal Server Error');
		
		exit();
	}

?>
