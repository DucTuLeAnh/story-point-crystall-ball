import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/**
 * Provides helpful methods to handle data sets.
 */
public class DataUtils {

    /**
     * Reads the file with the given parameters and returns an data set.
     *
     * @param resourcePath     path to data set
     * @param delimiter        delimiter used in the data set
     * @param numberOfRows     number of rows in the data set
     * @param numberOfFeatures number of features in the data set
     * @param numberOfLabels   number of labels in the data set
     * @return dataset from the given file
     */
    public static DataSet readFile(String resourcePath, char delimiter, int numberOfRows, int numberOfFeatures, int numberOfLabels) {
        try (RecordReader recordReader = new CSVRecordReader(0, delimiter)) {
            recordReader.initialize(new FileSplit(
                    new File(resourcePath)));

            DataSetIterator iterator = new RecordReaderDataSetIterator(
                    recordReader, numberOfRows, numberOfFeatures, numberOfLabels);
            return iterator.next();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new DataSet();
    }

    /**
     * Returns the highest probable prediction for the given probability output.
     *
     * @param output output of an prediction model. For each possible outcome (story points from 1-5), this array
     *               contains the respective probability.
     * @return the story point with the highest probability
     */
    public static int getHighestProbablePrediction(INDArray output) {
        int predction = 1;
        double currentProbability = 0.0;
        for (int i = 0; i < output.length(); i++) {
            if (currentProbability <= output.getDouble(i)) {
                currentProbability = output.getDouble(i);
                predction = i;
            }
        }
        return predction;
    }

    /**
     * Returns the user input as binary vector. If there were for example
     * 15 features selectable, an binary vector with 15 digits would be created.
     * If feature #10 was then selected, the 10th digit in the binary vector
     * would then be represented by a '1' otherwise '0'.
     * For example: [0,0,0,0,0,0,0,0,0,1,0,0,0,0,0].
     *
     * @return user input as binary vector.
     */
    public static double[] getUserSelectionVector(Set<Integer> userSelections, int numberOfFeatures) {
        double[] userSelectionVector = new double[numberOfFeatures];
        Arrays.fill(userSelectionVector, 0);
        userSelections.forEach(attributeNumber -> {
            if (attributeNumber <= numberOfFeatures) {
                userSelectionVector[attributeNumber - 1] = 1;
            }
        });
        return userSelectionVector;
    }
}
