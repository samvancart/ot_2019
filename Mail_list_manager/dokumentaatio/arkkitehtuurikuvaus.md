# Arkkitehtuurikuvaus

## Rakenne

Ohjelman pakkausrakenne on seuraava:

<img src="https://github.com/samvancart/ot_2019/blob/master/Mail_list_manager/dokumentaatio/kuvat/pakkausrakenne.jpg" width="160">

Pakkaus _mail_list_manager.ui_ sisältää JavaFX:llä tehdyn graafisen käyttöliittymän, mail_list_manager.domain sovelluslogiikan, 
mail_list_manager.services huolehtii verkkoyhteyden muodostamisesta mailerlite.comiin sekä tietojen noutamisesta ja lähettämisestä
ja mail_list_manager.dao huolehtii tietojen talletuksesta.

## Käyttöliittymä

Käyttöliittymä sisältää kaksi Scene-oliona toteutettua näkymää (henkilöiden lisäys, asetukset), joista yksi on kerrallaan näkyvissä.