# Signalement App : Backend

API REST d'une application web de **signalement citoyen**, avec gestion des rôles et authentification JWT.

> **Dépôts liés :** [frontend-signalement](https://github.com/omariabdelhadi/frontend-signalement) · [agent-signalement](https://github.com/omariabdelhadi/agent-signalement) (chatbot IA)

## Objectif

- soumettre des signalements avec titre, description et pièces jointes ;
- gérer les rôles et permissions (Administrateur / Utilisateur) ;
- sécuriser l'accès par authentification JWT ;
- fournir à l'agent IA les données dont il a besoin pour répondre.

## Technologies

| Domaine | Technologies |
|---|---|
| Langage | Java 21 |
| Framework | Spring Boot, API REST |
| Sécurité | Spring Security, JWT |
| Base de données | PostgreSQL |
| Build | Maven |

## Lancer le projet

### Prérequis

- JDK 21
- PostgreSQL installé et démarré

### 1. Récupérer le projet

```bash
git clone https://github.com/omariabdelhadi/backend-signalement.git
cd backend-signalement
```

### 2. Créer la base de données

```sql
CREATE DATABASE signalement_db;
```

Les tables sont créées automatiquement au premier démarrage. Adapte le nom d'utilisateur et le mot de passe PostgreSQL dans `src/main/resources/application.properties`.

### 3. Démarrer l'application

```bash
./mvnw spring-boot:run
```

Sous Windows : `mvnw.cmd spring-boot:run`

L'API est disponible sur `http://localhost:8080`.
