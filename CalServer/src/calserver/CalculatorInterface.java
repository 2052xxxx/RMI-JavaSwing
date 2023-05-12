/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package calserver;

/**
 *
 * @author acer
 */

// Thêm các thư viện liên quan đến rmi

import java.rmi.Remote;          
import java.rmi.RemoteException; 

public interface CalculatorInterface extends Remote {
    double add(double x, double y) throws RemoteException;
    double subtract(double x, double y) throws RemoteException;
    double multiply(double x, double y) throws RemoteException;
    double divide(double x, double y) throws RemoteException;
    double percent(double x) throws RemoteException;
    double inverse(double x) throws RemoteException;
    double squareRoot(double x) throws RemoteException;
}

