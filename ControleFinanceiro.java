import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

public class ControleFinanceiro {
    List<Lancamento> lancamentos = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    // Coleta os dados para criar um novo objeto de Lancamento e colocar na lista
    public void createLancamento() {
        // Variáveis
        String descricao;
        BigDecimal valor;
        Lancamento.TipoLancamento tipo;
        Lancamento.Categoria categoria;
        String inform;
        System.out.println("\nInforme a DESCRIÇÃO do lançamento:");
        System.out.print("> ");
        inform = scanner.nextLine();
        while (inform.isBlank()) {
            // Não permite prosseguir enquanto estiver vazio
            System.out.println("\nA descrição não pode estar vazia.\nInforme a Descrição do lançamento:");
            System.out.print("> ");
            inform = scanner.nextLine();
        }
        // Atribui o valor obtido no scanner à variável
        descricao = inform;
        while (true) {
            System.out.println("\nInforme o VALOR do lançamento:");
            System.out.print("> ");
            inform = scanner.nextLine();
            try {
                // Checa se o número digitado é inteiro. Se ele não for, ele repete o processo
                BigDecimal valorScan = new BigDecimal(inform);
                if (valorScan.compareTo(BigDecimal.ZERO) > 0) {
                    valor = valorScan;
                    break;
                } else {
                    System.out.println("Valor deve ser maior que zero");
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Valor deve ser um número inteiro");
            }
        }
        loopLancamento: while (true) {
            System.out.println("\nTipos de Lançamento:");
            // Lista todos os elementos possíveis do enum
            for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                System.out.println(info);
            }
            System.out.print("\n> ");
            inform = scanner.nextLine();
            // Checa se o valor digitado corresponde a algum valor definido no enum
            for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                if (inform.toUpperCase().equals(info.toString())) {
                    tipo = info;
                    break loopLancamento;
                }
            }
            System.out.println("\nDeve ser um valor pertencente à lista\n");
        }
        loopCategoria: while (true) {
            System.out.println("\nTipos de Categoria:");
            // Lista todos os elementos possíveis do enum
            for (Lancamento.Categoria info : EnumSet.allOf(Lancamento.Categoria.class)) {
                System.out.println(info);
            }
            System.out.print("\n> ");
            inform = scanner.nextLine();
            // Checa se o valor digitado corresponde a algum valor definido no enum
            for (Lancamento.Categoria info : EnumSet.allOf(Lancamento.Categoria.class)) {
                if (inform.toUpperCase().equals(info.toString())) {
                    categoria = info;
                    break loopCategoria;
                }
            }
            System.out.println("\nDeve ser uma categoria pertencente à lista\n");
        }
        // Cria um novo objeto Lancamento, utilizando as variáveis definidas
        // anteriormente
        Lancamento lancamento = new Lancamento(descricao, valor, tipo, categoria);
        lancamento.setIndentificador(definirIdentificador());
        lancamento.mostrarDadosLancamento();
        lancamentos.add(lancamento);
        // scanner.close();
        // return lancamento;
    }

    public void listarLancamentos() {
        if (lancamentos.isEmpty()) { // Se não houver lancamentos registrados
            System.out.println("Não há lançamentos listados.");
        } else { // Se houver lancamentos registrados, ele mostra os dados de cada um da lista
            for (Lancamento l : lancamentos) {
                l.mostrarDadosLancamento();
            }
        }
        System.out.println();
    }

    public void consultarLancamentoPeloID() {
        loopConsulta: while (true) {
            if (checarSeAListaIsEmpty("")) // Verifica se lancamentos está vazio. Se sim, executa a função e sai do while
                break;
            System.out.println("\nInforme o identificador do lançamento que deseja consultar");
            mostrarOpcoesDeIdentificador();
            System.out.print("> ");
            String scanID = scanner.nextLine();
            try {
                int checkID = Integer.parseInt(scanID);
                for (Lancamento l : lancamentos) {          // Percorre os lancamentos e
                    if (l.getIndentificador() == checkID) { // se existir um lancamento igual ao digitado pelo usuário, mostra os dados
                        System.out.println("\nDados do lançamento com identificador \"" + scanID + "\":");
                        l.mostrarDadosLancamento();
                        break loopConsulta;
                    }
                }
                System.out.println("Não existem lançamentos listados com esse identificador (" + scanID + ")");
                break; // Se não existirem lancamentos com o ID fornecido

            } catch (java.lang.NumberFormatException e) {
                System.out.println("\nValor deve ser um número inteiro\n");
            }

        }
        System.out.println();
    }

    private int definirIdentificador() {
        int id = 0;
        for (Lancamento l : lancamentos) {
            if (l.getIndentificador() == id + 2) { // tem que lembrar o que isso faz
                break;
            } else if (l.getIndentificador() > id) { // isso também
                id = l.getIndentificador(); // Eu até sei o que faz, mas não lembro o porque ele faz isso assim.
            }
        }
        return ++id;
    }

    public void alterarDadosPeloIdentificador() {
        loopConsulta: while (true) {
            if (checarSeAListaIsEmpty("Não há lançamentos listados para alterar.")) // Se a lista tá vazia, isso impede de alterar o nada
                break;
            System.out.println("\nInforme o identificador do lançamento que deseja alterar");
            mostrarOpcoesDeIdentificador();
            System.out.print("> ");
            String scanID = scanner.nextLine();
            try {
                int checkID = Integer.parseInt(scanID);

                for (Lancamento l : lancamentos) {
                    if (l.getIndentificador() == checkID) {
                        Lancamento antigoLancamento = l; // Guarda o lancamento antigo para mostrar a diferença no final
                        String inform;
                        System.out.println("\nobs.: TODOS OS DADOS DEIXADOS VAZIOS SERÃO MANTIDOS INALTERADOS\n");
                        System.out.println("\nInforme outra DESCRIÇÃO para o lançamento (atual: \""
                                + l.getDescricao() + "\")"); // Daqui pra baixo tá bem intuitivo de entender o que cada coisa faz
                        System.out.print("> ");
                        inform = scanner.nextLine();
                        if (!inform.isBlank()) {
                            l.setDescricao(inform);
                        } else
                            System.out.println("Descrição \"" + l.getDescricao() + "\" mantida.");
                        while (true) {
                            System.out.println("\nInforme outro VALOR para o lançamento (atual: \""
                                    + l.getValor().toString() + "\")");
                            System.out.print("> ");
                            inform = scanner.nextLine();
                            try {
                                if (inform.isBlank()) {
                                    System.out.println("Valor \"" + l.getValor().toString() + "\" mantido.");
                                    break;
                                }
                                BigDecimal valorScan = new BigDecimal(Integer.parseInt(inform));
                                if (valorScan.compareTo(BigDecimal.ZERO) > 0) {
                                    l.setValor(valorScan);
                                    break;
                                } else {
                                    System.out.println("Valor deve ser maior que zero");
                                }
                            } catch (java.lang.NumberFormatException e) {
                                System.out.println("Valor deve ser um número decimal");
                            }
                        }
                        loopTipo: while (true) {
                            System.out.println("\nTipos de Lançamento:"); // Este for verifica os itens de dentro do enum
                            for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                                System.out.println(info); // Imprime cada item
                            }
                            System.out.println("\nInforme outro TIPO para o lançamento (atual: \""
                                    + l.getTipo().toString() + "\")");
                            System.out.print("\n> ");
                            inform = scanner.nextLine();
                            if (inform.isBlank()) {
                                System.out
                                        .println("Tipo de lançamento \"" + l.getTipo().toString() + "\" mantido.");
                                break;
                            }
                            for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                                if (inform.toUpperCase().equals(info.toString())) {
                                    l.setTipo(info);
                                    break loopTipo;
                                }
                            }
                            System.out.println("\nDeve ser um valor pertencente à lista\n");
                        }
                        loopCategoria: while (true) {
                            System.out.println("\nTipos de Categoria:"); // Parecido com que o for acima fez
                            for (Lancamento.Categoria info : EnumSet.allOf(Lancamento.Categoria.class)) {
                                System.out.println(info);
                            }
                            System.out.println("\nInforme outra CATEGORIA para o lançamento (atual: \""
                                    + l.getCategoria().toString() + "\")");
                            System.out.print("\n> ");
                            inform = scanner.nextLine();
                            if (inform.isBlank()) {
                                System.out.println("Categoria \"" + l.getCategoria().toString() + "\" mantida.");
                                break;
                            }
                            for (Lancamento.Categoria info : EnumSet.allOf(Lancamento.Categoria.class)) {
                                if (inform.toUpperCase().equals(info.toString())) {
                                    l.setCategoria(info);
                                    break loopCategoria;
                                }
                            }
                            System.out.println("\nDeve ser uma categoria pertencente à lista\n");
                        }
                        l.dataAlterada(); // Define uma data de alteração para esse lancamento
                        System.out.println("\nDados antigos:");
                        antigoLancamento.mostrarDadosLancamento();
                        System.out.println("Dados atualizados:");
                        l.mostrarDadosLancamento();

                        break loopConsulta;
                    }
                }
                System.out.println("Não existem lançamentos listados com esse identificador (" + scanID + ")");
                break;

            } catch (java.lang.NumberFormatException e) {
                System.out.println("\nValor deve ser um número inteiro\n");
            }

        }
        System.out.println();
    }

    private void mostrarOpcoesDeIdentificador() {
        System.out.println("Identificadores disponíveis:");
        for (Lancamento l : lancamentos) { // Mostra dos ids dos lancamentos concatenados com sua respectiva descrição
            System.out.println(l.getIndentificador() + ". " + l.getDescricao());
        }
    }

    public void excluirLancamentoPeloID() {
        loopConsulta: while (true) {
            if (checarSeAListaIsEmpty("Não há lançamentos listados para excluir"))
                break;
            System.out.println("\nInforme o identificador do lançamento que deseja EXCLUIR");
            mostrarOpcoesDeIdentificador();
            System.out.print("> ");
            String scanID = scanner.nextLine();
            try {
                int checkID = Integer.parseInt(scanID);
                for (Lancamento l : lancamentos) {
                    if (l.getIndentificador() == checkID) {
                        System.out.println("\nDados do lançamento com identificador \"" + scanID + "\":");
                        l.mostrarDadosLancamento();
                        while (true) {
                            System.out.println("Tem certeza de que quer excluir?");
                            try {
                                System.out.println("\n0 - Não excluir\n1 - Excluir");
                                System.out.print("> ");
                                String scanExcluir = scanner.nextLine();
                                if (scanExcluir.isBlank()) {
                                    System.out.println("Operação cancelada.");
                                    break loopConsulta;
                                }
                                switch (Integer.parseInt(scanExcluir)) {
                                    case 1:
                                        System.out.println("Dados excluidos.");
                                        // Exclui a opção da lista equivalente ao ID
                                        lancamentos.remove(l);
                                        break loopConsulta;
                                    default:
                                        System.out.println("Dados mantidos.");
                                        break loopConsulta;
                                }
                            } catch (java.lang.NumberFormatException e) {
                                System.out.println("Deve ser um número inteiro");
                            }
                        }
                    }
                }
                System.out.println("Não existem lançamentos listados com esse identificador (" + scanID + ")");
                break;

            } catch (java.lang.NumberFormatException e) {
                System.out.println("\nValor deve ser um número inteiro\n");
            }
        }
        System.out.println();
    }

    private boolean checarSeAListaIsEmpty(String text) {
        if (!text.isBlank() && lancamentos.isEmpty()) { // Checa se a lista é vazia
            System.out.println(text); // Se o parâmetro for preenchido com um valor diferente de blank, ele imprime a mensagem personalizada
            return true;    // Retorna que a lista está vazia
        } else if (lancamentos.isEmpty()) {
            System.out.println("Não há lançamentos listados.");
            return true;    // Retorna que a lista está vazia
        }
        return false;       // Retorna que a lista NÃO está vazia
    }

    public void filtrarPorTipo()
    {
        String inform;
        loopLancamento: while (true) {
            System.out.println("\nFiltrar por Tipos de Lançamento");
            System.out.println("\nTIPOS DE LANÇAMENTO:");
            // Lista todos os elementos possíveis do enum
            for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                System.out.println(info);
            }
            System.out.print("\n> ");
            inform = scanner.nextLine();
            // Checa se o valor digitado no inform corresponde a algum valor definido no enum
            for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                if (inform.toUpperCase().equals(info.toString())) {
                    System.out.println("Lançamentos do tipo \"" + info.toString() + "\"");
                    for (Lancamento l : lancamentos)
                    {
                        if (l.getTipo() == info)
                        {
                            l.mostrarDadosLancamento();
                        }
                    }
                    break loopLancamento;
                }
            }
            System.out.println("\nDeve ser um valor pertencente à lista\n");
        }
    }
}