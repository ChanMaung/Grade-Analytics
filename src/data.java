
//Names: 
//	Alec Baldus
//	Matt Keeley
//	Chan Maung
//	Micah Schmidt
//Lecture: M 9:40-10:30AM
//Description: The program allows for the insertion of a set of floating point numbers and for analysis to be performed. 
//Data may be read from text files (.txt), comma separated value files (.csv), or by manual input using standard Java input. 
//When data has been inserted into the program, the user may ask for analysis which includes the number of entries, the range, 
//mean, median, mode of the data, and a graph. 

import java.io.File; //Import File Operation items
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList; //Import Arraylist/Scanner items
import java.util.Collections;
import java.util.Scanner;
import javafx.application.Application; //Import Javafx items
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class data extends Application {
	private ArrayList<Float> arrGrades = new ArrayList<>(); // Grade array of floats
	private ArrayList<String> arrErrorLog = new ArrayList<>(); // Error array of strings
	private ArrayList<String> arrReportLog = new ArrayList<>(); // Report array of strings
	private int min = 0, max = 100; // defaults
	private StackPane rootPane; // GUI window created from StackPane
	private GridPane gp; // Gridpane holds rows
	private HBox h1, h2, h3, h4, h5, h6, h7, h8, h9, h10; // HBox holds row contents
	private Label insertData, analysis, errors, report, end; // Labels
	private Button load, append, add, delete, setBoundaries, analyze, display, graph, distribution, errorLog, reportLog,
			exit; // Buttons

	public void start(Stage primaryStage) // Start called in main
	{
		rootPane = new StackPane(); // Instantiate
		gp = new GridPane();
		h1 = new HBox();
		h2 = new HBox();
		h3 = new HBox();
		h4 = new HBox();
		h5 = new HBox();
		h6 = new HBox();
		h7 = new HBox();
		h8 = new HBox();
		h9 = new HBox();
		h10 = new HBox();
		insertData = new Label("Insert Data:"); // Create Label
		analysis = new Label("Analysis:");
		errors = new Label("Errors:");
		report = new Label("Report:");
		end = new Label("End:");
		load = new Button("Load from File"); // Create Button
		append = new Button("Append from File");
		add = new Button("Add Value Manually");
		delete = new Button("Remove Value");
		setBoundaries = new Button("Set Boundaries");
		analyze = new Button("Print Analysis");
		display = new Button("Display");
		graph = new Button("Display Graph");
		distribution = new Button("Distribution");
		errorLog = new Button("Print Error Log");
		reportLog = new Button("Export Full Report as Text File");
		exit = new Button("Terminate Program");

		load.setOnAction(new ButtonHandler()); // Attach handler to buttons
		append.setOnAction(new ButtonHandler());
		add.setOnAction(new ButtonHandler());
		delete.setOnAction(new ButtonHandler());
		setBoundaries.setOnAction(new ButtonHandler());
		analyze.setOnAction(new ButtonHandler());
		display.setOnAction(new ButtonHandler());
		graph.setOnAction(new ButtonHandler());
		distribution.setOnAction(new ButtonHandler());
		errorLog.setOnAction(new ButtonHandler());
		reportLog.setOnAction(new ButtonHandler());
		exit.setOnAction(new ButtonHandler());

		h1.getChildren().add(insertData); // Add label to HBox
		h2.getChildren().addAll(load, append, add, delete, setBoundaries); // Add buttons to HBox in following row
		h3.getChildren().add(analysis);
		h4.getChildren().addAll(analyze, display, graph, distribution);
		h5.getChildren().add(errors);
		h6.getChildren().addAll(errorLog);
		h7.getChildren().add(report);
		h8.getChildren().addAll(reportLog);
		h9.getChildren().add(end);
		h10.getChildren().addAll(exit);
		gp.add(h1, 0, 0); // Add HBoxes to Gridpane
		gp.add(h2, 0, 1);
		gp.add(h3, 0, 2);
		gp.add(h4, 0, 3);
		gp.add(h5, 0, 4);
		gp.add(h6, 0, 5);
		gp.add(h7, 0, 6);
		gp.add(h8, 0, 7);
		gp.add(h9, 0, 8);
		gp.add(h10, 0, 9);

		Scene scene = new Scene(rootPane, 700, 400); // Create scene and place rootPane in the stage
		rootPane.getChildren().add(gp); // Place Gridpane in rootPane

		primaryStage.setTitle("Data Processing"); // Title
		primaryStage.setScene(scene); // Place scene in stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		Application.launch(args); // Launch GUI application
	}

	private class ButtonHandler implements EventHandler<ActionEvent> // ButtonHandler class
	{
		public void handle(ActionEvent event) // handler method
		{
			Object action = event.getSource(); // action that recieves source

			if (action == load) // Load Button
			{
				arrGrades = new ArrayList<>();
				System.out.println("Load");
				File file;
				Scanner inFile = null;

				TextInputDialog addFile = new TextInputDialog("File Name");
				addFile.setTitle("Load a File");
				addFile.setHeaderText(null);
				addFile.setContentText("Enter file name to add:");

				addFile.showAndWait();
				String tempLine = addFile.getResult();

				try {
					file = new File(tempLine);
					inFile = new Scanner(file);
					while (inFile.hasNextLine()) {
						String tempLine2 = inFile.nextLine();
						try {
							Float.parseFloat(tempLine2);
						} catch (NumberFormatException e) {
							Alert incorrectFormat = new Alert(AlertType.INFORMATION);
							incorrectFormat.setTitle("Alert");
							incorrectFormat.setHeaderText(null);
							incorrectFormat.setContentText("Data format incorrect (value must be float or integer)");
							incorrectFormat.showAndWait();
							arrErrorLog.add("Load from file: Data format incorrect (value must be float or integer)");

						}
						if (Float.parseFloat(tempLine2) <= max && Float.parseFloat(tempLine2) >= min) {
							float num = Float.parseFloat(tempLine2);
							arrGrades.add(num);
						} else {
							Alert outOfRange = new Alert(AlertType.INFORMATION);
							outOfRange.setTitle("Alert");
							outOfRange.setHeaderText(null);
							outOfRange.setContentText("Some Data Out of Range");

							outOfRange.showAndWait();
							arrErrorLog.add("Load from file: Some value out of bound");
						}
					}
					Alert displayLoaded = new Alert(AlertType.INFORMATION);
					displayLoaded.setTitle("Alert");
					displayLoaded.setHeaderText(null);
					displayLoaded.setContentText("Data Loaded");
					displayLoaded.showAndWait();
					arrReportLog.add("Loaded from file: " + tempLine);
				} catch (FileNotFoundException e1) {
					Alert displayLoaded = new Alert(AlertType.INFORMATION);
					displayLoaded.setTitle("Alert");
					displayLoaded.setHeaderText(null);
					displayLoaded.setContentText("Data Not Found");

					displayLoaded.showAndWait();
					System.out.println("File not found");
					arrErrorLog.add("Load from file: File name not fould");
				}
				if (inFile != null) {
					inFile.close();
				}
			} else if (action == append) // Append Button
			{
				System.out.println("Append");
				File file;
				Scanner inFile = null;

				TextInputDialog addAppend = new TextInputDialog("File Name");
				addAppend.setTitle("Append a File");
				addAppend.setHeaderText(null);
				addAppend.setContentText("Enter file name to append:");

				addAppend.showAndWait();
				String tempLine = addAppend.getResult();

				try {
					file = new File(tempLine);
					inFile = new Scanner(file);
					while (inFile.hasNextLine()) {
						String tempLine2 = inFile.nextLine();
						try {
							Float.parseFloat(tempLine2);
						} catch (NumberFormatException e) {
							Alert incorrectFormat = new Alert(AlertType.INFORMATION);
							incorrectFormat.setTitle("Alert");
							incorrectFormat.setHeaderText(null);
							incorrectFormat.setContentText("Data format incorrect (value must be float or integer)");
							incorrectFormat.showAndWait();
							arrErrorLog.add("Append from file: Data format incorrect (value must be float or integer)");
						}
						if (Float.parseFloat(tempLine2) <= max && Float.parseFloat(tempLine2) >= min) {
							float num = Float.parseFloat(tempLine2);
							arrGrades.add(num);
						} else {
							Alert outOfRange = new Alert(AlertType.INFORMATION);
							outOfRange.setTitle("Alert");
							outOfRange.setHeaderText(null);
							outOfRange.setContentText("Some Data Out of Range");

							outOfRange.showAndWait();
							arrErrorLog.add("Append from file: Some value out of bound");
						}
					}
					Alert displayAppended = new Alert(AlertType.INFORMATION);
					displayAppended.setTitle("Alert");
					displayAppended.setHeaderText(null);
					displayAppended.setContentText("Data Appended");
					displayAppended.showAndWait();
					arrReportLog.add("Appended from file: " + tempLine);
				} catch (FileNotFoundException e1) {

					Alert displayAppended = new Alert(AlertType.INFORMATION);
					displayAppended.setTitle("Alert");
					displayAppended.setHeaderText(null);
					displayAppended.setContentText("Data Not Found");

					displayAppended.showAndWait();
					arrErrorLog.add("Append from file: File name not fould");
				}
				if (inFile != null) {
					inFile.close();
				}

			} else if (action == add) // Add Button
			{
				System.out.println("Add");
				TextInputDialog addInput = new TextInputDialog("Value");
				addInput.setTitle("Add a Value");
				addInput.setHeaderText(null);
				addInput.setContentText("Enter value to add:");

				addInput.showAndWait();
				String tempLine = addInput.getResult();
				try {
					Float.parseFloat(tempLine);
				} catch (NumberFormatException e) {
					Alert incorrectFormat = new Alert(AlertType.INFORMATION);
					incorrectFormat.setTitle("Alert");
					incorrectFormat.setHeaderText(null);
					incorrectFormat.setContentText("Data format incorrect (value must be float or integer)");
					incorrectFormat.showAndWait();
					arrErrorLog.add("Add Value Manually: Value not a number");
				}
				if (Float.parseFloat(tempLine) <= max && Float.parseFloat(tempLine) >= min) {
					float num = Float.parseFloat(tempLine);
					arrGrades.add(num);
					arrReportLog.add("Added value: " + num);
				} else {
					Alert outOfRange = new Alert(AlertType.INFORMATION);
					outOfRange.setTitle("Alert");
					outOfRange.setHeaderText(null);
					outOfRange.setContentText("Data Out of Range");

					outOfRange.showAndWait();
					arrErrorLog.add("Add Value Manually: Value out of bound");
				}
			} else if (action == delete) // Delete Button
			{
				System.out.println("Delete");
				TextInputDialog deleteInput = new TextInputDialog("Value");
				deleteInput.setTitle("Remove a Value");
				deleteInput.setHeaderText(null);
				deleteInput.setContentText("Enter value to delete:");

				deleteInput.showAndWait();
				String tempLine = deleteInput.getResult();
				float num = Float.parseFloat(tempLine);
				int j = 0;
				for (int i = 0; i < arrGrades.size(); i++) {
					if (num == arrGrades.get(i)) {
						arrGrades.remove(i);
						j = 1;

						Alert displayRemove = new Alert(AlertType.INFORMATION);
						displayRemove.setTitle("Alert");
						displayRemove.setHeaderText(null);
						displayRemove.setContentText("Data Removed");
						displayRemove.showAndWait();
						arrReportLog.add("Removed value: " + num);
					}
				}
				if (j == 0) {
					Alert displayRemove = new Alert(AlertType.INFORMATION);
					displayRemove.setTitle("Alert");
					displayRemove.setHeaderText(null);
					displayRemove.setContentText("Data Not Found");

					displayRemove.showAndWait();
					arrErrorLog.add("Remove Value: Value not found");
				}

			} else if (action == setBoundaries) // Set Boundaries Button
			{
				System.out.println("Bounds");

				Dialog<String> bounds = new Dialog<String>();
				bounds.setTitle("Set Boundaries");
				bounds.setHeaderText(null);

				TextField MIN = new TextField("Enter MIN");
				TextField MAX = new TextField("Enter MAX");

				ButtonType SetBound = new ButtonType("Set Bounds", ButtonData.OK_DONE);
				ButtonType Cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
				bounds.getDialogPane().getButtonTypes().addAll(SetBound, Cancel);

				GridPane gridPane = new GridPane();

				gridPane.add(MIN, 0, 0);
				gridPane.add(MAX, 0, 1);

				bounds.getDialogPane().setContent(gridPane);
				bounds.showAndWait();

				String tempLine = MIN.getText();
				int tempMin = Integer.parseInt(tempLine);

				String tempLine2 = MAX.getText();
				int tempMax = Integer.parseInt(tempLine2);

				if (tempMin == tempMax) {
					Alert minMaxEqual = new Alert(AlertType.INFORMATION);
					minMaxEqual.setTitle("Alert");
					minMaxEqual.setHeaderText(null);
					minMaxEqual.setContentText("Error: Set Min and Max are equal.");

					minMaxEqual.showAndWait();
					arrErrorLog.add("Error: Set Min and Max are equal.");
				} else if (tempMin > tempMax) {
					Alert minGreater = new Alert(AlertType.INFORMATION);
					minGreater.setTitle("Alert");
					minGreater.setHeaderText(null);
					minGreater.setContentText("Error: Set Min is greater than Max.");

					minGreater.showAndWait();
					arrErrorLog.add("Error: Set Min is greater than Max.");
				} else {
					min = tempMin;
					max = tempMax;
					arrReportLog.add("Set Bounds: " + min + " to " + max);
				}
			} else if (action == analyze) // Analysis Button
			{
				System.out.println("Analysis");

				if (arrGrades.isEmpty() == false) {
					int iEntries = arrGrades.size();

					Collections.sort(arrGrades);
					float fLow = arrGrades.get(0);

					Collections.sort(arrGrades, Collections.reverseOrder());
					float fHigh = arrGrades.get(0);

					float fMean = mean(arrGrades); // method at end of code

					float fMedian = median(arrGrades);

					float fMode = mode(arrGrades);

					Dialog<String> analyze = new Dialog<String>();
					analyze.setTitle("Analysis");
					analyze.setHeaderText(null);

					Label Entries = new Label("# of Entries: " + Integer.toString(iEntries));
					Label High = new Label("High: " + Float.toString(fHigh));
					Label Low = new Label("Low: " + Float.toString(fLow));
					Label Mean = new Label("Mean: " + Float.toString(fMean));
					Label Median = new Label("Median: " + Float.toString(fMedian));
					Label Mode = new Label("Mode: " + Float.toString(fMode));

					ButtonType Ok = new ButtonType("Ok", ButtonData.OK_DONE);

					analyze.getDialogPane().getButtonTypes().add(Ok);

					GridPane gridPane = new GridPane();

					gridPane.setVgap(10);
					gridPane.setHgap(10);
					gridPane.add(Entries, 0, 0);
					gridPane.add(High, 1, 1);
					gridPane.add(Low, 0, 1);
					gridPane.add(Mean, 0, 2);
					gridPane.add(Median, 1, 2);
					gridPane.add(Mode, 2, 2);

					analyze.getDialogPane().setContent(gridPane);
					analyze.showAndWait();

					arrReportLog.add("Printed Analysis");
				} else {
					Alert noData = new Alert(AlertType.INFORMATION);
					noData.setTitle("Alert");
					noData.setHeaderText(null);
					noData.setContentText("Data Not Found");

					noData.showAndWait();
					arrErrorLog.add("Print Analysis: Data not found");
				}
			} else if (action == display) // Display Button
			{
				System.out.println("Display");

				Collections.sort(arrGrades, Collections.reverseOrder());
				Dialog<String> display = new Dialog<String>();
				display.setTitle("Display");
				display.setHeaderText(null);

				ButtonType Ok = new ButtonType("Ok", ButtonData.OK_DONE);

				display.getDialogPane().getButtonTypes().add(Ok);

				GridPane gridPane = new GridPane();

				gridPane.setVgap(10);
				gridPane.setHgap(10);

				ArrayList<Float> arrGradesCopy = new ArrayList<>();
				arrGradesCopy = (ArrayList<Float>) arrGrades.clone();
				while (arrGradesCopy.size() % 4 != 0) {
					arrGradesCopy.add((float) 0);
				}
				int j = 0;
				int k = 0;
				for (int i = 0; i < arrGradesCopy.size() / 4; i++) {
					if (arrGradesCopy.get(k).equals((float) 0)) {
						Label A = new Label(" ");
					} else {
						Label A = new Label(arrGradesCopy.get(k).toString());
						gridPane.add(A, 0, j);
					}
					k++;
					j++;
				}

				j = 0;
				for (int i = 0; i < arrGradesCopy.size() / 4; i++) {
					if (arrGradesCopy.get(k).equals((float) 0)) {
						Label A = new Label(" ");
					} else {
						Label A = new Label(arrGradesCopy.get(k).toString());
						gridPane.add(A, 1, j);
					}
					k++;
					j++;
				}

				j = 0;
				for (int i = 0; i < arrGradesCopy.size() / 4; i++) {
					if (arrGradesCopy.get(k).equals((float) 0)) {
						Label A = new Label(" ");
					} else {
						Label A = new Label(arrGradesCopy.get(k).toString());
						gridPane.add(A, 2, j);
					}
					k++;
					j++;
				}

				j = 0;
				for (int i = 0; i < arrGradesCopy.size() / 4; i++) {
					if (arrGradesCopy.get(k).equals((float) 0)) {
						Label A = new Label(" ");
					} else {
						Label A = new Label(arrGradesCopy.get(k).toString());
						gridPane.add(A, 3, j);
					}
					k++;
					j++;
				}

				display.getDialogPane().setContent(gridPane);
				display.showAndWait();
				arrReportLog.add("Printed Display");

			} else if (action == graph) // Graph Button
			{
				System.out.println("Graph");

				Stage graph = new Stage();
				graph.setTitle("Graph");

				String A = "0 - 10", B = "11 - 20", C = "21 - 30", D = "31 - 40", E = "41 - 50";
				String F = "51 - 60", G = "61 - 70", H = "71 - 80", I = "81 - 90", J = "91 - 100";
				int A1 = 0, B1 = 0, C1 = 0, D1 = 0, E1 = 0;
				int F1 = 0, G1 = 0, H1 = 0, I1 = 0, J1 = 0;
				for (int k = 0; k < arrGrades.size(); k++) {
					if (arrGrades.get(k) >= 0 && arrGrades.get(k) <= 10) {
						A1++;
					} else if (arrGrades.get(k) >= 11 && arrGrades.get(k) <= 20) {
						B1++;
					} else if (arrGrades.get(k) >= 21 && arrGrades.get(k) <= 30) {
						C1++;
					} else if (arrGrades.get(k) >= 31 && arrGrades.get(k) <= 40) {
						D1++;
					} else if (arrGrades.get(k) >= 41 && arrGrades.get(k) <= 50) {
						E1++;
					} else if (arrGrades.get(k) >= 51 && arrGrades.get(k) <= 60) {
						F1++;
					} else if (arrGrades.get(k) >= 61 && arrGrades.get(k) <= 70) {
						G1++;
					} else if (arrGrades.get(k) >= 71 && arrGrades.get(k) <= 80) {
						H1++;
					} else if (arrGrades.get(k) >= 81 && arrGrades.get(k) <= 90) {
						I1++;
					} else if (arrGrades.get(k) >= 91 && arrGrades.get(k) <= 100) {
						J1++;
					}
				}
				final CategoryAxis xAxis = new CategoryAxis();
				final NumberAxis yAxis = new NumberAxis();

				final BarChart<String, Number> BarChart = new BarChart<String, Number>(xAxis, yAxis);
				BarChart.setTitle("Amount of Grades by Percentage");
				xAxis.setLabel("Percentage");
				yAxis.setLabel("Amount of Grade");

				XYChart.Series series = new XYChart.Series();
				series.setName("grades");
				series.getData().add(new XYChart.Data(A, A1));
				series.getData().add(new XYChart.Data(B, B1));
				series.getData().add(new XYChart.Data(C, C1));
				series.getData().add(new XYChart.Data(D, D1));
				series.getData().add(new XYChart.Data(E, E1));
				series.getData().add(new XYChart.Data(F, F1));
				series.getData().add(new XYChart.Data(G, G1));
				series.getData().add(new XYChart.Data(H, H1));
				series.getData().add(new XYChart.Data(I, I1));
				series.getData().add(new XYChart.Data(J, J1));

				Scene scene = new Scene(BarChart, 800, 600);
				BarChart.getData().add(series);

				graph.setScene(scene);
				graph.showAndWait();
				arrReportLog.add("Printed Graph");

			} else if (action == distribution) // Distribution Button
			{
				System.out.println("Distribution");

				Stage graph = new Stage();
				graph.setTitle("Distribution");

				String A = "0 - 10", B = "11 - 20", C = "21 - 30", D = "31 - 40", E = "41 - 50";
				String F = "51 - 60", G = "61 - 70", H = "71 - 80", I = "81 - 90", J = "91 - 100";
				float A1 = 0, B1 = 0, C1 = 0, D1 = 0, E1 = 0;
				float F1 = 0, G1 = 0, H1 = 0, I1 = 0, J1 = 0;
				for (int k = 0; k < arrGrades.size(); k++) {
					if (arrGrades.get(k) >= 0 && arrGrades.get(k) <= 10) {
						A1++;
					} else if (arrGrades.get(k) >= 11 && arrGrades.get(k) <= 20) {
						B1++;
					} else if (arrGrades.get(k) >= 21 && arrGrades.get(k) <= 30) {
						C1++;
					} else if (arrGrades.get(k) >= 31 && arrGrades.get(k) <= 40) {
						D1++;
					} else if (arrGrades.get(k) >= 41 && arrGrades.get(k) <= 50) {
						E1++;
					} else if (arrGrades.get(k) >= 51 && arrGrades.get(k) <= 60) {
						F1++;
					} else if (arrGrades.get(k) >= 61 && arrGrades.get(k) <= 70) {
						G1++;
					} else if (arrGrades.get(k) >= 71 && arrGrades.get(k) <= 80) {
						H1++;
					} else if (arrGrades.get(k) >= 81 && arrGrades.get(k) <= 90) {
						I1++;
					} else if (arrGrades.get(k) >= 91 && arrGrades.get(k) <= 100) {
						J1++;
					}
				}
				final CategoryAxis xAxis = new CategoryAxis();
				final NumberAxis yAxis = new NumberAxis();

				final BarChart<String, Number> BarChart = new BarChart<String, Number>(xAxis, yAxis);
				BarChart.setTitle("Distribution of Grades");
				xAxis.setLabel("Percentage");
				yAxis.setLabel("Percent of Grades in Each Range");

				XYChart.Series series = new XYChart.Series();
				series.setName("grades");
				series.getData().add(new XYChart.Data(A, (A1 / arrGrades.size())));
				series.getData().add(new XYChart.Data(B, (B1 / arrGrades.size())));
				series.getData().add(new XYChart.Data(C, (C1 / arrGrades.size())));
				series.getData().add(new XYChart.Data(D, (D1 / arrGrades.size())));
				series.getData().add(new XYChart.Data(E, (E1 / arrGrades.size())));
				series.getData().add(new XYChart.Data(F, (F1 / arrGrades.size())));
				series.getData().add(new XYChart.Data(G, (G1 / arrGrades.size())));
				series.getData().add(new XYChart.Data(H, (H1 / arrGrades.size())));
				series.getData().add(new XYChart.Data(I, (I1 / arrGrades.size())));
				series.getData().add(new XYChart.Data(J, (J1 / arrGrades.size())));

				Scene scene = new Scene(BarChart, 800, 600);
				BarChart.getData().add(series);

				graph.setScene(scene);
				graph.showAndWait();
				arrReportLog.add("Printed Distribution");

			} else if (action == errorLog) // Error Log Button
			{
				System.out.println("Error Log");

				Dialog<String> Error = new Dialog<String>();
				Error.setTitle("Error Log");
				Error.setHeaderText(null);

				ButtonType Ok = new ButtonType("Ok", ButtonData.OK_DONE);
				Error.getDialogPane().getButtonTypes().add(Ok);

				GridPane gridPane = new GridPane();
				gridPane.setHgap(3);

				int j = 0;
				for (int i = 0; i < arrErrorLog.size(); i++) {
					Label A = new Label(arrErrorLog.get(i).toString());
					gridPane.add(A, 0, j);
					j++;
				}

				Error.getDialogPane().setContent(gridPane);
				Error.showAndWait();
				arrReportLog.add("Printed Error Log");

			} else if (action == reportLog) // Report Log Button
			{
				System.out.println("Report Log");

				arrReportLog.add("Printed Full Report");

				Dialog<String> Report = new Dialog<String>();
				Report.setTitle("Error Log");
				Report.setHeaderText(null);

				ButtonType Ok = new ButtonType("Ok", ButtonData.OK_DONE);
				Report.getDialogPane().getButtonTypes().add(Ok);

				GridPane gridPane = new GridPane();
				gridPane.setHgap(3);

				int j = 0;
				for (int i = 0; i < arrReportLog.size(); i++) {
					Label A = new Label(arrReportLog.get(i).toString());
					gridPane.add(A, 0, j);
					j++;
				}
				Report.getDialogPane().setContent(gridPane);
				Report.showAndWait();

				File outputFile = new File("output.txt"); // create txt file in project folder
				try {
					FileWriter fileWriter = new FileWriter(outputFile);
					for (int i = 0; i < arrReportLog.size(); i++) {
						fileWriter.write(arrReportLog.get(i) + "\n");
					}
					fileWriter.close();
				} catch (IOException e) {
					System.out.println("Error");
				}
			} else if (action == exit) // Exit Button
			{
				System.out.println("Exit");
				System.exit(0);
			}
		}
	}

	public static float mean(ArrayList<Float> arrGrades) {
		float sum = 0;
		for (int i = 0; i < arrGrades.size(); i++) {
			sum = sum + arrGrades.get(i);
		}
		return sum / arrGrades.size();
	}

	public static float median(ArrayList<Float> arrGrades) {
		Collections.sort(arrGrades);
		int mLocation = 0;
		float median = 0;
		if (arrGrades.size() % 2 != 0) {
			mLocation = arrGrades.size() / 2;
			median = arrGrades.get(mLocation);
		} else {
			mLocation = arrGrades.size() / 2;
			float median1 = arrGrades.get(mLocation);
			float median2 = arrGrades.get(mLocation - 1);
			median = ((median1 + median2) / 2);
		}
		return median;
	}

	public static float mode(ArrayList<Float> arrGrades) {
		Collections.sort(arrGrades);
		int count = 0;
		int most = 0;
		float currentMode;
		float mode = arrGrades.get(0);
		for (int i = 0; i < arrGrades.size(); i++) {
			currentMode = arrGrades.get(i);
			count = 0;
			for (int j = 0; j < arrGrades.size(); j++) {
				if (arrGrades.get(j) == currentMode) {
					count++;
				}
				if (count > most) {
					most++;
					mode = arrGrades.get(j);
				}
			}
		}
		return mode;
	}
}