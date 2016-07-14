/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;



import meirdev.simulator.gui.interfaces.implementations.StackRetIMP;
import meirdev.simulator.logical.enums.RefType;
import java.util.Scanner;
import meirdev.simulator.gui.interfaces.ActivityPieceINT;
import meirdev.simulator.gui.interfaces.RACmdNewValueIMP;
import meirdev.simulator.gui.interfaces.RADecRaIMP;
import meirdev.simulator.gui.interfaces.RAIncRaIMP;
import meirdev.simulator.simeasy8.SimpleCmdDetails;
import meirdev.simulator.gui.interfaces.*;
import meirdev.simulator.gui.interfaces.implementations.SDInIMP;
import meirdev.simulator.gui.interfaces.implementations.SDOutIMP;
import meirdev.simulator.logical.enums.CmdType;
import meirdev.simulator.logical.enums.CompareType;
/**
 *
 * @author MEIRKA
 */
public class ActivityPieceFactory {
    public ActivityPieceINT getActivity(String cmdName, int secValue) {
        String cmdText = null;
        Scanner scanner = new Scanner(cmdName);
        scanner.useDelimiter(" ");
        String realInstruction = scanner.next();
        
        ActivityPieceINT activityPieceINT = new RACmdNewValueIMP(secValue);
        switch(realInstruction){
            case "MOVEI":
                activityPieceINT = new MovePutRaIMP(secValue);
                break;
            case "MOVE":
                String raValue = scanner.next();
                activityPieceINT = (raValue.startsWith("RA"))?new MovePutRaIMP(secValue, RefType.RT_REF):new MovePullRaIMP(secValue);
                break;
            case "INC":
                activityPieceINT = new RAIncRaIMP(secValue);
                break;
            case "DEC":
                 activityPieceINT = new RADecRaIMP(secValue);
                break;
            case "ADDI":
                activityPieceINT = new RAIncRaIMP(secValue, CmdType.CT_ADD);
                break;
            case "SUBI":
                activityPieceINT = new RADecRaIMP(secValue, CmdType.CT_ADD);
                break;
            case "ADD":
                raValue = scanner.next();
                activityPieceINT =new MoveAddPutRaImp(secValue);
                break;
            case "SUB":
                raValue = scanner.next();
                if (!raValue.startsWith("RA"))  {
                    throw new RuntimeException("Cannot get here.");
                }
                activityPieceINT =new MoveDecPutRaImp(secValue);
                break;
            case "COMPAREI":
                raValue = scanner.next();
                if (!raValue.startsWith("RA"))  {
                    throw new RuntimeException("Cannot get here.");
                }
                activityPieceINT = new RADecRaIMP(secValue, CmdType.CT_ADD);
                RADecRaIMP v = (RADecRaIMP)activityPieceINT;
                v.setCompType(CompareType.COMP_TYPE_Y);
                break;
            case "COMPARE":
                 raValue = scanner.next();
                if (!raValue.startsWith("RA"))  {
                    throw new RuntimeException("Cannot get here.");
                }
                MoveDecPutRaImp t = new MoveDecPutRaImp(secValue);
                t.setCompType(CompareType.COMP_TYPE_Y);
                activityPieceINT = t;
                break;                
            case "JUMP":
                activityPieceINT = new RACmdNewValueIMP(secValue);
                break;
            case "JLESS":
                activityPieceINT = new RACmdNewValueIMP(secValue);
                break;
            case "JGREATER":
                activityPieceINT = new RACmdNewValueIMP(secValue);
                break;
            case "JEQUAL":
                activityPieceINT = new RACmdNewValueIMP(secValue);
                break;
            case "PUSH":
                activityPieceINT = new RACmdNewValueIMP(secValue);
                break;
            case "POP":
                activityPieceINT = new RACmdNewValueIMP(secValue);
                break;
            case "CALL":
                activityPieceINT = new StackCallIMP(secValue);    
                break;
            case "OUT":
                activityPieceINT = new SDOutIMP(secValue);    
                break;
            case "IN":
                
                activityPieceINT = new SDInIMP(secValue);    
                break;                
            case "RET":
                activityPieceINT = new StackRetIMP(secValue);   
                break;
        }
        return activityPieceINT;
    }
}
