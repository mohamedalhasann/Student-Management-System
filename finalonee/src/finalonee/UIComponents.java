package finalonee;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class UIComponents {
    
    public static Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; "
                   + "-fx-font-family: 'Segoe UI'; -fx-background-radius: 12; -fx-padding: 8 18;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; "
                                              + "-fx-font-family: 'Segoe UI'; -fx-background-radius: 12; -fx-padding: 8 18;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; "
                                             + "-fx-font-family: 'Segoe UI'; -fx-background-radius: 12; -fx-padding: 8 18;"));
        return btn;
    }
    
    public static Label createTitleLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 22px; -fx-font-family: 'Segoe UI';");
        return label;
    }
    
    public static Label createSectionLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20px; -fx-font-family: 'Segoe UI';");
        return label;
    }
    
    public static Label createWelcomeLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #2c3e50;");
        return label;
    }
    
    public static HBox createNavbar() {
        HBox navbar = new HBox(20);
        navbar.setPadding(new Insets(10));
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setStyle("-fx-background-color: linear-gradient(to right, #0F2027, #203A43, #2C5364);"
                      + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);");
        
        Label logo = new Label("SMS");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'Segoe UI'; -fx-font-weight: bold;");
        
        navbar.getChildren().add(logo);
        return navbar;
    }
    
    public static String getMainLayoutStyle() {
        return "-fx-background-color: #f4f4f4;";
    }
    
    public static String getFormLayoutStyle() {
        return "-fx-background-color: #f2f2f2;";
    }
    
    public static String getTableStyle() {
        return "-fx-background-color: white; -fx-border-radius: 8; -fx-background-radius: 8;"
             + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 6, 0, 0, 2);";
    }
}