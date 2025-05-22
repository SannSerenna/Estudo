package controller;

import model.Lancamento;
import persistence.LancamentoCSV;

import java.util.ArrayList;
import java.util.List;

public class ControleFinanceiro {
    private List<Lancamento> lancamentos;

    public ControleFinanceiro() {
        lancamentos = LancamentoCSV.carregar();
    }

    public void adicionarLancamento(Lancamento l) {
        lancamentos.add(l);
    }

    public double calcularSaldo() {
        double saldo = 0;
        for (Lancamento l : lancamentos) {
            if (l.getTipo().equals("Receita")) {
                saldo += l.getValor();
            } else {
                saldo -= l.getValor();
            }
        }
        return saldo;
    }

    public List<Lancamento> listarLancamentos() {
        return lancamentos;
    }

    public void salvarDados() {
        LancamentoCSV.salvar(lancamentos);
    }
}
