# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla käyttäjän on mahdollista lisätä sähköposteja omalle www.mailerlite.com -sivustolla olevalle sähköpostilistalleen.
Lisääminen tapahtuu mailerliten tarjoaman API:n avulla. Lopullisessa versiossa lisättävän henkilön etu- ja sukunimi sekä sähköpostiosoite tallenetaan
mailerlite.com -sivustolla johonkin käyttäjän siellä määrittelemistä ryhmistä.

## Ohjelman ulkoasu

Ohjelma sisältää graafisen käyttöliittymän, jossa on lomake sähköposteja varten sekä lisäys nappi. Lomake koostuu etu- ja sukunimikentistä sekä
sekä sähköpostiosoitekentästä. Lomakkeen vieressä on combobox johon haetaan käyttäjän hallinoimat ryhmät ohjelman avautessa. Ohjelmassa on myös 
settings sivu, jossa käyttäjä voi hallinnoida mailerlite API key -asetuksiaan. Settings sivu koostuu lomakkeesta, jossa on kenttä API keytä varten.

## Jatkokehitysideoita

* API keyn voi suojata salasanalla

* API key tallentuu ohjelman käyttämään tekstitiedostoon salattuna

