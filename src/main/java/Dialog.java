import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Class for running the dialog of the story points predictor. The input of the user will
 * be stored here and can be retrieved for feeding the prediction models.
 */
public class Dialog {

    private static final int EXIT_CODE = 1000;

    private static String ALL_TASKS = "1: extend repo interface, 2:extend uc interface, 3:extend queryUc interface, 4:extend webservice, \n" +
            "5:extend unit, 6:implement repo, 7:implement uc, 8: implement queryUc, \n" +
            "9:implement webservice, 10:implement dao, 11: implement unit, 12: validations, \n" +
            "13:ui container, 14: registers, 15: ui fields \n" +
            "(type: '1000' without quotes to end the selection)";

    /**
     * Runs the dialog and takes user input.
     * Through this dialog the user is selecting the features of the story to be evaluated.
     * The features will be represented as numbers.
     * For instance '5' for 'extending the Repository'.
     *
     * @return the feature selection of the user as set
     */
    public Set<Integer> runInputDialog() {
        Set<Integer> userSelection = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHi, i'm gonna help you predict the story points for your story.");
        System.out.println("For that I will need the specifics of the story.");
        System.out.println("\n");
        System.out.println(ALL_TASKS);
        System.out.println();
        int selectedAttribute = scanner.nextInt();
        userSelection.add(selectedAttribute);
        while (selectedAttribute != EXIT_CODE) {
            System.out.println("Is there something else?");
            System.out.println(ALL_TASKS);
            System.out.println();
            selectedAttribute = scanner.nextInt();
            userSelection.add(selectedAttribute);
        }
        return userSelection;
    }

    /**
     * Runs the Dialog for printing the predicted story points.
     * @param storyPoints story points to print in the output dialog.
     */
    public void runOutputDialog(int storyPoints) {
        System.out.println("\n");
        System.out.println("I would give the story: " + storyPoints + " points.");
    }

}
