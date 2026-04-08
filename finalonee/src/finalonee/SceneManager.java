package finalonee;


import finalonee.UIComponents;
import finalonee.CourseDAO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneManager {
    private Stage primaryStage;
    private Scene loginScene;
    private UserDAO userDAO;
    private CourseDAO courseDAO;
    private SessionManager sessionManager;
    
    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.userDAO = new UserDAO();
        this.courseDAO = new CourseDAO();
        this.sessionManager = SessionManager.getInstance();
    }
    
    public void showLoginScene() {
        VBox box = new VBox(20);
        box.setPadding(new Insets(30));
        box.setAlignment(Pos.CENTER);
        box.setStyle(UIComponents.getFormLayoutStyle());
        
        Label title = UIComponents.createTitleLabel("Welcome to SMS");
        
        TextField username = new TextField();
        username.setPromptText("Username");
        
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        
        Button login = UIComponents.createStyledButton("Login");
        Button signup = UIComponents.createStyledButton("Sign Up");
        
        login.setOnAction(e -> handleLogin(username.getText(), password.getText()));
        signup.setOnAction(e -> showSignupScene());
        
        box.getChildren().addAll(title, username, password, login, signup);
        loginScene = new Scene(box, 420, 340);
        primaryStage.setScene(loginScene);
    }
    
    public void showSignupScene() {
        VBox box = new VBox(20);
        box.setPadding(new Insets(30));
        box.setAlignment(Pos.CENTER);
        box.setStyle(UIComponents.getFormLayoutStyle());
        
        Label title = UIComponents.createTitleLabel("Create Account");
        
        TextField username = new TextField();
        username.setPromptText("Username");
        
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        
        Button create = UIComponents.createStyledButton("Create Account");
        Button back = UIComponents.createStyledButton("Back");
        
        create.setOnAction(e -> handleSignup(username.getText(), password.getText()));
        back.setOnAction(e -> primaryStage.setScene(loginScene));
        
        box.getChildren().addAll(title, username, password, create, back);
        Scene signupScene = new Scene(box, 420, 340);
        primaryStage.setScene(signupScene);
    }
    
    public void showHomeScene() {
        VBox box = new VBox(20);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);
        box.setStyle(UIComponents.getMainLayoutStyle());
        
        String welcomeText = "Welcome, " + 
            (sessionManager.getCurrentUsername() != null ? sessionManager.getCurrentUsername() : "User");
        Label welcome = UIComponents.createWelcomeLabel(welcomeText);
        
        HBox navbar = createNavigationBar();
        
        Button logout = UIComponents.createStyledButton("Logout");
        logout.setOnAction(e -> handleLogout());
        
        box.getChildren().addAll(navbar, welcome, logout);
        Scene scene = new Scene(box, 640, 400);
        primaryStage.setScene(scene);
    }
    
    public void showEnrolledCoursesScene() {
        VBox box = new VBox(15);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);
        box.setStyle(UIComponents.getMainLayoutStyle());
        
        Label title = UIComponents.createSectionLabel("My Enrolled Courses");
        
        TableView<Course> table = createCourseTable();
        ObservableList<Course> enrolledCourses = courseDAO.getEnrolledCourses(sessionManager.getCurrentUserId());
        table.setItems(enrolledCourses);
        
        Button back = UIComponents.createStyledButton("Back");
        back.setOnAction(e -> showHomeScene());
        
        box.getChildren().addAll(createNavigationBar(), title, table, back);
        Scene scene = new Scene(box, 640, 400);
        primaryStage.setScene(scene);
    }
    
    public void showAvailableCoursesScene() {
        VBox box = new VBox(15);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);
        box.setStyle(UIComponents.getMainLayoutStyle());
        
        Label title = UIComponents.createSectionLabel("Available Courses");
        
        TableView<Course> table = createCourseTable();
        ObservableList<Course> availableCourses = courseDAO.getAvailableCourses(sessionManager.getCurrentUserId());
        table.setItems(availableCourses);
        
        Button enrollBtn = UIComponents.createStyledButton("Enroll");
        enrollBtn.setOnAction(e -> handleEnrollment(table));
        
        Button back = UIComponents.createStyledButton("Back");
        back.setOnAction(e -> showHomeScene());
        
        box.getChildren().addAll(createNavigationBar(), title, table, enrollBtn, back);
        Scene scene = new Scene(box, 640, 400);
        primaryStage.setScene(scene);
    }
    
    private HBox createNavigationBar() {
        HBox navbar = UIComponents.createNavbar();
        
        Button homeBtn = UIComponents.createStyledButton("Home");
        Button coursesBtn = UIComponents.createStyledButton("Courses ▾");
        
        homeBtn.setOnAction(e -> showHomeScene());
        setupCoursesMenu(coursesBtn);
        
        navbar.getChildren().addAll(homeBtn, coursesBtn);
        return navbar;
    }
    
    private TableView<Course> createCourseTable() {
        TableView<Course> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle(UIComponents.getTableStyle());
        
        TableColumn<Course, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        TableColumn<Course, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Course, Integer> creditsCol = new TableColumn<>("Credits");
        creditsCol.setCellValueFactory(new PropertyValueFactory<>("credits"));
        
        table.getColumns().addAll(codeCol, nameCol, creditsCol);
        return table;
    }
    
    private void setupCoursesMenu(Button coursesBtn) {
        ContextMenu coursesMenu = new ContextMenu();
        
        MenuItem enrolled = new MenuItem("Enrolled Courses");
        MenuItem available = new MenuItem("Available Courses");
        
        enrolled.setOnAction(e -> showEnrolledCoursesScene());
        available.setOnAction(e -> showAvailableCoursesScene());
        
        coursesMenu.getItems().addAll(enrolled, available);
        coursesBtn.setOnMouseClicked(e -> coursesMenu.show(coursesBtn, e.getScreenX(), e.getScreenY()));
    }
    
    private void handleLogin(String username, String password) {
        User user = userDAO.authenticate(username, password);
        if (user != null) {
            sessionManager.login(user);
            System.out.println("Welcome!" + 
                "Login successful! Welcome back, " + user.getUsername() + ".");
            showHomeScene();
        } else {
            System.out.println("Login Failed" +
                "Invalid username or password. Please try again.");
        }
    }
    
    private void handleSignup(String username, String password) {
        if (userDAO.registerUser(username, password)) {
            primaryStage.setScene(loginScene);
        }
    }
    
    private void handleLogout() {
        sessionManager.logout();
        primaryStage.setScene(loginScene);
        System.out.println("Logged Out" + "You have been successfully logged out.");
    }
    
    private void handleEnrollment(TableView<Course> table) {
        Course selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (courseDAO.enrollInCourse(sessionManager.getCurrentUserId(), selected.getCode())) {
                showEnrolledCoursesScene();
            }
        } else {
            System.out.println("No Selection"+ "Please select a course to enroll in.");
        }
    }
}