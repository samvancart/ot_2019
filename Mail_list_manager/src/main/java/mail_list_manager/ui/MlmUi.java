
package mail_list_manager.ui;

import java.io.File;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import mail_list_manager.domain.MailerGroup;
import mail_list_manager.services.GroupService;
import mail_list_manager.services.SubscriberService;
import org.controlsfx.control.NotificationPane;

public class MlmUi extends Application {

    private Scene scene;
    private GroupService gs = new GroupService();
    private SubscriberService ss = new SubscriberService();

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
        groups.addAll(gs.getParsedGroups());

        ComboBox groupsCb = new ComboBox(groups);
        mainGp.add(groupsCb, 4, 1);
        
        groupsCb.getSelectionModel().selectFirst();
        groupsCb.setCellFactory(new Callback<ListView<MailerGroup>, ListCell<MailerGroup>>() {
            @Override
            public ListCell<MailerGroup> call(ListView<MailerGroup> p) {
                final ListCell<MailerGroup> cell = new ListCell<MailerGroup>() {
                    @Override
                    protected void updateItem(MailerGroup group, boolean b) {
                        super.updateItem(group, b);
                        if (group != null) {
                            setText(group.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        Button addButton = new Button("Add to Mailerlite");
        addButton.setMinSize(120, 40);
        HBox buttonHb = new HBox(10);
        buttonHb.setAlignment(Pos.BOTTOM_LEFT);
        buttonHb.getChildren().add(addButton);
        mainGp.add(buttonHb, 1, 4);

        addButton.setOnAction(e -> {
            MailerGroup group = (MailerGroup) groupsCb.getValue();
            String name = nameField.getText();
            String email = emailField.getText();
            if (name.equals("") || email.equals("") || !email.contains("@")) {
                mainNp.setText("Please enter valid name and email");
                mainNp.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
                mainNp.show();
            } else {
                String response = ss.createSubscriber(group, name, email);
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

        Label apiKeyLabel = new Label("API Key:");
        settingsFormGp.add(apiKeyLabel, 0, 1);

        TextField apiKeyInput = new TextField();
        settingsFormGp.add(apiKeyInput, 1, 1);

        Label passwordLabel = new Label("Password:");
        settingsFormGp.add(passwordLabel, 0, 2);

        PasswordField passwordInput = new PasswordField();
        settingsFormGp.add(passwordInput, 1, 2);

        Button saveButton = new Button("Save to");
        settingsFormGp.add(saveButton, 0, 3);

        Button browseButton = new Button("...");
        settingsFormGp.add(browseButton, 1, 3);

        settingsGp.add(settingsFormGp, 0, 1);
        settingsFormGp.setVisible(false);

        Tab settingsTab = new Tab("Settings");

        settingsGp.setAlignment(Pos.TOP_LEFT);
        settingsGp.setHgap(10);
        settingsGp.setVgap(10);
        settingsGp.setPadding(new Insets(25, 25, 25, 25));

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
        FileChooser fc = new FileChooser();
        browseButton.setOnAction(e -> {
            File file = fc.showOpenDialog(primaryStage);
            if (file != null) {
                browseButton.setText(file.getAbsolutePath());
                
            }
        });

        settingsTab.setContent(settingsNp);

        menuTabs.getTabs().addAll(mainTab, settingsTab);
        menuTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        scene = new Scene(menuTabs, 500, 250);

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
