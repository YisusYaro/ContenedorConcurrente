
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Brazo implements Runnable{
    
    private Contenedor contenedor;
    public int id;
    public int totalPiezas;
    
    public Brazo(Contenedor contenedor, int id) throws IOException{
        this.contenedor = contenedor;
        this.id=id;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // System.out.println("Ingrese el id: ");
        // String ide = br.readLine(); 
        // this.id = Integer.parseInt(ide);
        System.out.println("Ingrese el total de piezas a tomar por el brazo "+id+": ");
        String piezas = br.readLine(); 
        this.totalPiezas = Integer.parseInt(piezas);
    }
    
    public void run() {
        int descargas=0;
        for(int i=0; i < totalPiezas; i++){
            contenedor.bandera[this.id] = true;
            int j=1000;
            //System.out.println("Hilo intentando entrar a seccion critica: " +this.id);
            if(this.id==0){
                contenedor.turno=1;
                j=1;
            }
            else{
                contenedor.turno=0;
                j=0;
            }
            while(contenedor.bandera[j] && contenedor.turno==j){
                //No hace nada
            }//El id de los brazo necesariamente es 0 y 1      
            //seccion critica            
            try{
                Thread.sleep( 400 );
            }catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }

            if(contenedor.numeroObjetos > 0){ //Evita que el brazo siga descargando cuando el contenedor esté vacío
                
                descargas++;
                System.out.print("Brazo "+id);
                System.out.print(" descarga pieza numero "+descargas);
                //System.out.print(". Total piezas a descargar: "+ totalPiezas);
                System.out.print(" de  "+contenedor.numeroObjetos);
                System.out.print(" en el contenedor  ");
                contenedor.descargarUnaPieza();
                System.out.println("");
            }else{
                System.out.println("El contenedor esta vacio.");
                System.exit(0);
            }   
            contenedor.bandera[this.id] = false;  
        }    
        if(contenedor.numeroObjetos > 0){
            //System.out.println("El contenedor aun tiene: "+contenedor.numeroObjetos+" piezas.");
        }
        System.out.println("Brazo "+id+" ha finalizado.");
    }
}