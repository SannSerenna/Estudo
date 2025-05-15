import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraAvancadaComTeclado extends JFrame implements ActionListener, KeyListener {

    private JTextField campoTexto;
    private JTextArea historicoTexto;
    private double numero1, numero2, resultado;
    private char operador;
    private boolean operacaoSelecionada = false;

    public CalculadoraAvancadaComTeclado() {
        setTitle("Calculadora Avançada");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        campoTexto = new JTextField();
        campoTexto.setEditable(false);
        campoTexto.setFont(new Font("Arial", Font.BOLD, 24));
        campoTexto.addKeyListener(this);
        add(campoTexto, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(5, 4, 5, 5));
        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "√", "^", "%"
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 20));
            botao.addActionListener(this);
            painelBotoes.add(botao);
        }

        add(painelBotoes, BorderLayout.CENTER);

        historicoTexto = new JTextArea(5, 20);
        historicoTexto.setEditable(false);
        historicoTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(historicoTexto);
        add(scroll, BorderLayout.SOUTH);

        setFocusable(true);
        addKeyListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        processarComando(comando);
    }

    private void processarComando(String comando) {
        if (comando.matches("[0-9\\.]")) {
            if (operacaoSelecionada) {
                campoTexto.setText("");
                operacaoSelecionada = false;
            }
            campoTexto.setText(campoTexto.getText() + comando);
        } else if (comando.equals("C")) {
            campoTexto.setText("");
            numero1 = numero2 = resultado = 0;
        } else if (comando.equals("=")) {
            try {
                numero2 = Double.parseDouble(campoTexto.getText());
                switch (operador) {
                    case '+': resultado = numero1 + numero2; break;
                    case '-': resultado = numero1 - numero2; break;
                    case '*': resultado = numero1 * numero2; break;
                    case '/':
                        if (numero2 == 0) {
                            campoTexto.setText("Erro");
                            return;
                        }
                        resultado = numero1 / numero2;
                        break;
                    case '^': resultado = Math.pow(numero1, numero2); break;
                    case '%': resultado = (numero1 * numero2) / 100; break;
                    default: return;
                }
                campoTexto.setText(String.valueOf(resultado));
                historicoTexto.append(numero1 + " " + operador + " " + numero2 + " = " + resultado + "\n");
            } catch (NumberFormatException ex) {
                campoTexto.setText("Erro");
            }
        } else if (comando.equals("√")) {
            try {
                numero1 = Double.parseDouble(campoTexto.getText());
                resultado = Math.sqrt(numero1);
                campoTexto.setText(String.valueOf(resultado));
                historicoTexto.append("√" + numero1 + " = " + resultado + "\n");
            } catch (NumberFormatException ex) {
                campoTexto.setText("Erro");
            }
        } else {
            try {
                numero1 = Double.parseDouble(campoTexto.getText());
                operador = comando.charAt(0);
                operacaoSelecionada = true;
            } catch (NumberFormatException ex) {
                campoTexto.setText("Erro");
            }
        }
    }

    // KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        char tecla = e.getKeyChar();

        if (Character.isDigit(tecla) || tecla == '.') {
            processarComando(String.valueOf(tecla));
        } else if ("+-*/^%".indexOf(tecla) >= 0) {
            processarComando(String.valueOf(tecla));
        } else if (tecla == '\n') { // Enter
            processarComando("=");
        } else if (tecla == 'r') { // raiz
            processarComando("√");
        } else if (tecla == 27) { // ESC
            processarComando("C");
        } else if (tecla == '\b') { // backspace
            String atual = campoTexto.getText();
            if (!atual.isEmpty()) {
                campoTexto.setText(atual.substring(0, atual.length() - 1));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraAvancadaComTeclado::new);
    }
}
