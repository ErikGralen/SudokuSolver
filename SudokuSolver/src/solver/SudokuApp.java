package solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class SudokuApp extends Application {
	TilePane tile;
	HashMap<Integer, OneLetterTextField> inputFields;
	BorderPane bp;
	Scene sc;
	HBox tb;
	SudokuBoard board;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		board = new SudokuBoard();
		inputFields = new<Integer, OneLetterTextField> HashMap();
		setup(stage);
	}

	public void setup(Stage stage) {
		
		// Stage
		stage.setTitle("Sudoku Solver");
		bp = new BorderPane();
		bp.setStyle("-fx-background: #D5DED9");

		// TilePane board (center)
		tile = new TilePane();
		tile.setPrefColumns(9);
		tile.setPrefRows(9);
		tile.setVgap(15);
		tile.setHgap(15);
		tile.setMaxHeight(610);
		tile.setMaxWidth(610);
		tile.setAlignment(Pos.CENTER);
		for (int i = 0; i < 81; i++) {
			OneLetterTextField temp = new OneLetterTextField();
			temp.setPrefSize(50, 50);
			temp.setAlignment(Pos.CENTER);
			temp.setEditable(true);
			if ((((i / 9 < 3 || i / 9 > 5) && i / 3 % 3 != 1)) || (((i / 9 > 2 && i / 9 < 6) && i / 3 % 3 == 1))) {
				temp.setStyle(
						"-fx-effect: dropshadow(two-pass-box , darkgray, 10, 0.0 , 4, 5);-fx-font:bold 20px Verdana;-fx-focus-color:red;-fx-background-color:#948C75;-fx-text-fill:#2E221F;");
			} else {
				temp.setStyle(
						"-fx-effect: dropshadow(two-pass-box , darkgray, 10, 0.0 , 4, 5);-fx-font:bold 20px Verdana;-fx-focus-color:red;-fx-background-color:#D9CEB2   ;-fx-text-fill:#2E221F;");
			}

			// Key Pressed
			temp.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (temp.isFilled() && event.getText().matches("[0-9]")) {
						temp.clear();
					}
					temp.replaceText(1, 9, event.getCharacter());
				}
			});
			// Adds to tile and inputFields
			inputFields.put(i, temp);
			tile.getChildren().add(temp);
		}

		// Bottom Buttons
		tb = new HBox();
		//Quit Button
		Button buttonQuit = new Button("Quit");
		buttonQuit.setOnAction(e -> quit());
		//Clear Button
		Button buttonClear = new Button("Clear");
		buttonClear.setOnAction(e -> clear());
		//Solve Button
		Button buttonSolve = new Button("Solve");
		buttonSolve.setOnAction(e -> solve());
		tb.getChildren().addAll(buttonSolve, buttonClear, buttonQuit);
		tb.setAlignment(Pos.BOTTOM_CENTER);
		tb.setPadding(new Insets(10));

		// Add all to BorderPane
		bp.setCenter(tile);
		bp.setBottom(tb);
		// Final
		sc = new Scene(bp, 300, 300);
		stage.setScene(sc);
		stage.setMinHeight(720);
		stage.setMinWidth(690);
		stage.show();
	}

	private void quit() {
		Platform.exit();
	}

	private void clear() {
		for (int i = 0; i < 81; i++) {
			inputFields.get(i).clear();
		}
	}

	private void solve() {
		// Converts TilePane to int[][]
		int[][] matrix = new int[9][9];
		int i = 0;
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				OneLetterTextField t = inputFields.get(i);
				String s = t.getText();
				int input = (s.isEmpty()) ? 0 : Integer.parseInt(s);
				matrix[y][x] = input;
				i++;
			}
		}
		// Checks if legal import
		if (!board.importBoard(matrix)) {
			alertMessage("Board is not valid!");
		// Checks if solvable
		} else if (board.solve()) {
			i = 0;
			matrix = board.exportBoard();
			for (int y = 0; y < 9; y++) {
				for (int x = 0; x < 9; x++) {
					inputFields.get(i).setText(Integer.toString(matrix[y][x]));
					i++;
				}
			}
		} else {
			alertMessage("Not solvable");
		}
	}

	/**
	 * Popup message with main text s
	 * 
	 * @param s
	 *            - main text / error message
	 */
	private void alertMessage(String s) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Sudoku Board");
		alert.setHeaderText(null);
		alert.setContentText(s);
		alert.showAndWait();
	}
}
