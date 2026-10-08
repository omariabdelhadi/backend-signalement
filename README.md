# Signalement App : Backend

API REST d'une application web de **signalement citoyen** avec un **agent IA adaptatif** dont les services dépendent du rôle de l'utilisateur.

> **Dépôt frontend (Angular) :** [frontend-signalement](https://github.com/omariabdelhadi/frontend-signalement)

## Objectif

- soumettre des signalements avec titre, description et pièces jointes ;
- gérer les rôles et permissions (Administrateur / Utilisateur) ;
- sécuriser l'accès par authentification JWT ;
- proposer un agent IA dont les services varient selon le rôle.

## Technologies

| Domaine | Technologies |
|---|---|
| Langage | Java 21 |
| Framework | Spring Boot, API REST |
| Sécurité | Spring Security, JWT |
| Agent IA | Spring AI / LangChain, OpenAI |
| Base de données | PostgreSQL |
| Build | Maven |

## Lancer le projet

### Prérequis

- JDK 21
- PostgreSQL installé et démarré
- Une clé API OpenAI

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

La clé OpenAI se configure par variable d'environnement, jamais dans le code.

Sous Windows (PowerShell) :

```powershell
$env:OPENAI_API_KEY="votre_cle_openai"
.\mvnw.cmd spring-boot:run
```

Sous Linux ou macOS :

```bash
export OPENAI_API_KEY="votre_cle_openai"
./mvnw spring-boot:run
```

L'API est disponible sur `http://localhost:8080`.
