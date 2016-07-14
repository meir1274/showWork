/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.simeasy8.command;

import com.beust.jcommander.JCommander;
import java.util.Arrays;
import meirdev.simulator.simeasy8.AssemblerReader;
import meirdev.simulator.simeasy8.commands.CommandFile;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MEIRKA
 */
public class NewEmptyJUnitTest {

    public NewEmptyJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
        // Options available before commands
        AssemblerReader cm = new AssemblerReader();
        JCommander jc = new JCommander(cm);


        CommandFile commit = new CommandFile();
        jc.addCommand("commit", commit);

        jc.parse("commit", "A.java");

        Assert.assertEquals(jc.getParsedCommand(), "commit");

        Assert.assertEquals(commit.fileName, Arrays.asList("A.java"));
    }
}
