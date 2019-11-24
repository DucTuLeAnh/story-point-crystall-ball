import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DataUtilsTest {

    @Test
    public void test_getHighestProbablePrediction(){
        double [] probabilities = {0.0,0.25,0.5,0.25};
        INDArray modelOutput = Nd4j.create(probabilities);
        int storypoints = DataUtils.getHighestProbablePrediction(modelOutput);
        assertThat(storypoints, is(2));
    }

    @Test
    public void test_getStorypointModelInput(){
        Set<Integer> selections = new HashSet<>();
        selections.add(2);
        selections.add(3);

        double[] selectionVector = DataUtils.getUserSelectionVector(selections,5);

        assertThat(selectionVector[0], is(0.0));
        assertThat(selectionVector[1], is(1.0));
        assertThat(selectionVector[2], is(1.0));
        assertThat(selectionVector[3], is(0.0));
        assertThat(selectionVector[4], is(0.0));
    }

}