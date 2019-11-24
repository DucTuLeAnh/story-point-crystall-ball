import java.util.Set;

/**
 * Entry point of the story point predictor.
 */
public class Main {

    private static int NUMBER_OF_ROWS = 84;
    private static int NUMBER_OF_FEATURES = 15;
    private static int NUMBER_OF_LABELS = 6;

    /**
     * Entry point of the story point predictor.
     * @param args
     */
    public static void main(String[] args) {
        Dialog dialog = new Dialog();
        if (inPredictionMode(args)) {
            runPredictionMode(args, dialog);
        } else if (inTrainMode(args)) {
            runTrainMode(args);
        }
    }

    private static void runTrainMode(String[] args) {
        StoryPointModelTrainer trainer = new StoryPointModelTrainer();
        trainer.train(args[0], ';', NUMBER_OF_ROWS, NUMBER_OF_FEATURES, NUMBER_OF_LABELS);
        trainer.saveTo(args[1]);
        System.out.println("Model trained. You can find it in: " + args[1]);
    }

    private static void runPredictionMode(String[] args, Dialog dialog) {
        StoryPointModelReader reader = new StoryPointModelReader(args[0]);
        Set<Integer> userSelection = dialog.runInputDialog();
        int storyPoints = reader.getPrediction(DataUtils.getUserSelectionVector(userSelection, NUMBER_OF_FEATURES));
        dialog.runOutputDialog(storyPoints);
    }

    private static boolean inPredictionMode(String [] args){
        return args.length == 1;
    }

    private static boolean inTrainMode(String [] args){
        return args.length == 2;
    }
}
