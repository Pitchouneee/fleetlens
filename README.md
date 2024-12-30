# FleetLens

FleetLens est une application puissante de gestion de configuration, développée avec Spring Boot 3, Java 21, Thymeleaf et une base de données MariaDB. Elle offre une vue d'ensemble sur les machines Linux d'un parc informatique et facilite la gestion des comptes locaux, des groupes, et des packages installés.

## Fonctionnalités

- **Vue Machine** : Détails complets sur chaque machine, incluant les comptes locaux, les groupes et les packages installés.
- **Vue Logiciel** : Liste des logiciels, leur version et le nombre de machines sur lesquelles ils sont présents.
- **Vue Compte** : Liste des comptes locaux existants et sur quelles machines ils sont présents.
- **Authentification** : Accès sécurisé grâce à une authentification locale.
- **Automatisation via Playbook** : Un playbook déployé sur chaque machine collecte les informations et les transmet à l'API de FleetLens via un token d’authentification.

## Prérequis

### Environnement de développement
- **Java** : Version 21
- **Spring Boot** : Version 3
- **Base de données** : MariaDB
- **Frontend** : Thymeleaf

### Infrastructure
- Accès à un serveur capable d’exécuter des scripts de configuration pour envoyer des données via HTTP.
- Une instance MariaDB configurée.

## Installation pour les développeurs

1. **Clonez le dépôt** :
   ```bash
   git clone https://github.com/Pitchouneee/fleetlens.git
   cd FleetLens
   ```

2. **Configurez la base de données** :
    - Modifiez `application.yml` pour inclure les paramètres de connexion MariaDB.

3. **Lancez l’application** :
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Accédez à l’interface utilisateur** :
    - Ouvrez un navigateur et allez sur `http://localhost:8080`.

## Installation sur une infrastructure de production

1. **Utilisez l'image Docker** : Une image Docker prête à l'emploi est disponible sur le registry GitHub du projet. Pour déployer l'application, exécutez :
   ```bash
   docker pull ghcr.io/Pitchouneee/fleetlens:latest
   docker run -d -p 8080:8080 --name fleetlens \
       -e SPRING_DATASOURCE_URL=jdbc:mariadb://<host>:<port>/<database> \
       -e SPRING_DATASOURCE_USERNAME=<username> \
       -e SPRING_DATASOURCE_PASSWORD=<password> \
       ghcr.io/Pitchouneee/fleetlens:latest
   ```

2. **Création du compte administrateur initial** : Lors du premier démarrage, lancez l'application avec les arguments suivants pour créer un compte administrateur :
   ```bash
   --app.admin.lastName=<Nom> \
   --app.admin.firstName=<Prénom> \
   --app.admin.email=<Email> \
   --app.admin.password=<MotDePasse>
   ```

3. **Accédez à l'application** : Ouvrez un navigateur et allez sur l'URL de l'application (par exemple, `http://localhost:8080`).

## Configuration initiale

1. **Génération de tokens** :
    - Générez un token d’authentification dans l’interface.
2. **Déploiement du playbook** :
    - Configurez le playbook pour qu’il exécute des appels `curl` vers l'API de FleetLens avec le token approprié.

## API

L'API RESTful de FleetLens permet :
- L’envoi des informations des machines.

### Exemple d’appel API
```bash
curl -X POST -H "Authorization: Bearer <TOKEN>" -H "Content-Type: application/json" \
     -d '{"hostname": "machine-01", "packages": [ ... ]}' \
     http://localhost:8080/api/v1/machines
```

## Contribution

1. **Forkez le dépôt**
2. **Créez une branche pour votre fonctionnalité** :
   ```bash
   git checkout -b feature/nouvelle-fonctionnalite
   ```
3. **Faites une pull request**

## Licence

Ce projet est sous licence [MIT](LICENSE).

