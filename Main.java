import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("======================================\nSISTEMA DE CONTROLE FINANCEIRO PESSOAL\n======================================\n");
        Main menu = new Main();
        menu.menu();
        System.out.println("\nPROGRAMA ENCERRADO\n");
    }

    public void menu()
    {
        Scanner scanner = new Scanner(System.in);
        ControleFinanceiro controleFinanceiro = new ControleFinanceiro();

        loop: while (true) {
            System.out.println("COMANDOS:\n1 - Cadastrar lançamento\n2 - Listar lançamentos\n3 - Consultar por identificador\n4 - Alterar lançamento\n5 - Excluir lançamento\n6 - Filtrar por tipo\n7 - Consultar por período\n8 - Exibir resumo financeiro\n0 - Sair\n");
            System.out.print("> ");
            String cmd = scanner.nextLine();
            try
            {
                switch (Integer.parseInt(cmd))
                {
                    case 1:
                        controleFinanceiro.createLancamento();
                        break;
                    case 2:
                        controleFinanceiro.listarLancamentos();
                        break;
                    case 3:
                        controleFinanceiro.consultarLancamentoPeloID();
                        break;
                    case 4:
                        controleFinanceiro.alterarDadosPeloIdentificador();
                        break;
                    case 5:
                        System.out.println("\nComando inexistente\n");
                        break;
                    case 6:
                        System.out.println("\nComando inexistente\n");
                        break;
                    case 7:
                        System.out.println("\nComando inexistente\n");
                        break;
                    case 8:
                        System.out.println("\nComando inexistente\n");
                        break;
                    case 0:
                        break loop;
                    default:
                        System.out.println("\nComando inexistente\n");
                        continue loop;
                }
            }
            catch (java.lang.NumberFormatException e)
            {
                System.out.println("\nFormato do comando inválido (deve ser um número inteiro)\n");
                continue loop;
            }

        }
    }
}