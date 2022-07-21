# android_project
Android project for school exam.

## configuration du serveur Uwamp (php)
ouvrir le fichier **php_uwamp.ini** se trouvant dans le répértoire d'installation de Uwamp /bin/php/la_version_php_utiliser et modifier les valeurs suivantes :

- file_uploads = On
- upload_tmp_dir = ".\\images\\"
- upload_max_filesize = 10M
- max_file_uploads = 20
- post_max_size = 20M (la valeur doit être supérieur à *upload_max_filesize*)
