package view;

import controller.ControleFinanceiro;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Despesa;
import model.Lancamento;
import model.Receita;

import java.time.LocalDate;

public class Main extends Application {

    private ControleFinanceiro controle = new ControleFinanceiro();
    private ListView<String> listaView = new ListView<>();
    private Label saldoLabel = new Label();

    @Override
    public void start(Stage stage) {
        TextField descricaoField = new TextField();
        descricaoField.setPromptText("Descrição");

        TextField valorField = new TextField();
        valorField.setPromptText("Valor");

        DatePicker dataPicker = new DatePicker(LocalDate.now());

        TextField categoriaField = new TextField();
        categoriaField.setPromptText("Categoria (para despesa)");

        Button btnReceita = new Button("Adicionar Receita");
        btnReceita.setOnAction(e -> {
            Receita r = new Receita(descricaoField.getText(),
                    Double.parseDouble(valorField.getText()),
                    dataPicker.getValue());
            controle.adicionarLancamento(r);
            atualizarLista();
        });

        Button btnDespesa = new Button("Adicionar Despesa");
        btnDespesa.setOnAction(e -> {
            Despesa d = new Despesa(descricaoField.getText(),
                    Double.parseDouble(valorField.getText()),
                    dataPicker.getValue(),
                    categoriaField.getText());
            controle.adicionarLancamento(d);
            atualizarLista();
        });

        Button btnSalvar = new Button("Salvar Dados");
        btnSalvar.setOnAction(e -> controle.salvarDados());

        VBox inputBox = new VBox(5, descricaoField, valorField, dataPicker, categoriaField,
                btnReceita, btnDespesa, btnSalvar, saldoLabel);
        inputBox.setPrefWidth(200);

        HBox root = new HBox(10, inputBox, listaView);
        atualizarLista();

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Controle Financeiro Simples");
        stage.show();
    }

    private void atualizarLista() {
        listaView.getItems().clear();
        for (Lancamento l : controle.listarLancamentos()) {
            String texto = String.format("[%s] %s - R$ %.2f (%s)",
                    l.getTipo(), l.getDescricao(), l.getValor(), l.getData());
            if (l instanceof Despesa d) {
                texto += " - " + d.getCategoria();
            }
            listaView.getItems().add(texto);
        }
        saldoLabel.setText(String.format("Saldo atual: R$ %.2f", controle.calcularSaldo()));
    }

    public static void main(String[] args) {
        launch();
    }
}
