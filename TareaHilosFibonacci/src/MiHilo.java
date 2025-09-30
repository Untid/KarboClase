import java.util.ArrayList;
import java.util.List;

public class MiHilo implements Runnable{
   // private List<MiHilo> FibonacciList = new ArrayList();


    private static int primerNumero = 0;
    private static int segundoNumero = 1;

    // Ecuación Fibonacci F(n) = F(n-1) + F(n-2)
    @Override
    public void run() {

        for (int i=0;i<15000;i++){
//            Fibonacci = (i-1) + (i - 2);
//
//            System.out.println(Fibonacci);

            if ( i==1){
                System.out.println(primerNumero+" ");
            }
            if (i ==2){
                System.out.println(segundoNumero+" ");
            }
            int siguienteNumero = primerNumero + segundoNumero;
            System.out.print(siguienteNumero + " ");


            primerNumero = segundoNumero;
            segundoNumero = siguienteNumero;

            

            try {
                Thread.sleep(1000);

            }catch (InterruptedException e){
                e.getMessage();
            }
            System.out.println();
        }
    }
}
