/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calserver;

/**
 *
 * @author acer
 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements CalculatorInterface {

    public CalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public double add(double x, double y) throws RemoteException {
        return x + y;
    }

    @Override
    public double subtract(double x, double y) throws RemoteException {
        return x - y;
    }

    @Override
    public double multiply(double x, double y) throws RemoteException {
        return x * y;
    }

    @Override
    public double divide(double x, double y) throws RemoteException {
        return x / y;
    }

    @Override
    public double percent(double x) throws RemoteException {
        return (x / 100);
    }

    @Override
    public double inverse(double x) throws RemoteException {
        return 1 / x;
    }

    @Override
    public double squareRoot(double x) throws RemoteException {
        return Math.sqrt(x);
    }
}