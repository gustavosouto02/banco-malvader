package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Relatorio implements Serializable {
	private static final long serialVersionUID = 1L;
    private String tipo;
    private LocalDateTime dataGeracao;
    private List<String> dados;
    
    public Relatorio(String tipo, LocalDateTime dataGeracao, List<String> dados) {
        this.tipo = tipo;
        this.dataGeracao = dataGeracao;
        this.dados = dados;
    }

    // Método para gerar o relatório geral (em CSV)
    public void gerarRelatorioGeral() {
        System.out.println("Gerando relatório geral...");
        // O nome do arquivo pode incluir a data e o tipo do relatório
        String nomeArquivo = "relatorio_" + tipo + "_" + dataGeracao.toString().replace(":", "_") + ".csv";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            // Escreve o cabeçalho do CSV
            writer.write("Tipo,Data,Geração,Dados");
            writer.newLine();

            // Escreve os dados do relatório
            writer.write(tipo + "," + dataGeracao.toString());
            for (String dado : dados) {
                writer.write("," + dado);
            }
            writer.newLine();
            
            System.out.println("Relatório geral gerado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao gerar o relatório: " + e.getMessage());
        }
    }

    // Método para exportar o relatório gerado para o Excel
    public void exportarParaExcel() {
        System.out.println("Exportando relatório para Excel...");
        
        // Caminho do arquivo gerado
        String nomeArquivo = "relatorio_" + tipo + "_" + dataGeracao.toString().replace(":", "_") + ".csv";
        File arquivo = new File(nomeArquivo);

        if (arquivo.exists()) {
            System.out.println("Arquivo CSV gerado com sucesso! Abra o arquivo no Excel.");
        } else {
            System.err.println("Erro: O arquivo não foi encontrado.");
        }
    }

    // Getters e Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public List<String> getDados() {
        return dados;
    }

    public void setDados(List<String> dados) {
        this.dados = dados;
    }
}
