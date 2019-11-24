# Story point crystal ball
Fun project for making an effort estimate for a task measured in story points, based on neural networks using deeplearning4j.

## Requirements
This project was tested on the OpenJdk Runtime Environment Corretto-11.0.3.

## Build project
This project uses gradle for building.
Navigate from the command line into the project folder where the 'gradlew' file lies and run the following command:

```
gradlew toJar
```

This will create an executable jar into this folder: build/libs/story-point-crystal-ball-all-1.0-SNAPSHOT.jar

## How to use
The created jar file can be executed in two modes.

### 1. Training mode
The training mode allows you to create a model/neural network to predict the story points of your task. For that run
the following command:

```
java -jar story-point-crystal-ball-all-1.0-SNAPSHOT.jar firstParam secondParam
```

'firstParam' is the path where your data set lies (currently only .csv files work).
'secondParam' is the path where your model should be saved (the model has to be saved as .zip file).

I put an example .csv file into "src/main/resources/storypoints.csv".

Now you are capable of running the jar for predicting story points.

### 2. Prediction mode
The prediction mode allows you to run a dialog where you can select the attributes of your task. Based on the
selections, the model will give you an effort estimate ranging from 1(easy) to 5 (hard).

Run the following command:

```
java -jar story-point-crystal-ball-all-1.0-SNAPSHOT.jar firstParam
```
'firstParam' is the path where your model as .zip file lies.

Once you run this command the Dialog will ask you for specifics about your task. You can select the attributes
by typing the respective number showed in the dialog and pressing enter.
To End the selection, type '1000' (without quotes) and press enter.
After that you will get the prediction of your model.
