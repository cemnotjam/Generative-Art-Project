package src;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class MainView extends BorderPane {

    private ImageView originalView;
    private ImageView glitchedView;
    private Image originalImage;
    private WritableImage glitchedImage;
    private GlitchEngine engine;
    private ImageProcessor processor;
    private Label statusLabel;
    private javafx.scene.control.Slider stripSlider;

    public MainView() {
        engine = new GlitchEngine();
        processor = new ImageProcessor();

        getStyleClass().add("root-pane");

        // --- left panel: buttons ---
        Button uploadBtn = new Button("Upload Photo");
        Button startBtn  = new Button("Glitch It");
        Button saveBtn   = new Button("Save Result");
        statusLabel      = new Label("Upload a photo to begin");

        Label stripLabel = new Label("Strip size: 40");
        stripLabel.getStyleClass().add("status-label");

        stripSlider = new javafx.scene.control.Slider(10, 100, 40);
        stripSlider.setShowTickMarks(true);
        stripSlider.setMajorTickUnit(30);
        stripSlider.setBlockIncrement(5);
        stripSlider.setPrefWidth(160);

        stripSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            stripLabel.setText("Strip size: " + newVal.intValue());
        });

        uploadBtn.getStyleClass().add("btn");
        startBtn.getStyleClass().add("btn-primary");
        saveBtn.getStyleClass().add("btn");
        statusLabel.getStyleClass().add("status-label");

        startBtn.setDisable(true);
        saveBtn.setDisable(true);

        VBox leftPanel = new VBox(20, uploadBtn, startBtn, saveBtn, statusLabel, stripLabel, stripSlider);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPadding(new Insets(40, 20, 40, 20));
        leftPanel.getStyleClass().add("left-panel");
        leftPanel.setPrefWidth(200);

        // --- image panels ---
        originalView = new ImageView();
        originalView.setPreserveRatio(true);
        originalView.setFitWidth(460);
        originalView.setFitHeight(600);

        glitchedView = new ImageView();
        glitchedView.setPreserveRatio(true);
        glitchedView.setFitWidth(460);
        glitchedView.setFitHeight(600);

        Label originalLabel = new Label("Original");
        Label glitchedLabel = new Label("Glitched");
        originalLabel.getStyleClass().add("panel-label");
        glitchedLabel.getStyleClass().add("panel-label");

        VBox originalPanel = new VBox(10, originalLabel, originalView);
        originalPanel.setAlignment(Pos.TOP_CENTER);
        originalPanel.getStyleClass().add("image-panel");
        originalPanel.setPadding(new Insets(20));

        VBox glitchedPanel = new VBox(10, glitchedLabel, glitchedView);
        glitchedPanel.setAlignment(Pos.TOP_CENTER);
        glitchedPanel.getStyleClass().add("image-panel");
        glitchedPanel.setPadding(new Insets(20));

        HBox centerPanel = new HBox(10, originalPanel, glitchedPanel);
        centerPanel.setAlignment(Pos.CENTER);
        centerPanel.setPadding(new Insets(20));

        setLeft(leftPanel);
        setCenter(centerPanel);

        // --- button actions ---
        uploadBtn.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choose a photo");
            chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png")
            );
            File file = chooser.showOpenDialog(getScene().getWindow());
            if (file != null) {
                originalImage = processor.loadImage(file.getAbsolutePath());
                originalView.setImage(originalImage);
                glitchedView.setImage(null);
                startBtn.setDisable(false);
                saveBtn.setDisable(true);
                statusLabel.setText("Ready — hit Glitch It");
            }
        });

        startBtn.setOnAction(e -> {
            if (originalImage != null) {
                statusLabel.setText("Glitching...");
                startBtn.setDisable(true);
        
                Thread thread = new Thread(() -> {
                    WritableImage result = engine.applyEffect(originalImage, (int) stripSlider.getValue());
        
                    javafx.application.Platform.runLater(() -> {
                        glitchedImage = result;
                        glitchedView.setImage(glitchedImage);
                        saveBtn.setDisable(false);
                        startBtn.setDisable(false);
                        statusLabel.setText("Done — hit Save to export");
                    });
                });
                thread.setDaemon(true);
                thread.start();
            }
        });

        saveBtn.setOnAction(e -> {
            if (glitchedImage != null) {
                String path = "output/glitch_" + System.currentTimeMillis() + ".png";
                processor.saveImage(glitchedImage, path);
                statusLabel.setText("Saved to output/");
            }
        });
    }
}