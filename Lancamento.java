
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lancamento
{
    enum TipoLancamento
    {
        RECEITA,
        DESPESA
    }
    enum Categoria
    {
        ALIMENTACAO,
        TRANSPORTE,
        MORADIA,
        SAUDE,
        EDUCACAO,
        LAZER,
        SALARIO,
        OUTROS
    }
    
    int indentificador;
    String descricao;
    BigDecimal valor;
    LocalDateTime data = LocalDateTime.now();
    TipoLancamento tipo;
    Categoria categoria;
    LocalDateTime dataDeModificao = LocalDateTime.now();
    DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    
    public Lancamento(String descricao, BigDecimal valor, TipoLancamento tipo, Categoria categoria)
    {
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
    }
    
    public int getIndentificador() {
        return indentificador;
    }

    public void setIndentificador(int indentificador) {
        this.indentificador = indentificador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getData() {
        return data.format(dataFormat);
    }
/*
    public void setData(LocalDate data.format(dataFormat)) {
        this.data.format(dataFormat) = data.format(dataFormat);
    }
*/
    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void mostrarDadosLancamento()
    {
        if (getDataDeAlteracao().equals(getData())) 
            System.out.println("\nID: " + indentificador + "\nDescrição: " + descricao + "\nValor: " + valor + "\nData de criação: " + getData() + "\nTipo: " + tipo + "\nCategoria: " + categoria + "\n");
        else
            System.out.println("\nID: " + indentificador + "\nDescrição: " + descricao + "\nValor: " + valor + "\nData de criação: " + getData() + "\nÚltima alteração: " + getDataDeAlteracao() + "\nTipo: " + tipo + "\nCategoria: " + categoria + "\n");
    }

    public String dataAlterada()
    {
        dataDeModificao = LocalDateTime.now();
        return dataDeModificao.format(dataFormat);
    }

    public String getDataDeAlteracao()
    {
        dataDeModificao = LocalDateTime.now();
        return dataDeModificao.format(dataFormat);
    }

}
