/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meidev.compiler;

/**
 *
 * @author MEIRKA
 */
public class ArgFormatError extends Exception{

    public ArgFormatError() {
    }
    public ArgFormatError(Exception e) {
        this.setStackTrace(e.getStackTrace()); //To change body of generated methods, choose Tools | Templates.
    }
    public ArgFormatError(NumberFormatException nfe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
