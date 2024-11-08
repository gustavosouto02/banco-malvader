package model;

import java.time.LocalDateTime;
import java.util.List;

public class Relatorio {
    private String tipo;
    private LocalDateTime dataGeracao;
    private List<String> dados;
    
    public void gerarRelatorioGeral(){
        System.out.println("Gerando relatorio geral...");
    }

    public void exportarParaExcel(){
        System.out.println("Exportando relatório para Excel...");
    }
    
}