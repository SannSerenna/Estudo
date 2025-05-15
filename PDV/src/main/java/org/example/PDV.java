package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class PDV extends JFrame {
    private JTextField campoCodigo;
    private JTextField campoQuantidade;
    private JTextField campoPago;
    private JLabel labelTotal;
    private JLabel labelTroco;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> comboPagamento;

    private Map<String, Produto> produtos = new HashMap<>();
    private List<ItemVenda> carrinho = new ArrayList<>();

    public PDV() {
        setTitle("PDV Simples");
        setSize(700, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Produtos cadastrados
        produtos.put("001", new Produto("001", "Coca-Cola 350ml", 5.00));
        produtos.put("002", new Produto("002", "Água Mineral 500ml", 3.00));
        produtos.put("003", new Produto("003", "Sanduíche Natural", 8.50));
        produtos.put("004", new Produto("004", "Chips de Batata", 4.00));

        // Painel topo
        JPanel painelTopo = new JPanel();
        painelTopo.add(new JLabel("Código:"));
        campoCodigo = new JTextField(5);
        painelTopo.add(campoCodigo);

        painelTopo.add(new JLabel("Qtd:"));
        campoQuantidade = new JTextField("1", 3);
        painelTopo.add(campoQuantidade);

        JButton btnAdicionar = new JButton("Adicionar");
        painelTopo.add(btnAdicionar);
        add(painelTopo, BorderLayout.NORTH);

        // Tabela de produtos
        String[] colunas = {"Código", "Produto", "Qtd", "Preço Unit.", "Subtotal"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel rodapé
        JPanel painelRodape = new JPanel(new GridLayout(4, 4));

        painelRodape.add(new JLabel("Total:"));
        labelTotal = new JLabel("R$ 0.00");
        painelRodape.add(labelTotal);

        painelRodape.add(new JLabel("Pago:"));
        campoPago = new JTextField("0.00");
        painelRodape.add(campoPago);

        painelRodape.add(new JLabel("Troco:"));
        labelTroco = new JLabel("R$ 0.00");
        painelRodape.add(labelTroco);

        painelRodape.add(new JLabel("Pagamento:"));
        String[] meiosPagamento = {"Dinheiro", "Débito", "Crédito", "Pix"};
        comboPagamento = new JComboBox<>(meiosPagamento);
        painelRodape.add(comboPagamento);

        painelRodape.add(new JLabel()); // Espaço
        JButton btnFinalizar = new JButton("Finalizar Venda");
        painelRodape.add(btnFinalizar);

        JButton btnLimpar = new JButton("Limpar Carrinho");
        painelRodape.add(btnLimpar);

        add(painelRodape, BorderLayout.SOUTH);

        // Ações
        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnFinalizar.addActionListener(e -> finalizarVenda());
        btnLimpar.addActionListener(e -> limparCarrinho());

        campoPago.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularTroco();
            }
        });

        setVisible(true);
    }

    private void adicionarProduto() {
        String codigo = campoCodigo.getText().trim();
        int qtd;
        try {
            qtd = Integer.parseInt(campoQuantidade.getText().trim());
            if (qtd <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            return;
        }

        Produto p = produtos.get(codigo);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Produto não encontrado.");
            return;
        }

        boolean encontrado = false;
        for (ItemVenda item : carrinho) {
            if (item.getProduto().getCodigo().equals(codigo)) {
                item.setQuantidade(item.getQuantidade() + qtd);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            carrinho.add(new ItemVenda(p, qtd));
        }

        atualizarTabela();
        calcularTotal();
        campoCodigo.setText("");
        campoQuantidade.setText("1");
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (ItemVenda item : carrinho) {
            Produto p = item.getProduto();
            int qtd = item.getQuantidade();
            double subtotal = p.getPreco() * qtd;
            modeloTabela.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNome(),
                    qtd,
                    String.format("R$ %.2f", p.getPreco()),
                    String.format("R$ %.2f", subtotal)
            });
        }
    }

    private void calcularTotal() {
        double total = carrinho.stream()
                .mapToDouble(i -> i.getProduto().getPreco() * i.getQuantidade())
                .sum();
        labelTotal.setText(String.format("R$ %.2f", total));
        calcularTroco();
    }

    private void calcularTroco() {
        try {
            double pago = Double.parseDouble(campoPago.getText().replace(",", ".").trim());
            double total = carrinho.stream()
                    .mapToDouble(i -> i.getProduto().getPreco() * i.getQuantidade())
                    .sum();
            double troco = pago - total;
            labelTroco.setText(String.format("R$ %.2f", troco > 0 ? troco : 0));
        } catch (NumberFormatException e) {
            labelTroco.setText("R$ 0.00");
        }
    }

    private void finalizarVenda() {
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carrinho vazio!");
            return;
        }

        double pago;
        double total = carrinho.stream()
                .mapToDouble(i -> i.getProduto().getPreco() * i.getQuantidade())
                .sum();
        try {
            pago = Double.parseDouble(campoPago.getText().replace(",", ".").trim());
            if (pago < total) {
                JOptionPane.showMessageDialog(this, "Valor pago insuficiente.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor pago inválido.");
            return;
        }

        String formaPagamento = (String) comboPagamento.getSelectedItem();
        double troco = pago - total;

        JOptionPane.showMessageDialog(this,
                "Venda finalizada!\n" +
                        "Pagamento: " + formaPagamento + "\n" +
                        "Troco: R$ " + String.format("%.2f", troco > 0 ? troco : 0));

        limparCarrinho();
    }

    private void limparCarrinho() {
        carrinho.clear();
        atualizarTabela();
        labelTotal.setText("R$ 0.00");
        campoPago.setText("0.00");
        labelTroco.setText("R$ 0.00");
    }

    public static void main(String[] args) {
        new PDV();
    }

    // Classes internas
    static class Produto {
        private String codigo;
        private String nome;
        private double preco;

        public Produto(String codigo, String nome, double preco) {
            this.codigo = codigo;
            this.nome = nome;
            this.preco = preco;
        }

        public String getCodigo() { return codigo; }
        public String getNome() { return nome; }
        public double getPreco() { return preco; }
    }

    static class ItemVenda {
        private Produto produto;
        private int quantidade;

        public ItemVenda(Produto produto, int quantidade) {
            this.produto = produto;
            this.quantidade = quantidade;
        }

        public Produto getProduto() { return produto; }
        public int getQuantidade() { return quantidade; }
        public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    }
}
