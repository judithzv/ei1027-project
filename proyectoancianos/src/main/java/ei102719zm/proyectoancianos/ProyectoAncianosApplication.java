package ei102719zm.proyectoancianos;

import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ProyectoAncianosApplication {

  private static final Logger log = Logger.getLogger(ProyectoAncianosApplication.class.getName());

  public static void main(String[] args) {
     // Auto-configura l'aplicació
     new SpringApplicationBuilder(ProyectoAncianosApplication.class).run();
     
  }
  
}

