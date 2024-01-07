import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class VérificationLangueTest {

    @ParameterizedTest
    @MethodSource("casTestBienDit")
    @DisplayName("ETANT DONNE un utilisateur parlant une langue, QUAND on entre un palindrome, ALORS il est renvoyé ET le <bienDit> de cette langue est envoyé")
    public void testPalindromeAvecBienDit(String chaîne, LangueInterface langue, String bienDit) {
        var vérificateur = new VérificationPalindromeBuilder()
                .AyantPourLangue(langue)
                .Build();

        // QUAND on vérifie si c'est un palindrome
        String résultat = vérificateur.Vérifier(chaîne);

        // ALORS la chaîne est renvoyée, suivie de « Bien dit » dans cette langue
        String attendu = chaîne + System.lineSeparator() + bienDit;
        assertTrue(résultat.contains(attendu));
    }

    @ParameterizedTest
    @MethodSource("casTestBonjour")
    @DisplayName("ETANT DONNE un utilisateur parlant une langue, QUAND on saisit une chaîne, ALORS <bonjour> de cette langue est envoyé avant tout")
    public void testBonjour(String chaîne, LangueInterface langue, String bonjour) {
        var vérification = new VérificationPalindromeBuilder()
                .AyantPourLangue(langue)
                .Build();

        // QUAND on vérifie si c'est un palindrome
        String résultat = vérification.Vérifier(chaîne);

        // ALORS toute réponse est précédée de <bonjour> dans cette langue
        String[] lines = résultat.split(System.lineSeparator());
        assertEquals(bonjour, lines[0]);
    }

    @ParameterizedTest
    @MethodSource("casTestAuRevoir")
    @DisplayName("ETANT DONNE un utilisateur parlant une langue, QUAND on saisit une chaîne, ALORS <auRevoir> dans cette langue est envoyé en dernier")
    public void testAuRevoir(String chaîne, LangueInterface langue, String auRevoir) {
        var vérification = new VérificationPalindromeBuilder()
                .AyantPourLangue(langue)
                .Build();

        // QUAND on vérifie si c'est un palindrome
        String résultat = vérification.Vérifier(chaîne);

        // ALORS toute réponse est suivie de <auRevoir> dans cette langue
        String[] lines = résultat.split(System.lineSeparator());
        String lastLine = lines[lines.length - 1];
        assertEquals(auRevoir, lastLine);
    }

    static Stream<Arguments> casTestBienDit() {
        return Stream.of(
                Arguments.of("radar", new LangueFrançaise(), "Bien dit"),
                Arguments.of("level", new LangueAnglaise(), "Well said"),
                Arguments.of("test", new LangueEspagnole(), "Bien dicho")
                // Ajoutez d'autres cas de test avec différentes langues et <bienDit> correspondant
        );
    }

    static Stream<Arguments> casTestBonjour() {
        return Stream.of(
                Arguments.of("test", new LangueFrançaise(), "Bonjour"),
                Arguments.of("radar", new LangueAnglaise(), "Hello"),
                Arguments.of("prueba", new LangueEspagnole(), "Hola")
                // Ajoutez d'autres cas de test avec différentes langues et <bonjour> correspondant
        );
    }

    static Stream<Arguments> casTestAuRevoir() {
        return Stream.of(
                Arguments.of("test", new LangueFrançaise(), "Au revoir"),
                Arguments.of("radar", new LangueAnglaise(), "Goodbye"),
                Arguments.of("prueba", new LangueEspagnole(), "Adiós")
                // Ajoutez d'autres cas de test avec différentes langues et <auRevoir> correspondant
        );
    }
}
