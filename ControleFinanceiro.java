import java.math.BigDecimal;
import java.time.LocalDate;
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
            if (checarSeAListaIsEmpty("")) // Verifica se lancamentos está vazio. Se sim, executa a função e sai do
                                           // while
                break;
            System.out.println("\nInforme o identificador do lançamento que deseja consultar");
            mostrarOpcoesDeIdentificador();
            System.out.print("> ");
            String scanID = scanner.nextLine();
            try {
                int checkID = Integer.parseInt(scanID);
                for (Lancamento l : lancamentos) { // Percorre os lancamentos e
                    if (l.getIndentificador() == checkID) { // se existir um lancamento igual ao digitado pelo usuário,
                                                            // mostra os dados
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
            if (checarSeAListaIsEmpty("Não há lançamentos listados para alterar.")) // Se a lista tá vazia, isso impede
                                                                                    // de alterar o nada
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
                                + l.getDescricao() + "\")"); // Daqui pra baixo tá bem intuitivo de entender o que cada
                                                             // coisa faz
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
                            System.out.println("\nTipos de Lançamento:"); // Este for verifica os itens de dentro do
                                                                          // enum
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
            System.out.println(text); // Se o parâmetro for preenchido com um valor diferente de blank, ele imprime a
                                      // mensagem personalizada
            return true; // Retorna que a lista está vazia
        } else if (lancamentos.isEmpty()) {
            System.out.println("Não há lançamentos listados.\n");
            return true; // Retorna que a lista está vazia
        }
        return false; // Retorna que a lista NÃO está vazia
    }

    public void filtrarPorTipo() {
        String inform;
        loopLancamento: while (true) {
            if (checarSeAListaIsEmpty(""))
                break;
            System.out.println("\nFiltrar por Tipos de Lançamento");
            System.out.println("\nTIPOS DE LANÇAMENTO:");
            // Lista todos os elementos possíveis do enum
            for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                System.out.println(info);
            }
            System.out.print("\n> ");
            inform = scanner.nextLine();
            // Checa se o valor digitado no inform corresponde a algum valor definido no
            // enum
            for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                if (inform.toUpperCase().equals(info.toString())) {
                    System.out.println("Lançamentos do tipo \"" + info.toString() + "\"");
                    for (Lancamento l : lancamentos) {
                        if (l.getTipo() == info) {
                            l.mostrarDadosLancamento(); // Se é do tipo selecionado, mostra os dados de lançamento
                        }
                    }
                    break loopLancamento;
                }
            }
            System.out.println("\nDeve ser um valor pertencente à lista\n");
        }
    }

    public void consultarPorPeriodo() {
        while (true) {
            if (checarSeAListaIsEmpty("Não há lançamentos listados\n"))
                break;
            LocalDate periodoMin = LocalDate.of(1970, 01, 01);
            LocalDate periodoMax = LocalDate.now();
            System.out.println(
                    "\nobs.: DIAS E MESES DEIXADOS VAZIOS SERÃO LEVADOS PARA O EXTREMO\nex.: campo dia vazio na opção de período mínimo ou máximo -> 01 ou 31, respectivamente");
            while (true) {
                System.out.println("\nDigite o ANO MÍNIMO desejado");
                System.out.print("> ");
                try {
                    periodoMin = periodoMin.withYear(Integer.parseInt(scanner.nextLine()));
                    break;
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("\nValor deve ser um número inteiro.\n");
                }
            }
            while (true) {
                System.out.println("\nDigite o MÊS MÍNIMO desejado");
                System.out.print("> ");
                try {
                    String mesMin = scanner.nextLine();
                    if (mesMin.isBlank()) {
                        periodoMin = periodoMin.withMonth(01);
                        System.out.print("1");
                    } else
                        periodoMin = periodoMin.withMonth(Integer.parseInt(mesMin));
                    break;
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("\nValor deve ser um número inteiro.\n");
                } catch (java.time.DateTimeException f) {
                    System.out.println("\nValor deve estar entre 1 e 12\n");
                }

            }
            while (true) {
                System.out.println("\nDigite o DIA MÍNIMO desejado");
                System.out.print("> ");
                try {
                    String diaMin = scanner.nextLine();
                    if (diaMin.isBlank()) {
                        periodoMin = periodoMin.withDayOfMonth(01);
                        System.out.print("1");
                    } else
                        periodoMin = periodoMin.withDayOfMonth(Integer.parseInt(diaMin));
                    break;
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("\nValor deve ser um número inteiro.\n");
                } catch (java.time.DateTimeException f) {
                    System.out.println("\nValor deve estar entre 1 e 28/31\n");
                }

            }
            while (true) {
                System.out.println("\nDigite o ANO MÁXIMO desejado");
                System.out.print("> ");
                try {
                    periodoMax = periodoMax.withYear(Integer.parseInt(scanner.nextLine()));
                    if (periodoMax.getYear() < periodoMin.getYear()) {
                        System.out.println(
                                "O ano máximo precisa ser maior ou igual do que o ano mínimo (" + periodoMin.getYear()
                                        + ")");
                    } else
                        break;
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("\nValor deve ser um número inteiro.\n");
                }
            }
            while (true) {
                System.out.println("\nDigite o MÊS MÁXIMO desejado");
                System.out.print("> ");
                try {
                    String mesMax = scanner.nextLine();
                    if (mesMax.isBlank()) {
                        periodoMax = periodoMax.withMonth(12);
                        System.out.print("12");
                    } else {
                        periodoMax = periodoMax.withMonth(Integer.parseInt(mesMax));
                        if (periodoMax.getYear() == periodoMin.getYear()
                                && periodoMax.getMonthValue() < periodoMin.getMonthValue()) {
                            System.out.println(
                                    "O mês definido, relativo ao ano escolhidos, precisa ser maior ou igual do que o definido para a data mínima ("
                                            + periodoMin.getMonthValue() + ")");
                        } else
                            break;
                    }
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("\nValor deve ser um número inteiro.\n");
                } catch (java.time.DateTimeException f) {
                    System.out.println("\nValor deve estar entre 1 e 12\n");
                }

            }
            while (true) {
                System.out.println("\nDigite o DIA MÁXIMO desejado");
                System.out.print("> ");
                try {
                    String diaMax = scanner.nextLine();
                    if (diaMax.isBlank()) {
                        periodoMax = periodoMax.withDayOfMonth(31);
                        System.out.print("31");
                    } else {
                        periodoMax = periodoMax.withDayOfMonth(Integer.parseInt(diaMax));
                        if (periodoMax.getYear() == periodoMin.getYear()
                                && periodoMax.getMonthValue() == periodoMin.getMonthValue()
                                && periodoMax.getDayOfMonth() < periodoMin.getDayOfMonth()) { // Eu usei o atalho de
                                                                                              // formatação e ele virou
                                                                                              // esse monstro
                                                                                              // contorcionista
                            System.out.println(
                                    "O dia definido, relativo ao mês e ano escolhidos, precisa ser maior ou igual do que o definido para a data mínima ("
                                            + periodoMin.getDayOfMonth() + ")");
                        } else
                            break;
                    }
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("\nValor deve ser um número inteiro.\n");
                } catch (java.time.DateTimeException f) {
                    System.out.println("\nValor deve estar entre 1 e 28/31\n");
                }
            }
            // Quanta coisa
            boolean gotResultsAtThisRange = false;
            System.out.println();
            for (Lancamento l : lancamentos) {
                if (l.getRawData().toLocalDate().isAfter(periodoMin)
                        && l.getRawData().toLocalDate().isBefore(periodoMax)) {
                    l.mostrarDadosLancamento();
                    if (!gotResultsAtThisRange)
                        gotResultsAtThisRange = true;
                }
            }
            if (!gotResultsAtThisRange) {
                System.out.println(
                        "\nNenhum lançamento registrado entre as datas " + Lancamento.formatLocalDate(periodoMin)
                                + " e " + Lancamento.formatLocalDate(periodoMax) + "\n");
            }
            break;
        }

    }

    public void exibirResumoFinanceiro() {
        if (checarSeAListaIsEmpty("Não há lançamentos listados."))
            return; // Trocar os outros para return. Estava usando um while desnecessário com um
                    // break. Não lembrava que isso funcionava.
        BigDecimal receitas = new BigDecimal(0);
        int contadorReceita = 0;
        BigDecimal despesas = new BigDecimal(0);
        int contadorDespesa = 0;

        for (Lancamento l : lancamentos) {
            if (l.getTipo() == Lancamento.TipoLancamento.RECEITA) {
                receitas.add(l.getValor());
                contadorReceita++;
            } else // if (l.getTipo() == Lancamento.TipoLancamento.DESPESA)
            {
                despesas.add(l.getValor());
                contadorDespesa++;
            }
        }
        System.out.println("==============\nRESUMO FINANCEIRO\n==============");
        if (contadorReceita > 0) {
            if (contadorReceita == 1)
                System.out.println("Você tem 1 lançamento registrado como RECEITA");
            else
                System.out.println("Você tem " + contadorReceita + " lançamentos registrados como RECEITA");
        }
        System.out.println("Receita total: " + receitas);

        if (contadorDespesa > 0) {
            if (contadorDespesa == 1)
                System.out.println("Você tem 1 lançamento registrado como DESPESA");
            else
                System.out.println("Você tem " + contadorDespesa + " lançamentos registrados como DESPESA");
        }
        System.out.println("Despesa total: " + despesas);
        BigDecimal saldo = receitas.subtract(despesas);
        System.out.println("\nSaldo: " + (saldo));
        if (saldo.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("Você se encontra no \"VERDE/POSITIVO\"");
        }
        if (saldo.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Você se encontra no \"VERMELHO/NEGATIVO\"");
        }

    }
}