/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.simeasy8.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.List;

/**
 *
 * @author MEIRKA
 */
@Parameters(separators = "=")
public class CommandFile {
    @Parameter(description = "Record changes to the repository")
    public List<String> fileName;
    
    
    
}
