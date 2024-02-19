package lk.mail.Controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import lk.mail.Allert.allert;
import lk.mail.Mail.Mail;

import java.io.File;
import java.io.FileNotFoundException;

public class MailFormController {
    public ImageView root;
    public JFXTextField textTo;
    public JFXTextField textSub;
    public JFXTextArea textMsg;
    public JFXTextField textFile;

    public void attachOnAction(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        textFile.setText(fileChooser.showOpenDialog(null).getAbsolutePath());

    }

    public void sendOnAction(ActionEvent actionEvent) {

        if (!textTo.getText().isEmpty()) {
            if (textTo.getText().matches("(^[a-zA-Z0-9_.-]+)@([a-zA-Z]+)([\\.])([a-zA-Z]+)$")) {
                sendMail(textTo.getText(), textSub.getText(), textMsg.getText(), textFile.getText());
            } else {
                try {
                    new allert(Alert.AlertType.INFORMATION, "Error", "Enter valid email address", ButtonType.OK).show();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            try {
                new allert(Alert.AlertType.INFORMATION, "Error", "Enter email address", ButtonType.OK).show();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMail(String to, String subj, String msg, String path) {
        String email = to;
        String message = msg;
        String subject = subj;
        File file = new File(path);
        Mail mail = null;

        if (path == null) {
            mail = new Mail(email, message, subject);
        } else {
            mail = new Mail(email, subject, message, file);
        }

        Thread thread = new Thread(mail);

        mail.valueProperty().addListener((a, oldValue, newValue) -> {
            if (newValue) {
                try {
                    new allert(Alert.AlertType.INFORMATION, "Email", "Mail sent successfully", ButtonType.OK).show();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                clear();
                System.out.println("sent successfully");
            } else {
                try {
                    new allert(Alert.AlertType.NONE, "Connection Error", "Connection Error!", ButtonType.OK).show();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    public void clear() {
        textTo.clear();
        textSub.clear();
        textMsg.clear();
        textFile.clear();
    }
    }

