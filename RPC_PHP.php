<?php
	try {
		//Connection to DB
		$dbh = new PDO("pgsql:host=localhost; port=5433; dbname=js2118; user=js2118; password=brofi48fraicro");

		//HTTP response configuration
		header('Content-Type: application/json; charset=utf-8');

		//User inscription
		if(isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['postal_address']) && 
		   isset($_POST['street_number']) && isset($_POST['postal_code']) && isset($_POST['city'])){

		   	if (is_numeric($_POST['street_number']) && is_numeric($_POST['postal_code'])) {
		   		$passwordHash = hash("sha256", $_POST['password']);

				$stm = $dbh -> prepare("INSERT INTO public.user (username,email,password,postal_address,postal_code,city,street_number) VALUES (?,?,?,?,?,?,?)");
	            $stm -> execute(array($_POST['username'], $_POST['email'],$passwordHash,$_POST['postal_address'],
	            					  $_POST['postal_code'],$_POST['city'],$_POST['street_number']));

	            header('HTTP/1.1 201 Created');

			} else {
				header('HTTP/1.1 400 Bad Request');
			}

			exit();
		}

		//User connection
		elseif(isset($_POST['email']) && isset($_POST['password'])){

			$passwordHash = hash("sha256", $_POST['password']);

			$stm = $dbh -> prepare("SELECT id,username,email,password,postal_address,postal_code,city,street_number FROM public.user WHERE email = ? and password = ?");
	        $stm -> execute(array($_POST['email'], $passwordHash));
	        if($value = $stm -> fetch(PDO:: FETCH_ASSOC)){
	        	header('HTTP/1.1 200 OK');
	            echo json_encode($value);

	        } else {
				header('HTTP/1.1 404 Not Found');
				echo '{"errorMessage" : "User not found!"}';
			}

	        exit();
		}

		//Create a sale object
		elseif(isset($_POST['object_name']) && isset($_POST['object_type']) && isset($_POST['object_description']) &&
		   isset($_POST['price']) && isset($_POST['id_user']) && isset($_POST['longitude']) && isset($_POST['latitude'])){

		   	if (is_numeric($_POST['price']) && is_numeric($_POST['id_user']) && is_numeric($_POST['longitude']) && is_numeric($_POST['latitude'])) {
		   		$stm = $dbh -> prepare("INSERT INTO public.sale_object (object_name,object_type,price,object_description,id_user,longitude,latitude) VALUES (?,?,?,?,?,?,?)");
	            $stm -> execute(array($_POST['object_name'],$_POST['object_type'],$_POST['price'],$_POST['object_description'],$_POST['id_user'],$_POST['longitude'],$_POST['latitude']));

				echo '{"last_sale_object_id": '. $dbh->lastInsertId().'}';
            	header('HTTP/1.1 201 Created');

		   	} else {
				header('HTTP/1.1 400 Bad Request');
			}
			exit();
		}

		//Add sale object images
		elseif (isset($_POST['image_base64']) && isset($_POST['id_sale_object'])){
			// example : INSERT INTO "public"."object_photo"(path,id_sale_object) VALUES ('test', ((SELECT id FROM "public"."sale_object" where id = 21)));

			$stm = $dbh->prepare("INSERT INTO public.object_photo (path,id_sale_object) VALUES (?,?)");
			$stm->execute(array($_POST['image_base64'], $_POST['id_sale_object']));

			header('HTTP/1.1 201 Created');
			exit();
		}

		//Add tracking object
		elseif(isset($_POST['id_user']) && isset($_POST['id_sale_object'])){
			$stm = $dbh -> prepare("INSERT INTO public.object_user_tracking (id_user,id_sale_object) VALUES (?,?)");
			$stm -> execute(array($_POST['id_user'],$_POST['id_sale_object']));

	        header('HTTP/1.1 201 Created');
	        exit();
		}

		//Select tracking user's sale objects
		elseif(isset($_GET['id_user'])){
			$query = "SELECT id,object_name,object_type,price,object_description,longitude,latitude,id_user FROM public.sale_object WHERE id = ANY (SELECT id_sale_object FROM public.object_user_tracking WHERE id_user = ?)";
			$stm = $dbh -> prepare($query);
			$stm -> execute(array($_GET['id_user']));

			$tabjson = [];

			while($value = $stm -> fetch(PDO:: FETCH_ASSOC)){
	            array_push($tabjson, $value);
	        }

	        echo json_encode($tabjson);
	        header('HTTP/1.1 200 OK');
	        exit();
		}

		//Select All sale objects
		elseif (isset($_GET['all_sale_object'])){
			if ($_GET['all_sale_object'] == "true") {

				$stm = $dbh -> prepare("SELECT id,object_name,object_type,object_description,price,longitude,latitude,id_user FROM public.sale_object");
				$stm -> execute();

				$tabjson = [];

				while($value = $stm -> fetch(PDO:: FETCH_ASSOC)){
					array_push($tabjson, $value);
				}

				echo json_encode($tabjson);
				header('HTTP/1.1 200 OK');

			} else {
				header('HTTP/1.1 400 Bad Request');
			}

			exit();
		}

		//Select sale object images
		elseif (isset($_GET['id_sale_object'])){
			$stm = $dbh -> prepare("SELECT id,path FROM public.object_photo WHERE id_sale_object = ?");
			$stm -> execute(array($_GET['id_sale_object']));

			$tabjson = [];

			while($value = $stm -> fetch(PDO:: FETCH_ASSOC)){
				array_push($tabjson, $value);
			}

			echo json_encode($tabjson);
			header('HTTP/1.1 200 OK');
			exit();
		}

		else{
			header('HTTP/1.1 400 Bad Request');
			exit();
		}

	} catch (Exception $e) {
		echo ('Error : '.$e -> getMessage());
		header('HTTP/1.1 500 Internal Server Error');
		exit();
	}

?>