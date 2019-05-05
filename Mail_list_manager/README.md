# Sähköpostilistan apusovellus
Sovellusksen avulla käyttäjä voi lisätä henkilöitä ja heidän sähköpostejaan valitsemaansa ryhmään www.mailerlite.com -sivustolla sijaitsevalle tililleen.


## Dokumentaatio

[Käyttöohje](https://github.com/samvancart/ot_2019/blob/master/Mail_list_manager/dokumentaatio/k%C3%A4ytt%C3%B6ohje.md)

[Vaatimusmäärittely](https://github.com/samvancart/ot_2019/blob/master/Mail_list_manager/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/samvancart/ot_2019/blob/master/Mail_list_manager/dokumentaatio/arkkitehtuurikuvaus.md)

[Testausdokumentti](https://github.com/samvancart/ot_2019/blob/master/Mail_list_manager/dokumentaatio/testaus.md)

[Työaikakirjanpito](https://github.com/samvancart/ot_2019/blob/master/Mail_list_manager/dokumentaatio/tyoaikakirjanpito.md)

## Releaset

[viikko 5](https://github.com/samvancart/ot_2019/releases)

[viikko 6](https://github.com/samvancart/ot_2019/releases)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

`mvn test`

testikattavuusraportti luodaan komennolla

`mvn jacoco:report`

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

`mvn package`

generoi hakemistoon _target_ suoritettavan jar-tiedoston _Mail_list_manager-1.0-SNAPSHOT.jar_

