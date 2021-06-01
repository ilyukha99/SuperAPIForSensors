package org.sas.security.jwt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application.properties")
@ContextConfiguration(classes = JwtProvider.class)
public class JwtProviderTest {

    @Autowired
    private final JwtProvider jwtProvider = new JwtProvider();

    /**
     * @see org.sas.security.jwt.JwtProvider#generateToken(String, int)
     * @see org.sas.security.jwt.JwtProvider#getLoginFromToken(String)
     */
    @Test
    public void testTokenGeneration() {
        final int duration = 25;
        final String userLogin = "test";
        String token = jwtProvider.generateToken(userLogin, duration);
        assertEquals(jwtProvider.getLoginFromToken(token), userLogin);
    }

    @Test
    public void checkInvalidToken() {
        assertNull(jwtProvider.getLoginFromToken("ddfddffddd"));
    }
}
