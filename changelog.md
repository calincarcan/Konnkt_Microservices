# Changelog

## Contributors

- Calin: [github.com/calincarcan]
- Bogdan: [github.com/Pasca23]

## [0.6.1] - 2025-04-26 (Calin & Bogdan)

### Endpoint-uri pentru Post, Comment, Role, Vote, Forum

- Implementarea tuturor endpoint-urilor pentru Entitatile Post, Comment, Role, Vote si Forum
    - Realizarea endpoint-urilor a constat in implementarea functionalitatilor in cadrul serviciului de business logic si a serviciului de interactiune cu baza de date
    - Aplicatia contine endpoint-uri pentru a citi, adauga, modifica si sterge datele din baza de date pentru fiecare entitate in parte
    - Endpoint-urile sunt securizate prin JWT si sunt accesibile doar utilizatorilor autentificati in cadrul operatiilor care necesita autentificare
    - Unele endpoint-uri sunt accesibile si utilizatorilor neautentificati
    - Endpoint-urile sunt documentate si testate folosind OpenAPI/Swagger

## [0.6.0] - 2025-04-25 (Calin & Bogdan)

### Endpoint-uri pentru entitatea User

- Implementarea endpoint-urilor pentru entitatea User.
    - Realizarea endpoint-urilor a constat in implementarea functionalitatilor in cadrul serviciului de business logic si a serviciului de interactiune cu baza de date
    - Aplicatia contine endpoint-uri pentru a citi utilizatori.
    - Entitatea User este gestionata si de catre sistemul de Auth, de aceea nu contine toate tipurile de rute CRUD
    - Endpoint-urile sunt accesibile si utilizatorilor neautentificati
    - Endpoint-urile sunt documentate si testate folosind OpenAPI/Swagger

## [0.5.2] - 2025-04-22 (Calin)

### Adaugarea pgAdmin si Portainer

- DBeaver este inlocuit de pgAdmin in cadrul configuratiei Docker.
    - Permite vizualizarea datelor existente in baza de date
    - Permite manipularea datelor si obtinerea de telemetrie si statistici asupra datelor existente
- Adaugam configuratiei Docker serviciul de Portainer
    - Permite monitorizarea si gestionarea containerelor,imaginilor, retelelor si volumelor Docker printr-o interfata grafica
    - Faciliteaza administrarea stack-urilor Docker si ofera o vizualizare centralizata a resurselor

## [0.5.1] - 2025-04-22 (Bogdan)

### Adaugarea Kong Proxy

- Kong Proxy este adaugat in configuratia stack-ului Docker
    - Sunt mapate rutele catre serviciile de autorizare si backend in Kong
    - Este posibila utilizarea aplicatiei exclusiva prin cadrul API Gateway-ului Kong

## [0.5.0] - 2025-04-22 (Bogdan)

### Containerizarea cu Docker

- Aplicatiile de autentificare, backend si interactiune cu db sunt migrate in Docker folosind Docker Compose
    - Fiecare aplicatie Spring Boot este inclusa intr-o imagine eclipse-temurin:17-jdk-alpine
    - Baza de date PostgreSQL este inclusa in configurarea stack-ului de Docker Compose

### [0.4.0] - 2025-04-22 (Calin)

### Implementarea serviciului de interactiune cu baza de date

- Implementarea unui sistem de interactiune cu baza de date care expune endpoint-uri catre toate entitatile prezente in db in cadrul unei noi aplicatii Spring Boot care va fi migrata in viitor in cadrul unui container Docker
    - Aplicatia contine un serviciu de interactiune cu baza de date care contine rute CRUD pentru fiecare entitate stocata in baza de date
    - Aplicatia foloseste Hibernate ORM pentru a putea face posibila decuplarea bazei de date de tip PostgreSQL si schimbarea acesteia in viitor daca apare aceasta situtatie
    - In versiunea finala acest serviciu nu va fi accesibil utilizatorului, acesta va fi expus doar serviciului de business logic

### [0.3.0] - 2025-04-22 (Calin & Bogdan)

### Implementarea serviciului de business logic

- Implementarea unui sistem de business logic care contine rute de testare si rute definitive in cadrul unei noi aplicatii Spring Boot care va fi migrata in viitor in cadrul unui container Docker
    - Aplicatia contine toata logica aplicatiei, aceasta va folosi datele primite de la serviciul de interactiune cu db pentru a oferi informatii utilizatorilor
    - Pentru functionarea serviciului este necesara autentificarea folosind JWT provenind de a serviciul Auth
    - Principala metoda de comunicare intre utilizator si Backend si intre Backend si serviciul de DB se face prin mesaje HTTP

### [0.2.0] - 2025-04-22 (Calin)

### Implementarea serviciului de autentificare si autorizare

- Implementarea serviciului de autentificare si autorizare bazat pe token-uri JWT in cadrul unei noi aplicatii Spring Boot care va fi migrata in viitor in cadrul unui container Docker.
    - Serviciul foloseste JWT pentru a realiza autentificare si autorizare in cadrul mai multor sisteme
    - Implementarea filtrului de securitate utilizand JWT
    - Necesitatea autorizarii cu token va aparea in cadrul operatiilor care necesita permisiuni de admin sau in care utlizatorul care realizeaza actiunea trebuie sa fie cunoscut
    - Vor exista rute care nu necesita autorizare care expun date publice tuturor

## [0.1.0] - 2025-04-19 (Calin & Bogdan)

### Prototip de aplicatie

- Implementarea unei aplicatii prorotip, mobolit Spring Boot care contine serviciul de interactiune db, serviciul de autentificare si autorizare si serviciul de logica a aplicatiei
    - Aplicatia contine un serviciu de autentificare si autorizare bazat pe token-uri JWT
    - Aplicatia contine un serviciu de logica a aplicatiei care contine rute de testare si rute definitive care vor fi utilizate in aplicatia finala
    - Aplicatia contine un serviciu de interactiune cu baza de date care contine rute CRUD pentru informatiile de baza a entitatilor stocare in baza de date
- Utilizarea unei baze de date PostgreSQL
- Utilizarea DBeaver pentru a vizualiza baza de date PostgreSQL
- Utilizarea OpenAPI/Swagger pentru a genera documentatia API-ului si pentru testarea rutelor
