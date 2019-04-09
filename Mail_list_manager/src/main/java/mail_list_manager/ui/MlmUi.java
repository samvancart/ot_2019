package mail_list_manager.ui;

import java.io.FileInputStream;
import java.util.Properties;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mail_list_manager.dao.FileApiKeyDao;
import mail_list_manager.domain.ApiKeyService;
import mail_list_manager.domain.MailerGroup;
import mail_list_manager.services.Groups;
import mail_list_manager.services.Subscribers;
import org.controlsfx.control.NotificationPane;

public class MlmUi extends Application {

    private Scene scene;
    private Groups g = new Groups();
    private Subscribers s = new Subscribers();
    private boolean keyExists = false;
    private ApiKeyService keyService;
    private boolean isRestarted = false;

    @Override
    public void init() throws Exception {
        Properties properties = new Properties();


        properties.load(new FileInputStream("config.properties"));

        String keyFile = properties.getProperty("keyFile");
        FileApiKeyDao dao = new FileApiKeyDao(keyFile);
        keyExists = dao.getKeyExists();
        keyService = new ApiKeyService(dao);
    }

    public void restart(Stage stage) throws Exception {
        init();
        stage.close();
        g = new Groups();
        isRestarted = true;
        start(stage);
        stage.show();

    }

    @Override
    @SuppressWarnings("Convert2Lambda")
    public void start(Stage primaryStage) {

        // main tab
        TabPane menuTabs = new TabPane();

        GridPane mainGp = new GridPane();
        NotificationPane mainNp = new NotificationPane(mainGp);
        Tab mainTab = new Tab("Main");
        mainNp.setShowFromTop(false);
        mainNp.hide();

        mainGp.setAlignment(Pos.TOP_LEFT);
        mainGp.setHgap(10);
        mainGp.setVgap(10);
        mainGp.setPadding(new Insets(25, 25, 25, 25));

        EventHandler<WindowEvent> startAppHandler
                = new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (!keyExists) {
                    mainNp.setText("No API key specified. Go to settings tab to specify key.");
                    mainNp.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
                    mainNp.show();
                }
            }
        };

        Label nameLabel = new Label("Name:");
        mainGp.add(nameLabel, 0, 1);

        TextField nameField = new TextField();
        mainGp.add(nameField, 1, 1);

        Label emailLabel = new Label("Email:");
        mainGp.add(emailLabel, 0, 2);

        TextField emailField = new TextField();
        mainGp.add(emailField, 1, 2);

        Label groupsLabel = new Label("Group:");
        mainGp.add(groupsLabel, 3, 1);

        ObservableList<MailerGroup> groups = FXCollections.observableArrayList();
        groups.addAll(g.getParsedGroups());

        ComboBox groupsCb = new ComboBox(groups);
        mainGp.add(groupsCb, 4, 1);
        groupsCb.getSelectionModel().selectFirst();

        Button addButton = new Button("Add to Mailerlite");
        addButton.setMinSize(120, 40);
        HBox buttonHb = new HBox(10);
        buttonHb.setAlignment(Pos.BOTTOM_LEFT);
        buttonHb.getChildren().add(addButton);
        mainGp.add(buttonHb, 1, 4);

        addButton.setOnAction(e -> {
            s = new Subscribers();
            MailerGroup group = (MailerGroup) groupsCb.getValue();
            String name = nameField.getText();
            String email = emailField.getText();
            if (name.equals("") || email.equals("") || !email.contains("@")) {
                mainNp.setText("Please enter valid name and email");
                mainNp.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
                mainNp.show();
            } else {
                String response = s.createSubscriber(group, name, email);
                if (response != null) {
                    emailField.setText("");
                    nameField.setText("");
                    mainNp.setText("Added " + name + " (" + email + ") to " + group.getName() + " succesfully!");
                } else {
                    mainNp.setText("Error! Failed to send.");
                }
                mainNp.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
                mainNp.show();
            }
        });

        mainTab.setContent(mainNp);

        // settings tab
        Group root = new Group();
        GridPane settingsGp = new GridPane();
        root.getChildren().addAll(settingsGp);

        NotificationPane settingsNp = new NotificationPane(root);
        settingsNp.setShowFromTop(false);
        settingsNp.hide();

        GridPane settingsFormGp = new GridPane();

        Label keyLabel = new Label("API Key:");
        settingsFormGp.add(keyLabel, 0, 1);

        TextField keyField = new TextField(keyService.getKey());
        if (keyExists) {
            keyField.setEditable(false);
        }
        keyField.setPrefWidth(230);
        settingsFormGp.add(keyField, 1, 1);

        Button editButton = new Button("Edit");
        settingsFormGp.add(editButton, 2, 1);

        editButton.setOnAction(e -> {
            keyField.setEditable(true);
            keyField.requestFocus();
            keyField.selectAll();
        });

        EventHandler<MouseEvent> keyFieldHandler
                = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (keyExists) {
                    keyField.setEditable(false);
                }
            }
        };

        EventHandler<Event> menuTabChange
                = new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (!keyExists && isRestarted) {
                    mainNp.setText("No API key specified. Go to settings tab to specify key.");
                    mainNp.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
                    mainNp.show();
                }
            }
        };

        Button saveButton = new Button("Save");
        settingsFormGp.add(saveButton, 0, 3);

        settingsGp.add(settingsFormGp, 0, 1);
        settingsFormGp.setVisible(false);

        Tab settingsTab = new Tab("Settings");

        settingsGp.setAlignment(Pos.TOP_LEFT);
        settingsGp.setHgap(10);
        settingsGp.setVgap(10);
        settingsGp.setPadding(new Insets(10, 10, 10, 10));

        settingsFormGp.setAlignment(Pos.TOP_LEFT);
        settingsFormGp.setHgap(10);
        settingsFormGp.setVgap(10);
        settingsFormGp.setPadding(new Insets(25, 25, 25, 25));

        Label settingsLabel = new Label("Configure Api key and password:");

        Button configButton = new Button();
        configButton.setText("Configure");

        settingsGp.add(settingsLabel, 0, 0);
        settingsGp.add(configButton, 1, 0);

        configButton.setOnAction(e -> {
            if (configButton.getText().equals("Configure")) {
                configButton.setText("Hide");
                settingsFormGp.setVisible(true);
            } else {
                configButton.setText("Configure");
                settingsFormGp.setVisible(false);
            }
        });

        saveButton.setOnAction(e -> {
            String key = keyField.getText();
            if (key == null || key.equals("")) {
                key = "";
                settingsNp.setText("Warning: No key provided");
                settingsNp.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
                settingsNp.show();
                keyExists = false;
            } else {
                keyField.setEditable(false);
                keyExists = true;
            }
            try {
                keyService.createKey(key);
                restart(primaryStage);
            } catch (Exception ex) {

            }
        });

        settingsTab.setContent(settingsNp);

        menuTabs.getTabs().addAll(mainTab, settingsTab);
        menuTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        scene = new Scene(menuTabs, 500, 250);

        primaryStage.setOnShown(startAppHandler);
        settingsFormGp.addEventHandler(MouseEvent.MOUSE_CLICKED, keyFieldHandler);

        menuTabs.prefHeightProperty().bind(scene.heightProperty());
        menuTabs.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.setTitle("Mail List Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
