# Changelog

## 0.6.0

### Modificari

- Am rezolvat anumite rute problematice cauzate de accesul prin Kong.
- Adaugare PGAdmin pentru vizualizarea mai usoara a datelor stocate in DB
- Am adaugat Portainer pentru vizualizarea containerelor. 

## 0.5.2

### Adaugari

- Integrarea cu un docker-compose.yml pentru pornirea fiecarui serviciu prezent in momentul de fata.
- Am adaugat "Kong" pentru a facilita accesul catre fiecare ruta expusa de catre fiecare serviciu.

## 0.5.0

### Adaugari

- Fisiere dockerfile pentru fiecare serviciu care va fi implementat.
- Crearea de prototipuri pentru fiecare serviciu.

### Modificari

- Spargerea sistemului monolith intr-o structura bazata pe microservicii.
- Migrarea si implementarea functionalitatii de autentificare si autorizarea intr-un microserviciu separat.
- Migrarea serviciului de backend cu cateva rute pentru testare si integrare atat cu serviciul de autentificare si autorizare cat si cu serviciul de baza de date.
- Conectivitatea intre diferite containere docker.

## 0.4.0

### Adaugari

- Posibilitatea admin-ului de a sterge comentarii.
- Expunere endpoints de tip "GET"

## 0.3.0

### Adaugari

- Endpoints de pentru postari.
- Imbunatatire autentificare si autorizare pe baza de token JWT.

## 0.2.2

### Adaugari

- Endpoints de comment-uri
- DTOs pentru noile endpoints.

## 0.2.1

### Adăugări

- Rute noi pentru utilizatori
- Operatii CRUD pentru rutele de "user"

## 0.2.0

### Adăugări

- Integrarea cu o baza de date PostgreSQL.
- Adaugarea unui sistem de autentificare bazat pe token-uri JWT.
- Implementarea unui sistem de gestionare a erorilor.

## 0.1.0

### Adăugări

- Arhitectura initiala a aplicatiei, in forma monolitica.
- Implementarea unor rute de baza pentru rute de testare.
- Adaugare de DTO-uri pentru a defini structura datelor.
