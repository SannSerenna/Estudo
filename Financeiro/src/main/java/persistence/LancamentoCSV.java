package persistence;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LancamentoCSV {
    private static final String FILE_PATH = "src/main/data/lancamentos.csv";

    public static void salvar(List<Lancamento> lancamentos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Lancamento l : lancamentos) {
                if (l instanceof Despesa) {
                    writer.printf("%s;%s;%.2f;%s;%s\n", l.getTipo(), l.getDescricao(), l.getValor(),
                            l.getData(), ((Despesa) l).getCategoria());
                } else {
                    writer.printf("%s;%s;%.2f;%s;\n", l.getTipo(), l.getDescricao(), l.getValor(),
                            l.getData());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Lancamento> carregar() {
        List<Lancamento> lista = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                String tipo = partes[0];
                String descricao = partes[1];
                double valor = Double.parseDouble(partes[2]);
                LocalDate data = LocalDate.parse(partes[3]);

                if (tipo.equalsIgnoreCase("Receita")) {
                    lista.add(new Receita(descricao, valor, data));
                } else if (tipo.equalsIgnoreCase("Despesa")) {
                    String categoria = partes.length > 4 ? partes[4] : "Outros";
                    lista.add(new Despesa(descricao, valor, data, categoria));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
