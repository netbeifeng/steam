# Gruppe 9(IX) 
  - projekt.alias.zone/steam-0.1


  ## Team
    - M1
      - ReadMe.md erstellen
      - index.html
      - index.js
      - index.css
    - M2
      - Alle Schnittstelle(RESTful API Interface) Backend
      - api.pdf erstellen
      - search.html
      - search_light.css und search_dark.css
    - M3
      - login.html
      - login.css
      - login.js
      - detail.html
      - wireframe.pdf erstellen
  
  ## Struktur
    - login.html (einloggen und registeriern)
    - index.html (Hauptseite - Darstellung von allen Serien in Datenbank)
    - detail.html (Detailierte Darstellung von eine Serien Karte)
    - search.html (Suchen Funktionalität)

  ## Schnittstelle
    - UserRessource.java
      - login(username,password)
      - addUser(username,password)
    - SeriesRessource.java
      - getSeriesByUsername(username)
      - getSeries()
        - (Alle Serien in Datenbank zurückliefern)
      - addOrChangeSeries(username,title,season,genre,platform,score,remark)

  ## Test Methode
    - login
      - /steam-0.1/login?username=AAAAAA&password=******
    - register
      - /steam-0.1/register?username=AAAAAA&password=******
|  username   | password  |
|  ----  | ----  |
| AAAAAA  | ****** |
    - add
      - /steam-0.1/addOrChangeSeries?username=AAAAAA&title=BBBBBB&season=1&genre=Drama&platform=Skye&score=Good&remark=none

|  username   | title  | season  | genre  | platform  | score  | remark  |
|  ----  | ----  |  ----  | ----  |  ----  | ----  |  ----  |
| AAAAAA  | BBBBBB | 1  | Drama | Skye  | Good | none  |

    - getAllSeries
      - /steam-0.1/getSeries
    - getSeriesByUsername
      - /steam-0.1/getSeries/AAAAAA
