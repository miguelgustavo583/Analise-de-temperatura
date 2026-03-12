import java.util.*;

public class AnaliseClimatica {
    public static void main(String[] args) {
        Map<String, double[]> dadosCidades = new HashMap<>();
        dadosCidades.put("São Paulo", new double[]{22, 23, 21, 19, 17, 16, 15, 17, 18, 20, 21, 22});
        dadosCidades.put("Londres", new double[]{5, 6, 8, 11, 14, 17, 19, 19, 16, 12, 9, 6});
        dadosCidades.put("Dubai", new double[]{19, 21, 24, 28, 33, 35, 37, 37, 35, 31, 26, 21});

        System.out.println("--- Processamento de Dados e Conhecimento ---");

        for (String cidade : dadosCidades.keySet()) {
            double[] temps = dadosCidades.get(cidade);
            double soma = 0;

            for (double t : temps) soma += t;
            double mediaAnual = soma / temps.length;

            String sugestaoRoupa = obterSugestao(mediaAnual);

            System.out.printf("Cidade: %s\n", cidade);
            System.out.printf("  > Média Anual: %.2f°C\n", mediaAnual);
            System.out.printf("  > Recomendação: %s\n", sugestaoRoupa);
            System.out.println("-------------------------------------------");
        }
    }

    public static String obterSugestao(double temp) {
        if (temp < 15) return "Casacos pesados e roupas térmicas.";
        if (temp < 25) return "Roupas leves e talvez um casaco fino.";
        return "Roupas frescas, bermudas e muita hidratação.";
    }
}
