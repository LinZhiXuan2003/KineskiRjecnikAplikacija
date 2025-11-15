package org.example.kineskagramatika.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.kineskagramatika.services.UcenjeService;
import org.example.kineskagramatika.services.KvizService;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    @FXML private ProgressBar progressBar;
    @FXML private Label lblProgress;
    @FXML private VBox mainContainer;
    
    private UcenjeService ucenjeService;
    private KvizService kvizService;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ucenjeService = new UcenjeService();
        kvizService = new KvizService();
        updateProgress();
    }
    
    @FXML
    private void handleLekcije() {
        showAlert("Lekcije", "Otvara se modul za lekcije...");
        // Otvori prozor s lekcijama
    }
    
    @FXML
    private void handleRjecnik() {
        showAlert("Rječnik", "Otvara se rječnik...");
        // Otvori prozor s rječnikom
    }
    
    @FXML
    private void handleKviz() {
        showAlert("Kviz", "Pokreće se kviz...");
        // Otvori prozor s kvizom
    }
    
    @FXML
    private void handleStatistika() {
        double postotak = ucenjeService.getPostotakNapretka();
        showAlert("Statistika", 
            String.format("Završeno lekcija: %d/%d (%.0f%%)",
                ucenjeService.getBrojZavrsenihLekcija(),
                ucenjeService.getUkupnoLekcija(),
                postotak));
    }
    
    private void updateProgress() {
        double postotak = ucenjeService.getPostotakNapretka();
        progressBar.setProgress(postotak / 100.0);
        lblProgress.setText(String.format("Napredak: %.0f%%", postotak));
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
