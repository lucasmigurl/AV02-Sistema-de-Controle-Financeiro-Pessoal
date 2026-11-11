import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

public class ControleFinanceiro {
    List<Lancamento> lancamentos = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void createLancamento() {
        String descricao;
        BigDecimal valor;
        Lancamento.TipoLancamento tipo;
        Lancamento.Categoria categoria;
        String inform;
        System.out.println("\nInforme a DESCRIÇÃO do lançamento:");
        System.out.print("> ");
        inform = scanner.nextLine();
        while (inform.isBlank()) {
            System.out.println("\nA descrição não pode estar vazia.\nInforme a Descrição do lançamento:");
            System.out.print("> ");
            inform = scanner.nextLine();
        }
        descricao = inform;
        do {
            System.out.println("\nInforme o VALOR do lançamento:");
            System.out.print("> ");
            inform = scanner.nextLine();
            try {
                BigDecimal valorScan = new BigDecimal(Integer.parseInt(inform));
                if (valorScan.compareTo(BigDecimal.ZERO) > 0) {
                    valor = valorScan;
                    break;
                } else {
                    System.out.println("Valor deve ser maior que zero");
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Valor deve ser um número inteiro");
            }
        } while (true);
        loopLancamento: while (true) {
            System.out.println("\nTipos de Lançamento:");
            for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                System.out.println(info);
            }
            System.out.print("\n> ");
            inform = scanner.nextLine();
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
            for (Lancamento.Categoria info : EnumSet.allOf(Lancamento.Categoria.class)) {
                System.out.println(info);
            }
            System.out.print("\n> ");
            inform = scanner.nextLine();
            for (Lancamento.Categoria info : EnumSet.allOf(Lancamento.Categoria.class)) {
                if (inform.toUpperCase().equals(info.toString())) {
                    categoria = info;
                    break loopCategoria;
                }
            }
            System.out.println("\nDeve ser uma categoria pertencente à lista\n");
        }

        Lancamento lancamento = new Lancamento(descricao, valor, tipo, categoria);
        lancamento.setIndentificador(definirIdentificador());
        lancamento.mostrarDadosLancamento();
        lancamentos.add(lancamento);
        // scanner.close();
        // return lancamento;
    }

    public void listarLancamentos() {
        if (lancamentos.isEmpty()) {
            System.out.println("Não há lançamentos listados.");
        } else {
            for (Lancamento l : lancamentos) {
                l.mostrarDadosLancamento();
            }
        }
        System.out.println();
    }

    public void consultarLancamentoPeloID() {
        loopConsulta: while (true) {
            System.out.println("\nInforme o identificador do lançamento que deseja consultar");
            System.out.print("> ");
            String scanID = scanner.nextLine();
            try {
                int checkID = Integer.parseInt(scanID);
                if (lancamentos.isEmpty()) {
                    System.out.println("Não há lançamentos listados.");
                } else {
                    for (Lancamento l : lancamentos) {
                        if (l.getIndentificador() == checkID) {
                            System.out.println("\nDados do lançamento com identificador \"" + scanID + "\":");
                            l.mostrarDadosLancamento();
                            break loopConsulta;
                        }
                    }
                    System.out.println("Não existem lançamentos listados com esse identificador (" + scanID + ")");
                    break;
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println("\nValor deve ser um número inteiro\n");
            }

        }
        System.out.println();
    }

    public int definirIdentificador() {
        int id = 0;
        for (Lancamento l : lancamentos) {
            if (l.getIndentificador() == id + 2) {
                break;
            } else if (l.getIndentificador() > id) {
                id = l.getIndentificador();
            }
        }
        return ++id;
    }

    public void alterarDadosPeloIdentificador() {
        loopConsulta: while (true) {
            System.out.println("\nInforme o identificador do lançamento que deseja alterar");
            System.out.print("> ");
            String scanID = scanner.nextLine();
            try {
                int checkID = Integer.parseInt(scanID);
                if (lancamentos.isEmpty()) {
                    System.out.println("Não há lançamentos listados.");
                } else {
                    for (Lancamento l : lancamentos) {
                        if (l.getIndentificador() == checkID) {
                            Lancamento antigoLancamento = l;
                            String inform;
                            System.out.println("\nobs.: TODOS OS DADOS DEIXADOS VAZIOS SERÃO MANTIDOS\n");
                            System.out.println("\nInforme outra DESCRIÇÃO para o lançamento (atual: \"" + l.getDescricao() + "\")");
                            System.out.print("> ");
                            inform = scanner.nextLine();
                            if (!inform.isBlank()) {
                                l.setDescricao(inform);
                            } else
                                System.out.println("Descrição \"" + l.getDescricao() + "\" mantida.");
                            while (true) {
                                System.out.println("\nInforme outro VALOR para o lançamento (atual: \"" + l.getValor().toString() + "\")");
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
                                    System.out.println("Valor deve ser um número inteiro");
                                }
                            }
                            loopLancamento: while (true) {
                                System.out.println("\nTipos de Lançamento:");
                                for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                                    System.out.println(info);
                                }
                                System.out.println("\nInforme outro TIPO para o lançamento (atual: \"" + l.getTipo().toString() + "\")");
                                System.out.print("\n> ");
                                inform = scanner.nextLine();
                                if (inform.isBlank()) {
                                    System.out.println("Tipo de lançamento \"" + l.getTipo().toString() + "\" mantido.");
                                    break;
                                }
                                for (Lancamento.TipoLancamento info : EnumSet.allOf(Lancamento.TipoLancamento.class)) {
                                    if (inform.toUpperCase().equals(info.toString())) {
                                        l.setTipo(info);
                                        break loopLancamento;
                                    }
                                }
                                System.out.println("\nDeve ser um valor pertencente à lista\n");
                            }
                            loopCategoria: while (true) {
                                System.out.println("\nTipos de Categoria:");
                                for (Lancamento.Categoria info : EnumSet.allOf(Lancamento.Categoria.class)) {
                                    System.out.println(info);
                                }
                                System.out.println("\nInforme outra CATEGORIA para o lançamento (atual: \"" + l.getCategoria().toString() + "\")");
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
                            l.dataAlterada();
                            System.out.println("\nDados antigos:");
                            antigoLancamento.mostrarDadosLancamento();
                            System.out.println("Dados atualizados:");
                            l.mostrarDadosLancamento();

                            break loopConsulta;
                        }
                    }
                    System.out.println("Não existem lançamentos listados com esse identificador (" + scanID + ")");
                    break;
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println("\nValor deve ser um número inteiro\n");
            }

        }
        System.out.println();
    }
}
