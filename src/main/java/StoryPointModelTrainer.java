import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;

/**
 * Trains the model for the story points predictor.
 */
public class StoryPointModelTrainer {

    private static int ITERATIONS = 1000;
    private static double LEARNING_RATE = 0.1;
    private static double REGULARIZATION = 0.0001;
    private static int NUMBER_OF_FEATURES = 15;
    private static int NUMBER_OF_LABELS = 6;

    private MultiLayerNetwork model;

    /**
     * Trains the model with the given parameters
     * @param dataPath path of the file with data to feed the model
     * @param delimiter the delimiter used in the data file
     * @param numberOfRows number of rows to analyse
     * @param numberOfFeatures number of features existing in the file
     * @param numberOfLabels number of labels existing in the file
     */
    public void train(String dataPath, char delimiter, int numberOfRows, int numberOfFeatures, int numberOfLabels) {
        model = new MultiLayerNetwork(getMultilayerConfiguration());
        model.init();
        DataSet ds = DataUtils.readFile(dataPath, delimiter, numberOfRows, numberOfFeatures, numberOfLabels);
        model.fit(ds);
    }

    /**
     * Saves the model to the given path.
     * @param pathToSave path to save the model
     */
    public void saveTo(String pathToSave) {
        try {
            File locationToSave = new File(pathToSave);
            ModelSerializer.writeModel(model, locationToSave, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MultiLayerConfiguration getMultilayerConfiguration() {
        return new NeuralNetConfiguration.Builder()
                .iterations(ITERATIONS)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .learningRate(LEARNING_RATE)
                .regularization(true).l2(REGULARIZATION)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(NUMBER_OF_FEATURES).nOut(3).build())
                .layer(1, new DenseLayer.Builder().nIn(3).nOut(3).build())
                .layer(2, new OutputLayer.Builder(
                        LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(3).nOut(NUMBER_OF_LABELS).build())
                .backprop(true).pretrain(false)
                .build();
    }
}
