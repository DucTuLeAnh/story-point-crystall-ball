import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

/**
 * Uses the given model to make a prediction for the story point predictor.
 */
public class StoryPointModelReader {

    private MultiLayerNetwork model;

    /**
     * Creates the object for the given path of the model.
     * @param pathToModel path to the model for the story point predictor.
     */
    public StoryPointModelReader(String pathToModel){
        try {
            this.model = ModelSerializer.restoreMultiLayerNetwork(new File(pathToModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the predicted story points for the given input binary vector.
     * @param input the feature selection of the user encoded as binary vector.
     * @return story points predicted by the model.
     */
    public int getPrediction(double[] input) {
        INDArray output = model.output(Nd4j.create(input));
        return DataUtils.getHighestProbablePrediction(output);
    }

}
