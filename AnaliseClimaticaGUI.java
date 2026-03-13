import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AnaliseClimaticaGUI extends JFrame {
    private Map<String, double[]> dadosCidades;
    private JComboBox<String> comboCidades;
    private JTextArea areaResultado;

    public AnaliseClimaticaGUI() {
        configurarAparencia();
        inicializarDados();
        initUI();
    }

    private void configurarAparencia() {
        try {
            // Tenta definir o visual nativo do sistema operacional
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        setTitle("🌍 Analisador Climático Pro");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // --- Painel de Entrada ---
        JPanel painelTopo = new JPanel(new BorderLayout(10, 0));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel lblInstrucao = new JLabel("Selecione a Localidade para Análise:");
        lblInstrucao.setFont(new Font("SansSerif", Font.BOLD, 12));

        Vector<String> opcoes = new Vector<>();
        opcoes.add("Todas as Cidades"); // Adicionado conforme lógica do método executarAnalise
        opcoes.addAll(dadosCidades.keySet());

        comboCidades = new JComboBox<>(opcoes);
        comboCidades.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboCidades.addActionListener(e -> executarAnalise());

        painelTopo.add(lblInstrucao, BorderLayout.NORTH);
        painelTopo.add(comboCidades, BorderLayout.CENTER);

        // --- Painel de Resultados ---
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaResultado.setBackground(new Color(245, 245, 245));
        
        JScrollPane scroll = new JScrollPane(areaResultado);
        TitledBorder border = BorderFactory.createTitledBorder("Relatório Meteorológico");
        border.setTitleFont(new Font("SansSerif", Font.ITALIC, 12));
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 20, 20, 20),
                border
        ));

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Execução inicial
        executarAnalise();
    }

    private void inicializarDados() {
        dadosCidades = new HashMap<>();
        dadosCidades.put("São Paulo", new double[]{22, 23, 21, 19, 17, 16, 15, 17, 18, 20, 21, 22});
        dadosCidades.put("Londres", new double[]{5, 6, 8, 11, 14, 17, 19, 19, 16, 12, 9, 6});
        dadosCidades.put("Dubai", new double[]{19, 21, 24, 28, 33, 35, 37, 37, 35, 31, 26, 21});
    }

    private void executarAnalise() {
        String selecionado = (String) comboCidades.getSelectedItem();
        areaResultado.setText("");

        StringBuilder sb = new StringBuilder();
        if ("Todas as Cidades".equals(selecionado)) {
            dadosCidades.forEach((cidade, temps) -> sb.append(formatarResultado(cidade, temps)));
        } else if (selecionado != null) {
            sb.append(formatarResultado(selecionado, dadosCidades.get(selecionado)));
        }

        areaResultado.setText(sb.toString());
        areaResultado.setCaretPosition(0);
    }

    private String formatarResultado(String nome, double[] temps) {
        double soma = 0, max = temps[0], min = temps[0];
        for (double t : temps) {
            soma += t;
            if (t > max) max = t;
            if (t < min) min = t;
        }
        double media = soma / temps.length;

        // Gerar uma "barra" visual simples baseada na temperatura
        String termometro = "[" + "=".repeat(Math.max(0, (int) media / 2)) + ">";

        return String.format(
            "📍 CIDADE: %-15s\n" +
            "🌡️  Média: %5.1f°C %s\n" +
            "📉 Min: %7.1f°C | 📈 Max: %.1f°C\n" +
            "💡 Dica: %s\n" +
            "--------------------------------------------------\n",
            nome.toUpperCase(), media, termometro, min, max, obterSugestao(media)
        );
    }

    public String obterSugestao(double temp) {
        if (temp < 15) return "❄️  Clima frio. Use casacos pesados.";
        if (temp < 25) return "🌥️  Clima ameno. Roupas leves bastam.";
        return "☀️  Clima quente. Hidrate-se constantemente.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AnaliseClimaticaGUI().setVisible(true));
    }
}