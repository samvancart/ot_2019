# Käyttöohje

## Konfigurointi

Sovelluksen kaikki ominaisuudet ovat käytössä kun sovelluksen käynnistyshakemistossa on tekstitiedosto _key.txt_.

## Ohjelman käynnistäminen

Sovellus käynnistyy henkilöiden lisäys näkymään. Jos käynnistyshakemistosta löytyy tiedosto _key.txt_ niin sovellus ilmoittaa jos API keytä ei ole 
vielä määritelty. 

## API keyn määrittely

API key löytyy käyttäjän omalta mailerlite tililtä osoitteesta www.mailerlite.com/integrations/api. Key tulee kopioida ja sen jälkeen siirtyä
sovelluksen _settings_ välilehdelle. Tämän jälkeen painetaan _configure_ nappia jolloin lomake aukeaa näkyville. Kopioitu API key liitetään lomakkeen kenttään
ja painetaan nappia _save_, jonka seurauksena ohjelma uudelleenlatautuu. Nyt ohjelma on yhteydessä mailerliteen ja uusien henkilöiden lisääminen onnistuu.

## Uuden henkilön lisääminen

Uusi henkilö lisätään kirjoittamalla lisättävän henkilön tiedot (etunimi, sukunimi, sähköpostiosoite) lomakkeen kenttiin ja valitsemalla sen jälkeen 
oikea ryhmä lomakkeen viereisestä listasta. Tämän jälkeen painetaan _Add to Mailerlite_ nappia ja tiedot siirtyvät käyttäjän mailerlite tilille.

