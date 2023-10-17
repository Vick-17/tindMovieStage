# tindMovieApi
L'api de mon application de choix de film a regarder

1. USERCONTROLLER - Gestion des Utilisateurs :

/***********************************************************************************************/
   - Rechercher un Utilisateur par Email
   
            GET /users?email={email} :
           Récupère l'ID de l'utilisateur en fonction de son adresse e-mail.

     - Logique :

              Recherche l'utilisateur dans la base de données en utilisant l'adresse e-mail fournie.
              Renvoie l'ID de l'utilisateur s'il est trouvé, sinon renvoie une réponse "Not Found".
/***********************************************************************************************/
   - Récupérer Tous les Utilisateurs
   
         GET /users/allUser :
         Récupère la liste de tous les utilisateurs enregistrés dans la base de données.

     - Logique :

            Récupère la liste complète des utilisateurs enregistrés.
/***********************************************************************************************/
   - Créer un Nouvel Utilisateur
   
         POST /users/inscription :
         Permet de créer un nouveau compte utilisateur.

     - Logique :

           1.Génère un code de partage aléatoire.
           2.Hache le mot de passe de l'utilisateur avant de l'enregistrer.
           3.Affecte le rôle utilisateur par défaut s'il existe.
           4.Enregistre l'utilisateur dans la base de données.
           5.Crée une relation entre l'utilisateur et son rôle.
           6.Renvoie l'utilisateur créé.
/***********************************************************************************************/
   - Lier des Utilisateurs

          PUT /users/link :
          Permet de lier deux utilisateurs à l'aide d'un code de partage.

     - Logique :

            1.Vérifie la validité du code de partage.
            2.Récupère l'utilisateur à lier en fonction de son ID.
            3.Met à jour les informations des deux utilisateurs pour les lier entre eux.
            4.Enregistre les mises à jour dans la base de données.
            5.Renvoie une réponse indiquant que les utilisateurs ont été liés avec succès.
/***********************************************************************************************/
   - Obtenir le Nom d'Utilisateur Lié

         GET /users/linkedUsername?userId={userId} :
         Récupère le nom d'utilisateur de l'utilisateur lié à l'utilisateur spécifié.

     - Logique :

            1.Recherche l'utilisateur spécifié par son ID.
            2.Vérifie si cet utilisateur est lié à un autre utilisateur.
            3.Renvoie le nom d'utilisateur de l'utilisateur lié ou une indication qu'il n'est lié à aucun utilisateur.
/***********************************************************************************************/
2. SWIPECONTROLLER - Gestion des Swipes et des Correspondances

   - Créer un Swipe (Like ou Dislike)
   
         POST /swipe/like
         Permet à un utilisateur de créer un swipe (like ou dislike) pour un film.

     Logique :

         1.Vérifie si l'utilisateur a déjà effectué un swipe pour ce film.
         2.Enregistre le swipe en indiquant s'il s'agit d'un like ou d'un dislike.
         3.Vérifie s'il y a une correspondance (match) entre l'utilisateur et son partenaire s'ils ont tous deux "liké" le film.
         4.Renvoie le swipe créé.
/***********************************************************************************************/
