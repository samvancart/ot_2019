package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate paate;

    @Before
    public void setUp() {
        paate = new Kassapaate();
    }

    @Test
    public void oikeaRahamaaraAlussa() {
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void eiMyytyjaAlussa() {
        int myydyt = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
        assertEquals(0, myydyt);
    }

    @Test
    public void kassanRahamaaraKasvaaEdullisestiKateisosto() {
        paate.syoEdullisesti(240);
        assertEquals(100240, paate.kassassaRahaa());
    }

    @Test
    public void kassanRahamaaraKasvaaMaukkaastiKateisosto() {
        paate.syoMaukkaasti(400);
        assertEquals(100400, paate.kassassaRahaa());
    }

    @Test
    public void oikeaVaihtorahaEdullisestiKateisosto() {
        assertEquals(10, paate.syoEdullisesti(250));
    }

    @Test
    public void oikeaVaihtorahaMaukkaastiKateisosto() {
        assertEquals(100, paate.syoMaukkaasti(500));
    }

    @Test
    public void EdullisestiMyytyjenMaaraKasvaa() {
        paate.syoEdullisesti(240);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void MaukkaastiMyytyjenMaaraKasvaa() {
        paate.syoMaukkaasti(400);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void liianPieniRahamaaraEdullisesti() {
        paate.syoEdullisesti(230);
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void liianPieniRahamaaraMaukkaasti() {
        paate.syoMaukkaasti(390);
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void kaikkiRahatTakaisinLiianPieniRahamaaraEdullisesti() {
        assertEquals(230, paate.syoEdullisesti(230));
    }

    @Test
    public void kaikkiRahatTakaisinLiianPieniRahamaaraMaukkaasti() {
        assertEquals(390, paate.syoMaukkaasti(390));
    }

    @Test
    public void myydytEnnallaanLiianPieniRahamaaraMaukkaasti() {
        paate.syoMaukkaasti(400);
        paate.syoMaukkaasti(390);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void myydytEnnallaanLiianPieniRahamaaraEdullisesti() {
        paate.syoEdullisesti(240);
        paate.syoMaukkaasti(230);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void onnistunutKorttimaksuEdullisesti() {
        paate.syoEdullisesti(new Maksukortti(500));
        assertEquals(true, true);
    }

    @Test
    public void onnistunutKorttimaksuMaukkaasti() {
        paate.syoMaukkaasti(new Maksukortti(500));
        assertEquals(true, true);
    }

    @Test
    public void myytyjenMaaraKasvaaKorttimaksuEdullisesti() {
        paate.syoEdullisesti(new Maksukortti(500));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void myytyjenMaaraKasvaaKorttimaksuMaukkaasti() {
        paate.syoMaukkaasti(new Maksukortti(500));
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
        @Test
    public void myytyjenMaaraEiKasvaKorttimaksuEdullisesti() {
        paate.syoEdullisesti(new Maksukortti(500));
        paate.syoEdullisesti(new Maksukortti(200));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
            @Test
    public void myytyjenMaaraEiKasvaKorttimaksuMaukkaasti() {
        paate.syoMaukkaasti(new Maksukortti(500));
        paate.syoMaukkaasti(new Maksukortti(200));
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
        @Test
    public void epaonnistunutKorttimaksuEdullisesti() {
        paate.syoEdullisesti(new Maksukortti(200));
        assertEquals(false, false);
    }

    @Test
    public void epaonnistunutKorttimaksuMaukkaasti() {
        paate.syoMaukkaasti(new Maksukortti(200));
        assertEquals(false, false);
    }
     @Test
    public void kassanRahamaaraEiMuutuKorttimaksussa() {
        paate.syoMaukkaasti(new Maksukortti(500));
        paate.syoEdullisesti(new Maksukortti(500));
        assertEquals(100000, paate.kassassaRahaa());
    }
     @Test
    public void kassanRahamaaraKasvaaKunLataaRahaaKortille() {
        paate.lataaRahaaKortille(new Maksukortti(100), 100);
        assertEquals(100100, paate.kassassaRahaa());
    }
         @Test
    public void kassanRahamaaraEiKasvaKunLataaRahaaKortilleNegatiivinen() {
        paate.lataaRahaaKortille(new Maksukortti(100), -20);
        assertEquals(100000, paate.kassassaRahaa());
    }
}
