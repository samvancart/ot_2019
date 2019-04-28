# Sähköpostilistan apusovellus
Sovellusksen avulla käyttäjä voi lisätä henkilöitä ja heidän sähköpostejaan valitsemaansa ryhmään www.mailerlite.com -sivustolla sijaitsevalle tililleen.


## Dokumentaatio

[vaatimusmäärittely](https://github.com/samvancart/ot_2019/blob/master/Mail_list_manager/dokumentaatio/vaatimusmaarittely.md)

[työaikakirjanpito](https://github.com/samvancart/ot_2019/blob/master/Mail_list_manager/dokumentaatio/tyoaikakirjanpito.md)

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

