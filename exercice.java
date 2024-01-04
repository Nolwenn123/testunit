import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class VérificationMiroirTest {

    @ParameterizedTest
    @ValueSource(strings = {"test", "epsi"})
    @DisplayName("QUAND on saisit une chaîne ALORS celle-ci est renvoyée en miroir")
    public void testMiroir(String chaîne) {
   
        String résultat = VérificationPalindromeBuilder.Default().Vérifier(chaîne);
        String inversion = new StringBuilder(chaîne)
                .reverse()
                .toString();

        assertTrue(résultat.contains(inversion));
    }

    @Test
    @DisplayName("QUAND on saisit un palindrome ALORS celui-ci est renvoyé ET « Bien dit » est envoyé ensuite")
    public void testPalindromeAvecBienDit() {
        String palindrome = "radar";

        var bienDit = "Bien dit";
        var vérificateur = new VérificationPalindromeBuilder()
                .AyantPourLangue(
                        langue -> langue.AyantPourFélicitations(bienDit)
                )
                .Build();

        String résultat = vérificateur.Vérifier(palindrome);
        String attendu = palindrome + System.lineSeparator() + bienDit;
        assertTrue(résultat.contains(attendu));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "radar"})
    @DisplayName("QUAND on saisit une chaîne ALORS « Bonjour » est envoyé avant toute réponse")
    public void testBonjour(String chaîne) {
        var vérification = new VérificationPalindromeBuilder()
                .Build();

        String résultat = vérification.Vérifier(chaîne);

        String[] lines = résultat.split(System.lineSeparator());
        assertEquals(Expressions.Bonjour, lines[0]);
    }

    @Test
    @DisplayName("QUAND on saisit une chaîne ALORS « Au revoir » est envoyé en dernier")
    public void testAuRevoir() {
        String résultat =  VérificationPalindromeBuilder.Default().Vérifier("test");
        String[] lines = résultat.split(System.lineSeparator());
        String lastLine = lines[lines.length - 1];
        assertEquals(Expressions.AuRevoir, lastLine);
    }
}
