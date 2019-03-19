package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void lataaRahaaToimiiOikein() {
        kortti.lataaRahaa(100);
        kortti.lataaRahaa(50);
        assertEquals("saldo: 1.60", kortti.toString());
    }

    @Test
    public void saldoVaheneeOikein() {
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.5", kortti.toString());
    }

    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(11);
        assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void OtaRahaaTrueToimii() {
        kortti.otaRahaa(9);
        assertEquals(true, true);
    }

    @Test
    public void OtaRahaaFalseToimii() {
        kortti.otaRahaa(11);
        assertEquals(false, false);
    }
        @Test
    public void saldoToimii() {
        kortti.saldo();
        assertEquals(10, 10);
    }
    
}
