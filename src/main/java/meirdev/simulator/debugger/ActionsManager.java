/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.debugger;



/**
 *
 * @author MEIRKA
 */
public class ActionsManager {

  public static final Object SIM_ACTION_STEP_OVER = "stepOver";
  public static final Object SIM_ACTION_RUN_INTO_METHOD = "runIntoMethod";
  public static final Object SIM_ACTION_STEP_INTO = "stepInto";
  public static final Object SIM_ACTION_STEP_OUT = "stepOut";
  public static final Object SIM_ACTION_STEP_OPERATION = "stepOperation";
  public static final Object SIM_ACTION_CONTINUE = "continue";
  public static final Object SIM_ACTION_START = "start";
  public static final Object SIM_ACTION_KILL = "kill";
  public static final Object SIM_ACTION_MAKE_CALLER_CURRENT = "makeCallerCurrent";
  public static final Object SIM_ACTION_MAKE_CALLEE_CURRENT = "makeCalleeCurrent";
  public static final Object SIM_ACTION_PAUSE = "pause";
  public static final Object SIM_ACTION_RUN_TO_CURSOR = "runToCursor";
  public static final Object SIM_ACTION_POP_TOPMOST_CALL = "popTopmostCall";
  public static final Object SIM_ACTION_FIX = "fix";
  public static final Object SIM_ACTION_RESTART = "restart";
  public static final Object SIM_ACTION_TOGGLE_BREAKPOINT = "toggleBreakpoint";
  public static final Object SIM_ACTION_NEW_WATCH = "newWatch";
  public static final Object SIM_ACTION_EVALUATE = "evaluate";
}
