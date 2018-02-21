package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {

    Stage mainStage;

    static HashTable dictionary;
    String[] testDoc;
    ArrayList<Word> incorrectlySpelledWords = new ArrayList<>();
    String runInfo;

    int normalWidth = 500;
    int normalHeight = 300;

    Alert fileNotFoundAlert = new Alert(Alert.AlertType.ERROR);

    File iconImageFile = new File("Detective.png");
    Image iconImage = new Image(iconImageFile.toURI().toString());

    BorderPane mainMenuBorderPane = new BorderPane();
    GridPane mainMenuGridPane = new GridPane();
    StackPane mainMenuLabelStackPane = new StackPane();

    Button mainMenuFileButton = new Button("Check File...");
    Button mainMenuEnterTextButton = new Button("Enter Text...");
    Button mainMenuHelpButton = new Button("   Help...   ");
    TextField mainMenuDictionaryTextField = new TextField();
    TextField mainMenuFileTextField = new TextField();
    Label mainMenuTopLabel = new Label("Welcome to Spell Checker!");
    Label dictionaryLabel = new Label("Dictionary file:");
    Label fileLabel = new Label("Text File:");

    StackPane userTextStackPane = new StackPane();
    VBox userTextVBox = new VBox();

    TextField userTextTextField = new TextField();
    Label userTextLabel = new Label("Enter text to be corrected:");
    Button userTextConfirmButton = new Button("Confirm");

    ScrollPane helpScrollPane = new ScrollPane();
    VBox helpVBox = new VBox();

    Button helpMenuBackButton = new Button("Back...");
    Label helpText = new Label("Thank you for using the Spell Checker program.\n\n" +
            "To compare a file with a dictionary file, please specify the file names of " +
            "\nboth the dictionary and the file to be checked in the main menu in their " +
            "\nrespective fields. If you would like your file to be compared to the " +
            "\ndefault dictionary, simply leave the space blank and the program will " +
            "\nuse the default. If either of the files are not located within the same " +
            "\ndirectory as this program, please either place the file in the same " +
            "\ndirectory or specify the file's entire path so that it can be found.\n\n" +
            "If you would like to type text to be used in the spell checker, simply " +
            "\nclick the menu option titled \"Enter Text\" and it will be compared using " +
            "\nthe default dictionary. This will produce the same results as though you " +
            "\nhad used a file and the default dictionary." +
            "\n\nOn the final, output menu, you may choose to restart or quit the " +
            "\nprogram, as well as having your requested information displayed on " +
            "\nscreen. Clicking the restart button will simply bring you back to the " +
            "\nmain menu." +
            "\n\nIf there are any issues detected while using this program, please submit " +
            "\na detailed description of your problem to either of the authors of this " +
            "\ncode, whose email addresses can be found in the readme file in the " +
            "\nprogram directory. Thank you again for using our Spell Checker!");

    BorderPane finalSceneBorderPane = new BorderPane();
    ScrollPane finalSceneScrollPane = new ScrollPane();
    HBox finalSceneHBox = new HBox();

    Button finalSceneQuitButton = new Button("Quit");
    Button finalSceneRestartButton = new Button("Restart...");
    Label outputLabel = new Label();
    Scene finalScene = new Scene(finalSceneBorderPane,normalWidth,normalHeight);
    Scene helpScene = new Scene(helpScrollPane, normalWidth,normalHeight);
    Scene userTextScene = new Scene(userTextStackPane, normalWidth, normalHeight);
    Scene mainMenuScene = new Scene(mainMenuBorderPane, normalWidth,normalHeight);
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;

        mainMenuLabelStackPane.getChildren().add(mainMenuTopLabel);
        mainMenuBorderPane.setTop(mainMenuLabelStackPane);
        mainMenuBorderPane.setCenter(mainMenuGridPane);
        mainMenuBorderPane.setPadding(new Insets(20,10,10,10));
        mainMenuGridPane.add(dictionaryLabel,0,0);
        mainMenuGridPane.add(mainMenuDictionaryTextField,1,0);
        mainMenuGridPane.add(fileLabel,0,1);
        mainMenuGridPane.add(mainMenuFileTextField,1,1);
        mainMenuGridPane.add(mainMenuFileButton,1,2);
        mainMenuGridPane.add(mainMenuHelpButton,0,3);
        mainMenuGridPane.add(mainMenuEnterTextButton,1,3);
        mainMenuGridPane.setPadding(new Insets(20,20,20,20));
        mainMenuDictionaryTextField.setPromptText("Dictionary file name...");
        mainMenuDictionaryTextField.setText("SpellCheckDictionary.txt");
        mainMenuFileTextField.setPromptText("Text file name...");
        mainMenuGridPane.setVgap(10);
        mainMenuGridPane.setHgap(10);
        mainMenuGridPane.setAlignment(Pos.CENTER);

        userTextStackPane.getChildren().add(userTextVBox);
        userTextVBox.getChildren().addAll(userTextLabel,userTextTextField,userTextConfirmButton);
        userTextVBox.setSpacing(5);
        userTextTextField.setPromptText("Enter text for spell checking...");
        userTextVBox.setAlignment(Pos.CENTER);
        userTextTextField.setMaxWidth(300);

        helpVBox.getChildren().addAll(helpText,helpMenuBackButton);
        helpScrollPane.setContent(helpVBox);
        helpVBox.setSpacing(5);

        finalSceneBorderPane.setCenter(finalSceneScrollPane);
        finalSceneBorderPane.setBottom(finalSceneHBox);
        finalSceneScrollPane.setContent(outputLabel);
        finalSceneScrollPane.setPadding(new Insets(5,0,0,10));
        finalSceneHBox.getChildren().addAll(finalSceneRestartButton,finalSceneQuitButton);
        finalSceneHBox.setPadding(new Insets(10,10,10,10));
        finalSceneHBox.setAlignment(Pos.CENTER);
        finalSceneHBox.setSpacing(10);

        mainMenuEnterTextButton.setTooltip(new Tooltip("Goes to text entry menu"));
        mainMenuFileButton.setTooltip(new Tooltip("Checks the text file against the dictionary for misspellings"));
        mainMenuHelpButton.setTooltip(new Tooltip("Goes to the help menu"));
        helpMenuBackButton.setTooltip(new Tooltip("Goes back to the main menu"));
        userTextConfirmButton.setTooltip(new Tooltip("Confirms the entered text to be checked"));
        finalSceneRestartButton.setTooltip(new Tooltip("Restarts the program from the beginning"));
        finalSceneQuitButton.setTooltip(new Tooltip("Quits this program"));

        mainMenuFileButton.setDefaultButton(true);
        userTextConfirmButton.setDefaultButton(true);
        finalSceneRestartButton.setDefaultButton(true);
        finalSceneQuitButton.setCancelButton(true);
        helpMenuBackButton.setCancelButton(true);
        helpMenuBackButton.setDefaultButton(true);

        mainMenuEnterTextButton.setOnAction(e -> mainMenuButtons(e));
        mainMenuHelpButton.setOnAction(e -> mainMenuButtons(e));
        mainMenuFileButton.setOnAction(e -> mainMenuButtons(e));
        helpMenuBackButton.setOnAction(e -> mainMenuButtons(e));
        userTextConfirmButton.setOnAction(e -> userTextButtons(e));
        finalSceneRestartButton.setOnAction(e -> finalSceneButtons(e));
        finalSceneQuitButton.setOnAction(e -> finalSceneButtons(e));

        primaryStage.setTitle("Spell Checker");
        primaryStage.getIcons().add(iconImage);
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void mainMenuButtons(ActionEvent e){


        if(e.getSource() == mainMenuEnterTextButton){
            try {
                dictionary = readInDictionary(mainMenuDictionaryTextField.getText());
            } catch (FileNotFoundException ex) {
                fileNotFoundAlert.setHeaderText("Error: Dictionary file not found");
                fileNotFoundAlert.setContentText("The file " +
                        mainMenuDictionaryTextField.getText() + " could not be " +
                        "found in the given directory. Please change the directory of the file to match the program, " +
                        "or retype the file directory.");
                fileNotFoundAlert.showAndWait();
                }
            mainStage.setScene(userTextScene);
        }
        if(e.getSource() == mainMenuHelpButton){
            mainStage.setScene(helpScene);
        }
        if(e.getSource() == helpMenuBackButton){
            mainStage.setScene(mainMenuScene);
        }
        if(e.getSource() == mainMenuFileButton){
            try{
                dictionary = readInDictionary(mainMenuDictionaryTextField.getText());
            }catch (FileNotFoundException ex){
                fileNotFoundAlert.setHeaderText("Error: Dictionary file not found");
                fileNotFoundAlert.setContentText("The file " +
                        mainMenuDictionaryTextField.getText() + " could not be " +
                        "found in the given directory. Please change the directory of the file to match the program, " +
                        "or retype the file directory.");
                fileNotFoundAlert.showAndWait();
                }
            System.out.println(dictionary.collisions);
            try{
                testDoc = readInFile(mainMenuFileTextField.getText());
            }catch(FileNotFoundException ex){
                fileNotFoundAlert.setHeaderText("Error: User file not found");
                fileNotFoundAlert.setContentText("The file " + mainMenuFileTextField.getText() + " could not be " +
                        "found in the given directory. Please change the directory of the file to match the program, " +
                        "or retype the file directory.");
                fileNotFoundAlert.showAndWait();
            }
            spellCheck(testDoc);
            runInfo = "Collisions: " + dictionary.collisions +
                    "\n" + "Longest Chain: " + dictionary.longestChain +
                    "\n";
            if(incorrectlySpelledWords.size() == 0){
                outputLabel.setText(runInfo + "No incorrectly spelled words");
            }else {
                outputLabel.setText(runInfo + "Incorrectly Spelled words: " +
                        "\n----------------------------------------------------");
                for (int index = 0; index < incorrectlySpelledWords.size(); index++){
                    outputLabel.setText(outputLabel.getText() + "\n" +
                            "Incorrect Word: " + incorrectlySpelledWords.get(index).value +
                            "\nContext: " + incorrectlySpelledWords.get(index).getContext(testDoc) +
                            "\nSuggestions: \n" + incorrectlySpelledWords.get(index).getSuggestions() +
                            "\n----------------------------------------------------");
                }
            }
            mainStage.setScene(finalScene);
        }
    }
    public void userTextButtons(ActionEvent e) {
        if (e.getSource() == userTextConfirmButton) {
            testDoc = userTextTextField.getText().split(" ");
            spellCheck(testDoc);
            runInfo = "Collisions: " + dictionary.collisions +
                    "\n" + "Longest Chain: " + dictionary.longestChain +
            "\n";
            if (incorrectlySpelledWords.size() == 0) {
                outputLabel.setText(runInfo + "No incorrectly spelled words");
            } else {
                outputLabel.setText(runInfo);
                outputLabel.setText(outputLabel.getText() +
                        "Incorrectly Spelled words: "+
                        "\n----------------------------------------------------");
                for (int index = 0; index < incorrectlySpelledWords.size(); index++) {
                    outputLabel.setText(outputLabel.getText() + "\n" +
                            "Incorrect Word: " + incorrectlySpelledWords.get(index).value +
                            "\nContext: " + incorrectlySpelledWords.get(index).getContext(testDoc) +
                            "\nSuggestions: \n" + incorrectlySpelledWords.get(index).getSuggestions() +
                            "\n----------------------------------------------------");
                }
            }
            mainStage.setScene(finalScene);
        }
    }
    public void finalSceneButtons(ActionEvent e){
        if(e.getSource() == finalSceneQuitButton){
            System.exit(0);
        }
        if(e.getSource() == finalSceneRestartButton){
            incorrectlySpelledWords.clear();
            mainStage.setScene(mainMenuScene);
        }
    }
    public void spellCheck(String[] testDoc){
        for(int i = 0; i < testDoc.length; i++){
            if((!dictionary.contains(testDoc[i].toLowerCase()))){
                incorrectlySpelledWords.add(new Word(testDoc[i]));
                incorrectlySpelledWords.get(incorrectlySpelledWords.size() - 1).address = i;
            }
        }
    }
    public static String[] readInFile(String address) throws FileNotFoundException {
        File F;
        String line;
        String [] temp, total, result;
        F = new File(address);
        total = new String[0];
        System.out.println("file in");
        try {
            Scanner scanFile = new Scanner(F);
            while (scanFile.hasNextLine()) {
                line = scanFile.nextLine();
                temp = line.split(" ");
                result = new String[total.length + temp.length];
                System.arraycopy(total, 0, result, 0, total.length);
                System.arraycopy(temp, 0, result, total.length, temp.length);
                total = result;
            }
        }catch(FileNotFoundException ex){
            throw new FileNotFoundException("filenotfound");
        }
        return total;
    }
    public static HashTable readInDictionary(String address) throws FileNotFoundException {
        File F;
        String line;
        HashTable test;
        int fileSize;
        F = new File(address);

        System.out.println("file in");
        try {
            Scanner scanFile = new Scanner(F);
            //first line of dictionary is line count
            fileSize = scanFile.nextInt();
            scanFile.nextLine();

            fileSize = getHashSize(fileSize);
            test = new HashTable(fileSize);
            while (scanFile.hasNextLine()) {
                line = scanFile.nextLine();

                if(isAlphabetical(line)) {
                    test.insert(line.toLowerCase());
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException ("FileNOtFound");
        }
        return test;
    }
    private static boolean isAlphabetical(String line) {
        for(int i = 0; i < line.length(); i++){
            if(!(line.charAt(i) <= 90 && line.charAt(i) >= 65
                    || line.charAt(i) <= 122 && line.charAt(i) >= 97)){
                return false;
            }
        }
        return true;
    }
    public static int getHashSize(int data){
        int length;
        length = data*3;
        while(!isPrime(length)){
            length++;
        }
        return length;
    }
    public static boolean isPrime(int length){
        if(length%2 == 0)return false;
        else{
            for(int i = 3; i*i <= length; i+=2){
                if(length%i == 0) return false;
            }
            return true;
        }
    }
}