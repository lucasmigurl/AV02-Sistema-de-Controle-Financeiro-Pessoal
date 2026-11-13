import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Save {
    public ControleFinanceiro carregarLista() throws IOException {
        ControleFinanceiro cf = new ControleFinanceiro();
        try {
            Files.createFile(Path.of("lancamentos.csv"));
        } catch (java.nio.file.FileAlreadyExistsException e) {}
        
        Path arquivo = Path.of("lancamentos.csv");

        ArrayList<String> linhas = new ArrayList<>(Files.readAllLines(arquivo, StandardCharsets.UTF_8));
        DateTimeFormatter dataTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        for (String l : linhas) {
            String[] colunas = l.split(";", -1);
            Lancamento lancamento = new Lancamento(colunas[1], new BigDecimal(colunas[2]), Lancamento.TipoLancamento.valueOf(colunas[4]), Lancamento.Categoria.valueOf(colunas[5]));
            lancamento.setIndentificador(Integer.parseInt(colunas[0]));
            lancamento.setData(LocalDateTime.parse(colunas[3], dataTimeFormat));
            lancamento.setDataAlteracao(LocalDateTime.parse(colunas[6], dataTimeFormat));
            cf.lancamentos.add(lancamento);
        }
        return cf;
    }

    public void salvarLista(ControleFinanceiro cf) throws IOException {
        try {
            Files.createFile(Path.of("lancamentos.csv"));
        } catch (java.nio.file.FileAlreadyExistsException e) {}
        Path arquivo = Path.of("lancamentos.csv");

        List<String> linhas = new ArrayList<>();
        for (Lancamento l : cf.lancamentos) {
            linhas.add(l.getIndentificador() + ";" + l.getDescricao() + ";" + l.getValor() + ";" + l.getData() + ";"
                    + l.getTipo() + ";" + l.getCategoria() + ";" + l.getDataDeAlteracao());
        }

        Files.write(arquivo, linhas, StandardCharsets.UTF_8);
    }

    public void excluirLista(ControleFinanceiro cf) throws IOException
    {
        Files.deleteIfExists(Path.of("lancamentos.csv"));
        cf = new ControleFinanceiro();
    }
}