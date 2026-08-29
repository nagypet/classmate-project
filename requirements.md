A követelmények:

- Tanfolyamok létrehozása, szerkesztése és törlése. Tanfolyam törzsadatai: cím, oktató, hely, időpont, kapacitás
- Résztvevők jelentkezése egy adott órára vagy eseményre. Résztvevő törzsadatai: név, email, születési dátum, neme
- Valós idejű létszám
- Férfi/nő arány megjelenítése
- Várólista, ha betelik a csoport
- Oktató számára áttekintő felület

A bejelentkezés OAuth2-vel történik, akár facebook, akár google, akár MS account használható. Onnan a név és email
címeket fogja használni. A nem-et viszont a felhasználó maga fogja megadni.

---

## Adatbázis modell

### Entitások és táblák

---

#### `users` — Felhasználók

Az OAuth2-n keresztül bejelentkező felhasználók adatait tárolja. A név és email a szolgáltatótól érkezik, a nemet a
felhasználó maga adja meg.

| Oszlop         | Típus                         | Leírás                                     |
|----------------|-------------------------------|--------------------------------------------|
| id             | UUID, PK                      | Belső azonosító                            |
| oauth_provider | VARCHAR(32)                   | `google`, `facebook`, `microsoft`          |
| oauth_subject  | VARCHAR(255)                  | A szolgáltató által adott egyedi azonosító |
| name           | VARCHAR(255)                  | Teljes név (OAuth2-ből)                    |
| email          | VARCHAR(255), UNIQUE          | Email cím (OAuth2-ből)                     |
| gender         | ENUM('MALE','FEMALE','OTHER') | Nem (felhasználó által megadott)           |
| birth_date     | DATE                          | Születési dátum                            |
| created_at     | TIMESTAMP                     | Létrehozás ideje                           |
| updated_at     | TIMESTAMP                     | Utolsó módosítás ideje                     |

**Megszorítások:**

- `UNIQUE(oauth_provider, oauth_subject)` — egy provider+subject páros csak egyszer szerepelhet

---

#### `roles` — Szerepkörök

Az alkalmazásban elérhető szerepköröket definiálja.

| Oszlop      | Típus        | Leírás                                       |
|-------------|--------------|----------------------------------------------|
| id          | UUID, PK     | Belső azonosító                              |
| name        | VARCHAR(64)  | Szerepkör neve (pl. `STUDENT`, `INSTRUCTOR`) |
| description | VARCHAR(255) | Opcionális leírás                            |

**Megszorítások:**

- `UNIQUE(name)`

---

#### `user_roles` — Felhasználó–szerepkör kapcsolat (n:m)

| Oszlop  | Típus               | Leírás        |
|---------|---------------------|---------------|
| user_id | UUID, FK → users.id | A felhasználó |
| role_id | UUID, FK → roles.id | A szerepkör   |

**Megszorítások:**

- `PRIMARY KEY(user_id, role_id)`

---

#### `courses` — Tanfolyamok

A tanfolyamok/órák törzsadatait tartalmazza.

| Oszlop        | Típus               | Leírás                 |
|---------------|---------------------|------------------------|
| id            | UUID, PK            | Belső azonosító        |
| title         | VARCHAR(255)        | A tanfolyam/óra neve   |
| instructor_id | UUID, FK → users.id | Az oktató felhasználó  |
| location      | VARCHAR(255)        | Helyszín               |
| scheduled_at  | DATETIME            | Időpont                |
| capacity      | INT                 | Maximális férőhely     |
| created_at    | TIMESTAMP           | Létrehozás ideje       |
| updated_at    | TIMESTAMP           | Utolsó módosítás ideje |

---

#### `registrations` — Jelentkezések

Egy felhasználó és egy tanfolyam közötti kapcsolatot rögzíti. Kezeli a visszaigazolt és a várólistás állapotokat is.

| Oszlop            | Típus                          | Leírás                                                         |
|-------------------|--------------------------------|----------------------------------------------------------------|
| id                | UUID, PK                       | Belső azonosító                                                |
| course_id         | UUID, FK → courses.id          | A tanfolyam                                                    |
| user_id           | UUID, FK → users.id            | A résztvevő                                                    |
| status            | ENUM('CONFIRMED','WAITLISTED') | `CONFIRMED`: helye van; `WAITLISTED`: várólistán van           |
| waitlist_position | INT, nullable                  | Várólistán belüli sorrend (csak WAITLISTED esetén értelmezett) |
| registered_at     | TIMESTAMP                      | Jelentkezés időpontja                                          |

**Megszorítások:**

- `UNIQUE(course_id, user_id)` — egy felhasználó egy tanfolyamra csak egyszer jelentkezhet
- `waitlist_position` egyedi értékű `course_id`-n belül (ahol NOT NULL)

---

### Kapcsolatok

```
users ||--o{ user_roles      : "rendelkezik"
roles ||--o{ user_roles      : "hozzárendelt"
users ||--o{ courses         : "oktat (instructor_id)"
users ||--o{ registrations   : "jelentkezik"
courses ||--o{ registrations : "tartalmaz"
```

- Egy felhasználónak több szerepköre is lehet (pl. valaki egyszerre lehet INSTRUCTOR és STUDENT).
- Egy felhasználó (INSTRUCTOR szerepkörrel) több tanfolyamot is tarthat.
- Egy felhasználó (STUDENT szerepkörrel) több tanfolyamra is jelentkezhet.
- Egy tanfolyamhoz több jelentkezés tartozhat; ezek lehetnek visszaigazoltak (CONFIRMED, ha `count < capacity`) vagy
  várólistások (WAITLISTED).

---

### Üzleti logika megjegyzések

- **Valós idejű létszám:** `SELECT COUNT(*) FROM registrations WHERE course_id = ? AND status = 'CONFIRMED'`
- **Férfi/nő arány:** `registrations` + `users.gender` JOIN-nal, csak CONFIRMED sorokon.
- **Várólista kezelése:** ha egy CONFIRMED résztvevő lemondja helyét, a legkisebb `waitlist_position`-ű WAITLISTED
  bejegyzés automatikusan CONFIRMED státuszt kap (és `waitlist_position` NULL-ra áll).
- **Oktató áttekintő:** a `courses` táblán `instructor_id = ?` szűréssel, a hozzájuk tartozó `registrations`
  aggregálásával állítható össze.
