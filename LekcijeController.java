package org.example.kineskagramatika.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.kineskagramatika.domain.Lekcija;
import org.example.kineskagramatika.services.UcenjeService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LekcijeController implements Initializable {

    @FXML private ListView<Lekcija> listViewLekcije;
    @FXML private TextArea taSadrzaj;
    @FXML private Label lblNaslov;
    @FXML private Label lblOpis;
    @FXML private ProgressBar progressTezina;
    @FXML private Button btnZavrsi;
    
    private UcenjeService ucenjeService;
    private Lekcija trenutnaLekcija;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ucenjeService = new UcenjeService();
        setupListView();
        setupListeners();
        ucitajLekcije();
    }

    private void setupListView() {
        listViewLekcije.setCellFactory(param -> new ListCell<Lekcija>() {
            @Override
            protected void updateItem(Lekcija lekcija, boolean empty) {
                super.updateItem(lekcija, empty);
                
                if (empty || lekcija == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(lekcija.getNaslov());
                    
                    // Dodaj ikonu statusa
                    if (ucenjeService.jeLekcijaZavrsena(lekcija.getId())) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                        setText("✅ " + lekcija.getNaslov());
                    } else {
                        setStyle("-fx-text-fill: black;");
                        
                        // Ikona težine
                        String ikona = switch (lekcija.getTezina()) {
                            case 1 -> "🥢 ";
                            case 2 -> "📝 ";
                            case 3 -> "🎯 ";
                            case 4 -> "🔥 ";
                            case 5 -> "🚀 ";
                            default -> "📚 ";
                        };
                        setText(ikona + lekcija.getNaslov());
                    }
                }
            }
        });
    }

    private void setupListeners() {
        listViewLekcije.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    prikaziLekciju(newValue);
                }
            });

        btnZavrsi.setOnAction(e -> zavrsiLekciju());
    }

    private void ucitajLekcije() {
        List<Lekcija> lekcije = ucenjeService.getSveLekcije();
        listViewLekcije.getItems().setAll(lekcije);
        
        if (!lekcije.isEmpty()) {
            listViewLekcije.getSelectionModel().selectFirst();
        }
    }

    private void prikaziLekciju(Lekcija lekcija) {
        trenutnaLekcija = lekcija;
        lblNaslov.setText(lekcija.getNaslov());
        lblOpis.setText(lekcija.getOpis());
        
        // Postavi progres težine
        progressTezina.setProgress(lekcija.getTezina() / 5.0);
        
        // Prikaži sadržaj
        StringBuilder sadrzaj = new StringBuilder();
        for (String linija : lekcija.getSadrzaj()) {
            sadrzaj.append("• ").append(linija).append("\n\n");
        }
        taSadrzaj.setText(sadrzaj.toString());
        
        // Ažuriraj stanje dugmeta
        btnZavrsi.setDisable(ucenjeService.jeLekcijaZavrsena(lekcija.getId()));
        if (btnZavrsi.isDisable()) {
            btnZavrsi.setText("✅ Završeno");
        } else {
            btnZavrsi.setText("🎯 Završi lekciju");
        }
    }

    private void zavrsiLekciju() {
        if (trenutnaLekcija != null) {
            ucenjeService.oznaciLekcijuKaoZavrsenu(trenutnaLekcija.getId());
            
            // Ažuriraj prikaz
            listViewLekcije.refresh();
            btnZavrsi.setDisable(true);
            btnZavrsi.setText("✅ Završeno");
            
            // Prikaži congratulation poruku
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Čestitamo!");
            alert.setHeaderText("Lekcija završena");
            alert.setContentText("Uspješno ste završili lekciju: " + trenutnaLekcija.getNaslov());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));
            Stage stage = (Stage) listViewLekcije.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePractice() {
        if (trenutnaLekcija != null) {
            // Otvori kviz specifičan za ovu lekciju
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vježba");
            alert.setHeaderText("Vježba za lekciju");
            alert.setContentText("Pokreće se vježba za: " + trenutnaLekcija.getNaslov());
            alert.showAndWait();
        }
    }
}
