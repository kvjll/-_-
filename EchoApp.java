import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class EchoApp extends Application {
    private TableView<Student> table;
    private ObservableList<Student> data;
    private TextField nameField, secondNameField, groupField;

    @Override
    public void start(Stage stage) throws Exception {


        TableView<Student> table = new TableView<>();

        Label nameLabel = new Label("Введиие имя студента");
        TextField nameField = new TextField();
        Label secondNameLabel = new Label("Введиие фамилию студента");
        TextField secondNameField = new TextField();
        Label groupLabel = new Label("Введиие группу студента");
        TextField groupField = new TextField();
        Label zd1Label = new Label("Оценка за первое задание");
        TextField zd1Field = new TextField();
        Label zd2Label = new Label("Оценка за второе задание");
        TextField zd2Field = new TextField();

        TableColumn<Student, String> nameCol = new TableColumn<>("Имя");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> secondNameCol = new TableColumn<>("Фамилия");
        secondNameCol.setCellValueFactory(new PropertyValueFactory<>("secondName"));

        TableColumn<Student, String> groupCol = new TableColumn<>("Группа");
        groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));

        TableColumn<Student, Integer> zd1Col = new TableColumn<>("Задание 1");
        zd1Col.setCellValueFactory(new PropertyValueFactory<>("zd1"));
        zd1Col.setOnEditCommit(e ->{
            Student student = e.getRowValue();
            student.setZd1(e.getNewValue());
            table.refresh();
        });

        TableColumn<Student, Integer> zd2Col = new TableColumn<>("Задание 2");
        zd2Col.setCellValueFactory(new PropertyValueFactory<>("zd2"));
        zd2Col.setOnEditCommit(e ->{
            Student student = e.getRowValue();
            student.setZd2(e.getNewValue());
            table.refresh();
        });

        table.setEditable(true);

        TableColumn<Student, Integer> avgMarks = new TableColumn<>("Средний бал");
        avgMarks.setCellValueFactory(new PropertyValueFactory<>("avgMarks"));

        table.getColumns().addAll(nameCol, secondNameCol, groupCol, zd1Col, zd2Col, avgMarks);


        ObservableList<Student> students = FXCollections.observableArrayList();
        table.setItems(students);

        Button addButton = new Button("Добавить");
        addButton.setOnAction(e -> {
            try {
                if (nameField.getText().isEmpty() || secondNameField.getText().isEmpty() ||
                        groupField.getText().isEmpty() || zd1Field.getText().isEmpty() ||
                        zd2Field.getText().isEmpty()) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Предупреждение");
                    alert.setHeaderText(null);
                    alert.setContentText("Пожалуйста, заполните все поля!");
                    alert.showAndWait();
                    return;
                }

                Student newStudent = new Student(
                        nameField.getText(),
                        secondNameField.getText(),
                        groupField.getText(),
                        Integer.parseInt(zd1Field.getText()),
                        Integer.parseInt(zd2Field.getText())
                );
                students.add(newStudent);

                nameField.clear();
                secondNameField.clear();
                groupField.clear();
                zd1Field.clear();
                zd2Field.clear();

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка ввода");
                alert.setHeaderText(null);
                alert.setContentText("Пожалуйста, введите корректные числовые значения для оценок!");
                alert.showAndWait();
            }
        });

        Button deleteButton = new Button("Удалить студента");
        deleteButton.setOnAction(e -> {
            Student selectedStudent = table.getSelectionModel().getSelectedItem();
            {
                students.remove(selectedStudent);
            }
        });

        Button editButton = new Button("Редактировать студента");
        editButton.setOnAction(e -> {
            Student selectedStudent = table.getSelectionModel().getSelectedItem();

            {
                nameField.setText(selectedStudent.getName());
                secondNameField.setText(selectedStudent.getSecondName());
                groupField.setText(selectedStudent.getGroup());
                zd1Field.setText(String.valueOf(selectedStudent.getZd1()));
                zd2Field.setText(String.valueOf(selectedStudent.getZd2()));

                students.remove(selectedStudent);
            }
        });


        VBox root = new VBox(addButton,deleteButton,editButton, nameLabel, nameField,secondNameLabel,
                secondNameField,groupLabel, groupField,
                zd1Label, zd1Field, zd2Label, zd2Field, table);
        root.setStyle("-fx-padding: 15; -fx-background-color: #f8f8f8;");


        Scene scene = new Scene(root, 700, 600);

        stage.setTitle("Менеджер студентов");
        stage.setScene(scene);
        stage.show();

    }



    public static void main(String[] args) {
        launch();
    }}



