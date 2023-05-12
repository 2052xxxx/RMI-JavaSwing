/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calserver;

/**
 *
 * @author acer
 */
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

public class CalServer {

    public static void main(String[] args) throws Exception {

        // create and export CalculatorImpl object
        CalculatorImpl calculator = new CalculatorImpl();

        // create registry and bind calculator object
        LocateRegistry.createRegistry(5000);
        Naming.rebind("rmi://localhost:5000/MyServices", calculator);
        System.out.println("Calculator Server is running...");
    }
}
