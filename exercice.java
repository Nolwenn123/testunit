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
    @DisplayName("ETANT DONNE un utilisateur parlant une langue ET que la période de la journée est <période>, QUAND on saisit une chaîne, ALORS <salutation> de cette langue à cette période est envoyé avant tout")
    public void testBonjour(String chaîne, LangueInterface langue, MomentDeLaJournée momentDeLaJournée, String salutation) {
        var vérification = new VérificationPalindromeBuilder()
                .AyantPourLangue(langue)
                .AyantPourMomentDeLaJournée(momentDeLaJournée)
                .Build();

        // QUAND on vérifie si c'est un palindrome
        String résultat = vérification.Vérifier(chaîne);

        // ALORS toute réponse est précédée de <salutation> dans cette langue à cette période de la journée
        String[] lines = résultat.split(System.lineSeparator());
        assertEquals(salutation, lines[0]);
    }

    @ParameterizedTest
    @MethodSource("casTestAuRevoir")
    @DisplayName("ETANT DONNE un utilisateur parlant une langue ET que la période de la journée est <période>, QUAND on saisit une chaîne, ALORS <auRevoir> dans cette langue à cette période est envoyé en dernier")
    public void testAuRevoir(String chaîne, LangueInterface langue, MomentDeLaJournée momentDeLaJournée, String auRevoir) {
        var vérification = new VérificationPalindromeBuilder()
                .AyantPourLangue(langue)
                .AyantPourMomentDeLaJournée(momentDeLaJournée)
                .Build();

        // QUAND on vérifie si c'est un palindrome
        String résultat = vérification.Vérifier(chaîne);

        // ALORS toute réponse est suivie de <auRevoir> dans cette langue à cette période de la journée
        String[] lines = résultat.split(System.lineSeparator());
        String lastLine = lines[lines.length - 1];
        assertEquals(auRevoir, lastLine);
    }

    static Stream<Arguments> casTestBienDit() {
        return Stream.of(
                Arguments.of("radar", new LangueFrançaise(), MomentDeLaJournée.Matin, "Bonjour_am"),
                Arguments.of("level", new LangueAnglaise(), MomentDeLaJournée.AprèsMidi, "Bonjour_pm"),
                Arguments.of("test", new LangueEspagnole(), MomentDeLaJournée.Soirée, "Bonjour_soir")
                // Ajoutez d'autres cas de test avec différentes langues, périodes de la journée, et <salutation> correspondant
        );
    }

    static Stream<Arguments> casTestAuRevoir() {
        return Stream.of(
                Arguments.of("test", new LangueFrançaise(), MomentDeLaJournée.Matin, "AuRevoir_am"),
                Arguments.of("radar", new LangueAnglaise(), MomentDeLaJournée.AprèsMidi, "AuRevoir_pm"),
                Arguments.of("prueba", new LangueEspagnole(), MomentDeLaJournée.Soirée, "AuRevoir_soir")
                // Ajoutez d'autres cas de test avec différentes langues, périodes de la journée, et <auRevoir> correspondant
        );
    }
}
